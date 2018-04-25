package org.waithua.common.aoptest.cglibaop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Created by jch on 18/4/23.
 */
public class CglibProxy implements MethodInterceptor {
    /**
     *
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return null;
    }
}
