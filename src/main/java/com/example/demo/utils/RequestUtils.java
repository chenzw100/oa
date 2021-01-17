package com.example.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Collections;

/**
 * Created by laikui on 2019/9/2.
 */
@Component
public class RequestUtils {
    @Autowired
    private RestTemplate restTemplate;
    public Object request(String url){
        Object response =null;
        try {
            response =  restTemplate.getForObject(url, String.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
    public Object post(String url,MultiValueMap<String, String>requestBody){
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("utf-8"));
        restTemplate.setMessageConverters(Collections.singletonList(converter));
        //headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("authority","www.xgj668.com");
        requestHeaders.add("method","POST");
        requestHeaders.add("path","/index.php?m=ajax&c=for_link");
        requestHeaders.add("scheme","https");
        requestHeaders.add("accept","*/*");
        requestHeaders.add("accept-encoding","gzip, deflate, br");
        requestHeaders.add("accept-language","zh-CN,zh;q=0.9");
        requestHeaders.add("content-length","10");
        requestHeaders.add("content-type","application/x-www-form-urlencoded; charset=UTF-8");
        requestHeaders.add("cookie","Hm_lvt_3532d24926275109dcd5aeb46430dc96=1609650480; PHPSESSID=0c98pt2okvfpobel0u3n3j4bna; uid=248149; shell=56f9795167c353c12610f09ea583a31e; usertype=3; userdid=0; couponOT=248149; lookresume=247681%2C247715%2C247065%2C227487%2C247713; Hm_lpvt_3532d24926275109dcd5aeb46430dc96=1609657271");
        requestHeaders.add("origin","https://www.xgj668.com");
        requestHeaders.add("referer","https://www.xgj668.com/resume/index.php?c=show&id=247713");
        requestHeaders.add("sec-ch-ua","'Google Chrome';v='87', ' Not;A Brand';v='99', 'Chromium';v='87'");
        requestHeaders.add("sec-ch-ua-mobile","?0");
        requestHeaders.add("sec-fetch-dest","empty");
        requestHeaders.add("sec-fetch-mode","cors");
        requestHeaders.add("sec-fetch-site","same-origin");
        requestHeaders.add("user-agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");
        requestHeaders.add("x-requested-with","XMLHttpRequest");


        //HttpEntity
        HttpEntity<MultiValueMap> requestEntity = new HttpEntity<MultiValueMap>(requestBody, requestHeaders);
        RestTemplate restTemplate = new RestTemplate();
        //post
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
        String response = responseEntity.getBody();
        System.out.println(response);
        return response;
    }
    public Object post(String url,MultiValueMap<String, String>requestBody,HttpHeaders requestHeaders){
       // StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("utf-8"));
       // restTemplate.setMessageConverters(Collections.singletonList(converter));
        //HttpEntity
        HttpEntity<MultiValueMap> requestEntity = new HttpEntity<MultiValueMap>(requestBody, requestHeaders);
        Object o = restTemplate.postForObject(url, requestEntity, String.class);
        System.out.println(o.toString());
        //post
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
        String response = responseEntity.getBody();
        System.out.println(response);
        return response;
    }
}
