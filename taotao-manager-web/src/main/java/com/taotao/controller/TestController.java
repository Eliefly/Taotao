package com.taotao.controller;

import com.taotao.manager.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * TestController
 *
 * @author eliefly
 * @date 2018-01-07
 */
@Controller
public class TestController {

    @Autowired
    private TestService testService;

    /**
     * 测试
     *
     * @return 当前时间
     */
    @RequestMapping("/queryNow")
    @ResponseBody
    public String queryNow() {
        //1.引入服务
        //2.注入服务
        //3.调用服务的方法
        //4.返回
        return testService.queryNow();
    }
}