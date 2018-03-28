package com.ice.annotation;

/**
 * Created by Xie Gengcai on 2015/3/19.
 */
public enum IgnoreSignType {
	YES, NO, DEFAULT;

	public static boolean isIgnoreSign(IgnoreSignType type) {
		if (NO == type || DEFAULT == type) {
			return false;
		} else {
			return true;
		}
	}
}
