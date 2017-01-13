package org.waithua.web.service;

import org.waithua.web.entity.User;

import java.util.List;

/**
 * Created by jch on 17/1/12.
 */
public interface UserReadService {

    public User login();

    public Long Regist();

    public User getUserById();

    public List<User> getUserList();
}
