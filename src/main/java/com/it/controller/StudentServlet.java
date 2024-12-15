package com.it.controller;

import com.alibaba.fastjson.JSON;
import com.it.pojo.*;
import com.it.service.api.StudentService;
import com.it.service.api.TopicResService;
import com.it.service.api.TopicService;
import com.it.service.impl.StudentServiceImpl;
import com.it.service.impl.TopicResServiceImpl;
import com.it.service.impl.TopicServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet("/student/*")
public class StudentServlet extends BaseServlet {
    private TopicService topicService = new TopicServiceImpl();
    private TopicResService topicResService = new TopicResServiceImpl();
    private StudentService studentService = new StudentServiceImpl();

    public void removeTopic(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Student student = (Student) request.getSession().getAttribute("student");
        //1.获取课题的studentID
        int id = Integer.parseInt(request.getParameter("id"));
        Topic topic = topicService.selectById(id);
        boolean flag = false;
        //如果学生的选择不是空 就执行删除操作
        if (student.getSoption() != 0 && student.getSoption() == id) {
            //1.将topic中学生的集合拿出来
            String[] arr = {};
            Set<Integer> Slist = new HashSet<>();
            String studentID = topic.getStudentID();
            if (studentID.length() == 3) {
                Slist.add(Integer.parseInt(studentID.substring(1, studentID.length() - 1)));
            } else if (studentID.length() > 3) {
                arr = studentID.substring(1, studentID.length() - 1).split(", ");
                Slist = Arrays.stream(arr).map(Integer::parseInt).collect(Collectors.toSet());
            }
            //2.移除当前学生id
            Slist.remove(student.getId());
            topic.setStudentID(Slist.toString());
            //3.设置当前题目状态为未选择
            topic.setStatus(0);
            //更新当前课题
            topicService.updateTopic(topic);
            flag = true;
            //4.恢复学生选择为空
            student.setSoption(0);
            studentService.updateStudent(student);
        }
        String jsonString = JSON.toJSONString(new Result(flag ? Code.UPDATE_OK : Code.UPDATE_ERR, flag));
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void selectTopic(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Student student = (Student) request.getSession().getAttribute("student");
        System.out.println(student);
        //1.获取当前选择题号
        int id = Integer.parseInt(request.getParameter("id"));
        Topic topic = topicService.selectById(id);
        boolean flag = false;
        //2.将学生选择题号存入库中 先判断是否已经选择 已选则提示先移除在选择
        //如果还未选择 执行选择
        if (student.getSoption() == 0) {
            //1.将topic中学生的集合拿出来
            String[] arr = {};
            Set<Integer> Slist = new HashSet<>();
            String studentID = topic.getStudentID();
            if (studentID.length() == 3) {
                Slist.add(Integer.parseInt(studentID.substring(1, studentID.length() - 1)));
            } else if (studentID.length() > 3) {
                arr = studentID.substring(1, studentID.length() - 1).split(", ");
                Slist = Arrays.stream(arr).map(Integer::parseInt).collect(Collectors.toSet());
            }
            //1.2并添加当前学生
            Slist.add(student.getId());
            //1.3更新题目studentID
            topic.setStudentID(Slist.toString());
            //2.将当前课题状态设置为已选择
            topic.setStatus(1);
            topicService.updateTopic(topic);
            //3.更新学生课题选择
            flag = true;
            student.setSoption(id);
            studentService.updateStudent(student);
            //更新课题分配结果
            String toname = topic.getToname();
            TopicRes topicRes = topicResService.selectBySname(student.getSusername());
            if (topicRes != null){
                //如果学生选择了 将选择结果进行更新
                topicRes.setToname(toname);
                //更新课设分配结果
                topicResService.updateTopicRes(topicRes);
            }
        }
        String jsonString = JSON.toJSONString(new Result(flag ? Code.UPDATE_OK : Code.UPDATE_ERR, flag));
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void selectByPageAndCondition(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //1. 接收 当前页码 和 每页展示条数    url?currentPage=1&pageSize=5
        String _currentPage = request.getParameter("currentPage");
        String _pageSize = request.getParameter("pageSize");

        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);

        // 获取查询条件对象
        BufferedReader br = request.getReader();
        String params = br.readLine();//json字符串

        Student student1 = JSON.parseObject(params, Student.class);

        //2. 调用service查询
        PageBean<Student> pageBean = studentService.selectByPageAndCondition(currentPage, pageSize, student1);

        //2. 转为JSON
        String jsonString = JSON.toJSONString(pageBean);
        //3. 写数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    //保存数据
    public void saveStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        String jsonstr = reader.readLine();
        Student student = JSON.parseObject(jsonstr, Student.class);
        boolean flag = studentService.saveStudent(student);

        String jsonString = JSON.toJSONString(new Result(flag ? Code.SAVE_OK : Code.SAVE_ERR, flag));
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    //更新数据
    public void updateStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        String jsonstr = reader.readLine();
        Student student = JSON.parseObject(jsonstr, Student.class);
//        student.setSpassword(MD5Util.encode(student.getSpassword()));
        boolean flag = studentService.updateStudent(student);

        String jsonString = JSON.toJSONString(new Result(flag ? Code.UPDATE_OK : Code.UPDATE_ERR, flag));
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    //删除数据
    public void deleteById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        boolean flag = studentService.deleteStudent(Integer.parseInt(id));

        String jsonString = JSON.toJSONString(new Result(flag ? Code.DELETE_OK : Code.DELETE_ERR, flag));
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    //数据回显
    public void selectById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        Student student = studentService.selectById(Integer.parseInt(id));

        String msg = student != null ? "查询成功" : "查询错误,请重试!";
        Integer code = student != null ? Code.QUERY_OK : Code.QUERY_ERR;

        String jsonString = JSON.toJSONString(new Result(code, student, msg));
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

}
