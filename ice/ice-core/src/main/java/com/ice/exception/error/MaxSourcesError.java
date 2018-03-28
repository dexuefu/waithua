
package com.ice.exception.error;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ice.exception.ErrorType;
import com.ice.exception.IceErrorResponse;

/**
 * 超出最大资源限制
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class MaxSourcesError extends IceErrorResponse {

	public MaxSourcesError(){
		//java 遗留问题
		super();
	}
	
	
	public MaxSourcesError(String method,String version){
		this.setMessage("调用服务方法:" + method + "#" + version+ "，超过最大资源限制，无法提供服务。");
		this.setCode(ErrorType.maxSourcesError);
		List<String> list = new ArrayList<String>(1);
		list.add(getMessage());
		this.setSolutions(list);
	}
	
	
}
