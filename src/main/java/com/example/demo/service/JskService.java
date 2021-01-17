package com.example.demo.service;

import com.example.demo.dao.StockZyRepository;
import com.example.demo.domain.table.StockZy;
import com.example.demo.service.base.BaseService;
import org.apache.commons.lang.StringUtils;
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
public class JskService extends BaseService {
    private static Map cache = new HashMap();
    @Autowired
    StockZyRepository stockZyRepository;
    static String urlId ="http://www.27guakao.com/city/hb/information.php?id=";
    static String pageId ="http://www.27guakao.com/category.php?catid=2&page=";
    static String cookiesStr = "UM_distinctid=1755d86f15d242-05748ed3145656-3e604000-100200-1755d86f15e239; Hm_lvt_881d4cf37741783ebdba3d130d30cb84=1603595052; CNZZDATA1259217558=643901521-1603591927-null%7C1603603587; jskx__s_uid=17623430884; jskx__s_pwd=VlcGBwUNCAMFBAMLVAYDWwBRAFMHBQEEBVFXBgRWAQM%3D9e65575921; Hm_lpvt_881d4cf37741783ebdba3d130d30cb84=1603603744";
    private static Map<String, String> cookies= new HashMap<>();

    public void doUrl (){
        //body
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("orderFlag","0");
        requestBody.add("pageIndex","2");
        requestBody.add("pageSize","10");

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Accept","application/json, text/javascript, */*; q=0.01");
        requestHeaders.add("Accept-Encoding","gzip, deflate, br");
        requestHeaders.add("Accept-Language","zh-CN,zh;q=0.9");
        requestHeaders.add("client","nodejs");
        requestHeaders.add("Connection","keep-alive");
        requestHeaders.add("Content-Length","43");
        requestHeaders.add("Content-Type","application/json");
        requestHeaders.add("Cookie","UM_distinctid=177092783cc41d-0da989ef8892a3-3f604900-100200-177092783ce34d; __snaker__id=Py2JJWJkzygdp5pI; _9755xjdesxxd_=32; YD00116597454221%3AWM_TID=ci5gUoU1FHFFBEAAAQYqbwSWDTrKS4%2Bj; token=551fafbfc46b43158cbd5ab8f1b7bce5; 53gid2=10120942650019; 53gid1=10120942650019; 53revisit=1610766587553; Hm_lvt_03b8714a30a2e110b8a13db120eb6774=1610766320,1610844423; CNZZDATA1275173796=521836465-1610766320-%7C1610841658; YD00116597454221%3AWM_NI=tVJ1HVbbAlWaXCMvd1lOogjtwX9luLUeGp2k0BkPtaGigoDi8tAxKrEdMepTY6VCbgGKmGXjBUO1tS%2FotNoZtaiWHFzbVdd5aZawWnsKoGO514J3hm0Rp466csGEaK89NDM%3D; YD00116597454221%3AWM_NIKE=9ca17ae2e6ffcda170e2e6ee83eb3bf7bba4aef04fb2868eb2c14a868e9fabf845bc89b690f9708a9bbe82c12af0fea7c3b92ab88a86d2cb5ea2be8f8dd66b9c9d8397e73bb091a188e980b588f78df84897bb8991b161f49ea5b1f76b85b7a990c763fcac8f99ca7a87bc8386f3609bea9aa4e16d8595a3a8db62828b81d2f759edb1bcafee52b8bfa7b8cb4ebabe9a85e73fa386af98d666b195a1a8ce3ff3ec86d4eb67f48ec0d5b53ea1ad89a6e441fce782a5d037e2a3; regionId=510000; visitor_type=old; 53gid0=10120942650019; 53kf_72242839_from_host=www.jiansheku.com; 53kf_72242839_keyword=https%3A%2F%2Fwww.jiansheku.com%2Fuser%2Findex.html; 53kf_72242839_land_page=https%253A%252F%252Fwww.jiansheku.com%252F; kf_72242839_land_page_ok=1; isClose=yes; 53uvid=1; onliner_zdfq72242839=0; Hm_lpvt_03b8714a30a2e110b8a13db120eb6774=1610844822; gdxidpyhxdE=GojzlUzc%2BgI6A92%2Bu%5ClCIKH%2BDS%2Fag87QdMV%2FCgX6utdL75azPS%5Cvd3Llt8xHnI8CcE58DBT7H%2F8s%5CtvlaxCJGwTdAHB%5CO7kHIn0fNZrtEh1cGQrNLtp162JZrVUn4ErsXVor1XE%5C%5CG9wjZGJ6%2F0bEf2VhzdgqmwI7q5SwJe91tcTJs8%2B%3A1610849073649");
        requestHeaders.add("Host","www.jiansheku.com");
        requestHeaders.add("Origin","https://www.jiansheku.com");
        requestHeaders.add("pw","dce208295cbdd7dae3ed4c423ab75007");
        requestHeaders.add("Referer","https://www.jiansheku.com/qiye.html");
        requestHeaders.add("sec-ch-ua-mobile","?0");
        requestHeaders.add("Sec-Fetch-Dest","empty");
        requestHeaders.add("Sec-Fetch-Mode","cors");
        requestHeaders.add("Sec-Fetch-Site","same-origin");
        requestHeaders.add("token","551fafbfc46b43158cbd5ab8f1b7bce5");
        requestHeaders.add("ty","js");
        requestHeaders.add("sec-ch-ua","'Google Chrome';v='87', ' Not;A Brand';v='99', 'Chromium';v='87'");
        requestHeaders.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36");
        Object str =postRequest("https://www.jiansheku.com/api/jsk/enterprise/search",requestBody,requestHeaders);
        //String id = HttpClientUtil.doGet("https://www.xgj668.com/index.php?m=ajax&c=for_link&eid=247711");
        System.out.println(str );
    }
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
            doc = Jsoup.connect(urlId+id).cookies(getCookies()).get();

            System.out.println("jieshao =" +doc.getElementsByClass("jieshao").text() );
            System.out.println("tel =" +doc.getElementsByClass("tel").get(1).text() );
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public String getPhoneInfo(String url){

        Document doc = null;
        try {
            doc = Jsoup.connect(url).cookies(getCookies()).get();
            return doc.getElementsByClass("tel").get(1).text();
        } catch (Exception e) {
            System.out.println("----------------------------------------------err url = [" + url + "]");
            e.printStackTrace();
        }
        return "";

        //stockZyRepository.save(stockZy);

    }

    public void infoPages(Integer page){
        Document doc = null;
        try {
            doc = Jsoup.connect(pageId+page).cookies(getCookies()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements elements = doc.getElementsByClass("hover");
        Integer size = elements.size();
        for (int i =1 ;i<size;i++){
            Element ine = elements.get(i);
            Elements names = ine.getElementsByClass("name");
            String info = names.get(0).getElementsByClass("content").text();
            String phone = getPhone(info);
            String url =names.get(0).children().get(1).getElementsByAttribute("href").attr("href");
            if(StringUtils.isBlank(phone)){
                phone=getPhoneInfo(url);
            }
            String name = names.get(0).children().get(1).text();
            if(cache.get(name+phone)!=null){
                continue;
            }
            Elements zhuanyes = ine.getElementsByClass("zhuanye");
            String zhuanye = zhuanyes.text();
            //System.out.println("name = [" + name + "]"+"zhuanye = [" + zhuanye +"][phone:"+phone+ "]url:"+url);
            StockZy stockZy = new StockZy();
            stockZy.setName(name);
            stockZy.setZy(zhuanye);
            stockZy.setPhone(phone);
            stockZy.setInfoDesc(info);
            try {
                stockZyRepository.save(stockZy);
            }catch (Exception e){
                System.out.println(page+stockZy.toString());
            }
            cache.put(name+phone,stockZy);


        }


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
