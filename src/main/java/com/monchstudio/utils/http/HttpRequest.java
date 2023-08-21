package com.monchstudio.utils.http;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HttpRequest {

    private  int MAX_SOCKET_TIMEOUT = 60000;
    private  int MAX_CONNECTION_TIMEOUT = 60000;

    /**
     * http 请求地址
     */
    protected String url;
    /**
     * http请求头部参数
     */
    protected Map<String,String> headers=new ConcurrentHashMap<>(16);
    /**
     * form 表单参数 （适用于POST等请求）
     */
    protected Map<String,Object> form=null;

    /**
     * body 参数（常见json字符）
     */
    protected String body=null;

    /**
     * 方法
     */
    private HttpMethod httpMethod;

    /**
     * 自定义配置
     */
    protected RequestConfig customConfig=null;

    private final BasicCookieStore cookieStore=new BasicCookieStore();

    public HttpRequest(String url) {
        if (null==url){
            throw new RuntimeException("Url Can't Null.");
        }else{
            String urlLowerCase = url.toLowerCase();
            if (!urlLowerCase.startsWith("http://")&&!urlLowerCase.startsWith("https://")){
                throw new RuntimeException("Url Must Be Start With Http:// or https://");
            }
        }
        this.httpMethod = HttpMethod.GET;
        this.url = url;
        //默认头设置（后面通过header覆盖）
        this.headers.put("Accept","text/html,application/json,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        this.headers.put("Accept-Encoding","gzip, deflate");
        this.headers.put("Accept-Language","zh-CN,zh;q=0.8");
        this.headers.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
    }

    /**
     * 方法说明: 创建
     * @author xqlee
     */
    protected static HttpRequest create(String url){
        return new HttpRequest(url);
    }


    /**
     * 方法说明: 设置请求头部参数
     * @author xqlee
     */
    public HttpRequest header(String name,String value){
        if (null!=name&&null!=value){
            this.headers.put(name,value);
        }
        return this;
    }

    /**
     * 方法说明: cookie设置
     * @author xqlee
     */
    public HttpRequest cookie(String name,String value){
        BasicClientCookie cookie = new BasicClientCookie(name, value);
        cookie.setDomain(cookieDomain());
        cookie.setPath("/");
        this.cookieStore.addCookie(cookie);
        return this;
    }

    /**
     * 方法说明: cookie设置
     * @author xqlee
     */
    public HttpRequest cookie(BasicClientCookie cookie){
        this.cookieStore.addCookie(cookie);
        return this;
    }

    private String cookieDomain(){
        if (Objects.isNull(this.url)){
           return "localhost";
       }else if (url.isEmpty()){
           return "localhost";
       }else{
            String urlLowerCase = this.url.toLowerCase();
            if (urlLowerCase.startsWith("http")||urlLowerCase.startsWith("https")){
                Matcher matcher = Pattern.compile("^http(s)?://([^:/]*)[:/].*$").matcher(urlLowerCase);
                if (matcher.find()){
                    return matcher.group(2);
                }
            }
        }
        return "localhost";
    }

    /**
     * 方法说明: 超时设置，默认60s
     * @author xqlee
     */
    public HttpRequest timeout(int seconds){
        this.MAX_SOCKET_TIMEOUT=seconds*1000;
        this.MAX_CONNECTION_TIMEOUT=seconds*1000;
        return this;
    }


    public HttpRequest config(RequestConfig config){
        if (Objects.nonNull(config)){
            this.customConfig=config;
        }
        return this;
    }
    /**
     * 方法说明: 设置表单参数
     * @author xqlee
     */
    public HttpRequest form(String name,Object value){
        this.body=null;
        if (value instanceof Serializable){
           return this.putToForm(name,value);
        }else{
            throw new RuntimeException("Form Value Must Instanceof Serializable");
        }
    }

    /**
     * 方法说明: form map参数
     * @author xqlee
     */
    public HttpRequest formMap(Map<String,Object> map){
        if (Objects.nonNull(map)){
            for (String key : map.keySet()) {
               this.putToForm(key,map.get(key));
            }
        }
        return this;
    }

    /**
     * 方法说明: form map参数
     * @author xqlee
     */
    public HttpRequest formMapStr(Map<String,String> map){
        if (Objects.nonNull(map)){
            for (String key : map.keySet()) {
                this.putToForm(key,map.get(key));
            }
        }
        return this;
    }

    /**
     * 方法说明: body参数（post等方法适用）
     * @author xqlee
     */
    public HttpRequest body(String body){
        if (null!=body){
            this.form=null;
            this.body=body;
        }
        return this;
    }


    /**
     * 方法说明: 请求方法
     * @author xqlee
     */
    public HttpRequest method(HttpMethod httpMethod){
        this.httpMethod = httpMethod;
        return this;
    }

    /**
     * 方法说明: 添加form参数
     * @author xqlee
     */
    private HttpRequest putToForm(String name,Object value){
        if (null != name && null != value){
            if (null == this.form){
                this.form=new ConcurrentHashMap<>(16);
            }
            this.form.put(name,value);
        }
        return this;
    }


    public enum HttpMethod {
        GET("GET"),
        POST("POST"),
        DELETE("DELETE"),
        PATCH("PATCH"),
        PUT("PUT"),
        HEAD("HEAD"),
        OPTIONS("OPTIONS"),
        TRACE("TRACE"),
        ;
        /**
         * 方法名称
         */
        final String name;

        public String getName() {
            return name;
        }

        HttpMethod(String name){
            this.name=name;
        }
    }

    public HttpResponse execute() throws IOException {

        String method=this.httpMethod.getName();
        RequestConfig config = RequestConfig.custom().setSocketTimeout(MAX_SOCKET_TIMEOUT).setConnectTimeout(MAX_CONNECTION_TIMEOUT).build();
        if (Objects.nonNull(customConfig)){
            config=customConfig;
        }
        try (CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(this.cookieStore)
                .setDefaultRequestConfig(config)
                .build()){
            HttpEntityEnclosingRequestBase request = new HttpEntityEnclosingRequestBase() {
                @Override
                public String getMethod() {
                    return method;
                }
            };
            //头部处理
            if (Objects.nonNull(this.headers)){
                for (String key : this.headers.keySet()) {
                    request.addHeader(key,this.headers.get(key));
                }
            }
            //方法处理
            if (Objects.equals(this.httpMethod, HttpMethod.GET)){
                //处理参数
                if (Objects.nonNull(this.form)){
                    if (!this.url.contains("?")){
                        this.url+="?";
                    }
                    List<String> params=new ArrayList<>();
                    for (String key : this.form.keySet()) {
                        params.add(key+"="+this.form.get(key));
                    }
                    this.url+=String.join("&",params);
                }
            } else if (Objects.equals(this.httpMethod,HttpMethod.POST)) {
                //目前仅判断form参数和json参数
                if (Objects.nonNull(this.form)){
                    //form 参数（不含file）
                    List<NameValuePair> list = new ArrayList<>();
                    for (String key : this.form.keySet()) {
                        list.add(new BasicNameValuePair(key, (String) form.get(key)));
                    }
                    request.setEntity(new UrlEncodedFormEntity(list));
                }else{
                    //json body参数
                    assert this.headers != null;
                    String contentType = this.headers.get("Content-Type");
                    if (Objects.isNull(contentType)||contentType.isEmpty()){
                        this.headers.put("Content-Type", "application/json");
                    }
                    request.setEntity(new StringEntity(body));
                }
            }

            request.setURI(URI.create(this.url));
            CloseableHttpResponse response = httpclient.execute(request);
            return new HttpResponse(response, EntityUtils.toByteArray(response.getEntity()), cookieStore.getCookies());
        }
    }


}
