package com.monchstudio.utils.http;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HttpClientUtil {

    /**
     * POST请求
     * @param url api地址
     * @return 执行结果
     */
    public static HttpRequest post(String url){
        return HttpRequest.create(url).method(HttpRequest.HttpMethod.POST);
    }

    /**
     * get请求
     * @param url api地址
     * @return 执行结果
     */
    public static HttpRequest get(String url){
        return HttpRequest.create(url).method(HttpRequest.HttpMethod.GET);
    }

    /**
     * put 请求
     * @param url api地址
     * @return 执行结果
     */
    public static HttpRequest put(String url){
        return HttpRequest.create(url).method(HttpRequest.HttpMethod.PUT);
    }

    /**
     * patch 请求
     * @param url api地址
     * @return 执行结果
     */
    public static HttpRequest patch(String url){
        return HttpRequest.create(url).method(HttpRequest.HttpMethod.PATCH);
    }

    /**
     * head 请求
     * @param url api地址
     * @return 执行结果
     */
    public static HttpRequest head(String url){
        return HttpRequest.create(url).method(HttpRequest.HttpMethod.HEAD);
    }

    /**
     * delete 请求
     * @param url api地址
     * @return 执行结果
     */
    public static HttpRequest delete(String url){
        return HttpRequest.create(url).method(HttpRequest.HttpMethod.DELETE);
    }
    /**
     * options 请求
     * @param url api地址
     * @return 执行结果
     */
    public static HttpRequest options(String url){
        return HttpRequest.create(url).method(HttpRequest.HttpMethod.OPTIONS);
    }

    /**
     * trace 请求
     * @param url api地址
     * @return 执行结果
     */
    public static HttpRequest trace(String url){
        return HttpRequest.create(url).method(HttpRequest.HttpMethod.TRACE);
    }


    /**
     * 连接池管理
     * @param max 总共最大链接数量
     */
    public static void poolMaxTotal(int max){
        HttpRequest.poolingHttpClientConnectionManager.setMaxTotal(max);
    }

    /**
     * 连接池管理
     * @param max 并发数量
     */
    public static void poolDefaultMaxPerRoute(int max){
        HttpRequest.poolingHttpClientConnectionManager.setDefaultMaxPerRoute(max);
    }

}
