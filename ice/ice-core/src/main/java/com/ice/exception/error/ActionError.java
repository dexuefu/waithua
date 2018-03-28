
package com.ice.exception.error;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ice.annotation.HttpAction;
import com.ice.exception.ErrorType;
import com.ice.exception.IceErrorResponse;

/**
 * post get限制错误
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class ActionError extends IceErrorResponse {

	public ActionError(){
		//java 遗留问题
		super();
	}
	
	public ActionError(String method,String version,HttpAction action ){
		this.setMessage("方法"+method+"#"+version+"不支持" + action.toString());
		this.setCode(ErrorType.actionError);
		List<String> list = new ArrayList<String>(1);
		list.add("请查看API中对应的方法支持的 httpAction【get/post】");
		this.setSolutions(list);
	}
	
	
}
