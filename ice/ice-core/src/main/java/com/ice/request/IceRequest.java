package com.ice.request;

import com.ice.context.IceRequestContext;

/**
 * <pre>
 *    ICE服务的请求对象，请求方法的入参必须是继承于该类。
 * </pre>
 * 
 * @version 1.0
 */
public interface  IceRequest {

	IceRequestContext getIceRequestContext() ;

	public void setIceRequestContext(IceRequestContext iceRequestContext);

}
