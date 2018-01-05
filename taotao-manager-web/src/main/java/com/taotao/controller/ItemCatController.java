package com.taotao.controller;

import com.taotao.pojo.ItemCat;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("item/cat")
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    /**
     * 商品类目查询
     *
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("query/{page}")
    @ResponseBody
    public List<ItemCat> queryItemByPage(@PathVariable("page") Integer page, @RequestParam("rows") Integer rows) {

//        return itemCatService.queryItemByPage(page, rows);

        return itemCatService.queryByPage(page, rows);
    }
}
