package com.it.controller;

import com.it.pojo.*;
import com.it.service.api.LoginService;
import com.it.service.impl.LoginServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/loginServlet/*")
public class LoginServlet extends BaseServlet {

    private LoginService loginService = new LoginServiceImpl();


    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String identity = request.getParameter("identity");
        String remember = request.getParameter("remember");
        String checkCode = request.getParameter("checkCode");
//        String autolog = request.getParameter("autolog");
        User u = new User(username, password);

        // 程序生成的验证码，从Session获取
        HttpSession session = request.getSession();
        String checkCodeGen = (String) session.getAttribute("checkCodeGen");
        System.out.println(checkCodeGen);

        // 比对
        /*if(!checkCode.equalsIgnoreCase(checkCodeGen)){
            request.setAttribute("ERR_message","验证码错误");
            request.getRequestDispatcher("/portalServlet").forward(request,response);
            // 不允许登录
            return;
        }*/

        if ("1".equals(identity)) {
            Admin admin = loginService.adminLogin(username, password);
            if (admin != null) {
                if ("1".equals(remember)) {
                    //设置cookie

                    Cookie C_username = new Cookie("username", admin.getUsername());
                    Cookie C_password = new Cookie("password", admin.getPassword());

                    C_username.setMaxAge(60 * 60);
                    C_password.setMaxAge(60 * 60);

                    C_username.setPath(request.getContextPath());
                    C_password.setPath(request.getContextPath());

                    response.addCookie(C_username);
                    response.addCookie(C_password);
                }
                session.setAttribute("user", u);
                session.setAttribute("admin", admin);
                session.setMaxInactiveInterval(60 * 10);
                processTemplate("awelcome", request, response);
            } else {
                request.setAttribute("ERR_message", LoginInfo.ADMINLOGIN_ERR);
                processTemplate("login", request, response);
            }
        } else if ("2".equals(identity)) {
            Teacher teacher = loginService.teacherLogin(username, password);
            System.out.println(teacher);
            if (teacher != null) {

                if ("1".equals(remember)) {

                    Cookie C_username = new Cookie("username", teacher.getTusername());
                    Cookie C_password = new Cookie("password", teacher.getTpassword());

                    C_username.setMaxAge(60 * 60);
                    C_password.setMaxAge(60 * 60);

                    C_username.setPath(request.getContextPath());
                    C_password.setPath(request.getContextPath());

                    response.addCookie(C_username);
                    response.addCookie(C_password);

                }
                session.setAttribute("user", u);
                session.setAttribute("teacher", teacher);
                session.setMaxInactiveInterval(60 * 10);
                processTemplate("twelcome", request, response);
            } else {
                request.setAttribute("ERR_message", LoginInfo.TEACHERLOGIN_ERR);
                processTemplate("login", request, response);
            }
        } else if ("3".equals(identity)) {
            Student student = loginService.studentLogin(username, password);
            if (student != null) {

                if ("1".equals(remember)) {

                    Cookie C_username = new Cookie("username", student.getSusername());
                    Cookie C_password = new Cookie("password", student.getSpassword());

                    C_username.setMaxAge(60 * 60);
                    C_password.setMaxAge(60 * 60);

                    C_username.setPath(request.getContextPath());
                    C_password.setPath(request.getContextPath());

                    response.addCookie(C_username);
                    response.addCookie(C_password);

                }
                session.setAttribute("user", u);
                session.setAttribute("student", student);
                session.setMaxInactiveInterval(60 * 10);
                processTemplate("swelcome", request, response);
            } else {
                request.setAttribute("ERR_message", LoginInfo.STUDENTLOGIN_ERR);
                processTemplate("login", request, response);
            }
        }


    }

    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

//        Context ctx = new Context();

        // 1、通过 request 对象获取 HttpSession 对象
        HttpSession session = request.getSession();

        // 2、将 HttpSession 对象强制失效
        session.removeAttribute("user");
        session.invalidate();


        response.setContentType("text/html;charset=utf-8");
//        response.sendRedirect(request.getContextPath() + "/login");

//        ThymeleafUtil.process("login.html", ctx, response.getWriter());
        response.getWriter().write("success");
//        request.getRequestDispatcher("/login").forward(request, response);



    }
}
