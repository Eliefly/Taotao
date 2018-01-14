package com.taotao.search.service.impl;

import com.taotao.common.pojo.PageBean;
import com.taotao.mapper.ItemMapper;
import com.taotao.pojo.Item;
import com.taotao.search.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * SearchServiceImpl
 *
 * @author eliefly
 * @date 2018-01-13
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private CloudSolrServer cloudSolrServer;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public PageBean<Item> search(String query, Integer page, Integer pageNums) {

        SolrQuery solrQuery = new SolrQuery();

        // 设置查询语句
        if (StringUtils.isNotBlank(query)) {
            solrQuery.setQuery("item_title:" + query + " AND item_status:1");
        } else {
            solrQuery.setQuery("item_status:1");
        }

        // 设置分页
        solrQuery.setStart((page - 1) * pageNums);
        solrQuery.setRows(pageNums);

        // 设置高亮
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");
        PageBean<Item> pageBean = null;
        try {
            QueryResponse response = cloudSolrServer.query(solrQuery);
            SolrDocumentList results = response.getResults();
            // 返回结果
            pageBean = new PageBean<>(new Long(results.getNumFound()).intValue(), pageNums, page);

            // 获取高亮数据
            Map<String, Map<String, List<String>>> map = response.getHighlighting();

            ArrayList<Item> items = new ArrayList<>();

            for (SolrDocument solrDocument : results) {
                Item item = new Item();
                item.setId(Long.parseLong(solrDocument.get("id").toString()));

                // 获取高亮的字段
                List<String> hightList = map.get(solrDocument.get("id").toString()).get("item_title");
                if (hightList != null && hightList.size() > 0) {
                    item.setTitle(hightList.get(0));
                } else {
                    item.setTitle(solrDocument.get("item_title").toString());
                }

                // 商品图片
                item.setImage(solrDocument.get("item_image").toString());

                // 商品价格
                item.setPrice(Double.parseDouble(solrDocument.get("item_price").toString()));

                // 商品cid
                item.setCid(Long.parseLong(solrDocument.get("item_cid").toString()));

                items.add(item);

            }

            pageBean.setRows(items);

        } catch (SolrServerException e) {
            e.printStackTrace();
        }

        return pageBean;
    }

    @Override
    public void saveItem(Long itemId) {
        Item item = itemMapper.selectByPrimaryKey(itemId);

        // 创建SolrInputDocument对象，调用add方法构建文档内容
        SolrInputDocument document = new SolrInputDocument();

        document.setField("id", item.getId().toString());
        document.setField("item_title", item.getTitle());
        document.setField("item_price", item.getPrice());
        document.setField("item_image", item.getImage());
        document.setField("item_cid", item.getCid());
        document.setField("item_status", item.getStatus());

        try {
            // 保存到索引库中
            this.cloudSolrServer.add(document);
            this.cloudSolrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
