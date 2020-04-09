/**
 * <html>
 * <body>
 * <p> All rights reserved.</p>
 * <p> Created on 2020/4/9</p>
 * <p> Created by huangjy</p>
 * </body>
 * </html>
 */
package com.john.hystrix;

import com.john.commons.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * @author huangjy
 * @since v1.0
 */
@Service
public class UserService {
    @Autowired
    RestTemplate restTemplate;

    public List<User> getUsersByIds(List<Integer> ids) {
        User[] forObject = restTemplate.getForObject("http://myprovider/getUsersByIds/{1}", User[].class, StringUtils.join(ids, ","));
        List<User> userList = Arrays.asList(forObject);
        return userList;
    }
}
