package com.taotao.httpclient.test;

import com.taotao.httpclient.ApiService;
import com.taotao.httpclient.HttpResult;
import com.taotao.httpclient.util.HttpClientUtil;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DoPOSTParamTest {

    @Test
    public void test01() throws Exception {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // 创建http POST请求
        HttpPost httpPost = new HttpPost("http://www.oschina.net/search");

        // 设置2个post参数，一个是scope、一个是q
        List<NameValuePair> parameters = new ArrayList<>(0);
        parameters.add(new BasicNameValuePair("scope", "project"));
        parameters.add(new BasicNameValuePair("q", "java"));
        // 构造一个form表单式的实体
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(formEntity);

        CloseableHttpResponse response = null;

        HttpClientUtil.executeRequest(httpclient, httpPost, response);

    }

    /*
     * 增加商品
     */
    @Test
    public void test02() throws Exception {

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost("http://localhost:8180/item");

        // 构造表单数据
        ArrayList<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("cid", "3"));
        parameters.add(new BasicNameValuePair("title", "青年文摘"));
        parameters.add(new BasicNameValuePair("price", "12"));
        parameters.add(new BasicNameValuePair("num", "56"));
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");

        httpPost.setEntity(formEntity);

        CloseableHttpResponse response = null;

        HttpClientUtil.executeRequest(httpClient, httpPost, response);

    }

    /*
     * 增加商品
     */
    @Test
    public void test03() throws Exception {

        ApiService apiService = new ApiService();
        HashMap<String, Object> map = new HashMap<>();

        map.put("cid", "3");
        map.put("title", "读者");
        map.put("price", "10");
        map.put("num", 100);
        // HttpResult httpResult = apiService.doPost("http://localhost:8180/item", map);
        HttpResult httpResult = apiService.doPost("http://localhost:8180/rest/item", map);

        System.out.println(httpResult);

    }

}
