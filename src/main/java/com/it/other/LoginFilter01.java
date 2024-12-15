package com.it.other;

import com.it.dao.api.UserDao;
import com.it.dao.impl.UserDaoImpl;
import com.it.pojo.User;
import com.it.util.CookieUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//自动登录
//@WebFilter("/*")
public class LoginFilter01 implements Filter {


    private UserDao userDao = new UserDaoImpl();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;

        String username = CookieUtil.getCookieValByKey("username", req);
        String password = CookieUtil.getCookieValByKey("password", req);


        if (username != null && password != null){
            User user = userDao.selectUser(username, password);
            //如果查到了 就进行session的存储
            if (user != null){
                req.getSession().setAttribute("user",user);
            }
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }
}
