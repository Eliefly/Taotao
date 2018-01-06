package com.taotao.service.impl;

import com.taotao.common.pojo.Item;
import com.taotao.common.pojo.ItemDesc;
import com.taotao.service.ItemDescService;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ItemServiceImpl
 * @create 2018-01-06 11:44
 */
@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {

    @Autowired
    private ItemDescService itemDescService;

    @Override
    public void saveItem(Item item, String desc) {

        // 保存商品
        item.setStatus(1);
        super.save(item);

        // 保存商品描述
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDescService.save(itemDesc);
    }
}
