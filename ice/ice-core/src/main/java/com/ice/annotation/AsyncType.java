package com.ice.annotation;

/**
 * Created with IntelliJ IDEA.
 *
 * @author <a href="mailto:gengcai.xie@skyroam.com">Xie Gengcai</a>
 * @version 1.0
 * @date Jan 6, 2015
 */
public enum AsyncType {
	YES, NO;
	public static boolean isAsync(AsyncType type) {
		if (NO == type) {
			return false;
		}
		return true;
	}
}
