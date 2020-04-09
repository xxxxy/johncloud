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
import feign.hystrix.FallbackFactory;

/**
 * 使用FallbackFactory的方式进行服务降级
 *
 * @author huangjy
 * @since v1.0
 */
public class HelloFallbackFactory implements FallbackFactory<HelloFeign> {

    @Override
    public HelloFeign create(Throwable throwable) {
        return new HelloFeign() {
            @Override
            public String hello() {
                return "error";
            }

            @Override
            public String helloGet(String name) {
                return null;
            }

            @Override
            public User addUser(User user) {
                return null;
            }

            @Override
            public void get3(String name) {

            }
        };
    }
}
