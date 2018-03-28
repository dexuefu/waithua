package com.ice.interfaces;

import com.ice.exception.IceErrorResponse;

/**
 * 访问次数、频率服务,可以针对 appKey, sessionId, method ,IP进行限制
 */
public interface InvokeService {

	 /**
     * 计算应用、会话及用户服务调度总数
     * @param appKey
     * @param session
     */
    void countTimes(String appKey, String method, String version, String ip, String sessionId);
    
    /**
     * 用户服务访问超限
     * @param session
     * @return
     */
    IceErrorResponse invokeLimit(String appKey, String method, String version, String ip, String sessionId);

 
}
