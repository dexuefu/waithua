package org.waithua.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jch on 17/1/12.
 */
@RestController
@SpringBootApplication
public class WaithuaWebApplication {
    @RequestMapping("/")
    String home() {
        return "Hello Waithua!";
    }
    public static void main(String[] args) {
        SpringApplication.run(WaithuaWebApplication.class, args);
    }
}
