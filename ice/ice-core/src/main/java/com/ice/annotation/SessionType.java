package com.ice.annotation;

/**
 * <pre>
 * 功能说明：是否需求会话检查
 * </pre>
 * @version 1.0
 */
public enum SessionType {

	YES, NO;

	public static boolean isNeedInSession(SessionType type) {
		if (YES == type) {
			return true;
		}
		return false;
	}
}
