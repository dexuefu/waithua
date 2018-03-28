package com.ice.exception.error;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ice.exception.ErrorType;
import com.ice.exception.IceErrorResponse;

/**
 * 超时错误
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class ExecuteError extends IceErrorResponse {

	
	public ExecuteError(){
		//java 遗留问题
		super();
	}
	
	
	public ExecuteError(String method,String version){
		this.setMessage("调用服务方法:" + method + "#" + version+ "，执行异常");
		this.setCode(ErrorType.executeError);
		List<String> list = new ArrayList<String>(1);
		list.add(getMessage());
		this.setSolutions(list);
	}
	
	
}
