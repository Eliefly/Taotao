package com.taotao.search.service;

import com.taotao.common.pojo.PageBean;
import com.taotao.pojo.Item;

/**
 * SearchService
 *
 * @author eliefly
 * @date 2018-01-13
 */
public interface SearchService {

    /**
     * 商品搜索
     *
     * @param query    搜索参数
     * @param page     页码
     * @param pageNums 每页显示的记录数
     * @return
     */
    PageBean<Item> search(String query, Integer page, Integer pageNums);
}
