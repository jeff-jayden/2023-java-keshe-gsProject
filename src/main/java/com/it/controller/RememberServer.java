package com.it.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/rememberServer")
public class RememberServer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies=request.getCookies();
		HashMap<String, String> map = new HashMap<>();
		for(Cookie tmp:cookies) {
			if(tmp.getName().equals("username")) {
				map.put("username",tmp.getValue());
			}else if(tmp.getName().equals("password")) {
				map.put("password", tmp.getValue());
			}
		}

		ObjectMapper mapper=new ObjectMapper();
		mapper.writeValue(response.getWriter(),map);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}