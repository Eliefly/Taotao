package com.taotao.manager.service;

import com.taotao.pojo.Content;
import com.taotao.common.pojo.EasyUIDataGridResult;

/**
 * ContentService
 *
 * @author eliefly
 * @create 2018-01-07
 */
public interface ContentService extends BaseService<Content> {

    /**
     * 通过内容分类id分页查询内容
     * @param categoryId 内容id
     * @param page 当前页码
     * @param rows 数据条数
     * @return 数据表格数据
     */
    EasyUIDataGridResult queryContentPageByCid(Long categoryId, Integer page, Integer rows);

    /**
     * 根据内容id查询内容
     * @param categoryId 内容id
     * @return 结果字符串
     */
    String queryContentByCid(Long categoryId);
}
