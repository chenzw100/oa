package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.StockZyRepository;
import com.example.demo.domain.table.StockZy;
import com.example.demo.service.base.BaseService;
import com.example.demo.utils.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class XgjService extends BaseService {
    @Autowired
    StockZyRepository stockZyRepository;
    static String urlId ="https://www.kpwhr.com/index/index/jianlishow/id/";
    static String pageId ="https://www.xgj668.com/member/index.php?c=down_resume&page=";
    static String cookiesStr = "Hm_lvt_3532d24926275109dcd5aeb46430dc96=1609650480; PHPSESSID=0c98pt2okvfpobel0u3n3j4bna; uid=248149; shell=56f9795167c353c12610f09ea583a31e; usertype=3; userdid=0; couponOT=248149; lookresume=247681%2C247715%2C247065%2C227487; Hm_lpvt_3532d24926275109dcd5aeb46430dc96=1609651998";
    private static Map<String, String> cookies= new HashMap<>();

    public static Map<String, String> getCookies() {
        if(cookies.size()!=0){
            return cookies;
        }
        String parameStr[] = cookiesStr.split("; ");
        Map<String, String> cookies = new HashMap<>();
        for(String coo : parameStr){
            String parame[] =coo.split("=");
            cookies.put(parame[0],parame[1]);
        }
        return cookies;
    }
    public static HttpCookie[] getHttpCookies() {

        String parameStr[] = cookiesStr.split("; ");
        HttpCookie[] cookies = new HttpCookie[parameStr.length];
        int i=0;
        for(String coo : parameStr){
            String parame[] =coo.split("=");
            HttpCookie cookie = new HttpCookie(parame[0],parame[1]);
            cookies[i]=cookie;
            i++;
        }
        return cookies;
    }

    public String getPhoneInfo(String url){
       //TestProxyIp.setHttpsProxyIp();
        Document doc = null;
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla/4.0 (compatible; MSIE 7.0; NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)").cookies(getCookies()).get();
            //System.out.println("id =" + doc.html());
            String phone = doc.getElementsByClass("tcktouch_box_p_sj").text();
            return phone;
        } catch (IOException e) {
            System.out.println("err---------url = [" + url + "]");
            e.printStackTrace();
        }
        return "";

    }

    public void infoPages(Integer page){
        //TestProxyIp.setHttpsProxyIp();
        Document doc = null;
        try {
            doc = Jsoup.connect(pageId+page).userAgent("Mozilla/4.0 (compatible; MSIE 7.0; NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)").cookies(getCookies()).get();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Elements elements = doc.getElementsByTag("tbody").get(0).getElementsByTag("tr");
        Integer size = elements.size();
        for (int i =1 ;i<size;i++){
            Element ine = elements.get(i);
            String name = ine.child(1).text();
            String zy = ine.child(2).text();

            //System.out.println("id:"+id+",姓名"+name+",级别"+jibie);
            StockZy stockZy = new StockZy();
            stockZy.setName(name);
            stockZy.setZy(zy);
            String url =ine.child(1).children().get(0).getElementsByAttribute("href").attr("href");

            String phone = getPhoneInfo(url);
            stockZy.setPhone(phone);
            System.out.println(stockZy.toString());
            stockZyRepository.save(stockZy);
        }


    }

    public void doUrl (){
        //body
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("eid","247701");
        Object id =postRequest("https://www.xgj668.com/index.php?m=ajax&c=for_link",requestBody);
        //String id = HttpClientUtil.doGet("https://www.xgj668.com/index.php?m=ajax&c=for_link&eid=247711");
        System.out.println(id );
    }
    public static void main(String[] args) {
        String text = "手上有本17年的结构中级工程师，不转社保，单证网查，有需要的联系：朱工 18002577001 联系我时，请说明是在27建筑网上看到的，谢谢！";
        Pattern pattern = Pattern.compile("(?<!\\d)(?:(?:1[34578]\\d{9})|(?:861[358]\\d{9}))(?!\\d)");
        Matcher matcher = pattern.matcher(text);
        StringBuffer bf = new StringBuffer(64);
        while (matcher.find()) {
            bf.append(matcher.group()).append(",");
        }
        int len = bf.length();
        if (len > 0) {
            bf.deleteCharAt(len - 1);
        }

        System.out.println(bf.toString());
    }

    String getPhone(String content){
        Pattern pattern = Pattern.compile("(?<!\\d)(?:(?:1[34578]\\d{9})|(?:861[358]\\d{9}))(?!\\d)");
        Matcher matcher = pattern.matcher(content);
        StringBuffer bf = new StringBuffer(64);
        while (matcher.find()) {
            bf.append(matcher.group()).append(",");
        }
        int len = bf.length();
        if (len > 0) {
            bf.deleteCharAt(len - 1);
        }

        return bf.toString();
    }



}
