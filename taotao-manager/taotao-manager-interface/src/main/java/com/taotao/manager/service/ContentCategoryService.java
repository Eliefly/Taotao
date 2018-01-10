package com.taotao.manager.service;

import com.taotao.pojo.ContentCategory;

import java.util.List;

/**
 * ContentCategoryService
 *
 * @author eliefly
 * @create 2018-01-07
 */
public interface ContentCategoryService extends BaseService<ContentCategory> {

    /**
     * 通过父id查询内容分类
     *
     * @param parentId 父id
     * @return
     */
    List<ContentCategory> queryContentCategoryByParentId(Long parentId);

    /**
     * 保存内容分类
     *
     * @param contentCategory
     * @return
     */
    ContentCategory saveContentCategory(ContentCategory contentCategory);

    /**
     * 删除内容分类
     *
     * @param parentId 父id
     * @param id       自身id
     */
    void deleteContentCategory(Long parentId, Long id);
}
