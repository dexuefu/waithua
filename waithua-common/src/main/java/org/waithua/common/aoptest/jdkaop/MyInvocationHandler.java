package org.waithua.common.aoptest.jdkaop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by jch on 18/4/23.
 * 代理类真正执行的是InvocationHandler的invoke方法
 */
public class MyInvocationHandler implements InvocationHandler {
    private Object target;

    public MyInvocationHandler(Object obj) {
        this.target = obj;
    }
    /**
     * 真正执行的是InvocationHandler的invoke方法
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Befroe invoke .............");
        Object r = method.invoke(target, args);
        System.out.println("After invoke ...............");
        return r;
    }
}
