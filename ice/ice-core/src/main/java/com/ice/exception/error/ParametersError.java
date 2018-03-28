package com.ice.exception.error;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.ice.exception.ErrorType;
import com.ice.exception.IceErrorResponse;

/**
 * 参数错误
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class ParametersError extends IceErrorResponse {

	
	public ParametersError(){
		//java 遗留问题
		super();
	}
	
	public ParametersError(String method, String version,
			List<ObjectError> errors) {
		this.setMessage("调用方法" + method + "#" + version + "参数有误");
		this.setCode(ErrorType.parametersError);
		List<String> list = new ArrayList<String>();
		for (ObjectError objectError : errors) {
			if (objectError instanceof FieldError) {
				FieldError fieldError = (FieldError) objectError;
				list.add("error filed :" + fieldError.getField()
						+ ",error code :" + fieldError.getCode()
						+ ", rejectedValue :" + fieldError.getRejectedValue());
			}
		}
		this.setSolutions(list);
	}

}
