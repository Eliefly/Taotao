package com.taotao.manager.service.impl;

import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.util.JsonUtils;
import com.taotao.manager.service.ItemDescService;
import com.taotao.manager.service.ItemService;
import com.taotao.pojo.Item;
import com.taotao.pojo.ItemDesc;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.HashMap;
import java.util.List;

/**
 * @author eliefly
 * @create 2018-01-06 11:44
 */
@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {

    @Autowired
    private ItemDescService itemDescService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Destination destination;

    /**
     * 保存商品
     *
     * @param item 商品
     * @param desc 商品描述
     */
    @Override
    public void saveItem(Item item, String desc) {

        // 保存商品
        item.setStatus(1);
        super.save(item);

        // 保存商品描述
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDescService.save(itemDesc);

        // 保存商品时, 使用ActiveMQ发送消息, (操作符 + 商品id). 消息订阅者可进行solr索引, redis缓存等进行更新
        sendMessage("save", item.getId());
    }

    /**
     * activeMQ 发送消息
     *
     * @param opreator 操作符
     * @param id       商品id
     */
    private void sendMessage(final String opreator, final Long id) {

        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {

                ActiveMQTextMessage textMessage = new ActiveMQTextMessage();

                // 使用json格式封装消息
                HashMap<String, Object> map = new HashMap<>();

                map.put("type", opreator);
                map.put("itemId", id);

                try {

                    String jsonStr = JsonUtils.objectToJson(map);
                    textMessage.setText(jsonStr);

                } catch (Exception e) {

                    e.printStackTrace();
                }

                return textMessage;
            }
        });

    }

    /**
     * 分页查询商品
     *
     * @param page 页码
     * @param rows 数据列表
     * @return easyui表格数据
     */
    @Override
    public EasyUIDataGridResult queryItemList(Integer page, Integer rows) {

        List<Item> items = super.queryByPage(page, rows);

        PageInfo<Item> pageInfo = new PageInfo<>(items);

        EasyUIDataGridResult result = new EasyUIDataGridResult();

        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());

        return result;
    }
}
