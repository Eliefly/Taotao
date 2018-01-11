package com.taotao.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* 页面跳转控制器
*
* @author eliefly
* @date 2018-01-08
*/
@Controller
@RequestMapping("page")
public class PageController {

    /**
     * 通用页面的跳转
     *
     * @param pageName 页面名称(无.jsp后缀)
     * @return 跳转页面
     */
    @RequestMapping("{pageName}")
    public String toPage(@PathVariable("pageName") String pageName){
        return pageName;
    }
}
