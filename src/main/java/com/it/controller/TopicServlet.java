package com.it.controller;

import com.alibaba.fastjson.JSON;
import com.it.pojo.Code;
import com.it.pojo.PageBean;
import com.it.pojo.Result;
import com.it.pojo.Topic;
import com.it.service.api.TopicService;
import com.it.service.impl.TopicServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/topic/*")
public class TopicServlet extends BaseServlet{

    private TopicService topicService = new TopicServiceImpl();

    public void selectByPageAndCondition(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1. 接收 当前页码 和 每页展示条数    url?currentPage=1&pageSize=5
        String _currentPage = request.getParameter("currentPage");
        String _pageSize = request.getParameter("pageSize");

        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);

        // 获取查询条件对象
        BufferedReader br = request.getReader();
        String params = br.readLine();//json字符串

        Topic topic = JSON.parseObject(params, Topic.class);

        //2. 调用service查询
        PageBean<Topic> pageBean = topicService.selectByPageAndCondition(currentPage, pageSize, topic);

        //2. 转为JSON
        String jsonString = JSON.toJSONString(pageBean);
        //3. 写数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    //保存数据
    public void saveTopic(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        String jsonstr = reader.readLine();
        Topic topic = JSON.parseObject(jsonstr, Topic.class);
        boolean flag = topicService.saveTopic(topic);

        String jsonString = JSON.toJSONString(new Result(flag ? Code.SAVE_OK : Code.SAVE_ERR, flag));
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    //更新数据
    public void updateTopic(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        String jsonstr = reader.readLine();
        Topic topic = JSON.parseObject(jsonstr, Topic.class);
        boolean flag = topicService.updateTopic(topic);

        String jsonString = JSON.toJSONString(new Result(flag ? Code.UPDATE_OK : Code.UPDATE_ERR, flag));
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    //删除数据
    public void deleteById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        boolean flag = topicService.deleteTopic(Integer.parseInt(id));

        String jsonString = JSON.toJSONString(new Result(flag ? Code.DELETE_OK : Code.DELETE_ERR, flag));
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    //数据回显
    public void selectById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        Topic topic = topicService.selectById(Integer.parseInt(id));

        String msg = topic != null ? "查询成功" : "查询错误,请重试!";
        Integer code = topic != null ? Code.QUERY_OK : Code.QUERY_ERR;

        String jsonString = JSON.toJSONString(new Result(code, topic, msg));
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

}
