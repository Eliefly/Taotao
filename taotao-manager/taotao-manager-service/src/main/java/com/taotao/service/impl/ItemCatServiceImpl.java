package com.taotao.service.impl;

import com.taotao.pojo.ItemCat;
import com.taotao.service.ItemCatService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemCatServiceImpl extends BaseServiceImpl<ItemCat> implements ItemCatService {

    @Override
    public List<ItemCat> queryItemCatByParentId(Long parentId) {

        ItemCat itemCat = new ItemCat();

        itemCat.setParentId(parentId);

        return super.queryListByWhere(itemCat);

    }

/*  改造  ItemCatServiceImpl 继承  BaseServiceImpl, 直接使用 父类的分页查询
    @Autowired
    private ItemCatMapper itemCatMapper;

    @Override
    public List<ItemCat> queryItemByPage(Integer page, Integer rows) {

        PageHelper.startPage(page, rows);

        return itemCatMapper.select(null);
    }*/



}
