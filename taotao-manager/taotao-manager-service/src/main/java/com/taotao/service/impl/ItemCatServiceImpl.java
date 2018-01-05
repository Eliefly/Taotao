package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.taotao.mapper.ItemCatMapper;
import com.taotao.pojo.ItemCat;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemCatServiceImpl extends BaseServiceImpl<ItemCat> implements ItemCatService {

/*  改造  ItemCatServiceImpl 继承  BaseServiceImpl, 直接使用 父类的分页查询
    @Autowired
    private ItemCatMapper itemCatMapper;

    @Override
    public List<ItemCat> queryItemByPage(Integer page, Integer rows) {

        PageHelper.startPage(page, rows);

        return itemCatMapper.select(null);
    }*/



}
