/**
 * <html>
 * <body>
 * <p> All rights reserved.</p>
 * <p> Created on 2020-03-25</p>
 * <p> Created by huangjy</p>
 * </body>
 * </html>
 */
package com.john.commons.myconsumer;

import com.alibaba.fastjson.JSON;
import com.john.commons.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huangjy
 * @since v1.0
 */
@RestController
public class HelloConsumerController {
    @Autowired
    DiscoveryClient discoveryClient;

    @GetMapping("/helloConsumer")
    public String helloConsumer() {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://localhost:1114/hello");
            connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String s = reader.readLine();
                reader.close();
                return s;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    @Autowired
    @Qualifier("restTemplate")
    RestTemplate restTemplate;

    int count = 0;
    @GetMapping("/helloConsumer1")
    public String helloConsumer1() {
        List<ServiceInstance> myprovider = discoveryClient.getInstances("myprovider");
        //ServiceInstance serviceInstance = myprovider.get(myprovider.size() % 2);
        ServiceInstance serviceInstance = myprovider.get((count++) % myprovider.size());

        String host = serviceInstance.getHost();
        int port = serviceInstance.getPort();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("http://")
                .append(host)
                .append(":")
                .append(port)
                .append("/hello");
         String forObject = restTemplate.getForObject(stringBuilder.toString(), String.class);
        return forObject;
    }

    @Autowired
    @Qualifier("restTemplateOne")
    RestTemplate restTemplateOne;

    /**
     * 使用负载均衡的方式调用
     *
     * @return
     */
    @GetMapping("/helloConsumer2")
    public String helloConsumer2() {
        return restTemplateOne.getForObject("http://myprovider/hello", String.class);
    }

    @GetMapping("/helloConsumerGet")
    public void helloConsumerGet() throws UnsupportedEncodingException {
        String forObject = restTemplateOne.getForObject("http://myprovider/helloGet?name={1}", String.class, "john");
        System.out.println(forObject);

        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        String forObject1 = restTemplateOne.getForObject("http://myprovider/helloGet?name={name}", String.class, map);
        System.out.println(forObject1);

        //处理中文乱码的情况
        String url = "http://myprovider/helloGet?name=" + URLEncoder.encode("张三", "UTF-8");
        URI uri = URI.create(url);
        String forObject2 = restTemplateOne.getForObject(uri, String.class);
        System.out.println(forObject2);

        ResponseEntity<String> huang = restTemplateOne.getForEntity("http://myprovider/helloGet?name={1}", String.class, "huang");
        System.out.println(huang);

    }

    @GetMapping("/helloConsumerPost")
    public void helloConsumerPost() {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("id", "1");
        map.add("username", "john");
        map.add("password", "123");
        User user = restTemplateOne.postForObject("http://myprovider/addUser", map, User.class);
        System.out.println(JSON.toJSONString(user));

        user.setId(2);
        User user1 = restTemplateOne.postForObject("http://myprovider/addUser1", user, User.class);
        System.out.println(JSON.toJSONString(user1));
    }

    //请求之后的重定向,例如:注册完成之后,跳转到登录页
    @GetMapping("/helloConsumerPostForLocation")
    public void helloConsumerPostForLocation() {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("id", "1");
        map.add("username", "john");
        map.add("password", "123");
        URI uri = restTemplateOne.postForLocation("http://myprovider/registry", map, User.class);
        System.out.println(uri);

        String forObject = restTemplateOne.getForObject(uri, String.class);
        System.out.println(forObject);
    }

    @GetMapping("/helloConsumerPut")
    public void helloConsumerPut() {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("id", "1");
        map.add("username", "john");
        map.add("password", "123");
        restTemplateOne.put("http://myprovider/updateUser", map);

        User user = new User();
        user.setId(2);
        user.setUsername("john");
        user.setPassword("123");
        restTemplateOne.put("http://myprovider/updateUser2", user);
    }

    @GetMapping("/helloConsumerDelete")
    public void helloConsumerDelete() {
        restTemplateOne.delete("http://myprovider/deleteUser?id={1}", 1);
        restTemplateOne.delete("http://myprovider/deleteUser2/{id}", 2);
    }
}
