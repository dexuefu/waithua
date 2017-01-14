package org.waithua.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jch on 17/1/13.
 */
@Controller
public class Demo {
    /**
     * 登录页
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String login(HttpServletRequest request, HttpServletResponse response, ModelMap model){
        System.out.println("index");
        return "/index";
    }
}
