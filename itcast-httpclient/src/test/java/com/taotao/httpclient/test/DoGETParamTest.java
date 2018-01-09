package com.taotao.httpclient.test;

import com.taotao.httpclient.ApiService;
import com.taotao.httpclient.HttpResult;
import com.taotao.httpclient.util.HttpClientUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class DoGETParamTest {

    @Test
    public void test01() throws Exception {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // 定义请求的参数
        URI uri = new URIBuilder("http://www.baidu.com/").setParameter("wd", "java").build();

        System.out.println(uri);

        // 创建http GET请求
        HttpGet httpGet = new HttpGet(uri);

        CloseableHttpResponse response = null;

        HttpClientUtil.executeRequest(httpclient, httpGet, response);
    }

    /*
     * 分页查询商品
     */
    @Test
    public void test02() throws Exception {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // 定义请求的参数
        URI uri = new URIBuilder("http://localhost:8180/item/list").
                setParameter("page", "1").setParameter("rows", "30").build();

        System.out.println(uri);

        // 创建http GET请求
        HttpGet httpGet = new HttpGet(uri);

        CloseableHttpResponse response = null;

        HttpClientUtil.executeRequest(httpclient, httpGet, response);

    }

    /*
     * 分页查询商品
     */
    @Test
    public void test03() throws Exception {

        ApiService apiService = new ApiService();
        Map<String, Object> map = new HashMap<>();

        map.put("page", "1");
        map.put("rows", 30);

        HttpResult httpResult = apiService.doGet("http://localhost:8180/item/list", map);

        System.out.println("\n\n" + httpResult);

    }

}
