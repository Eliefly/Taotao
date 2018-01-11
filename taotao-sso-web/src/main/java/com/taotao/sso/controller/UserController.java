package com.taotao.sso.controller;

import com.taotao.pojo.User;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.security.auth.callback.Callback;

/**
 * UserController
 *
 * @author eliefly
 * @date 2018-01-10
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 检查数据是否有用
     *
     * @param param 要验证的内容
     * @param type  要验证数据的类型. 可选参数1、2、3分别代表username、phone、email.
     * @return true：数据可用，false：数据不可用
     */
    @RequestMapping(value = "check/{param}/{type}", method = RequestMethod.GET)
    public ResponseEntity<String> check(@PathVariable(value = "param") String param,
                                        @PathVariable(value = "type") Integer type,
                                        String callback) {
        final Integer minType = 1;
        final Integer maxType = 3;

        if (type < minType || type > maxType) {
            // 400 1、语义有误，当前请求无法被服务器理解。除非进行修改，否则客户端不应该重复提交这个请求。 2、请求参数有误。
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        try {
            Boolean result = userService.check(param, type);

            String resultStr = "";

            // 如果传入callback参数, 说明要使用 jsonp (跨域获取json格式数据)
            if (StringUtils.isNotBlank(callback)) {

                resultStr = callback + "(" + result + ")";

            } else {
                resultStr += result;
            }

            // 200
            return ResponseEntity.ok(resultStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }

    /**
     * 通过ticker查询用户
     *
     * @param ticket redis保存用户的键数据
     * @return 响应数据, 如果查询到用户, 则封装用户数据
     */
    @RequestMapping(value = "{ticket}", method = RequestMethod.GET)
    public ResponseEntity<User> queryUserByTicker(@PathVariable(value = "ticket") String ticket) {

        try {
            User user = userService.queryUserByTicker(ticket);

            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                // 404 	请求失败，请求所希望得到的资源未被在服务器上发现。
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }

}
