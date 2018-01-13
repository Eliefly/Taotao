package com.taotao.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * solr集群测试
 *
 * @author eliefly
 * @date 2018-01-13
 */
public class SolrCloudTest {
    private CloudSolrServer cloudSolrServer;

    @Before
    public void init() {
        // 声明接口地址
        String zkHost = "192.168.56.101:3181,192.168.56.101:3182,192.168.56.101:3183";
        // 创建HttpSolrServer
        this.cloudSolrServer = new CloudSolrServer(zkHost);
        // 设置collection
        this.cloudSolrServer.setDefaultCollection("collection2");
    }

    @Test
    public void testCreateAndUpdateIndex() throws Exception {

        // 创建SolrInputDocument对象
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", "c1001");
        document.addField("item_title", "java编程思想");

        // 把SolrInputDocument对象添加到索引库中
        this.cloudSolrServer.add(document);

        // 4. 提交
        this.cloudSolrServer.commit();
    }

    @Test
    public void testDeleteIndex() throws Exception {
        // 根据id删除索引数据
        // this.httpSolrServer.deleteById("c1001");

        // 根据条件删除（如果是*:*就表示全部删除，慎用）
        this.cloudSolrServer.deleteByQuery("*:*");

        // 提交
        this.cloudSolrServer.commit();
    }

    @Test
    public void testSearchIndex() throws Exception {
        // 创建搜索对象
        SolrQuery solrQuery = new SolrQuery();
        // 设置查询条件
        solrQuery.setQuery("item_title:java");

        // 设置分页
        solrQuery.setStart(0);
        solrQuery.setRows(10);

        // 设置高亮
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<font color=\"red\">");
        solrQuery.setHighlightSimplePost("</font>");

        // 查询数据
        QueryResponse response = this.cloudSolrServer.query(solrQuery);
        SolrDocumentList results = response.getResults();

        System.out.println("搜索到的数据总条数：" + results.getNumFound());

        Map<String, Map<String, List<String>>> map = response.getHighlighting();

        // 解析查询结果
        for (SolrDocument solrDocument : results) {
            System.out.println("----------------------------------------------------");
            // 获取高亮数据
            List<String> list = map.get(solrDocument.get("id")).get("item_title");

            System.out.println("商品ID：" + solrDocument.get("id"));
            // 显示高亮
            if (list != null && list.size() > 0) {
                System.out.println("商品名称：" + list.get(0));
            } else {
                System.out.println("商品名称：" + solrDocument.get("item_title"));
            }
        }
    }

}
