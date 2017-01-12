package org.waithua.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.waithua.web.entity.User;

/**
 * Created by jch on 17/1/12.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @RequestMapping("/login")
    public User login() {
        System.out.println("login");
        User user = new User();
        user.setId(1l);
        user.setName("name");
        user.setPassword("12345678");
        user.setSex("1");
        return user;
    }
}
