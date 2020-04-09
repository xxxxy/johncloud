/**
 * <html>
 * <body>
 * <p> All rights reserved.</p>
 * <p> Created on 2020-03-31</p>
 * <p> Created by huangjy</p>
 * </body>
 * </html>
 */
package com.john.hystrix;

import com.john.commons.User;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author huangjy
 * @since v1.0
 */
@RestController
public class HelloHystrixController {

    @Autowired
    HelloHystrixService helloHystrixService;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/helloHystrix")
    public String helloHystrix() {
        return helloHystrixService.hello();
    }

    @GetMapping("/helloHystrix2")
    public String helloHystrix2() {
        //两种获取返回值的方式
        HelloCommand command1 = new HelloCommand(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("john")), restTemplate);
        String execute = command1.execute();
        System.out.println("execute:" + execute);

        HelloCommand command2 = new HelloCommand(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("john")), restTemplate);
        Future<String> queue = command2.queue();
        try {
            String s = queue.get();
            System.out.println("get:" + s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return execute;
    }

    @GetMapping("/helloHystrix3")
    public void helloHystrix3() {
        Future<String> stringFuture = helloHystrixService.hello2();
        try {
            String s = stringFuture.get();
            System.out.println(s);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/helloHystrix4")
    public void HelloHystrix4(@RequestParam String name, @RequestParam Integer age) {
        HystrixRequestContext hystrixRequestContext = HystrixRequestContext.initializeContext();
        String s = helloHystrixService.hello3(name, age);
        System.out.println(s);

        String s2 = helloHystrixService.hello3(name, age);
        System.out.println(s2);

        hystrixRequestContext.close();
    }

    @Autowired
    UserService userService;

    @GetMapping("/helloHystrix5")
    public void helloHystrix5() throws ExecutionException, InterruptedException {
        HystrixRequestContext hystrixRequestContext = HystrixRequestContext.initializeContext();
        UserCollapseCommand collapseCommand = new UserCollapseCommand(userService, 1);
        UserCollapseCommand collapseCommand2 = new UserCollapseCommand(userService, 2);
        UserCollapseCommand collapseCommand3 = new UserCollapseCommand(userService, 3);
        Future<User> queue = collapseCommand.queue();
        Future<User> queue2 = collapseCommand2.queue();
        Future<User> queue3 = collapseCommand3.queue();
        User user = queue.get();
        User user2 = queue2.get();
        User user3 = queue3.get();
        System.out.println(user);
        System.out.println(user2);
        System.out.println(user3);

        Thread.sleep(2000);
        UserCollapseCommand collapseCommand4 = new UserCollapseCommand(userService, 4);
        Future<User> queue4 = collapseCommand4.queue();
        User user4 = queue4.get();
        System.out.println(user4);
        hystrixRequestContext.close();
    }

    @GetMapping("/helloHystrix6")
    public void helloHystrix6() throws ExecutionException, InterruptedException {
        HystrixRequestContext hystrixRequestContext = HystrixRequestContext.initializeContext();
        Future<User> usersByIds = userService.getUsersByIds(1);
        Future<User> usersByIds2 = userService.getUsersByIds(2);
        Future<User> usersByIds3 = userService.getUsersByIds(3);

        System.out.println(usersByIds.get());
        System.out.println(usersByIds2.get());
        System.out.println(usersByIds3.get());

        Thread.sleep(2000);
        Future<User> usersByIds4 = userService.getUsersByIds(4);
        System.out.println(usersByIds4.get());
        hystrixRequestContext.close();
    }
}
