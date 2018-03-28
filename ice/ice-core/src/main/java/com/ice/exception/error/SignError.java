
package com.ice.exception.error;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ice.exception.ErrorType;
import com.ice.exception.IceErrorResponse;

/**
 * 签名有误
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class SignError extends IceErrorResponse {

	
	public SignError(){
		//java 遗留问题
		super();
	}
	
	public SignError(String method,String version){
		this.setMessage("调用方法"+method+"#"+version+"时，签名有误");
		this.setCode(ErrorType.signError);
		List<String> list = new ArrayList<String>(1);
		list.add("请重新获签名");
		this.setSolutions(list);
	}
	
	
}
