package com.taotao.portal.controller;

import com.taotao.common.util.CookieUtils;
import com.taotao.pojo.User;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * UserController
 *
 * @author eliefly
 * @date 2018-01-11
 */
@Controller
@RequestMapping("/service/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${COOKIE_NAME}")
    private String cookieName;

    /**
     * 注册用户
     *
     * @param user 用户参数
     * @return 响应
     */
    @RequestMapping(value = "doRegister", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doRegister(User user) {

        userService.doRegister(user);
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", 200);
        return map;

    }

    /**
     * 用户登录
     *
     * @param user     用户参数
     * @param request  http请求
     * @param response http响应
     * @return 响应
     */
    @RequestMapping(value = "doLogin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doLogin(User user, HttpServletRequest request, HttpServletResponse response) {

        // 调用服务进行用户登录，需要返回ticket，目的是放到cookie中用户
        String ticket = userService.doLogin(user);

        // 判断ticket是否为空，如果不为空表示用户登录成功
        if (StringUtils.isNotBlank(ticket)) {
            // 如果登录成功ticket需要放到cookie中
            CookieUtils.setCookie(request, response, cookieName, ticket, 60 * 60 * 24, true);
            HashMap<String, Object> map = new HashMap<>();
            map.put("status", 200);
            return map;
        }

        return null;

    }

}
