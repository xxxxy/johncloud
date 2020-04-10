/**
 * <html>
 * <body>
 * <p> All rights reserved.</p>
 * <p> Created on 2020/4/10</p>
 * <p> Created by huangjy</p>
 * </body>
 * </html>
 */
package com.john.resilience4jcloud;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author huangjy
 * @since v1.0
 */
@Service
//@Retry(name = "retryA")
@CircuitBreaker(name = "cbA", fallbackMethod = "error")
public class HelloResilience4jService {
    @Autowired
    RestTemplate restTemplate;

    public String hello() {
        for (int i = 0; i < 5; i++) {
            restTemplate.getForObject("http://myprovider/hello", String.class);
        }
        return "success";
    }

    public String error(Throwable throwable) {
        String message = throwable.getMessage();
        System.out.println(message);
        return "error";
    }
}
