package com.monchstudio.test;

import com.monchstudio.utils.http.HttpClientUtil;
import com.monchstudio.utils.http.HttpResponse;
import org.apache.http.Header;

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

}
