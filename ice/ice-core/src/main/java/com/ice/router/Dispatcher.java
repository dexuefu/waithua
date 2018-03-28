
package com.ice.router;

import com.ice.annotation.HttpAction;
import com.ice.config.SystemValue;
import com.ice.config.SystemParameterNames;
import com.ice.context.IceRequestContext;
import com.ice.exception.IceErrorResponse;
import com.ice.exception.error.*;
import com.ice.interfaces.AppKeyService;
import com.ice.interfaces.InvokeService;
import com.ice.interfaces.SessionService;
import com.ice.request.IceRequest;
import com.ice.response.CommonRopResponse;
import com.ice.session.Session;
import com.ice.utils.JsonUtils;
import com.ice.utils.ResponseUtil;
import com.ice.utils.SignUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.ObjectError;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
@Component
public class Dispatcher {
	
	@Resource
	private AppKeyService appKeyService;
	
	@Resource
	private SessionService sessionService;
	
	@Resource
	private InvokeService invokeService;

	@Resource
	private ThreadPoolTaskExecutor asyncCallPoolTaskExecutor;

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 验证系统最基本的参数
	 * @param requestContext
	 */
	public IceErrorResponse validateSystemParameters(IceRequestContext requestContext){
		//1方法名 2是否废弃 3postget 4 时间戳   5 开发者验证 6会话验证 7签名验证 8调用次数验证 9权限验证 10拦截器  11 请求参数格式验证
		
		//1 验证方法名和版本 
		if(requestContext.getServiceMethodHandler() == null){
			 return new MehodError(requestContext.getMethod(),requestContext.getVersion());
		}
		//2是否被废弃
		if(requestContext.getServiceMethodHandler().getServiceMethodValue().isObsoleted()){
			return new ObsoletedError(requestContext.getMethod(),requestContext.getVersion());
		}
		
		//3post/get
		// system
		HttpAction [] actions =  requestContext.getServiceMethodHandler().getServiceMethodValue().getHttpAction();
		// request
		HttpAction httpAction = requestContext.getHttpAction();
		boolean isValidate = false;
		for(HttpAction action : actions){
			if(action == httpAction){
				isValidate = true;
				break;
			}
		}
		//不通过
		if(!isValidate){
			return new ActionError(requestContext.getMethod(),requestContext.getVersion(),httpAction);
		}
		
		//4验证时间戳
		//是否开启时间戳
        if (requestContext.getServiceMethodHandler().getServiceMethodValue().isOpenTimestamp())
        {
			String timestamp = requestContext.getTimestamp();
			if(StringUtils.isEmpty(timestamp)){
				return  new TimestampError(requestContext.getMethod(),requestContext.getVersion());
			}else{
				long urlTimestamp = Long.parseLong(timestamp);
				long nowTime = System.currentTimeMillis();
				if(Math.abs(nowTime-urlTimestamp) > SystemValue.getTimestampValue()){
					return new TimestampError(requestContext.getMethod(),requestContext.getVersion());
				}
			}
		}

		// 方法注解优先级高于全局配置
		boolean isOpenSign = requestContext.getServiceMethodHandler().getServiceMethodValue().isNeedSign();
		//5 开发者验证
		String secret = null;
		// 如果开启签名验证，那么必须开启开发者验证
		if(isOpenSign || requestContext.getServiceMethodHandler().getServiceMethodValue().isOpenAppKey()){
			String appKey = requestContext.getAppKey();
			if(StringUtils.isEmpty(appKey)){
				return new AppKeyError(requestContext.getMethod(),requestContext.getVersion());
			}else{
				secret  = appKeyService.getSecretByAppKey(requestContext.getAppKey());
				if(StringUtils.isEmpty(secret)){
					return new AppKeyError(requestContext.getMethod(),requestContext.getVersion());
				}
			}
			
		}

		//6session
		if(requestContext.getServiceMethodHandler().getServiceMethodValue().isNeedSession()){
			Session session =  sessionService.getSession(requestContext.getSessionId());
			if(session == null){
				return new SessionError(requestContext.getMethod(),requestContext.getVersion());
			}else{
				//往回写，防止多次调用 缓存中 getSession();
				requestContext.setSession(session);
			}
		}

		//7签名验证
		if(isOpenSign){
			List<String> ignoreSignFieldNames = requestContext.getServiceMethodHandler().getIgnoreSignFieldNames();
			Map<String, String> needSignParams = new HashMap<String, String>();
			for (Map.Entry<String, String> entry : requestContext.getAllParams().entrySet()) {
				if (!ignoreSignFieldNames.contains(entry.getKey())) {
					needSignParams.put(entry.getKey(), entry.getValue());
				}
			}
			//签名
			String signValue = SignUtils.sign(needSignParams, secret);
			if(!signValue.equals(requestContext.getSign())){
				//签名有误
				return  new SignError(requestContext.getMethod(),requestContext.getVersion());
			}
		}
		

		//8调用次数
		invokeService.countTimes(requestContext.getAppKey(), requestContext.getMethod(), 
				requestContext.getVersion(), requestContext.getIp(), requestContext.getSessionId());
		
		IceErrorResponse invokeError = invokeService.invokeLimit(requestContext.getAppKey(), requestContext.getMethod(), 
				requestContext.getVersion(), requestContext.getIp(), requestContext.getSessionId());
		if(invokeError !=null ){
			return invokeError;
		}
		
		return null;
	}
	
	/**
	 * 验证方法参数的合法性
	 * @param requestContext
	 */
	public IceErrorResponse validateMethodParameters(IceRequestContext requestContext){
		List<ObjectError> errors = requestContext.getParamErrors();
		if(errors != null && errors.size() > 0){
			return new ParametersError(requestContext.getMethod(),requestContext.getVersion(),errors);
		}
		return  null;
	}
	
	/***
	 * 输出
	 * @param result
	 * @param isJson
	 * @param response
	 */
	public void output(Object result,boolean isJson,HttpServletResponse response){
		if(result != null){
			if(isJson){
				ResponseUtil.wirteJson(result, response);
			}else{
				//xml
				ResponseUtil.wirteXml(result, response);
			}
		}
	}

	private Object doInvoke(IceRequestContext requestContext, IceRequest iceRequest) throws InvocationTargetException, IllegalAccessException {
		//有参数
		if(requestContext.getServiceMethodHandler().isHasParameter()){
			return requestContext.getServiceMethodHandler().getHandlerMethod().invoke (requestContext.getServiceMethodHandler().getHandler(),iceRequest);
			//无参数
		}else{
			return requestContext.getServiceMethodHandler().getHandlerMethod().invoke( requestContext.getServiceMethodHandler().getHandler());
		}
	}
	
	/**
	 * 执行方法并输出结果
	 * @param  requestContext
	 * @param iceRequest
	 * 
	 */
	public void invoke(final IceRequestContext requestContext, final IceRequest iceRequest) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Object result = null;
		// 异步调用
		if (requestContext.getServiceMethodHandler().getServiceMethodValue().isAsync()){
			result= CommonRopResponse.SUCCESSFUL_RESPONSE;
			asyncCallPoolTaskExecutor.submit(new Runnable() {
				@Override
				public void run() {
					String callBackUrl = appKeyService.getCallBackUrlByAppKey(requestContext.getAppKey());
					if (StringUtils.isNotEmpty(callBackUrl)) {
						try {
							final RestTemplate restTemplate = new RestTemplate();
							final MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
							form.add(SystemParameterNames.getMehod(), requestContext.getMethod());
							String json = JsonUtils.toJson(doInvoke(requestContext, iceRequest));
							form.add("data", json);
							logger.info("回调{}, method={}, data={}", callBackUrl,requestContext.getMethod(), json);
							restTemplate.postForObject(callBackUrl,form, String.class);
						} catch (Exception e) {
							logger.error("回调{}错误。{}", callBackUrl, e.getMessage());
						}
					} else {
						logger.error("Partner[{}]回调地址为空", requestContext.getAppKey());
					}
				}
			});

		} else {
			result =  doInvoke(requestContext, iceRequest);
		}
		//执行时间
		requestContext.setServiceEndTime(System.currentTimeMillis());

		if(result != null){
			requestContext.setIceResponse(result);
		}

	}
	
	
	
}
