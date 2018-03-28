package com.ice.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 *
 * @author <a href="mailto:gengcai.xie@skyroam.com">Xie Gengcai</a>
 * @version 1.0
 * @date Jan 6, 2015
 */
public class CustomizedPropertyConfigurer extends PropertyPlaceholderConfigurer {
	private static Map<String, Object> ctxPropertiesMap;

	private static Logger logger = LoggerFactory.getLogger(CustomizedPropertyConfigurer.class);

	public static final String KEY_PREV = "ice.system.";

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory,
									 Properties props) throws BeansException {

		super.processProperties(beanFactory, props);
		//load properties to ctxPropertiesMap
		ctxPropertiesMap = new HashMap<String, Object>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			ctxPropertiesMap.put(keyStr, value);
		}
	}

	//static method for accessing context properties
	public static Object getContextProperty(String name) {
		return ctxPropertiesMap.get(name);
	}

	public static String getConfig(String key, String defaultValue) {
		String value = (String) getContextProperty(key);
		if (StringUtils.isBlank(value)) {
			return defaultValue;
		}
		return value;
	}

	public static int getConfig(String key, int defaultValue) {
		String value = (String) getContextProperty(key);
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
		String value = (String) getContextProperty(key);
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
		String value = (String) getContextProperty(key);
		if (StringUtils.isBlank(value)) {
			return defaultValue;
		}
		return Boolean.parseBoolean(value.trim());
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
