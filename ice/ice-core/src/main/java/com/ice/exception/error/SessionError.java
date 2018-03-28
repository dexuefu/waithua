package com.ice.exception.error;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ice.exception.ErrorType;
import com.ice.exception.IceErrorResponse;

/**
 * 会话错误
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class SessionError extends IceErrorResponse {

	public SessionError() {
		// java 遗留问题
		super();
	}

	public SessionError(String method, String version) {
		this.setMessage("调用方法" + method + "#" + version + "时，会话已过期");
		this.setCode(ErrorType.sessionError);
		List<String> list = new ArrayList<String>(1);
		list.add("请重新获取会话");
		this.setSolutions(list);
	}

}
