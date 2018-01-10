package com.tatao.service.test;

import com.taotao.manager.service.ContentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ContentServiceTest
 *
 * @author eliefly
 * @date 2018-01-08
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-*.xml")
public class ContentServiceTest {

    @Autowired
    private ContentService contentService;


    @Test
    public void test01() {

        String s = contentService.queryContentByCid(6L);

        System.out.println(s);

    }

}
