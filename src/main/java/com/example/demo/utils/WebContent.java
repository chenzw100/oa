package com.example.demo.utils;

import org.apache.logging.log4j.util.Strings;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.FrameworkServlet;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class WebContent {
    private static Pattern ip = Pattern.compile("^(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9]{1,2})(\\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9]{1,2})){3}$");
    private static ThreadLocal<Map<String, Object>> local = new ThreadLocal();

    public WebContent() {
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest)get("req");
    }

    public static HttpServletResponse getResponse() {
        return (HttpServletResponse)get("resp");
    }

    public static PermissionContext getPC() {
        return (PermissionContext)get("manage.permission_context");
    }


    public static void resetLocal() {
        local.remove();
    }

    public static ApplicationContext getApplicationContext() {
        HttpServletRequest req = (HttpServletRequest)get("req");
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(req.getSession().getServletContext());
        if (context == null) {
            ServletContext sc = req.getSession().getServletContext();
            Enumeration e = sc.getAttributeNames();

            while(e.hasMoreElements()) {
                String name = e.nextElement().toString();
                if (name.startsWith(FrameworkServlet.SERVLET_CONTEXT_PREFIX) && sc.getAttribute(name) instanceof ApplicationContext) {
                    context = (ApplicationContext)sc.getAttribute(name);
                    return context;
                }
            }
        }

        return context;
    }

    public static void put(HttpServletRequest request) {
        put("req", request);
    }

    public static void put(HttpServletResponse response) {
        put("resp", response);
    }

    public static void put(String key, Object obj) {
        Map<String, Object> map = (Map)local.get();
        if (map == null) {
            map = new HashMap();
            local.set(map);
        }

        ((Map)map).put(key, obj);
    }

    public static Object get(String key) {
        Map<String, Object> map = (Map)local.get();
        return map == null ? null : map.get(key);
    }

    public static void clean() {
        local.set((Map<String, Object>) null);
    }

    public static void setCookie(String key, String val) {
        setCookie(key, val, SessionConstants.COOKIE_TIMEOUT, getCookieDomain(getRequest().getRequestURL().toString()));
    }

    public static void setCookie(String key, String val, String domain) {
        setCookie(key, val, SessionConstants.COOKIE_TIMEOUT, getCookieDomain(getRequest().getRequestURL().toString()));
    }

    public static void setCookie(String key, String val, Integer maxAge, String domain) {
        Cookie cookie = null;

        try {
            cookie = new Cookie(key, URLEncoder.encode(val, "utf-8"));
            cookie.setDomain(domain);
            cookie.setMaxAge(maxAge);
            cookie.setPath("/");
            HttpServletResponse resp = getResponse();
            resp.addCookie(cookie);
        } catch (UnsupportedEncodingException var6) {
            throw new RuntimeException("cookie写入失败");
        }
    }

    public static String getCookie(String key) {
        HttpServletRequest req = getRequest();
        if (req.getCookies() == null) {
            return null;
        } else {
            Cookie[] arr$ = req.getCookies();
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                Cookie c = arr$[i$];
                if (c.getName().equals(key)) {
                    try {
                        return URLDecoder.decode(c.getValue(), "utf-8");
                    } catch (UnsupportedEncodingException var7) {
                        var7.printStackTrace();
                    }
                }
            }

            return null;
        }
    }

    private static String getCookieDomain(String uri) {
        try {
            return getCookieDomain(new URI(uri));
        } catch (URISyntaxException var2) {
            throw new RuntimeException("URI转换出错!");
        }
    }

    private static String getCookieDomain(URI uri) throws URISyntaxException {
        String host = uri.getHost();
        if (ip.matcher(host).find()) {
            return host;
        } else {
            byte var3 = -1;
            switch(host.hashCode()) {
                case -1204607085:
                    if (host.equals("localhost")) {
                        var3 = 1;
                    }
                    break;
                case 1505998205:
                    if (host.equals("127.0.0.1")) {
                        var3 = 0;
                    }
            }

            switch(var3) {
                case 0:
                case 1:
                    return host;
                default:
                    String domain = host.substring(host.lastIndexOf(".", host.lastIndexOf(".") - 1) + 1);
                    return domain;
            }
        }
    }

    public static String getCookieVal(String cookieName) {
        Cookie[] cookies = getRequest().getCookies();
        if (cookies != null && !Strings.isEmpty(cookieName)) {
            Cookie[] arr$ = cookies;
            int len$ = cookies.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                Cookie cookie = arr$[i$];
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
    public static Long getUserId() {
        HttpServletRequest request = (HttpServletRequest)get("req");
        Object userId = request.getSession().getAttribute("userId");
        return Long.parseLong(userId.toString());
    }
    public static String getUserName() {
        HttpServletRequest request = (HttpServletRequest) get("req");
        Object userName = request.getSession().getAttribute("userName");
        return userName.toString();
    }
    public static String getUserInfoLevel() {
        HttpServletRequest request = (HttpServletRequest) get("req");
        Object userName = request.getSession().getAttribute("userInfoLevel");
        return userName.toString();
    }

}
