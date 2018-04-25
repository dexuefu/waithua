package org.waithua.common.aoptest.jdkaop;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;

/**
 * Created by jch on 18/4/23.
 */
public class App {
    public static void main(String[] args) {
        UserServiceImpl impl = new UserServiceImpl();
        UserService service =
                (UserService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                        impl.getClass().getInterfaces(), new MyInvocationHandler(impl));
        service.test();
        //这里将动态代理生成的类文件写入磁盘，方便反编译
        byte[] classFile = ProxyGenerator.generateProxyClass("$Proxy11",
                UserServiceImpl.class.getInterfaces());

        FileOutputStream out = null;

        try {
            out = new FileOutputStream("/Users/jch/work/output/clazz.class");
            out.write(classFile);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
