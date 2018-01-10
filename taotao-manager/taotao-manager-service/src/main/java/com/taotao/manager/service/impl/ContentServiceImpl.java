package com.taotao.manager.service.impl;

import com.github.pagehelper.PageInfo;
import com.taotao.pojo.Content;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.util.JsonUtils;
import com.taotao.redis.impl.JedisClientCluster;
import com.taotao.manager.service.ContentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private JedisClientCluster jedisClientCluster;

    @Value("${TAOTAO_PORTAL_BIG_AD}")
    private String taotaoPortalBigAd;

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

        /*
         * 首页大广告, 访问频率高, 更新频率低, 在服务层和持久层加了redis集群缓存.
         * 先从redis查询数据，如果redis没有数据，再去MySQL查询.
         */
        // 1.先从redis查询数据
        try {
            String redisJson = jedisClientCluster.get(taotaoPortalBigAd);
            if (StringUtils.isNotBlank(redisJson)) {
                return redisJson;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2.redis中没有, 从MySql数据库查询
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

        // JsonUtils的工具类，可以把对象转json格式的数据
        String jsonResult = JsonUtils.objectToJson(results);

        // 把数据存放到redis
        jedisClientCluster.set(taotaoPortalBigAd, jsonResult, 60 * 60 * 24);

        return jsonResult;
    }
}
