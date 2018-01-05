package com.tatao.test;

import com.taotao.mapper.ItemCatMapper;
import com.taotao.mapper.TestMapper;
import com.taotao.pojo.ItemCat;
import com.taotao.service.ItemCatService;
import com.taotao.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-*.xml")
public class MapperTest {

//    @Autowired
//    private TestMapper testMapper;

    @Autowired
    private TestService testService;

    @Autowired
    private ItemCatMapper itemCatMapper;

    @Autowired
    private ItemCatService itemCatService;


    @Test
    public void test01() {

//        testMapper.queryNow();
        testService.queryNow();
    }

    @Test
    public void test02() {

        List<ItemCat> list = itemCatMapper.select(null);

        for (ItemCat itemCat : list) {

            System.out.println(itemCat);
        }
    }

    @Test
    public void test03() {

//        List<ItemCat> list = itemCatService.queryItemByPage(1, 10);
        List<ItemCat> list = itemCatService.queryByPage(1, 10);

        for (ItemCat itemCat : list) {

            System.out.println(itemCat);
        }
    }

}
