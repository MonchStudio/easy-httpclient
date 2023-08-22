/*
 *  Copyright 2017-2023 monchstudio.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.monchstudio.test;

import com.monchstudio.utils.http.HttpClientUtil;
import com.monchstudio.utils.http.HttpResponse;
import org.apache.http.Header;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * Unit test for simple App.
 */
public class HttpClientTests {


    public static void main(String[] args) {
        try {
            for (int i = 0; i < 20; i++) {
                get();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


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

}
