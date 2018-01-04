package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.taotao.mapper.ItemCatMapper;
import com.taotao.pojo.ItemCat;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;

    @Override
    public List<ItemCat> queryItemByPage(Integer page, Integer rows) {

        PageHelper.startPage(page, rows);

        return itemCatMapper.select(null);
    }
}
