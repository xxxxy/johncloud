/**
 * <html>
 * <body>
 * <p> All rights reserved.</p>
 * <p> Created on 2020-03-31</p>
 * <p> Created by huangjy</p>
 * </body>
 * </html>
 */
package com.john.consulprovider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangjy
 * @since v1.0
 */
@RestController
public class ConsulProviderlController {

    @Value("${server.port}")
    Integer port;

    @GetMapping("/helloConsul")
    public String helloConsul() {
        return "hello,port:" + port;
    }
}
