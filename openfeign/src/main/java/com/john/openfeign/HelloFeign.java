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

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author huangjy
 * @since v1.0
 */
@FeignClient("myprovider")
public interface HelloFeign {

    @GetMapping("/hello")
    String hello();
}
