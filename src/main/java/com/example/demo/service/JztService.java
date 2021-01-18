package com.example.demo.service;

import com.example.demo.dao.CompanyInfoRepository;
import com.example.demo.dao.StockZyRepository;
import com.example.demo.domain.table.CompanyInfo;
import com.example.demo.domain.table.StockZy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class JztService {
    @Autowired
    CompanyInfoRepository companyInfoRepository;
    static String urlId ="https://www.kpwhr.com/index/index/jianlishow/id/";

    //static String pageId ="https://www.cbi360.net/hhb/techlist/?pageindex=2&andor=1&layer=true";
    static String cookiesStr = "BAIDU_SSP_lcr=https://www.baidu.com/link?url=U2L2tNtYLeaUz6lEOPZsuSFrWOM2VA1jsdrzgCyIsP3nlXmF7uhxpu9KH3i1RSas&wd=&eqid=93f0de8400201aca0000000660038933; UM_distinctid=1755eac4fb117f-07cf108e5a8c4d-3e604000-100200-1755eac4fb247f; ad_s_v_t_1024=1; cbi360_province=%e5%9b%9b%e5%b7%9d; sajssdk_2015_cross_new_user=1; Hm_lvt_680d256f97e094a807ba2e598bd502f9=1610844480,1610844483; Hm_lvt_9f1a930c5ecc26fe983d898c26bd5fea=1610844480,1610844483; Hm_lvt_2c83b793310ff6a04ed72f97dfc92eb9=1610844483; ad_s_v_t_0105=1; ad_s_v_m_0105=true; cbi360=accesstoken=AEF5033F50CC5721C5FAF1165DAA1671580C34894C91E7BA420697CD77F9E03CC0770F98161CC47CCD4F00196A3A32C87D45873A78E21390&expiretime=2021-12-27&ishistoryvip=0&isvip=1&nickname=&parentuseraccount=18782264409&parentuserid=554CB8134FD9C211D07EB4A82A2CDFE64C98EE89FB527211B0E0AA05B6B8EBCA42CB4B70F7607055&province=%e5%9b%9b%e5%b7%9d&sign=b4196d3d81eb2f2d&token=6d049c6e90134073af6121497c69bfb7&uid=3d51a46237fe6e2a&uidsign=dc14a39099a2a674&useraccount=18782264409&userid=554CB8134FD9C211D07EB4A82A2CDFE64C98EE89FB527211B0E0AA05B6B8EBCA42CB4B70F7607055&username=%e6%9d%a8%e8%a1%8c%e6%94%bf&viplevel=|10|&wxlogin=True; CNZZDATA30036250=cnzz_eid%3D1043080172-1603609384-https%253A%252F%252Fwww.baidu.com%252F%26ntime%3D1610846886; ad_s_u_t_0105=1; ad_s_u_m_0105=true; cbi360_localization=false; ad_lb_u_t_0105=1; ad_lb_u_m_0105=true; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%223b413230-8fcc-4e78-bace-b9e845ee3a59%22%2C%22first_id%22%3A%221770dd02452144-07c5b8ae387a06-3f604900-1049088-1770dd024533be%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22%24device_id%22%3A%221770dd02452144-07c5b8ae387a06-3f604900-1049088-1770dd024533be%22%7D; Hm_lpvt_680d256f97e094a807ba2e598bd502f9=1610853890; Hm_lpvt_2c83b793310ff6a04ed72f97dfc92eb9=1610853890; Hm_lpvt_9f1a930c5ecc26fe983d898c26bd5fea=1610853890";


    static String pageId ="https://www.cbi360.net/hhb/techlist/?&andor=1&layer=true&technique=";
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



    public void infoPages(Integer page,String param,String namezy){
        //TestProxyIp.setHttpsProxyIp();
        Document doc = null;
        try {
            doc = Jsoup.connect(pageId+param+"&pageindex="+page).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:2.0.1) Gecko/20100101 Firefox/4.0.1").cookies(getCookies()).get();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        String text =doc.getElementsByTag("script").get(10).toString();
        int start1 = text.lastIndexOf("公司");
        while (start1>-1){
            CompanyInfo companyInfo =new CompanyInfo();

            int start0 =text.lastIndexOf(",",start1);
            String name = text.substring(start0+2,start1+2);
            List<CompanyInfo> list = companyInfoRepository.findByName(name);

            start1 = text.lastIndexOf("公司",start0);
            companyInfo.setName(name);
            if(list.size()>0){
                companyInfo=list.get(0);
                if(companyInfo.getZy().length()<1999){
                    companyInfo.setZy(companyInfo.getZy()+"_"+namezy);
                }
                System.out.println("=========================================》》已存在的公司 aname = [" + name + "]");
            }else {
                companyInfo.setZy(namezy+"_一级及以上");
                System.out.println("------新记录--- [" + name + "]");
            }
            companyInfoRepository.save(companyInfo);
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
