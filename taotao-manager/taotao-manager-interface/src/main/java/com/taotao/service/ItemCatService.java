package com.taotao.service;

import com.taotao.common.pojo.ItemCat;

import java.util.List;

/**
* ItemCatService
*
* @author eliefly
* @date 18/1/7
*/
public interface ItemCatService extends BaseService<ItemCat> {

    /**
     * 通过 ParentId 查找商品类目
     *
     * @param parentId
     * @return
     */
    List<ItemCat> queryItemCatByParentId(Long parentId);

    /**
     * 分页查询商品类目
     *
     * @param page
     * @param rows
     * @return
     */
//    List<ItemCat> queryItemByPage(Integer page, Integer rows);
}
