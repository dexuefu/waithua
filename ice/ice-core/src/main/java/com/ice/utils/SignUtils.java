package com.ice.utils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * <pre>
 * 功能说明：
 * </pre>
 * 
 * @author <a href="mailto:gengcai.xie@skyroam.com">Xie Gengcai</a>
 * @version 1.0
 */
public class SignUtils {

	/**
	 * @param paramNames
	 *            需要签名的参数名
	 * @param paramValues
	 *            参数列表
	 * @param secret
	 * @return
	 */
	public static String sign(Map<String, String> paramValues, String secret) {
		return sign(paramValues, null, secret);
	}

	/**
	 * 对paramValues进行签名，其中noSignParamNames这些参数不参与签名
	 * 
	 * @param paramValues
	 * @param noSignParamNames
	 * @param secret
	 * @return
	 */
	public static String sign(Map<String, String> paramValues,
			List<String> noSignParamNames, String secret) {
		try {
			StringBuilder buffer = new StringBuilder();
			List<String> paramNames = new ArrayList<String>(paramValues.size());
			paramNames.addAll(paramValues.keySet());
			if (noSignParamNames != null && noSignParamNames.size() > 0) {
				for (String ignoreParamName : noSignParamNames) {
					paramNames.remove(ignoreParamName);
				}
			}
			Collections.sort(paramNames);

			buffer.append(secret);
			for (String paramName : paramNames) {
				buffer.append(paramName).append(paramValues.get(paramName));
			}
			buffer.append(secret);
			byte[] sha1Digest = getSHA1Digest(buffer.toString());
			return byte2hex(sha1Digest);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 只留下a-zA-Z0-9的字符来签名，更方便兼容其他语言
	 * @param data
	 */
	private static byte[] getSHA1Digest(String data) throws IOException {
		data = Pattern.compile("[^a-zA-Z0-9]").matcher(data).replaceAll("");
		byte[] bytes = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			bytes = md.digest(data.getBytes("UTF-8"));
		} catch (GeneralSecurityException gse) {
			throw new IOException(gse);
		}
		return bytes;
	}

	/**
	 * 二进制转十六进制字符串
	 * 
	 * @param bytes
	 */
	private static String byte2hex(byte[] bytes) {
		StringBuilder sign = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sign.append("0");
			}
			sign.append(hex.toUpperCase());
		}
		return sign.toString();
	}

}
