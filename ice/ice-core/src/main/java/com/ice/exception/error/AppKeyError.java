
package com.ice.exception.error;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ice.exception.ErrorType;
import com.ice.exception.IceErrorResponse;

/**
 * appKey错误
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class AppKeyError extends IceErrorResponse {

	public AppKeyError(){
		//java 遗留问题
		super();
	}
	
	public AppKeyError(String method,String version){
		this.setMessage("调用方法"+method+"#"+version+"使用的appKey不存在");
		this.setCode(ErrorType.appKeyError);
		List<String> list = new ArrayList<String>(1);
		list.add("请使用已经申请审核过的appKey");
		this.setSolutions(list);
	}
	
	
}
