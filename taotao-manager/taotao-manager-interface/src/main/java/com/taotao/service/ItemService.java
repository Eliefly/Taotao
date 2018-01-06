package com.taotao.service;

import com.taotao.common.pojo.Item;

public interface ItemService extends BaseService<Item> {

    /**
     * 保存商品
     *
     * @param item
     * @param desc
     */
    void saveItem(Item item, String desc);

}
