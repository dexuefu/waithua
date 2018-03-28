
package com.ice.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="mailto:gengcai.xie@skyroam.com">Xie Gengcai</a>
 */
public class ResponseUtil {

	/**
	 * 写json
	 * @param result
	 * @param  response
	 */
	public static void wirteJson(Object result, HttpServletResponse response) {
		PrintWriter out;
		try {
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.addHeader("Access-Control-Allow-Methods", "*");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			out = response.getWriter();
			out.print(JsonUtils.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 写xml
	 * @param result
	 * @param  response
	 */
	public static void wirteXml(Object result, HttpServletResponse response) {
		PrintWriter out;
		try {
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.addHeader("Access-Control-Allow-Methods", "*");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/xml");
			out = response.getWriter();
			String xmlString =  XmlUitls.toXml(result);
			out.print(xmlString);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
