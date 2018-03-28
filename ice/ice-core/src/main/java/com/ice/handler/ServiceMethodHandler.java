package com.ice.handler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.ice.request.IceRequest;

/**
 * <pre>
 *     服务处理器的相关信息
 * </pre>
 *
 * @version 1.0
 */
public class ServiceMethodHandler {

    //处理器对象
    private Object handler;

    //处理器的处理方法
    private Method handlerMethod;

    //处理方法的注解值
    private ServiceMethodValue serviceMethodValue;

    //处理方法的请求对象类
    private Class<? extends IceRequest> requestType = IceRequest.class;

    //无需签名的字段列表
    private List<String> ignoreSignFieldNames = new ArrayList<String>();

    //属性类型为FileItem的字段列表
    private List<String> uploadFileFieldNames;

	public Object getHandler() {
		return handler;
	}

	public void setHandler(Object handler) {
		this.handler = handler;
	}

	public Method getHandlerMethod() {
		return handlerMethod;
	}

	public void setHandlerMethod(Method handlerMethod) {
		this.handlerMethod = handlerMethod;
	}

	public ServiceMethodValue getServiceMethodValue() {
		return serviceMethodValue;
	}

	public void setServiceMethodValue(ServiceMethodValue serviceMethodValue) {
		this.serviceMethodValue = serviceMethodValue;
	}

	public Class<? extends IceRequest> getRequestType() {
		return requestType;
	}

	public void setRequestType(Class<? extends IceRequest> requestType) {
		this.requestType = requestType;
	}

	public List<String> getIgnoreSignFieldNames() {
		return ignoreSignFieldNames;
	}

	public void setIgnoreSignFieldNames(List<String> ignoreSignFieldNames) {
		if(ignoreSignFieldNames !=null ){
			this.ignoreSignFieldNames.addAll(ignoreSignFieldNames);
		}
	}

	public List<String> getUploadFileFieldNames() {
		return uploadFileFieldNames;
	}

	public void setUploadFileFieldNames(List<String> uploadFileFieldNames) {
		this.uploadFileFieldNames = uploadFileFieldNames;
	}

	/**
	 * 该方法名是否有请求参数 
	 */
    public boolean isHasParameter(){
	   if(requestType != null){
		   return true;
	   }
	   return false;
   }
    
 

}

