package com.it.other;

import com.it.pojo.Admin;
import com.it.pojo.LoginInfo;
import com.it.pojo.Student;
import com.it.pojo.Teacher;
import com.it.service.api.LoginService;
import com.it.service.impl.LoginServiceImpl;
import com.it.util.ThymeleafUtil;
import org.thymeleaf.context.Context;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/loginServlet")
public class LoginServlet2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        if(req.getSession().getAttribute("username")!=null)
        {
            //已经登录根据用户的身份跳转不同的页面
            return;
        }
        // 先判断是否之前登录失败，如果失败则无需从Cookie中获取
        if(req.getSession().getAttribute("ERR_message")!=null)
        {
            // 检查是否存在记住密码的Cookie
            Cookie[] cookies = req.getCookies();
            String rememberedUsername = null;
            String rememberedPassword = null;
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("username".equals(cookie.getName())) {
                        rememberedUsername = cookie.getValue();
                    } else if ("password".equals(cookie.getName())) {
                        rememberedPassword = cookie.getValue();
                    }
                }
            }

            // 设置Thymeleaf模板中的变量，以便自动填充用户名和密码

            context.setVariable("username",rememberedUsername);
            context.setVariable("password",rememberedPassword);
        }

        // 转发到登录页面
        ThymeleafUtil.process("login.html",context,resp.getWriter());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Integer identity = Integer.parseInt(req.getParameter("identity"));
        Integer remeber = 0;
        Integer autolog = 0;
        if(req.getParameter("remeber")!=null)
        {
            remeber = Integer.parseInt(req.getParameter("remeber"));
        }
        if(req.getParameter("autolog")!=null)
        {
            autolog = Integer.parseInt(req.getParameter("autolog"));
        }

        LoginService loginService = new LoginServiceImpl();
        Context context = new Context();
        switch(identity){
            case 1:
                Admin admin = loginService.adminLogin(username, password);
                if(admin!=null)
                {
                    if(remeber==1){
                        //处理记住逻辑
                        //将用户名和密码保存到Cookie中
                        // 处理记住密码逻辑
                        Cookie usernameCookie = new Cookie("username", username);
                        Cookie passwordCookie = new Cookie("password", password);

                        // 设置Cookie的有效期，例如设置为30天
                        int maxAge = 30 * 24 * 60 * 60; // 30天的秒数
                        usernameCookie.setMaxAge(maxAge);
                        passwordCookie.setMaxAge(maxAge);

                        resp.addCookie(usernameCookie);
                        resp.addCookie(passwordCookie);
                    }
                    //将用户已经登录的数据存入session中
                    req.getSession().setAttribute("username",username);
                    //登录跳转的页面
//                    resp.sendRedirect("/awelcome.html");

                    ThymeleafUtil.process("awelcome.html",context,resp.getWriter());
                } else {
                    context.setVariable("ERR_message", LoginInfo.ADMINLOGIN_ERR);
                    ThymeleafUtil.process("login.html",context,resp.getWriter());
                }
                break;
            case 2:
                Teacher teacher = loginService.teacherLogin(username, password);
                break;
            case 3:
                Student student = loginService.studentLogin(username, password);
                break;
        }

    }
}
