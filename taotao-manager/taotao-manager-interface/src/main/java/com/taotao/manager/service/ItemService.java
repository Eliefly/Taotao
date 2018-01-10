package com.taotao.manager.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.pojo.Item;

/**
* ItemService
*
* @author eliefly
* @date 18/1/7
*/
public interface ItemService extends BaseService<Item> {

    /**
     * 保存商品
     *
     * @param item
     * @param desc
     */
    void saveItem(Item item, String desc);

    /**
     * 分页查询商品
     *
     * @param page
     * @param rows
     * @return
     */
    EasyUIDataGridResult queryItemList(Integer page, Integer rows);
}
