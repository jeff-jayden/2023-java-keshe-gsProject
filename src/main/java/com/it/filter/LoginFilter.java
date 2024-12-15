package com.it.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String[] urls = {"/css/", "/element-ui", "/imgs/", "/js/",
                "/login.html", "/loginServlet", "/checkCodeServlet", "/rememberServer"};

        HttpServletRequest req = (HttpServletRequest) request;
        String str = req.getRequestURL().toString();
        for (String url : urls) {
            if (str.contains(url)) {
                chain.doFilter(request, response);
                return;
            }
        }

        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        System.out.println("filter里的user：" + user);
        if (user != null) {
            //如果已经登录 放行
            chain.doFilter(req, response);
        } else {
            //未登录转到登录页面
            req.setAttribute("ERR_message", "当前状态未登录!");
            req.getRequestDispatcher("/portalServlet").forward(req, response);
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
