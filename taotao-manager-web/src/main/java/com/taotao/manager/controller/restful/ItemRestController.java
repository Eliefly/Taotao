package com.taotao.manager.controller.restful;

import com.taotao.pojo.Item;
import com.taotao.manager.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * RestFUl 风格商品控制器
 *
 * @author eliefly
 * @date 2018-01-09
 */
@Controller
@RequestMapping("rest/item")
public class ItemRestController {

    @Autowired
    private ItemService itemService;

    /**
     * 根据商品id查询商品
     *
     * @param id 商品id
     * @return 响应实体
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Item> queryItemById(@PathVariable(value = "id") Long id) {
        //返回的是ResponseEntity或者加上@ResponseBody注解的效果是一样的，任选其一即可，也可以都设置。

        try {
            Item item = itemService.queryById(id);
            // 设置相应码及实体
            return ResponseEntity.ok().body(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 添加商品
     *
     * @param item 商品
     * @return 响应实体
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Item> saveItem(Item item) {

        try {
            itemService.saveItem(item, null);
            // 201
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 更新商品
     *
     * @param item 商品
     * @return 响应实体
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateItem(Item item) {

        System.out.println(item);
        try {
            itemService.updateByIdSelective(item);
            // 204
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 删除商品
     *
     * @param id 商品id
     * @return 相应实体
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteItem(@PathVariable(value = "id") Long id) {

        try {
            itemService.deleteById(id);
            // 204
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

}
