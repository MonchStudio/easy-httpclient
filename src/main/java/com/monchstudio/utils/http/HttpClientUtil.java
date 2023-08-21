package com.monchstudio.utils.http;

public class HttpClientUtil {


    /**
     * 方法说明: POST请求
     * @author xqlee
     */
    public static HttpRequest post(String url){
        return HttpRequest.create(url).method(HttpRequest.HttpMethod.POST);
    }

    /**
     * 方法说明: GET 请求
     * @author xqlee
     */
    public static HttpRequest get(String url){
        return HttpRequest.create(url).method(HttpRequest.HttpMethod.GET);
    }

    /**
     * 方法说明: PUT 请求
     * @author xqlee
     */
    public static HttpRequest put(String url){
        return HttpRequest.create(url).method(HttpRequest.HttpMethod.PUT);
    }

    /**
     * 方法说明: PATCH 请求
     * @author xqlee
     */
    public static HttpRequest patch(String url){
        return HttpRequest.create(url).method(HttpRequest.HttpMethod.PATCH);
    }

    /**
     * 方法说明: HEAD 请求
     * @author xqlee
     */
    public static HttpRequest head(String url){
        return HttpRequest.create(url).method(HttpRequest.HttpMethod.HEAD);
    }

    /**
     * 方法说明: DELETE 请求
     * @author xqlee
     */
    public static HttpRequest delete(String url){
        return HttpRequest.create(url).method(HttpRequest.HttpMethod.DELETE);
    }
    /**
     * 方法说明: OPTIONS 请求
     * @author xqlee
     */
    public static HttpRequest options(String url){
        return HttpRequest.create(url).method(HttpRequest.HttpMethod.OPTIONS);
    }

    /**
     * 方法说明: TRACE 请求
     * @author xqlee
     */
    public static HttpRequest trace(String url){
        return HttpRequest.create(url).method(HttpRequest.HttpMethod.TRACE);
    }


}
