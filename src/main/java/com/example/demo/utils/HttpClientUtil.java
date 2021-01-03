package com.example.demo.utils;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;

import java.net.HttpCookie;
import java.util.Map;

/**
 * Created by ShaoFan
 */
public class HttpClientUtil {
    public static String doPost(String url, Map<String, Object> map) {
        return HttpUtil.post(url, map);
    }
    public static String doPostBody(String url, String body) {
        return HttpUtil.post(url, body);
    }
    public static String doGet(String url) {
        return HttpUtil.get(url);
    }
    public static String doPostBody(String url, Map<String, Object> map, HttpCookie[] cookie) {
        //HttpRequest.post(url).cookie(cookie).form(map).execute().toString();
        url="https://www.xgj668.com/index.php?m=ajax&c=for_link";
        String result = HttpRequest.post(url)
                .header(Header.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36")//头信息，多个头信息多次调用此方法即可
                .header(":authority","www.xgj668.com")
                .header(":method","POST")
                .header(":path","/index.php?m=ajax&c=for_link")
                .header(":scheme","https")
                .header("accept","*/*")
                .header("accept-encoding","gzip, deflate, br")
                .header("accept-language","zh-CN,zh;q=0.9")
                .header("content-length","10")
                .header("content-type","application/x-www-form-urlencoded; charset=UTF-8")
                .header("cookie","Hm_lvt_3532d24926275109dcd5aeb46430dc96=1609650480; PHPSESSID=0c98pt2okvfpobel0u3n3j4bna; uid=248149; shell=56f9795167c353c12610f09ea583a31e; usertype=3; userdid=0; couponOT=248149; lookresume=247681%2C247715%2C247065%2C227487%2C247713; Hm_lpvt_3532d24926275109dcd5aeb46430dc96=1609657271")
                .header("origin","https://www.xgj668.com")
                .header("referer","https://www.xgj668.com/resume/index.php?c=show&id=247711")
                .header("sec-ch-ua","'Google Chrome';v='87'")
                .header("sec-ch-ua-mobile","?0")
                .header("sec-fetch-dest","empty")
                .header("sec-fetch-mode","cors")
                .header("sec-fetch-site","same-origin")
                .header("user-agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36")
                .header("x-requested-with","XMLHttpRequest")
                .form(map)//表单内容
                .timeout(20000)//超时，毫秒
                .execute().body();
        return result;
    }

}
