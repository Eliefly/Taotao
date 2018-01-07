package com.taotao.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 门户首页
 *
 * @author eliefly
 * @create 2018-01-07 16:32
 */
@Controller
@RequestMapping("index")
public class IndexController {

    /**
     * 显示首页
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String toIndex() {

        return "index";
    }

}
