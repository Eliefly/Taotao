package com.taotao.portal.interceptor;

import com.taotao.common.util.CookieUtils;
import com.taotao.pojo.User;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 订单结算拦截. 如果用户未登录拦截跳转到登录页.
 *
 * @author eliefly
 * @date 2018-01-15
 */
public class OrderHandlerInterceptor implements HandlerInterceptor {

    @Value("${TT_TICKET_COOKIE_NAME}")
    private String taotaoTicketCookieName;

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String ticket = CookieUtils.getCookieValue(httpServletRequest, taotaoTicketCookieName);

        // 用户未登录
        if (StringUtils.isBlank(ticket)) {
            // 重定向到登录页面
            // 跳转到登录页面, 需要保存用户现有的请求地址, 如果登录成功, 应该跳转到这个页面
            String redirectURL = httpServletRequest.getRequestURL().toString();
            httpServletResponse.sendRedirect("/page/login?redirectURL=" + redirectURL);
            return false;
        }

        // 使用ticket从redis中查询用户
        User user = userService.queryUserByTicker(ticket);

        // 用户登录超时
        if (user == null) {
            httpServletResponse.sendRedirect("/page/login");
            return false;
        }

        // 用户已登录, 把user放入request中, controller直接从request中取
        httpServletRequest.setAttribute("user", user);

        // 放行, 进入controller
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
