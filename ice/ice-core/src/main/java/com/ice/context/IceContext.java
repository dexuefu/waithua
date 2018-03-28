package com.ice.context;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.ice.annotation.ServiceMethod;
import com.ice.annotation.WebService;
import com.ice.exception.IceException;
import com.ice.handler.ServiceMethodHandler;
import com.ice.handler.ServiceMethodValue;
import com.ice.request.IceRequest;
import com.ice.utils.IceBuilder;


/**
 * ice上下文
 * 
 * @version 1.0
 */
public class IceContext {

	protected  Logger logger = LoggerFactory.getLogger(IceContext.class);

	private Map<String, ServiceMethodHandler> serviceHandlerMap = new HashMap<String, ServiceMethodHandler>();

	/**
	 * 初始化注解等
	 * @param  context
	 */
	public IceContext(final ApplicationContext context) {
		logger.debug("对Spring上下文中的Bean进行扫描，查找ice服务方法: {}", context);
        logger.debug("对Spring上下文中的Bean进行扫描，查找ice服务方法: {}", context);
		String[] beanNames = context.getBeanNamesForType(Object.class);
		for (final String beanName : beanNames) {
			Class<?> handlerType = context.getType(beanName);
			if (AnnotationUtils.findAnnotation(handlerType, WebService.class) != null) {
				ReflectionUtils.doWithMethods(handlerType,
						new ReflectionUtils.MethodCallback() {
							@Override
							public void doWith(Method method)throws IllegalArgumentException,IllegalAccessException {
								//webservice 方法的注解
								ServiceMethod serviceMethod = AnnotationUtils.findAnnotation(method,ServiceMethod.class);
								//方法注解上的值
								ServiceMethodValue serviceMethodValue = IceBuilder.buildServiceMethodValue(serviceMethod);
								//处理方法的类
								ServiceMethodHandler serviceMethodHandler = new ServiceMethodHandler();
								//serviceMethodValue
								serviceMethodHandler.setServiceMethodValue(serviceMethodValue);
								//handler
                                serviceMethodHandler.setHandler(context.getBean(beanName));
                                //method
                                serviceMethodHandler.setHandlerMethod(method);
                                
                                if (method.getParameterTypes().length > 1) {
                                	logger.error("参数必须是一个参数或者无参数");
                                	throw new IceException("参数必须是一个参数或者无参数");
                                } else if (method.getParameterTypes().length == 1) {
                                    Class<?> paramType = method.getParameterTypes()[0];
                                    if (ClassUtils.isAssignable(IceRequest.class, paramType)) {
                                    	@SuppressWarnings("unchecked")
										Class<? extends IceRequest> classType = (Class<? extends IceRequest>)paramType;
                                    	serviceMethodHandler.setRequestType(classType);
                                    	//忽略签名的列表
                                        List<String> ignoreSignFieldNames = IceBuilder.buildNoSignFieldNames(classType);
                                        serviceMethodHandler.setIgnoreSignFieldNames(ignoreSignFieldNames);
                                    }else{
                                    	logger.error("参数必须继承IceRequest");
                                    	throw new IceException("参数必须继承IceRequest");
                                    }
                                }
                                //存储
                                ServiceMethodHandler oldHandler = serviceHandlerMap.put(serviceMethodValue.getMethod()+"#"+serviceMethodValue.getVersion(), serviceMethodHandler);
                                if(oldHandler != null){
                                	logger.error("定义了重复的方法名+版本");
                                	throw new IceException("定义了重复的方法名+版本数");
                                }
							}
						},
						new ReflectionUtils.MethodFilter() {
                            public boolean matches(Method method) {
                                return !method.isSynthetic() && AnnotationUtils.findAnnotation(method, ServiceMethod.class) != null;
                            }
                        }
				);
			}
		}
	}

	
	
	public ServiceMethodHandler getServiceMethodHandler(String method,String version){
		return serviceHandlerMap.get(method+"#"+version);
	}
	
	
	
}
