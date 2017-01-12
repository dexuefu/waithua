package org.waithua.web;

/**
 * Created by jch on 17/1/12.
 */
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
