/**
 * <html>
 * <body>
 * <p> All rights reserved.</p>
 * <p> Created on 2020-03-31</p>
 * <p> Created by huangjy</p>
 * </body>
 * </html>
 */
package com.john.consulconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author huangjy
 * @since v1.0
 */
@RestController
public class ConsulConsumerController {

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/helloConsulConsumer")
    public String helloConsulConsumer() {
        ServiceInstance choose = loadBalancerClient.choose("consul-provider");
        System.out.println("服务地址:" + choose.getUri());
        System.out.println("服务名称:" + choose.getServiceId());

        String forObject = restTemplate.getForObject(choose.getUri()+"/helloConsul", String.class);
        System.out.println(forObject);
        return "hello";
    }
}
