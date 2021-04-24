package com.example.demo.service;

import com.example.demo.dao.StockZyRepository;
import com.example.demo.domain.table.StockZy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class GkService {
    @Autowired
    StockZyRepository stockZyRepository;
    static String urlId ="http://555gk.com/resume/check_resume?id=";
    static String pageId ="http://555gk.com/resume/r_0t_0c_0/p/";
    static String cookiesStr = "QYK=v4g57lo35o2o1sjfdeirgsmlm5; UM_distinctid=17903161d25270-02a13f81cc680e-5e4d2f10-100200-17903161d265ab; CNZZDATA1279161104=345433476-1619254457-%7C1619254457; motto=%E4%BF%A1%E5%BF%83%EF%BC%8C%E4%BB%8E%E6%9D%A5%E4%B8%8D%E6%98%AF%E5%A4%A9%E7%94%9F%E7%9A%84%EF%BC%8C%E8%A6%81%E5%8E%BB%E5%9F%B9%E5%85%BB%EF%BC%81; HOUSE[history]=146110%2C";
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

    public String getPhoneInfo(String url){
        //TestProxyIp.setHttpsProxyIp();
        Document doc = null;
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla/4.0 (compatible; MSIE 7.0; NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)").cookies(getCookies()).get();
            //System.out.println("id =" + doc.html());
            String phone = doc.getElementsByClass("layui-word-aux").get(0).text();
            System.out.println("====---------phone = [" + phone + "]");
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
        // doc.getElementsByTag("table").get(0).childNodes.get(1).childNodes().get(2).attributes().get("onclick").substring(50,56)
        Elements elements = doc.getElementsByTag("tbody").get(0).getElementsByTag("tr");
        Integer size = elements.size();
        for (int i =0 ;i<size;i++){
            Element ine = elements.get(i);
            String name = ine.child(0).text();
            String zy = ine.child(4).text();
            String city = ine.child(5).text();
            //System.out.println("id:"+id+",姓名"+name+",级别"+jibie);
            StockZy stockZy = new StockZy();
            stockZy.setName(name);
            stockZy.setZy(zy);
            stockZy.setInfoCity(city);
            String url =ine.child(0).getElementsByAttribute("href").attr("href");
            int index = url.lastIndexOf("/"); int end = url.indexOf(".html");
            url = url.substring(index+1,end);
            String phone = getPhoneInfo(urlId+url);
            stockZy.setPhone(phone);
            System.out.println(stockZy.toString());
            stockZyRepository.save(stockZy);
        }


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
