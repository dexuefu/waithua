package org.waithua.common.aoptest.cglibaop;

/**
 * Created by jch on 18/4/23.
 */
public class UserServiceImpl implements UserService {
    @Override
    public void test() {
        System.out.println("UserService 逻辑执行。。。。");
    }
}
