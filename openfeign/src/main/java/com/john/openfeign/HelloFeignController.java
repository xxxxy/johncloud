/**
 * <html>
 * <body>
 * <p> All rights reserved.</p>
 * <p> Created on 2020/4/9</p>
 * <p> Created by huangjy</p>
 * </body>
 * </html>
 */
package com.john.openfeign;

import com.john.commons.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author huangjy
 * @since v1.0
 */
@RestController
public class HelloFeignController {

    @Autowired
    HelloFeign helloFeign;

    @GetMapping("/helloFeign")
    public String hello() {
        return helloFeign.hello();
    }

    @GetMapping("/helloFeign2")
    public String helloGet() {
        return helloFeign.helloGet("john");
    }

    @GetMapping("/addUser")
    public void addUser() {
        User user = new User();
        user.setId(1);
        user.setPassword("123");
        user.setUsername("张三");
        User user1 = helloFeign.addUser(user);
        System.out.println(user1);
    }

    @GetMapping("/get3")
    public void get3() throws UnsupportedEncodingException {
        helloFeign.get3(URLEncoder.encode("中文思密达", "UTF-8"));
    }
}
