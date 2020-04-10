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

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author huangjy
 * @since v1.0
 */
@Service
@Retry(name = "retryA")
public class HelloResilience4jService {
    @Autowired
    RestTemplate restTemplate;

    public String hello() {
        return restTemplate.getForObject("http://myprovider/hello", String.class);
    }
}
