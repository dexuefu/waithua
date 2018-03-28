package com.ice.exception.error;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ice.exception.ErrorType;
import com.ice.exception.IceErrorResponse;

/**
 * 调用方法错误
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class MehodError extends IceErrorResponse {

	public MehodError(){
		//java 遗留问题
		super();
	}
	
	public MehodError(String method,String version){
		this.setMessage("没有找到方法"+method+"#"+version);
		this.setCode(ErrorType.methodError);
		List<String> list = new ArrayList<String>(1);
		list.add("请查看API中对应的method和v");
		this.setSolutions(list);
	}
	
	
}
