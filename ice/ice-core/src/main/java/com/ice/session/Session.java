package com.ice.session;

import java.io.Serializable;
import java.util.Map;

/**
 */
public interface Session extends Serializable {

    /**
     * 设置属性
     * @param name
     * @param obj
     */
    void setAttribute(String name, Object object);

    /**
     * 获取属性
     * @param name
     * @return
     */
    Object getAttribute(String name);

    /**
     * 获取所有的属性
     * @return
     */
    Map<String, Object> getAllAttributes();

    /**
     * 删除属性条目
     * @param name
     */
    void removeAttribute(String name);

    

}