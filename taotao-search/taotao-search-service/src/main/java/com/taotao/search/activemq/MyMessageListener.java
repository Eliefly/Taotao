package com.taotao.search.activemq;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.search.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 消息监听
 * 1. 	判断消息类型是否是TextMessage，如果是强转消息为TextMessage
 * 2. 	获取消息内容getText()
 * 3. 	解析消息MAPPER.readTree(msg)
 * 4. 	获取操作类型get(key)
 * 5. 	获取操作商品id，get(key)
 * 6. 	调用Search服务，跟据id查询商品，更新索引库
 *
 * @author eliefly
 * @date 2018-01-13
 */
public class MyMessageListener implements MessageListener {

    private final static ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private SearchService searchService;

    @Override
    public void onMessage(Message message) {

        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;

            try {
                String msg = textMessage.getText();

                if (StringUtils.isNotBlank(msg)) {

                    // Jackson 把json转为建设你哦
                    JsonNode jsonNode = MAPPER.readTree(msg);
                    String type = jsonNode.get("type").asText();
                    Long itemId = jsonNode.get("itemId").asLong();

                    System.out.println("type: " + type);
                    System.out.println("itemId: " + itemId);

                    searchService.saveItem(itemId);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
