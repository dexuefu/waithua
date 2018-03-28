package com.ice;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import com.ice.exception.IceException;
import com.ice.router.Router;

/**
 * @version 1.0
 */
public class IceServlet extends HttpServlet {

	private static final long serialVersionUID = 8092313047926951512L;
	
	private Router router;

	/**
	 * 将请求导向到ice的框架中。
	 * 
	 * @param request
	 * @param respone
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse respone) throws ServletException, IOException {
		router.service(request, respone);
	}

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		ApplicationContext ctx = (ApplicationContext) servletConfig
				.getServletContext()
				.getAttribute(
						WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		this.router = ctx.getBean(Router.class);
		if (this.router != null) {
			this.router.init(servletConfig, ctx);
		} else {
			throw new IceException("在Spring容器中未找到");
		}
 
	}

	@Override
	public void destroy() {
		this.router.destroy();
	}

}
