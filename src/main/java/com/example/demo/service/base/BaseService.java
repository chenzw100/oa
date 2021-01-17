package com.example.demo.service.base;

import com.example.demo.utils.RequestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;

/**
 * Created by laikui on 2019/9/2.
 */
@Component
public class BaseService {
    public Log log = LogFactory.getLog(BaseService.class);
    public final static HashMap<String,String> PRICE_CACHE = new HashMap();
    @Autowired
    RequestUtils requestUtils;

    public Object getRequest(String url){
        return requestUtils.request(url);
    }
    public Object postRequest(String url,MultiValueMap<String, String> requestBody){

        return requestUtils.post(url,requestBody);
    }
    public Object postRequest(String url, MultiValueMap<String, String>requestBody, HttpHeaders requestHeaders){

        return requestUtils.post(url,requestBody,requestHeaders);
    }

}
