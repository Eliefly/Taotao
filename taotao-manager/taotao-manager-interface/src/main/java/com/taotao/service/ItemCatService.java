package com.taotao.service;

import com.taotao.pojo.ItemCat;

import java.util.List;

public interface ItemCatService {

    /**
     * 分页查询商品类目
     *
     * @param page
     * @param rows
     * @return
     */
    List<ItemCat> queryItemByPage(Integer page, Integer rows);
}
