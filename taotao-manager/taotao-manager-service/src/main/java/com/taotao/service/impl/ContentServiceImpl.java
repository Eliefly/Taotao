package com.taotao.service.impl;

import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.Content;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.service.ContentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ContentServiceImpl
 *
 * @author eliefly
 * @date 2018-01-07
 */
@Service
public class ContentServiceImpl extends BaseServiceImpl<Content> implements ContentService {

    @Override
    public EasyUIDataGridResult queryContentPageByCid(Long categoryId, Integer page, Integer rows) {

        List<Content> contents = super.queryByPage(page, rows);

        PageInfo<Content> pageInfo = new PageInfo<>(contents);

        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(contents);

        return result;
    }
}
