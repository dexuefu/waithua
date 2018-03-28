package com.ice.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ice.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.ServletRequestDataBinder;

import com.ice.config.SystemParameterNames;
import com.ice.context.IceContext;
import com.ice.context.IceRequestContext;
import com.ice.handler.ServiceMethodValue;
import com.ice.request.IceRequest;

/**
 * @author <a href="mailto:gengcai.xie@skyroam.com">Xie Gengcai</a>
 * @date 2014-06-01
 */
public class IceBuilder {

	protected static Logger logger = LoggerFactory.getLogger(IceBuilder.class);

	private static FormattingConversionService conversionService;
    private static Validator validator;
    
	static {
		//conversionService
		FormattingConversionServiceFactoryBean serviceFactoryBean = new FormattingConversionServiceFactoryBean();
		serviceFactoryBean.afterPropertiesSet();
		conversionService = serviceFactoryBean.getObject();
		
		//validator
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        validator = localValidatorFactoryBean;
		
	};

	
	/**
	 * 构建请求上下文
	 * 
	 * @param request
	 * @param response
	 */
	public static IceRequestContext buildIceRequestContext(IceContext iceContext, HttpServletRequest request,HttpServletResponse response) {

		IceRequestContext rContext = new IceRequestContext(iceContext);
		//毫秒
		rContext.setServiceBeginTime(System.currentTimeMillis());
		rContext.setHttpServletRequest(request);
		//3 xx2
		rContext.setHttpServletResponse(response);
		
		String method = request.getParameter(SystemParameterNames.getMehod());
		String version = request.getParameter(SystemParameterNames.getVersion());
		//1
		rContext.setTimestamp(request.getParameter(SystemParameterNames.getTimestamp()));
		//2  xx1
		
		//除去 sign
		Map<String, String> allParams = RequestUtil.getRequestParams(request);
		allParams.remove(SystemParameterNames.getSign());
		//4
		rContext.setAllParams(allParams);
		//5
		rContext.setAppKey(request.getParameter(SystemParameterNames.getAppKey()));
		//6
		//默认json
		rContext.setJson(RequestUtil.getIsJson(request));
		//7 xx3
		rContext.setHttpAction(HttpAction.fromValue(request.getMethod()));
		//8 构造函数已经设置
		//rContext.setIceContext(iceContext);
	
		//rContext.setIceResponse(iceResponse);返回结果，执行的时候设置
		//9 xx4
		rContext.setIp(RequestUtil.getRemoteAddr(request));
		//10
		rContext.setMethod(method);
		//11 xx5
		rContext.setRequestId(RequestUtil.getUuid());
		
		// rContext.setServiceEndTime(serviceEndTime);//执行完成后设置
		//12
		rContext.setServiceMethodHandler(iceContext.getServiceMethodHandler(method, version));
		// rContext.setSession(session);//验证session的时候设置
		//13
		rContext.setSessionId(request.getParameter(SystemParameterNames.getSessionId()));
		//14
		rContext.setSign(request.getParameter(SystemParameterNames.getSign()));
		//15
		rContext.setVersion(version);
		
		return rContext;
	}

	
	/**
	 * 构建请求参数JSR校验
     * @param iceRequestContext
     */
    public static IceRequest buildIceRequest(IceRequestContext iceRequestContext) {
        IceRequest iceRequest = null;
        if (iceRequestContext.getServiceMethodHandler().isHasParameter()) {
           
        	Class<? extends IceRequest> classType = iceRequestContext.getServiceMethodHandler().getRequestType();
            IceRequest bindObject = BeanUtils.instantiateClass(classType);
            ServletRequestDataBinder dataBinder = new ServletRequestDataBinder(bindObject, "bindObject");
            dataBinder.setConversionService(conversionService);
            dataBinder.setValidator(validator);
            dataBinder.bind(iceRequestContext.getHttpServletRequest());
            dataBinder.validate();
            BindingResult bindingResult = dataBinder.getBindingResult();
            
            iceRequest = (IceRequest)bindingResult.getTarget();

            iceRequest.setIceRequestContext(iceRequestContext);
            //错误处理
            List<ObjectError> paramErrors = bindingResult.getAllErrors();
            iceRequestContext.setParamErrors(paramErrors);
        } 
        return iceRequest;
    }
	
	
	/**
	 * 构建注解值
	 * 
	 * @param serviceMethod
	 * @return {@link ServiceMethodValue}
	 */
	public static ServiceMethodValue buildServiceMethodValue(ServiceMethod serviceMethod) {

		ServiceMethodValue service = new ServiceMethodValue();
		// 支持的post 或者 get
		service.setHttpAction(serviceMethod.httpAction());
		// 方法体名称
		service.setMethod(serviceMethod.method());
		//是否需要session验证
		service.setNeedSession(SessionType.isNeedInSession(serviceMethod.session()));
		// 是否需要签名验证
		service.setNeedSign(SignType.isNoSign(serviceMethod.sign()));
		// 开发者验证
		if (service.isNeedSign()) {
			service.setOpenAppKey(Boolean.TRUE);
		} else {
			service.setOpenAppKey(AppKeyType.isOpenAppKey(serviceMethod.openAppKey()));
		}
		// 是否过期
		service.setObsoleted(ObsoletedType.isObsoleted(serviceMethod.obsoleted()));
		//是否开启时间戳验证
		service.setOpenTimestamp(TimestampType.isOpenTimestamp(serviceMethod.openTimestamp()));
		// 超时
		service.setTimeout(serviceMethod.timeout());
		// 版本
		service.setVersion(serviceMethod.version());
		// 是否异步调用
		service.setAsync(AsyncType.isAsync(serviceMethod.isAsync()));
		return service;
	}

	/**
	 * 构建不需要签名的属性列表
	 * @param requestType
	 * @return
	 */
	public static List<String> buildNoSignFieldNames(
			Class<? extends IceRequest> requestType) {
		final ArrayList<String> igoreSignFieldNames = new ArrayList<String>(1);
		if (requestType != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("获取" + requestType.getCanonicalName() + "不需要签名的属性");
			}
			ReflectionUtils.doWithFields(requestType,
					new ReflectionUtils.FieldCallback() {
						public void doWith(Field field)
								throws IllegalArgumentException,
								IllegalAccessException {
							igoreSignFieldNames.add(field.getName());
						}
					}, new ReflectionUtils.FieldFilter() {
						public boolean matches(Field field) {
							// 属性类标注了@NoSign
							NoSign typeNo = AnnotationUtils.findAnnotation(field.getType(), NoSign.class);
							if (typeNo != null)
                            {
                                return typeNo != null;
                            }
							// 属性定义处标注了@NoSign
							NoSign varNoSign = field.getAnnotation(NoSign.class);
							// 属性定义处标注了@Temporary
							Temporary varTemporary = field.getAnnotation(Temporary.class);
							return varNoSign != null|| varTemporary != null;
						}
					});
			if (igoreSignFieldNames.size() > 1 && logger.isDebugEnabled()) {
				logger.debug(requestType.getCanonicalName() + "不需要签名的属性:"+ igoreSignFieldNames.toString());
			}
		}
		return igoreSignFieldNames;
	}


}
