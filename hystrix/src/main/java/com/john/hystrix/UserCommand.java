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
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.List;

/**
 *
 * @author huangjy
 * @since v1.0
 */
public class UserCommand extends HystrixCommand<List<User>> {
    private List<Integer> ids;

    private UserService userService;

    public UserCommand(List<Integer> ids, UserService userService) {
        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("batchCmd")));
        this.ids = ids;
        this.userService = userService;
    }

    @Override
    protected List<User> run() throws Exception {
        return userService.getUsersByIds(ids);
    }
}
