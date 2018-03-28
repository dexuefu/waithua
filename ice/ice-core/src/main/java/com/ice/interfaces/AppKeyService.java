package com.ice.interfaces;

/**
 * 密钥管理接口，需要实现类
 */
public interface AppKeyService {

	/**
	 * 获取密钥
	 * @param appKey
	 */
	String getSecretByAppKey(String appKey);

	/**
	 * 获取回调地址
	 * @param appKey
	 * @return
	 */
	String getCallBackUrlByAppKey(String appKey);
	
	/**
	 * 设置密钥
	 * @param appKey
	 * @param secret
	 */
	void setSecretByAppKey(String appKey, String secret);
}
