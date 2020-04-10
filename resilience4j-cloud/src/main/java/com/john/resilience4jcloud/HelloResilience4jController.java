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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangjy
 * @since v1.0
 */
@RestController
public class HelloResilience4jController {

    @Autowired
    HelloResilience4jService helloResilience4jService;

    @GetMapping("/helloResilience4j")
    public String hello() {
        return helloResilience4jService.hello();
    }
}
