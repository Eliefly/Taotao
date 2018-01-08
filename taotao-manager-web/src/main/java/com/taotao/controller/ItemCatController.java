package com.taotao.controller;

import com.taotao.common.pojo.ItemCat;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 商品类目控制器
*
* @author eliefly
* @date 2018-01-08
*/
@RequestMapping("item/cat")
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    /**
     * 商品类目查询
     *
     * @param page 页码
     * @param rows  页面显示条数
     * @return 页面数据
     */
    @RequestMapping("query/{page}")
    @ResponseBody
    public List<ItemCat> queryItemByPage(@PathVariable("page") Integer page, @RequestParam("rows") Integer rows) {

        // 重构: return itemCatService.queryItemByPage(page, rows);
        return itemCatService.queryByPage(page, rows);
    }

    /**
     * 通过 ParentId 查找商品类目
     *
     * @param parentId 父节点id
     * @return 商品类目信息
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<ItemCat> queryItemByParentId(@RequestParam(name = "id", defaultValue = "0") Long parentId) {
        // 第一次请求是没有id参数，需要设置默认的parentId为0，查询一级类目

        return itemCatService.queryItemCatByParentId(parentId);

    }
}
