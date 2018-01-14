package com.taotao.item.controller;

import com.taotao.manager.service.ItemDescService;
import com.taotao.manager.service.ItemService;
import com.taotao.pojo.Item;
import com.taotao.pojo.ItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ItemController
 *
 * @author eliefly
 * @date 2018-01-14
 */
@Controller
@RequestMapping("item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemDescService itemDescService;

    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    public String toItem(@PathVariable(value = "itemId") Long itemId, Model model) {

        Item item = itemService.queryById(itemId);

        ItemDesc itemDesc = itemDescService.queryById(itemId);

        model.addAttribute("item", item);
        model.addAttribute("itemDesc", itemDesc);

        return "item";

    }

}
