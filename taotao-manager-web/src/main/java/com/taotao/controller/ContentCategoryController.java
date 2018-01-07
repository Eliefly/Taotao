package com.taotao.controller;

import com.taotao.common.pojo.ContentCategory;
import com.taotao.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 内容分类控制器
 *
 * @author eliefly
 * @date 2018-01-07
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    /**
     * 删除内容分类
     * @param parentId 父id
     * @param id 自身id
     * @return 删除是否成功
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(Long parentId, Long id) {
        String msg = "0";

        try {
            contentCategoryService.deleteContentCategory(parentId, id);
        } catch (Exception e) {
            msg = "1";
        }

        return msg;
    }

    /**
     * 更新内容分类
     *
     * @param contentCategory 传入的内容分类
     * @return 更新是否成功
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public String update(ContentCategory contentCategory) {

        String msg = "0";

        try {
            contentCategoryService.updateByIdSelective(contentCategory);
        } catch (Exception e) {
            msg = "1";
        }

        return msg;
    }

    /**
     * 添加内容分类
     *
     * @param contentCategory 传入的内容分类
     * @return 新增的内容分类
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ContentCategory add(ContentCategory contentCategory) {

        return contentCategoryService.saveContentCategory(contentCategory);

    }

    /**
     * 通过父id查询内容分类
     *
     * @param parentId 传入的 parentId
     * @return List<ContentCategory>
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<ContentCategory> queryContentCategoryByParentId(@RequestParam(value = "id", defaultValue = "0") Long parentId) {

        return contentCategoryService.queryContentCategoryByParentId(parentId);
    }

}
