
package com.ice.exception.error;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ice.exception.ErrorType;
import com.ice.exception.IceErrorResponse;

/**
 * 时间戳
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class TimestampError extends IceErrorResponse {

	public TimestampError(){
		//java 遗留问题
		super();
	}
	
	public TimestampError(String method,String version){
		this.setMessage("调用方法"+method+"#"+version+"传入的时间戳不正确");
		this.setCode(ErrorType.timestampError);
		List<String> list = new ArrayList<String>(1);
		list.add("请查看API对时间戳的要求");
		this.setSolutions(list);
	}
	
	
}
