package com.ice.request;

import java.util.List;

import org.apache.commons.fileupload.FileItem;

import com.ice.annotation.Temporary;
import com.ice.context.IceRequestContext;

/**
 * <pre>
 *    ICE服务的请求对象，请求方法的入参必须是继承于该类。
 * </pre>
 * 
 * @version 1.0
 */
public abstract class AbstracetIceRequest implements IceRequest {

	@Temporary
	private IceRequestContext iceRequestContext;

	@Temporary
	private List<FileItem> fileItems;
	
	@Override
	public IceRequestContext getIceRequestContext() {
		return iceRequestContext;
	}
	@Override
	public void setIceRequestContext(IceRequestContext iceRequestContext) {
		this.iceRequestContext = iceRequestContext;
	}
	

	

	
	
}
