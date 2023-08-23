# Easy-Httpclient
## 简介
本工具主要是简化apache httpclient 使用，更短代码篇幅。

## 使用

maven 依赖
````xml
<dependency>
    <groupId>com.monchstudio.utils</groupId>
    <artifactId>easy-httpclient</artifactId>
    <version>1.0.3</version>
</dependency>
````

Java  代码
````java
    public static void get() throws IOException {
        HttpResponse response = HttpClientUtil.get("http://www.baidu.com")
                .execute();
        String body = response.body();
        System.out.println("body:\n"+body);
        int code = response.code();
        System.out.println("http code:"+code);
        Header firstHeader = response.getFirstHeader("Content-Type");
        if (Objects.nonNull(firstHeader)){
            System.out.println("header :"+firstHeader.getValue());
        }
    }


    public static void postForm() throws IOException {
        HttpResponse response = HttpClientUtil.post("https://www.baidu.com")
                .form("keywords", "测试")
                .header("token", "123456")
                .cookie("BAIDU", "11234")
                .execute();
        String body = response.body();
    }

    public static void postJson() throws IOException {
        HttpResponse response = HttpClientUtil.post("https://www.baidu.com")
                .body("{\"keywords\":\"测试\"}")
                .header("token", "123456")
                .cookie("BAIDU", "11234")
                .execute();
        String body = response.body();
    }

    public static void getFile()throws IOException {
        HttpResponse response = HttpClientUtil.get("https://www.baidu.com")
                .execute();
        byte[] bytes = response.bodyBytes();
        try (FileOutputStream fos=new FileOutputStream("d:/tmp/baidu.html")){
            fos.write(bytes);
            fos.flush();
        }
    }
````

## 相关依赖

apache httpclient 4.15.3

## 开源许可
Apache License 2.0
