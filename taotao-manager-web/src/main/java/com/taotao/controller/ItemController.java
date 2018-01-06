package com.taotao.controller;

import com.taotao.pojo.Item;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品控制器
 *
 * @author ItemController
 * @create 2018-01-06 10:52
 */
@Controller
@RequestMapping("item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String save(Item item, String desc) {

        String msg = "0";

        try {
            itemService.saveItem(item, desc);
        } catch (Exception e) {
            e.printStackTrace();
            // 商品添加失败
            msg = "1";
        }

        return msg;
    }

}
