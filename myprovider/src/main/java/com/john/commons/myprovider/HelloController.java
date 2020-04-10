/**
 * <html>
 * <body>
 * <p> All rights reserved.</p>
 * <p> Created on 2020-03-25</p>
 * <p> Created by huangjy</p>
 * </body>
 * </html>
 */
package com.john.commons.myprovider;

import com.john.commons.User;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author huangjy
 * @since v1.0
 */
@RestController
public class HelloController {

    @Value("${server.port}")
    Integer port;

    @GetMapping("/hello")
    //添加限流
    @RateLimiter(name = "rlA")
    public String hello() {
        System.out.println("hello:" + port);
        System.out.println(new Date());
        //int a = 1 / 0;
        return "hello john; port:" + port;
    }

    @GetMapping("/helloGet")
    public String helloGet(String name) {
        System.out.println(new Date() + ">>>" + name);
        return "hello " + name;
    }

    @GetMapping("/getUsersByIds/{ids}")
    public List<User> getUsersByIds(@PathVariable String ids) {
        String[] split = ids.split(",");
        List<User> userList = new ArrayList<>();
        for (String s : split) {
            User user = new User();
            user.setId(Integer.parseInt(s));
            userList.add(user);
        }
        System.out.println(userList);
        return userList;
    }

    /**
     * 使用key-value的形式传递
     *
     * @param user user
     * @return
     */
    @PostMapping("/addUser")
    public User addUser(User user) {
        return user;
    }

    /**
     * 使用json的形式传递user
     *
     * @param user user
     * @return
     */
    @PostMapping("/addUser1")
    public User addUser1(@RequestBody User user) {
        return user;
    }

    @PutMapping("/updateUser")
    public void updateUser(User user) {
        System.out.println(user);
    }

    @PutMapping("/updateUser2")
    public void updateUser2(@RequestBody User user) {
        System.out.println(user);
    }

    @DeleteMapping("/deleteUser")
    public void delete(@RequestParam Integer id) {
        System.out.println(id);
    }

    @DeleteMapping("/deleteUser2/{id}")
    public void delete2(@PathVariable Integer id) {
        System.out.println(id);
    }

    @GetMapping("/get3")
    public void get3(@RequestHeader String name) throws UnsupportedEncodingException {
        System.out.println(URLDecoder.decode(name, "UTF-8"));
    }
}
