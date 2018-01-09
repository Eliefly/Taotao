package com.taotao.httpclient.test;

import com.taotao.httpclient.util.HttpClientUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

public class DoPOSTTest {

    @Test
    public void test01() throws Exception {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // 创建http POST请求
        HttpPost httpPost = new HttpPost("http://www.oschina.net/");

        CloseableHttpResponse response = null;

        HttpClientUtil.executeRequest(httpclient, httpPost, response);

    }

}
