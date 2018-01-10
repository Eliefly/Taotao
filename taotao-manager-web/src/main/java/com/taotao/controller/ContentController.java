package com.taotao.controller;

import com.taotao.pojo.Content;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.manager.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 广告内容控制器
 *
 * @author eliefly
 * @date 2018-01-07
 */
@Controller()
@RequestMapping("content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    /**
     * 添加内容
     *
     * @param content 传入的内容
     * @return 是否添加成功
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String save(Content content) {

        String msg = "0";

        try {
            contentService.save(content);
        } catch (Exception e) {
            msg = "1";
        }

        return msg;

    }

    /**
     * 通过内容分类id分页查询内容
     *
     * @param page 当前页码
     * @param rows 数据条数
     * @return 数据表格数据
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public EasyUIDataGridResult list(@RequestParam(value = "categoryId", defaultValue = "0") Long categoryId,
                                     @RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "rows", defaultValue = "20") Integer rows) {

        return contentService.queryContentPageByCid(categoryId, page, rows);

    }

}
