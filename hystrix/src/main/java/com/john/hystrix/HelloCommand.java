/**
 * <html>
 * <body>
 * <p> All rights reserved.</p>
 * <p> Created on 2020-04-01</p>
 * <p> Created by huangjy</p>
 * </body>
 * </html>
 */
package com.john.hystrix;

import com.netflix.hystrix.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 * @author huangjy
 * @since v1.0
 */
public class HelloCommand extends HystrixCommand<String> {

    @Autowired
    RestTemplate restTemplate;

    private String name;

    public HelloCommand(Setter setter, RestTemplate restTemplate) {
        super(setter);
        this.restTemplate = restTemplate;
    }

    @Override
    protected String run() throws Exception {
        return restTemplate.getForObject("http://myprovider/hello", String.class);
    }

    @Override
    protected String getCacheKey() {
        return name;
    }

    @Override
    protected String getFallback() {
        return "error By Extends" + getExecutionException().getMessage();
    }
}
