package com.ice.exception.error;

import com.ice.exception.ErrorType;
import com.ice.exception.IceErrorResponse;

public class AsyncError extends IceErrorResponse {
	public AsyncError() {
		// java 遗留问题
		super();
	}

	public AsyncError(String method, String version, Throwable throwable) {
		this.setMessage("调用服务方法:" + method + "#" + version + "，执行异常："
				+ throwable);
		this.setCode(ErrorType.AsyncError);
	}
}
