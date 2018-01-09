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
public class doPUTTest {

    @Test
    public void test03() throws Exception {

        ApiService apiService = new ApiService();
        HashMap<String, Object> map = new HashMap<>();

        map.put("id", 12);
        map.put("num", 1002);

        HttpResult httpResult = apiService.doPut("http://localhost:8180/rest/item", map);

        System.out.println(httpResult);

    }

}
