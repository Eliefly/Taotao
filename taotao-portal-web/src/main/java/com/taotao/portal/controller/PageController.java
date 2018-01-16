package com.taotao.portal.controller;

import com.taotao.manager.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 页面跳转控制器
 *
 * @author eliefly
 * @date 2018-01-08
 */
@Controller
@RequestMapping("page")
public class PageController {

    @Autowired
    private ContentService contentService;

    @Value("${AD1_CATEGORY_ID}")
    private Long bigAdCategoryId;

    /**
     * 通用页面的跳转
     *
     * @param pageName 页面名称(无.jsp后缀)
     * @return 跳转页面
     */
    @RequestMapping("{pageName}")
    public String toPage(@PathVariable("pageName") String pageName, Model model,
                         @RequestParam(value = "redirectURL", defaultValue = "") String redirectURL) {

        final String indexPage = "index";

        if (indexPage.equals(pageName)) {

            String bigAdImage = contentService.queryContentByCid(bigAdCategoryId);
            model.addAttribute("bigAdImage", bigAdImage);

        }

        model.addAttribute("redirectURL", redirectURL);

        return pageName;
    }
}