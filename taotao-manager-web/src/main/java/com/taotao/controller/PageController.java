package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("page")
public class PageController {

    /**
     * 通用页面的跳转
     *
     * @param pageName
     * @return
     */
    @RequestMapping("{pageName}")
    public String toPage(@PathVariable("pageName") String pageName){
        return pageName;
    }
}
