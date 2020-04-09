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
import org.springframework.web.bind.annotation.*;

/**
 * @author huangjy
 * @since v1.0
 */
@FeignClient("myprovider")
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
