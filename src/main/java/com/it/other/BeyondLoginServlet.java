package com.it.other;

import com.it.controller.ViewBaseServlet;
import com.it.util.CookieUtil;
import com.it.util.ThymeleafUtil;
import org.thymeleaf.context.Context;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/login")
public class BeyondLoginServlet extends ViewBaseServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = CookieUtil.getCookieValByKey("username", req);
        String password = CookieUtil.getCookieValByKey("password", req);

        System.out.println(username);
        System.out.println(password);

        Context ctx = new Context();

        if (username != null && password != null) {
            ctx.setVariable("username", username);
            ctx.setVariable("password", password);
        }
        resp.setContentType("text/html;charset=utf-8");
        System.out.println("未执行");
        ThymeleafUtil.process("login.html", ctx, resp.getWriter());
//        req.getRequestDispatcher("/portalServlet").forward(req, resp);
        System.out.println("已执行");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
