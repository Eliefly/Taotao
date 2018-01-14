package com.taotao.item.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.manager.service.ItemDescService;
import com.taotao.manager.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

/**
 * ItemMessageListener
 *
 * @author eliefly
 * @date 2018-01-14
 */
public class ItemMessageListener implements MessageListener {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    private ItemService itemService;

    @Autowired
    ItemDescService itemDescService;

    @Value("${ITEM_TAOTAO_HTML_PATH}")
    private String itemTaotaoHtmlPath;

    @Override
    public void onMessage(Message message) {

        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;

            String typeSave = "save";

            try {
                String msg = textMessage.getText();
                if (StringUtils.isNotBlank(msg)) {
                    JsonNode jsonNode = MAPPER.readTree(msg);

                    String type = jsonNode.get("type").asText();
                    Long itemId = jsonNode.get("itemId").asLong();

                    if (typeSave.equals(type)) {
                        // 根据商品id生成静态化页面
                        createHtml(itemId);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 生成商品的静态页面
     *
     * @param itemId 商品id
     */
    private void createHtml(Long itemId) throws Exception {
        // 获取 freemarker 核心对象, 使用spring整合的对象获取
        Configuration configuration = freeMarkerConfigurer.getConfiguration();

        Template template = configuration.getTemplate("item.ftl");

        // 设置模型数据
        HashMap<String, Object> root = new HashMap<>(1);

        root.put("item", itemService.queryById(itemId));
        root.put("itemDesc", itemDescService.queryById(itemId));

        // 指定页面输出位置
        FileWriter out = new FileWriter(new File(itemTaotaoHtmlPath + itemId + ".html"));

        // 输出页面
        template.process(root, out);

    }
}
