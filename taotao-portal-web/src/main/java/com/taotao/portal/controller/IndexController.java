package com.taotao.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 门户首页
 *
 * @author eliefly
 * @date 2018-01-07
 */
@Controller
public class IndexController {

    /**
     * 显示首页
     *
     * @return 跳转到首页
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String toIndex() {

        return "index";
    }

}
