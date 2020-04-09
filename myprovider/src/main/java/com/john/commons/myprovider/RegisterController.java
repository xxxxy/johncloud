/**
 * <html>
 * <body>
 * <p> All rights reserved.</p>
 * <p> Created on 2020-03-30</p>
 * <p> Created by huangjy</p>
 * </body>
 * </html>
 */
package com.john.commons.myprovider;

import com.john.commons.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author huangjy
 * @since v1.0
 */
@Controller
public class RegisterController {

    @PostMapping("/registry")
    public String registry(User user) {
        return "redirect:http://myprovider/loginPage?username=" + user.getUsername();
    }

    @GetMapping("/loginPage")
    @ResponseBody
    public String loginPage(String username) {
        return "loginPage:" + username;
    }
}
