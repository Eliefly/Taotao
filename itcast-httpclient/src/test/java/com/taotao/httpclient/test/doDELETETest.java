package com.taotao.httpclient.test;

import com.taotao.httpclient.ApiService;
import com.taotao.httpclient.HttpResult;
import org.junit.Test;

import java.util.HashMap;

/**
 * doPUTTest
 *
 * @author eliefly
 * @date 2018-01-09
 */
public class doDELETETest {

    @Test
    public void test03() throws Exception {

        ApiService apiService = new ApiService();

        // 1. 路径参数数据
        HttpResult httpResult = apiService.doDelete("http://localhost:8180/rest/item/12");

        System.out.println(httpResult);

    }

}
