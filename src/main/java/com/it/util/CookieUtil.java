package com.it.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class CookieUtil {
    public static String getCookieValByKey(String key, HttpServletRequest request) {
        String value = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            /*Cookie cookie1 = Arrays.stream(cookies).filter(e -> key.equals(e.getName())).findAny().orElse(null);
            cookie1.getValue();*/
            for (Cookie cookie : cookies) {
                if (key.equals(cookie.getName())) {
                    value = cookie.getValue();
                }
            }
        }
        return value;
    }
    public static void deleteCookieValByKey(String key, HttpServletRequest request, HttpServletResponse response){
        String value = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (key.equals(cookie.getName())) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }
}