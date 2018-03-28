package com.ice.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统的值,可以在这里更改
 * 
 * @version 1.0
 */
public class SystemValue {

	private static Logger logger = LoggerFactory.getLogger(SystemValue.class);

	public static final String KEY_PREV = "ice.system.";

	public static String getConfig(String key, String defaultValue) {
		String value = System.getProperty(key);
		if (StringUtils.isBlank(value)) {
			return defaultValue;
		}
		return value;
	}

	public static int getConfig(String key, int defaultValue) {
		String value = System.getProperty(key);
		int intValue = defaultValue;
		if (StringUtils.isBlank(value)) {
			return intValue;
		}
		try {
			intValue = Integer.parseInt(value);
		} catch (Exception e) {
			logger.warn("arg:" + key
					+ " has parse error,it will be setted default value:"
					+ defaultValue);
		}
		return intValue;
	}

	public static long getConfig(String key, long defaultValue) {
		String value = System.getProperty(key);
		long intValue = defaultValue;
		if (StringUtils.isBlank(value)) {
			return intValue;
		}
		try {
			intValue = Long.parseLong(value);
		} catch (Exception e) {
			logger.warn("arg:" + key
					+ " has parse error,it will be setted default value:"
					+ defaultValue);
		}
		return intValue;
	}

	public static boolean getConfig(String key, boolean defaultValue) {
		String value = System.getProperty(key);
		if (StringUtils.isBlank(value)) {
			return defaultValue;
		}
		return Boolean.parseBoolean(value.trim());
	}

	/**
	 * 系统是否开启签名验证
	 */
	public static boolean isOpenSign() {
		return getConfig(KEY_PREV + "openSign", Boolean.FALSE);
	}

	/**
	 * 系统是否开启签名验证
	 */
	public static boolean isOpenAppKey() {
		return isOpenSign();
	}

	/**
	 * 系统是否开会话
	 */
	public static boolean isOpenSession() {
		return getConfig(KEY_PREV + "openSession", Boolean.FALSE);
	}

	/**
	 * 系统是否开启时间戳验证
	 */
	public static boolean isOpenTimestamp() {
		return getConfig(KEY_PREV + "openTimestamp", Boolean.FALSE);
	}

	/**
	 * 请求时间与服务收到时间允许最大误差。15分钟,时间戳
	 */
	public static long getTimestampValue() {
		return getConfig(KEY_PREV + "timestampValue", 15 * 60 * 1000L);
	}

	/**
	 * 最大超时时间 30分钟,单位秒
	 */
	public static int getMaxTimeout() {
		return getConfig(KEY_PREV + "maxTimeout", 30 * 60);
	}

	/**
	 * 缺省超时时间 1分钟,单位秒
	 */
	public static int getDefaultTimeout() {
		return getConfig(KEY_PREV + "defaultTimeout", 60);
	}

	/**
	 * 最大上传文件，单位M
	 */
	public static int getMaxUploadSizeM() {
		return getConfig(KEY_PREV + "maxUploadSize", 60);
	}

}
