package com.taotao.service.impl;

import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.Content;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.util.JsonUtils;
import com.taotao.service.ContentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public String queryContentByCid(Long categoryId) {

        Content param = new Content();
        param.setCategoryId(categoryId);

        List<Content> contents = super.queryListByWhere(param);

        // 遍历内容，把内容封装到List<Map>中，根据前端数据格式进行封装
        ArrayList<Map<String, Object>> results = new ArrayList<>();
        for (Content content : contents) {
            HashMap<String, Object> map = new HashMap<>(6);
            map.put("srcB", content.getPic());
            map.put("height", 240);
            map.put("width", 670);
            map.put("alt", content.getTitleDesc());
            map.put("src", content.getPic2());
            map.put("widthB", 550);
            map.put("heightB", 240);
            map.put("href", content.getUrl());

            results.add(map);
        }

        return JsonUtils.objectToJson(results);
    }
}
