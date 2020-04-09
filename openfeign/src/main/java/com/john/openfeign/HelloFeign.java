/**
 * <html>
 * <body>
 * <P> Copyright 2017 www.figo.cn Group.</p>
 * <p> All rights reserved.</p>
 * <p> Created on 2020/4/9</p>
 * <p> Created by huangjy</p>
 * </body>
 * </html>
 */
package com.john.openfeign;

import com.john.commons.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author huangjy
 * @since v1.0
 */
@FeignClient(value = "myprovider", fallback = HelloServiceFallBack.class)
public interface HelloFeign {

    @GetMapping("/hello")
    String hello();

    @GetMapping("/helloGet")
    String helloGet(@RequestParam("name") String name);

    @PostMapping("/addUser1")
    User addUser(@RequestBody User user);

    @GetMapping("/get3")
    void get3(@RequestHeader("name") String name);
}

@Component
class HelloServiceFallBack implements HelloFeign {

    @Override
    public String hello() {
        return "error";
    }

    @Override
    public String helloGet(String name) {
        return null;
    }

    @Override
    public User addUser(User user) {
        return null;
    }

    @Override
    public void get3(String name) {

    }
}