package com.taotao.httpclient;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;

/**
 * ApiService
 *
 * @author eliefly
 * @date 2018-01-09
 */
public class ApiService {

    private CloseableHttpClient httpClient;

    public ApiService() {
        httpClient = HttpClients.createDefault();
    }

    /**
     * 处理带参数Get请求
     *
     * @param url    请求url
     * @param params 参数
     * @return 响应
     */
    public HttpResult doGet(String url, Map<String, Object> params) throws Exception {
        URIBuilder uriBuilder = wrapQueryPara(url, params);

        // 创建http GET请求
        HttpGet httpGet = new HttpGet(uriBuilder.build());

        // 发起请求
        return executeRequest(httpGet);

    }

    private URIBuilder wrapQueryPara(String url, Map<String, Object> params) throws URISyntaxException {
        // 创建URIBuilder
        URIBuilder uriBuilder = new URIBuilder(url);

        // 遍历请求的参数, 封装到 URIBuilder 中
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }
        return uriBuilder;
    }

    /**
     * 处理无参数Get请求
     *
     * @param url 请求url
     * @return 响应
     * @throws Exception 抛出异常
     */
    public HttpResult doGet(String url) throws Exception {

        return doGet(url, null);
    }

    /**
     * 处理有参数的POST请求
     *
     * @param url    请求URL
     * @param params 参数
     * @return 响应
     * @throws Exception 抛出异常
     */
    public HttpResult doPost(String url, Map<String, Object> params) throws Exception {

        HttpPost httpPost = new HttpPost(url);
        return dealRequestWithFormParm(httpPost, params);

    }

    /**
     * 处理无参数的POST请求
     *
     * @param url 请求URL
     * @return 响应
     * @throws Exception 抛出异常
     */
    public HttpResult doPost(String url) throws Exception {

        return doPost(url, null);
    }

    /**
     * 处理有参数的PUT请求
     *
     * @param url    请求URL
     * @param params 参数
     * @return 响应
     * @throws Exception 抛出异常
     */
    public HttpResult doPut(String url, Map<String, Object> params) throws Exception {

        HttpPut httpPut = new HttpPut(url);
        return dealRequestWithFormParm(httpPut, params);

    }

    /**
     * 处理无参数的PUT请求
     *
     * @param url 请求URL
     * @return 响应
     * @throws Exception 抛出异常
     */
    public HttpResult doPut(String url) throws Exception {

        return doPut(url, null);
    }

    /**
     * 处理带请求参数的DELETE请求
     *
     * @param url    请求URL
     * @param params 参数
     * @return 响应
     * @throws Exception 抛出异常
     */
    public HttpResult doDelete(String url, Map<String, Object> params) throws Exception {

        // 创建URIBuilder
        URIBuilder uriBuilder = wrapQueryPara(url, params);

        // 创建http DELETE请求
        HttpDelete httpDelete = new HttpDelete(uriBuilder.build());

        return executeRequest(httpDelete);

    }

    /**
     * 理无请求参数的DELETE请求
     *
     * @param url 请求URL
     * @return 响应
     * @throws Exception 抛出异常
     */
    public HttpResult doDelete(String url) throws Exception {

        return doDelete(url, null);
    }

    /**
     * 处理POST/PUT请求
     *
     * @param httpRequestBase 请求对象
     * @param params          参数
     * @return 响应
     * @throws IOException 抛出异常
     */
    private HttpResult dealRequestWithFormParm(HttpRequestBase httpRequestBase, Map<String, Object> params)
            throws IOException {

        if (params != null) {
            // 遍历请求参数, 把参数封装成表单实体.
            ArrayList<NameValuePair> list = new ArrayList<>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list, "UTF-8");
            // 表单实体封装到PUT请求中.
            if (httpRequestBase instanceof HttpPost) {
                HttpPost httpRequest = (HttpPost) httpRequestBase;
                httpRequest.setEntity(formEntity);
            } else if (httpRequestBase instanceof HttpPut) {
                HttpPut httpRequest = (HttpPut) httpRequestBase;
                httpRequest.setEntity(formEntity);
            }

        }

        return executeRequest(httpRequestBase);
    }

    /**
     * 执行请求, 封装响应
     *
     * @param httpRequestBase 请求对象
     * @return 响应
     * @throws IOException 抛出异常
     */
    private HttpResult executeRequest(HttpRequestBase httpRequestBase) throws IOException {

        CloseableHttpResponse response = httpClient.execute(httpRequestBase);

        // 获取响应码和响应体
        int statusCode = response.getStatusLine().getStatusCode();
        String body = null;

        if (response.getEntity() != null) {
            body = EntityUtils.toString(response.getEntity(), "UTF-8");
        }

        return new HttpResult(statusCode, body);
    }

}
