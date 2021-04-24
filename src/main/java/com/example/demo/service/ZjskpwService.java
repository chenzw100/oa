package com.example.demo.service;

import com.example.demo.dao.StockZyRepository;
import com.example.demo.domain.table.StockZy;
import com.example.demo.service.base.BaseService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class ZjskpwService extends BaseService {
    @Autowired
    StockZyRepository stockZyRepository;
    static String urlId ="https://www.zjskpw.com/personal_show.php?showid=";
    static String pageId ="https://www.zjskpw.com/personal.php?PageNo=";
    static String cookiesStr = "PHPSESSID=8gf9prrhbpdk531f214eb8vt25; LiveWSLZT89607389=946c231ada3e47acbfc4d7c0a82d94d2; LiveWSLZT89607389sessionid=946c231ada3e47acbfc4d7c0a82d94d2; NLZT89607389fistvisitetime=1619260127884; NLZT89607389visitecounts=1; UM_distinctid=179036cbbe346-0b76cccd7c3641-5e4d2f10-100200-179036cbbe59c; CNZZDATA1274190661=1581881951-1619260128-|1619260128; IESESSION=alive; pgv_pvi=41781619260129116; _qddaz=QD.75wcxw.ui11gk.knvlo84f; _qdda=3-1.1; _qddab=3-ryfseu.knvlo858; _qddamta_4006065084=3-0; tencentSig=3139063808; _qddac=3-2-1.1.ryfseu.knvlo858; NLZT89607389lastvisitetime=1619260633417; NLZT89607389visitepages=12";
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
    public void testId(String id){

        Document doc = null;
        try {
            doc = Jsoup.connect(urlId+id+".html").cookies(getCookies()).get();
            //System.out.println("id =" + doc.html());
            String phone = doc.getElementsByClass("telbox").get(0).getElementsByTag("li").get(1).getElementsByTag("b").text();
            System.out.println("phone = [" + phone + "]");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public String getPhoneInfo(String url){
        Document doc = null;
        try {
            doc = Jsoup.connect(url).cookies(getCookies()).get();
            //System.out.println("id =" + doc.html());
            Elements element =doc.getElementsByTag("tbody");
            if(element.size()<3){
                return "1111111111111111111111111";
            }
            String phone = element.get(2).getElementsByTag("td").get(1).text();
            return phone;
        } catch (IOException e) {
            System.out.println("err---------url = [" + url + "]");
            e.printStackTrace();
        }
        return "";

    }
    public void doUrl (String name){
        //body
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("action","ONseetel");
        requestBody.add("personalid",name);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Accept","application/json, text/javascript, */*; q=0.01");
        requestHeaders.add("Accept-Encoding","gzip, deflate, br");
        requestHeaders.add("Accept-Language","zh-CN,zh;q=0.9");
        requestHeaders.add("client","nodejs");
        requestHeaders.add("Connection","keep-alive");
        requestHeaders.add("Content-Length","32");
        requestHeaders.add("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        requestHeaders.add("sec-ch-ua","'Google Chrome';v='87', ' Not;A Brand';v='99', 'Chromium';v='87'");
        requestHeaders.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36");
        requestHeaders.add("x-requested-with","XMLHttpRequest");
        requestHeaders.add("sec-ch-ua-mobile","?0");
        requestHeaders.add("Sec-Fetch-Dest","empty");
        requestHeaders.add("Sec-Fetch-Mode","cors");
        requestHeaders.add("Sec-Fetch-Site","same-origin");
        requestHeaders.add("method","POST");
        requestHeaders.add("scheme","https");
        requestHeaders.add("path","/plus/ajax.php");
        requestHeaders.add("authority","www.zjskpw.com");
        requestHeaders.add("Origin","https://www.zjskpw.com");
        requestHeaders.add("Cookie","PHPSESSID=8gf9prrhbpdk531f214eb8vt25; LiveWSLZT89607389=946c231ada3e47acbfc4d7c0a82d94d2; LiveWSLZT89607389sessionid=946c231ada3e47acbfc4d7c0a82d94d2; NLZT89607389fistvisitetime=1619260127884; NLZT89607389visitecounts=1; UM_distinctid=179036cbbe346-0b76cccd7c3641-5e4d2f10-100200-179036cbbe59c; CNZZDATA1274190661=1581881951-1619260128-%7C1619260128; IESESSION=alive; pgv_pvi=41781619260129116; _qddaz=QD.75wcxw.ui11gk.knvlo84f; _qdda=3-1.1; _qddab=3-ryfseu.knvlo858; tencentSig=3139063808; _qddamta_4006065084=3-0; NLZT89607389lastvisitetime=1619263912912; NLZT89607389visitepages=70");
        //requestHeaders.add("Host","www.jiansheku.com");
        //requestHeaders.add("pw","dce208295cbdd7dae3ed4c423ab75007");
        //requestHeaders.add("Referer","https://www.jiansheku.com/qiye.html");
        //requestHeaders.add("token","551fafbfc46b43158cbd5ab8f1b7bce5");
        //requestHeaders.add("ty","js");
        /*HttpCookie httpCookie[] =new  HttpCookie[cookies.size()];
        int i =0;
        for(Map.Entry<String, String> entry:cookies.entrySet()){
            HttpCookie cookie = new HttpCookie(entry.getKey(),entry.getValue());
            httpCookie[i]=cookie;
           i++;
        }
        String str = HttpClientUtil.doPostBody("https://www.zjskpw.com/plus/ajax.php",requestBody,httpCookie);*/
        Object str =postRequest("https://www.zjskpw.com/plus/ajax.php",requestBody,requestHeaders);
        //Object id =postRequest("https://www.zjskpw.com/plus/ajax.php",requestBody);
        //String id = HttpClientUtil.doGet("https://www.xgj668.com/index.php?m=ajax&c=for_link&eid=247711");
        System.out.println("执行点击事件 "+name );
    }

    public void infoPages(Integer page){
        Document doc = null;
        try {
            doc = Jsoup.connect(pageId+page).cookies(getCookies()).get();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
       // doc.getElementsByTag("table").get(0).childNodes.get(1).childNodes().get(2).attributes().get("onclick").substring(50,56)
        Elements elements = doc.getElementsByTag("tbody").get(0).getElementsByTag("tr");
        Integer size = elements.size();
        for (int i =1 ;i<size;i++){
            Element ine = elements.get(i);
            String name = ine.child(0).text();
            //String jibie = ine.child(3).text();
            String zy = ine.child(2).text();
            //String city = ine.child(4).text();
            //System.out.println("id:"+id+",姓名"+name+",级别"+jibie);
            StockZy stockZy = new StockZy();
            stockZy.setName(name);
            stockZy.setZy(zy);
            //stockZy.setInfoCity(city);
            String url =ine.getElementsByAttribute("onclick").attr("onclick");
            int index = url.indexOf("("); int end = url.indexOf(")");
            url = url.substring(index+2,end-1);
            String phone = getPhoneInfo("https://www.zjskpw.com/"+url);
            if(phone.length()>20){
                String id = url.substring(url.indexOf("=")+1,url.length());
                doUrl(id);
                phone = getPhoneInfo("https://www.zjskpw.com/"+url);
            }
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
