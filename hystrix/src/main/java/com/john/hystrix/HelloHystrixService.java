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

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

/**
 * @author huangjy
 * @since v1.0
 */
@Service
public class HelloHystrixService {

    @Autowired
    RestTemplate restTemplate;

    /**
     * 在方法上面添加HystrixCommand注解,配置fallbackMethod,当访问调用失败时,系统会调用对应的方法.
     * <p>
     * ignoreExceptions 不降级某些异常
     *
     * @return
     */
    @HystrixCommand(fallbackMethod = "error", ignoreExceptions = ArithmeticException.class)
    public String hello() {
        return restTemplate.getForObject("http://myprovider/hello", String.class);
    }

    @HystrixCommand(fallbackMethod = "error3")
    @CacheResult//缓存的key就是方法的参数
    public String hello3(@CacheKey String name, Integer age) {
        return restTemplate.getForObject("http://myprovider/helloGet?name={1}", String.class, name);
    }

    /**
     * 这个方法要和fallbackMethod一致
     * 捕获异常信息
     *
     * @return
     */
    public String error(Throwable throwable) {
        return "error:" + throwable.getMessage();
    }

    public String error3(String name) {
        return "error" + name;
    }

    /**
     * 异步调用
     *
     * @return
     */
    @HystrixCommand(fallbackMethod = "error")
    public Future<String> hello2() {
        return new AsyncResult<String>() {
            @Override
            public String invoke() {
                return restTemplate.getForObject("http://myprovider/hello", String.class);
            }
        };
    }
}
