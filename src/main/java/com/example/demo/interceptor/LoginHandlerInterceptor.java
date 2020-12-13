package com.example.demo.interceptor;

import com.example.demo.utils.WebContent;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Object user = request.getSession().getAttribute("userName");
        System.out.println("afterCompletion----" + user + " ::: " + request.getRequestURL());
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Object user = request.getSession().getAttribute("userName");

        System.out.println("postHandle----" + user + " ::: " + request.getRequestURL());

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user = request.getSession().getAttribute("userName");
        System.out.println("preHandle----" + user + " ::: " + request.getRequestURL());

        if (user == null) {
            request.setAttribute("msg","无权限请先登录");
            // 获取request返回页面到登录页
            request.getRequestDispatcher("/index.html").forward(request, response);
            return false;
        }
        WebContent.put(request);
        WebContent.put(response);
        return true;
    }
}
