package com.tatao.test;

import com.taotao.mapper.TestMapper;
import com.taotao.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-*.xml")
public class MapperTest {

//    @Autowired
//    private TestMapper testMapper;

    @Autowired
    private TestService testService;

    @Test
    public void test01() {

//        testMapper.queryNow();
        testService.queryNow();
    }


}
