package com.ice.session;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 功能说明：
 * </pre>
 * 
 * @version 1.0
 */
public  class IceSession implements Session {

	private static final long serialVersionUID = 4878830302746711528L;
	
	private Map<String, Object> attributes = new HashMap<String, Object>();

	@Override
	public void setAttribute(String name, Object obj) {

		attributes.put(name, obj);
	}

	@Override
	public Object getAttribute(String name) {

		return attributes.get(name);
	}

	@Override
	public Map<String, Object> getAllAttributes() {
		Map<String, Object> tempAttributes = new HashMap<String, Object>(
				attributes.size());
		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			tempAttributes.put(entry.getKey(), entry.getValue());
		}
		return tempAttributes;
	}

	@Override
	public void removeAttribute(String name) {

		attributes.remove(name);
	}

}
