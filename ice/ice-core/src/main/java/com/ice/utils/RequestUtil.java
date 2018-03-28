
package com.ice.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ice.config.SystemParameterNames;

/**
 * @author <a href="mailto:gengcai.xie@skyroam.com">Xie Gengcai</a>
 */
public class RequestUtil {

	/**
	 * 获取地址
	 * @param request
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		// nginx反向代理
		String remoteIp = request.getHeader("X-Real-IP");
		if (StringUtils.hasText(remoteIp)) {
			return remoteIp;
		} else {
			// apache反射代理
			remoteIp = request.getHeader("X-Forwarded-For");
			if (StringUtils.hasText(remoteIp)) {
				String[] ips = remoteIp.split(",");
				for (String ip : ips) {
					if (!"null".equalsIgnoreCase(ip)) {
						return ip;
					}
				}
			}
			return request.getRemoteAddr();
		}
	}
	/**
	 * 构建所有的请求参数封装成 map
	 * @param request
	 */
	public static Map<String, String> getRequestParams(HttpServletRequest request) {
		Map<String, String[]> srcParamMap = request.getParameterMap();
		
		//过滤掉文件
		if(request instanceof MultipartHttpServletRequest){
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
			if(multipartHttpServletRequest.getFileNames() != null){
				while(multipartHttpServletRequest.getFileNames().hasNext()){
					srcParamMap.remove(multipartHttpServletRequest.getFileNames().next());
				}
			}
		}
		HashMap<String, String> destParamMap = new HashMap<String, String>(
				srcParamMap.size());
		for (Object obj : srcParamMap.keySet()) {
			String[] values = (String[]) srcParamMap.get(obj);
			if (values != null && values.length > 0) {
				destParamMap.put((String) obj, values[0]);
			} else {
				destParamMap.put((String) obj, null);
			}
		}
		return destParamMap;
	}

	/**
	 * 获取uuid for String
	 */
	public static String getUuid(){
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	
	/**
	 * 是否是json 
	 */
    public static boolean getIsJson(HttpServletRequest request)
    {
		String format = request.getParameter(SystemParameterNames.getFormat());
		if("xml".equals(format)){
			 return false;
		}
		return true;
	}
	/**
	 * 是否是json 
	 */
	public static boolean getIsJson(String format){
		if("xml".equals(format)){
			 return false;
		}
		return true;
	}
}
