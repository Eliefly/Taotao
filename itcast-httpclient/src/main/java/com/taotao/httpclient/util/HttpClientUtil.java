package com.taotao.httpclient.util;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * HttpClientUtil
 *
 * @author eliefly
 * @date 2018-01-09
 */
public class HttpClientUtil {

    public static void executeRequest(CloseableHttpClient httpclient, HttpRequestBase httpRequestBase, CloseableHttpResponse response) throws IOException {
        try {
            // 执行请求
            response = httpclient.execute(httpRequestBase);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println(content);
            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }
    }
}
