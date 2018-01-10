package com.taotao.manager.service.impl;

import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.pojo.Item;
import com.taotao.pojo.ItemDesc;
import com.taotao.manager.service.ItemDescService;
import com.taotao.manager.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author eliefly
 * @create 2018-01-06 11:44
 */
@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {

    @Autowired
    private ItemDescService itemDescService;

    /**
     * 保存商品
     *
     * @param item
     * @param desc
     */
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

    /**
     * 分页查询商品
     * @param page
     * @param rows
     * @return
     */
    @Override
    public EasyUIDataGridResult queryItemList(Integer page, Integer rows) {

        List<Item> items = super.queryByPage(page, rows);

        PageInfo<Item> pageInfo = new PageInfo<>(items);

        EasyUIDataGridResult result = new EasyUIDataGridResult();

        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());

        return result;
    }
}
