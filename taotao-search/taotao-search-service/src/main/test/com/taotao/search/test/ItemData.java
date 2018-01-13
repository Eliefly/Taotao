package com.taotao.search.test;

import com.github.pagehelper.PageHelper;
import com.taotao.mapper.ItemMapper;
import com.taotao.pojo.Item;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * ItemData
 *
 * @author eliefly
 * @date 2018-01-13
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-*.xml")
public class ItemData {

    @Autowired
    private CloudSolrServer cloudSolrServer;

    @Autowired
    private ItemMapper itemMapper;

//    @Before
//    public void init() {
//        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext-*.xml");
//
//        // 从容器中获取声明的bean
//        this.cloudSolrServer = context.getBean(CloudSolrServer.class);
//        this.itemMapper = context.getBean(ItemMapper.class);
//
//    }

    /**
     * 所有商品数据导入solr集群, 建立索引
     * @throws Exception 抛出异常
     */
    @Test
    public void testItemData() throws Exception {
        // 可以使用循环来获取所有的商品数据
        int i = 1, pagesize = 500;
        do {
            // 分页查询商品数据
            PageHelper.startPage(i, pagesize);
            List<Item> list = this.itemMapper.select(null);

            // 遍历结果，把商品放到索引库中
            List<SolrInputDocument> docs = new ArrayList<>();
            for (Item item : list) {
                // 把获取的商品数据，批量导入到solr索引库中
                SolrInputDocument document = new SolrInputDocument();
                // 商品id
                document.setField("id", item.getId().toString());
                // 商品名称
                document.setField("item_title", item.getTitle());
                // 商品价格
                document.setField("item_price", item.getPrice());
                // 商品图片
                if (StringUtils.isNotBlank(item.getImage())) {
                    document.setField("item_image", StringUtils.split(item.getImage(), ",")[0]);
                } else {
                    document.setField("item_image", "");
                }
                // 商品类目id
                document.setField("item_cid", item.getCid());
                // 商品状态
                document.setField("item_status", item.getStatus());

                // 把Document放到集合中，统一提交
                docs.add(document);
            }

            // 把数据保存在solr索引库中
            this.cloudSolrServer.add(docs);
            this.cloudSolrServer.commit();

            i++;
            pagesize = list.size();

        } while (pagesize == 500);

    }

}
