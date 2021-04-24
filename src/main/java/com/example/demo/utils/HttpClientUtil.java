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
       // url="https://www.xgj668.com/index.php?m=ajax&c=for_link";
        String result = HttpRequest.post(url)
                .header(Header.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36")//头信息，多个头信息多次调用此方法即可
               // .header(":authority","www.xgj668.com")
                .header(":method","POST")
                //.header(":path","/index.php?m=ajax&c=for_link")
                .header(":scheme","https")
                .header("accept","*/*")
                .header("accept-encoding","gzip, deflate, br")
                .header("accept-language","zh-CN,zh;q=0.9")
                .header("content-length","10")
                .header("content-type","application/x-www-form-urlencoded; charset=UTF-8")
                //.header("origin","https://www.xgj668.com")
                //.header("referer","https://www.xgj668.com/resume/index.php?c=show&id=247711")
                .header("sec-ch-ua","'Google Chrome';v='87'")
                .header("sec-ch-ua-mobile","?0")
                .header("sec-fetch-dest","empty")
                .header("sec-fetch-mode","cors")
                .header("sec-fetch-site","same-origin")
                .header("user-agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36")
                .header("x-requested-with","XMLHttpRequest")
                .header("cookie","PHPSESSID=8gf9prrhbpdk531f214eb8vt25; LiveWSLZT89607389=946c231ada3e47acbfc4d7c0a82d94d2; LiveWSLZT89607389sessionid=946c231ada3e47acbfc4d7c0a82d94d2; NLZT89607389fistvisitetime=1619260127884; NLZT89607389visitecounts=1; UM_distinctid=179036cbbe346-0b76cccd7c3641-5e4d2f10-100200-179036cbbe59c; CNZZDATA1274190661=1581881951-1619260128-%7C1619260128; IESESSION=alive; pgv_pvi=41781619260129116; _qddaz=QD.75wcxw.ui11gk.knvlo84f; _qdda=3-1.1; _qddab=3-ryfseu.knvlo858; tencentSig=3139063808; _qddamta_4006065084=3-0; NLZT89607389lastvisitetime=1619263912912; NLZT89607389visitepages=70")
                .form(map)//表单内容
                .timeout(20000)//超时，毫秒
                .execute().body();
        return result;
    }

}
