package com.tatao.service.test;

import com.taotao.manager.service.ContentCategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ContentCategoryTest
 *
 * @author eliefly
 * @date 2018-01-07
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-*.xml")
public class ContentCategoryTest {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @Test
    public void test01() {

        contentCategoryService.deleteContentCategory(4L, 5L);

    }

}
