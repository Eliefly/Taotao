package com.taotao.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * 单击版solr测试
 *
 * @author eliefly
 * @date 2018-01-12
 */
public class SolrTest {

    private HttpSolrServer httpSolrServer;

    @Before
    public void init() {

        // 声明接口地址
        String baseURL = "http://solr.taotao.com";

        // 创建HttpSolrServer
        httpSolrServer = new HttpSolrServer(baseURL);

    }

    /**
     * 添加索引域
     *
     * @throws Exception 抛出异常
     */
    @Test
    public void testSolrAddAndUpdate() throws Exception {

        // 2. 创建SolrInputDocument对象，调用add方法构建文档内容
        SolrInputDocument document = new SolrInputDocument();

        /*
         * 3.添加域到文档中
         * 参数1：域的名称
         * 参数2：域的值 一定需要添加id域
         */
//        test_solr_id1:我感觉很好！
//        test_solr_id2:我感觉好极了！
//        test_solr_id3:我感觉奇妙极了！
//        test_solr_id4:世界美好！

//        document.addField("id", "test_solr_id1");
//        document.addField("title", "我感觉很好");

//        document.addField("id", "test_solr_id2");
//        document.addField("title", "我感觉好极了");

//        document.addField("id", "test_solr_id3");
//        document.addField("title", "我感觉奇妙极了");

        document.addField("id", "test_solr_id4");
        document.addField("title", "世界美好");

        // 更新文档
        // document.addField("title", "title更新了....");

        // 4.添加文档到索引库中
        httpSolrServer.add(document);

        // 5.提交
        httpSolrServer.commit();

    }

    /**
     * testDeleteById:根据ID删除索引.
     *
     * @throws Exception 抛出异常
     */
    @Test
    public void testDeleteById() throws Exception {

        // 2.直接根据Id删除
        httpSolrServer.deleteById("test_id_01");
        // 3.提交
        httpSolrServer.commit();
    }

    /**
     * testDeleteByQuery:根据查询结果删除.
     *
     * @throws Exception 抛出异常
     */
    @Test
    public void testDeleteByQuery() throws Exception {

        // 2.创建查询的对象
        SolrQuery query = new SolrQuery();
        // 3.设置查询的条件
        query.setQuery("title:测试solr");
        // 4.执行删除
        httpSolrServer.deleteByQuery(query.getQuery());
        // solrServer.deleteByQuery("product_name:solrJ测试商品.");
        // 5.提交
        httpSolrServer.commit();
    }

    /**
     * testQuery:简单查询. <br/>
     *
     * @throws Exception 抛出异常
     */
    @Test
    public void testQuery() throws Exception {
        // 2.创建查询的对象
        SolrQuery query = new SolrQuery();
        // 3.设置查询的条件
        query.setQuery("*:*");
        // 4.执行查询
        QueryResponse response = httpSolrServer.query(query);
        // 5.获取结果集 查询了多少数据
        SolrDocumentList results = response.getResults();
        System.out.println(">>>>>>>>size>>>>>" + results.size());
        System.out.println("查询命中的数量>>>" + results.getNumFound());
        // 6.遍历结果集
        for (SolrDocument solrDocument : results) {
            // 7.打印
            System.out.println(solrDocument.get("id") + "-->" + solrDocument.get("title"));
        }
    }

    /**
     * fuzaQuery:复杂的查询.
     *
     * @throws Exception 抛出异常
     */
    @Test
    public void fuzaQuery() throws Exception {

        // 2.创建查询的对象
        SolrQuery query = new SolrQuery();
        // 3.设置主查询条件
        query.setQuery("title:好");
        // 4.设置各种各样的过滤条件
        // 4.1设置价格区间
//        query.addFilterQuery("id:[10 TO 60]");
//        query.setFilterQueries("title:我");
        // 4.2设置id排序
        query.setSort("id", ORDER.asc);
        // 4.3 分页 page-1 * rows
        query.setStart(0);
        query.setRows(5);
        // 4.4 设置显示的域的列表
        query.setFields("id", "title");

        // 4.5 设置默认的搜索域
        query.set("df", "title");

        query.set("wt", "json");

        // 4.6 开启高亮
        query.setHighlight(true);

        // 4.7 设置高亮显示的域 设置前缀 和后缀
        query.addHighlightField("title");

        query.setHighlightSimplePre("<em color='red'>");
        query.setHighlightSimplePost("</em>");
        // 5.执行查询
        QueryResponse response = httpSolrServer.query(query);
        // 6.获取结果集
        SolrDocumentList results = response.getResults();
        System.out.println("命中数：" + results.getNumFound());
        // 7.遍历结果集
        // 取高亮
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

        for (SolrDocument solrDocument : results) {
            List<String> list = highlighting.get(solrDocument.get("id")).get("title");
            // 判断是否为空
            String titles = "";
            if (list != null && list.size() > 0) {
                // 有高亮
                titles = list.get(0);
            } else {
                // 没有高亮
                titles = solrDocument.get("title").toString();
            }
            System.out.println(titles);
            System.out.println(solrDocument.get("id"));
        }
    }

}
