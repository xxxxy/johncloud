/**
 * <html>
 * <body>
 * <p> All rights reserved.</p>
 * <p> Created on 2020/4/11</p>
 * <p> Created by huangjy</p>
 * </body>
 * </html>
 */
package com.john.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author huangjy
 * @since v1.0
 */
@Component
public class PermissionFilter extends ZuulFilter {

    /**
     * 过滤器类型,权限判断一般是pre
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器顺序
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否过滤
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 核心过滤逻辑
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (!Objects.equals("john", username) || !Objects.equals("123456", password)) {
            //如果请求条件不满足,直接给出响应
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(401);
            currentContext.addZuulResponseHeader("content-type", "text/html; charset=utf-8");
            currentContext.setResponseBody("非法访问");
        }
        return null;
    }
}
