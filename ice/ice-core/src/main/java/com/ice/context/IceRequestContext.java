
package com.ice.context;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.ObjectError;

import com.ice.annotation.HttpAction;
import com.ice.handler.ServiceMethodHandler;
import com.ice.session.Session;

/**
 * 接到服务请求后，将产生一个{@link IceRequestContext}上下文对象，它被本次请求直到返回响应的这个线程共享。
 * @version 1.0
 */
public class IceRequestContext {

	// http
	private HttpServletRequest httpServletRequest;
	
	// http
	private HttpServletResponse httpServletResponse;
	
	//系统上下文
	private IceContext iceContext;
	
	//处理方法Handler
	private ServiceMethodHandler serviceMethodHandler;
	
	//请求的方法
	private String method;

	//请求的版本
	private String version;

	//请求的开发者账号
	private String appKey;

	//会话id
	private String sessionId;

	//请求格式xml/json
	private boolean isJson = true;

	//签名
	private String sign;

	//时间戳
	private String timestamp;

	//请求后的响应
	private Object IceResponse;

	//请求ip
	private String ip;

	//post get
	private HttpAction httpAction;

	//请求有参数
	private Map<String, String> allParams;

	//请求id
	private String requestId;

	//session
	private Session session;


	private long serviceBeginTime = -1;
	private long serviceEndTime = -1;
	
	private List<ObjectError> paramErrors;
	
	
	public IceRequestContext(IceContext iceContext){
		this.iceContext = iceContext;
	} 
	
	
	public IceContext getIceContext() {
		return iceContext;
	}

	public void setIceContext(IceContext iceContext) {
		this.iceContext = iceContext;
	}

	public ServiceMethodHandler getServiceMethodHandler() {
		return serviceMethodHandler;
	}

	public void setServiceMethodHandler(ServiceMethodHandler serviceMethodHandler) {
		this.serviceMethodHandler = serviceMethodHandler;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public boolean isJson() {
		return isJson;
	}


	public void setJson(boolean isJson) {
		this.isJson = isJson;
	}


	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public List<ObjectError> getParamErrors() {
		return paramErrors;
	}


	public void setParamErrors(List<ObjectError> paramErrors) {
		this.paramErrors = paramErrors;
	}


	public Object getIceResponse() {
		return IceResponse;
	}

	public void setIceResponse(Object iceResponse) {
		IceResponse = iceResponse;
	}



	public long getServiceBeginTime() {
		return serviceBeginTime;
	}

	public void setServiceBeginTime(long serviceBeginTime) {
		this.serviceBeginTime = serviceBeginTime;
	}

	public long getServiceEndTime() {
		return serviceEndTime;
	}

	public void setServiceEndTime(long serviceEndTime) {
		this.serviceEndTime = serviceEndTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public HttpAction getHttpAction() {
		return httpAction;
	}

	public void setHttpAction(HttpAction httpAction) {
		this.httpAction = httpAction;
	}

	public Map<String, String> getAllParams() {
		return allParams;
	}

	public void setAllParams(Map<String, String> allParams) {
		this.allParams = allParams;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}


	public HttpServletRequest getHttpServletRequest() {
		return httpServletRequest;
	}


	public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}


	public String getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}


	public HttpServletResponse getHttpServletResponse() {
		return httpServletResponse;
	}


	public void setHttpServletResponse(HttpServletResponse httpServletResponse) {
		this.httpServletResponse = httpServletResponse;
	}
	
	
	
	
}

