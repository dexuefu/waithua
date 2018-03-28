
package com.ice.exception.error;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ice.exception.ErrorType;
import com.ice.exception.IceErrorResponse;

/**
 * 方法被抛弃
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class ObsoletedError extends IceErrorResponse {

	public ObsoletedError(){
		//java 遗留问题
		super();
	}
	
	public ObsoletedError(String method,String version){
		this.setMessage("方法"+method+"#"+version+"已经被废弃");
		this.setCode(ErrorType.obsoletedError);
		List<String> list = new ArrayList<String>(1);
		list.add("请查看最新API");
		this.setSolutions(list);
	}
	
	
}
