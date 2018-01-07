package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.Item;

/**
 * @author eliefly
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
