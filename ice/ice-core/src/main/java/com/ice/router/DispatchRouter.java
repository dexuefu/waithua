package com.ice.router;

import com.ice.config.SystemValue;
import com.ice.config.SystemParameterNames;
import com.ice.context.IceContext;
import com.ice.context.IceRequestContext;
import com.ice.exception.IceErrorResponse;
import com.ice.exception.IceException;
import com.ice.exception.error.AsyncError;
import com.ice.exception.error.ExecuteError;
import com.ice.exception.error.MaxSourcesError;
import com.ice.exception.error.TimeoutError;
import com.ice.handler.ServiceMethodHandler;
import com.ice.request.IceRequest;
import com.ice.utils.IceBuilder;
import com.ice.utils.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 分发器
 * 
 * @date 2014-06-01
 */
@Service
public class DispatchRouter implements Router {

	// 上下文
	private IceContext context;

	@Resource
	private Dispatcher dispatcher;

	private CommonsMultipartResolver multipartResolver;
	
	@Resource
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {
		AsyncContext asyncContext = request.startAsync();
		
		if (multipartResolver.isMultipart(request)) {
			request = multipartResolver.resolveMultipart(request);
		}
		final String method = request.getParameter(SystemParameterNames.getMehod());
		final String version = request
				.getParameter(SystemParameterNames.getVersion());
		final boolean isJson = RequestUtil.getIsJson(request);
		asyncContext.setTimeout(2000L);
		asyncContext.addListener(new AsyncListener() {
			
			@Override
			public void onComplete(AsyncEvent event) throws IOException {
				logger.debug("completed {}#{}", method, version);
			}

			@Override
			public void onError(AsyncEvent event) throws IOException {
				logger.error("{}#{}", method, version);
				dispatcher.output(new ExecuteError(method, version), isJson,
						(HttpServletResponse) event.getAsyncContext().getResponse());

			}

			@Override
			public void onStartAsync(AsyncEvent event) throws IOException {
				logger.debug("startAsync {}#{}", method, version);
			}

			@Override
			public void onTimeout(AsyncEvent event) throws IOException {
				dispatcher.output(new TimeoutError(method, version), isJson,
						(HttpServletResponse) event.getAsyncContext().getResponse());

			}
		});
		try {
			ServiceMethodHandler serviceMethodHandler = context
					.getServiceMethodHandler(method, version);
			if (serviceMethodHandler==null) {
				logger.info("不存在的方法:{}#{}。", method, version);
				return;
			}
			// 执行超时时间
			int timeout = serviceMethodHandler.getServiceMethodValue()
					.getTimeout();
			if (timeout < 0) {
				// 默认
				timeout = SystemValue.getDefaultTimeout();
			} else if (timeout > SystemValue.getMaxTimeout()) {
				// 最大超时时间
				timeout = SystemValue.getMaxTimeout();
			}
			Future<?> future = threadPoolTaskExecutor.submit(new ServiceRunnable(request, response));
			while (!future.isDone()) {
				future.get(timeout, TimeUnit.SECONDS);
				asyncContext.complete();
			}
		} catch (RejectedExecutionException e) {
			logger.info("调用服务方法:{}#{}，超过最大资源限制，无法提供服务。", method, version);
			// 直接输出错误
			dispatcher.output(new MaxSourcesError(method, version), isJson,
					response);
		} catch (TimeoutException e) {
			logger.info("调用服务方法:{}#{}，服务调用超时。", method, version);
			dispatcher.output(new TimeoutError(method, version), isJson,
					response);
		} catch (Throwable throwable) {
			// 产生未知的错误
			logger.info("调用服务方法:{}#{}，产生异常", method, version, throwable);
			throw new IceException(String.format("调用服务方法:%s#%s，产生异常", method, version));
		}
	}

	// 初始化
	@Override
	public void init(ServletConfig servletConfig,
			ApplicationContext applicationContext) {
		this.context = new IceContext(applicationContext);
		this.multipartResolver = new CommonsMultipartResolver();
		this.multipartResolver
				.setMaxUploadSize(SystemValue.getMaxUploadSizeM() * 1024 * 1024);
		this.multipartResolver.setServletContext(servletConfig
				.getServletContext());
		this.multipartResolver.setDefaultEncoding("UTF-8");

	}

	@Override
	public void destroy() {
	}

	/**
	 * 线程处理 Runnable
	 */
	private class ServiceRunnable implements Runnable {

		private HttpServletRequest request;

		private HttpServletResponse response;

		private ServiceRunnable(HttpServletRequest request,
				HttpServletResponse response) {
			this.request = request;
			this.response = response;
		}

		@Override
		public void run() {
			IceRequestContext iceRequestContext = null;
			IceRequest iceRequest = null;
			try {
				// 1构建请求上下文
				iceRequestContext = IceBuilder.buildIceRequestContext(context,
						request, response);
				// 校验系统参数
				IceErrorResponse error = dispatcher
						.validateSystemParameters(iceRequestContext);
				if (error == null) {
					iceRequest = IceBuilder.buildIceRequest(iceRequestContext);
					// 校验方法上的参数
					error = dispatcher
							.validateMethodParameters(iceRequestContext);
					if (error != null) {
						// 直接输出错误
						dispatcher.output(error, iceRequestContext.isJson(),
								response);
					} else {
						// 执行
						dispatcher.invoke(iceRequestContext, iceRequest);
						// 输出
						dispatcher.output(iceRequestContext.getIceResponse(),
								iceRequestContext.isJson(), response);
					}// methodParameters end
				} else {
					// 直接输出错误
					dispatcher.output(error, iceRequestContext.isJson(),
							response);
				}// systemParameters end
			} catch (Exception e) {
				throw new IceException("执行异常", e);
			}
		}

	}

}
