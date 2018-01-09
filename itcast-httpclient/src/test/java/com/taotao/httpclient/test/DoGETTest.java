package com.taotao.httpclient.test;

import com.taotao.httpclient.ApiService;
import com.taotao.httpclient.HttpResult;
import com.taotao.httpclient.util.HttpClientUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.io.IOException;

public class DoGETTest {

    @Test
    public void test01() throws IOException {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // 创建http GET请求
        HttpGet httpGet = new HttpGet("http://www.baidu.com/");

        CloseableHttpResponse response = null;

        HttpClientUtil.executeRequest(httpclient, httpGet, response);

    }

    @Test
    public void test03() throws Exception {

        ApiService apiService = new ApiService();

        HttpResult httpResult = apiService.doGet("http://localhost:8180/rest/item/1");

        System.out.println("\n\n" + httpResult);

    }

}
