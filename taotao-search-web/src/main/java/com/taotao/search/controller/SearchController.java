package com.taotao.search.controller;

import com.taotao.common.pojo.PageBean;
import com.taotao.pojo.Item;
import com.taotao.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 淘淘搜索控制器
 *
 * @author eliefly
 * @date 2018-01-13
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Value("${SEARCH_TAOTAO_ITEM_ROWS}")
    private Integer pageNums;

    /**
     * 商品搜索
     *
     * @param model 数据模型
     * @param query 搜索参数
     * @param page  当前页码
     * @return 跳转页面名
     */
    @RequestMapping(method = RequestMethod.GET)
    public String search(Model model,
                         @RequestParam(value = "q") String query,
                         @RequestParam(value = "page", defaultValue = "1") Integer page) {

        System.out.println(query);

        PageBean<Item> pageBean = searchService.search(query, page, pageNums);

        // 数据加入模型中, 在前台页面回显
        model.addAttribute("query", query);

        model.addAttribute("itemList", pageBean.getRows());

        model.addAttribute("page", page);

        model.addAttribute("totalPages", pageBean.getTotalPages());

        return "search";

    }

}
