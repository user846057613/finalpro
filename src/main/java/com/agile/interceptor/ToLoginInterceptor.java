package com.agile.interceptor;

import com.agile.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

public class ToLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String[] noNeedAuthPage = {
                "/home",
                "/admin",
                "/login",
                "/checkLogin",
                "/loginPage",
                "/registerPage",
                "/registerSuccessPage",
                "/register",
                "/searchProduct",
                "/showProduct",
                "/sortProduct",
        };
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        uri = uri.substring(uri.lastIndexOf(contextPath)+contextPath.length());
//        System.out.println(uri);
        if(Arrays.asList(noNeedAuthPage).contains(uri)) {
            return true;
        }else {
            User user = (User) session.getAttribute("user");
            if(user == null) {
                response.sendRedirect("./loginPage");
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
