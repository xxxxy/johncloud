/**
 * <html>
 * <body>
 * <p> All rights reserved.</p>
 * <p> Created on 2020/4/13</p>
 * <p> Created by huangjy</p>
 * </body>
 * </html>
 */
package com.john.configclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author huangjy
 * @since v1.0
 */
@RestController
public class HelloConfigController {
    @Value("${john}")
    private String s;

    @RequestMapping("/helloConfig")
    public String helloConfig(){
        return s;
    }
}
