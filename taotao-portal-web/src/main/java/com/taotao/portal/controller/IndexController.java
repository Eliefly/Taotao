package com.taotao.portal.controller;

import com.taotao.common.pojo.Content;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @Autowired
    private ContentService contentService;

    @Value("${AD1_CATEGORY_ID}")
    private Long bigAdCategoryId;

    /**
     * 显示首页
     *
     * @return 跳转到首页
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String toIndex(Model model) {

        String bigAdImage = contentService.queryContentByCid(bigAdCategoryId);

        model.addAttribute("bigAdImage", bigAdImage);


        return "index";
    }

}
