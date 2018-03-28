
package com.ice.interfaces;

import com.ice.session.Session;

/**
 *
 */
public interface SessionService {

	/**
	 * 获取会话
	 * @param  appKey
	 */
	Session getSession(String sessionId);
	
	/**
	 * 设置session
	 * @param  sessionId
	 * @param session
	 */
	void setSession(String sessionId, Session session);
}
