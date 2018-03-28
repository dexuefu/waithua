/**
 *
 * 日    期：12-2-13
 */
package com.ice.router;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;

/**
 * <pre>
 * </pre>
 * @version 1.0
 */
public interface Router {

	/**
	 * ice框架的总入口，一般框架实现，开发者无需关注。
	 * 
	 * @param request
	 * @param response
	 */
	void service(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 初始化设置Spring的上下文
	 * @param servletConfig
	 * @param applicationContext
	 */
	void init(ServletConfig servletConfig, ApplicationContext applicationContext);

	/**
	 * servlet销毁
	 */
	void destroy();
}
