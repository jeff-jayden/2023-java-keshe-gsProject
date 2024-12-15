package com.it.controller;

import com.alibaba.fastjson.JSON;
import com.it.dao.api.TopicDao;
import com.it.pojo.*;
import com.it.service.api.StudentService;
import com.it.service.api.TeacherService;
import com.it.service.api.TopicResService;
import com.it.service.api.TopicService;
import com.it.service.impl.StudentServiceImpl;
import com.it.service.impl.TeacherServiceImpl;
import com.it.service.impl.TopicResServiceImpl;
import com.it.service.impl.TopicServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/teacher/*")
public class TeacherServlet extends BaseServlet {

    private TopicService topicService = new TopicServiceImpl();
    private TopicResService topicResService = new TopicResServiceImpl();
    private StudentService studentService = new StudentServiceImpl();
    private TeacherService teacherService = new TeacherServiceImpl();


    public void removeStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.获取老师对象将所选学生id删除
        Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
        //获取老师姓名
        String tusername = teacher.getTusername();
        //获取学生的id
        int id = Integer.parseInt(request.getParameter("id"));
        //得到老师的选择列表
        String[] arr = {};
        String toption = teacher.getToption();
        if (toption.length() > 1) {
            arr = toption.substring(1, toption.length() - 1).split(", ");
        }
        Set<Integer> Tlist = Arrays.stream(arr).map(Integer::parseInt).collect(Collectors.toSet());
        boolean removeflag = Tlist.remove(id);
        if (removeflag) {
            teacher.setToption(Tlist.toString());
            teacherService.updateTeacher(teacher);
            //2.删除已选择的毕设结果
            Student student = studentService.selectById(id);
            //学生名称
            String susername = student.getSusername();
            //根据老师姓名 学生姓名进行删除
            topicResService.deleteByDoubleName(tusername, susername);
            //3.根据id查询学生将状态复原
            student.setStatus(0);
            studentService.updateSelectStudent(student);
        }
        String jsonString = JSON.toJSONString(new Result(removeflag ? Code.DELETE_OK : Code.DELETE_ERR, removeflag));
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void selectStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
        //获取老师名称
//        System.out.println(teacher);
        String tusername = teacher.getTusername();

        //获取当前学生id并存入库中
        //1.将学生id存入老师选择
        //获取学生的id
        int id = Integer.parseInt(request.getParameter("id"));
        Student student = studentService.selectById(id);
        Integer status = student.getStatus();
        //如果该学生已经被选择 则提示已被选择 请选择其他学生
        if (status == 1) {
            String jsonString = JSON.toJSONString(new Result(Code.SELECTFAIL,null,"该学生已被选择"));
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write(jsonString);
        } else {
            //得到老师的选择列表
            String[] arr = {};
            Set<Integer> Tlist = new HashSet<>();
            String toption = teacher.getToption();
//            System.out.println(toption.length());
            if (toption.length() == 3) {
                Tlist.add(Integer.parseInt(toption.substring(1,toption.length()-1)));
            } else if (toption.length() > 3){
                arr = toption.substring(1, toption.length() - 1).split(", ");
                Tlist = Arrays.stream(arr).map(Integer::parseInt).collect(Collectors.toSet());
            }

            boolean addflag = Tlist.add(id);
            if (addflag) {
                teacher.setToption(Tlist.toString());
                teacherService.updateTeacher(teacher);
                //2.通过学生id封装topicres对象存入毕设结果中

                //学生名称
                String susername = student.getSusername();
                //获取学生所选课题
                String toname = topicService.selectById(student.getSoption()).getToname();
                //封装结果
                TopicRes topicRes = new TopicRes();
                topicRes.setToname(toname);
                topicRes.setTname(tusername);
                topicRes.setSname(susername);
                topicResService.saveTopicRes(topicRes);
                //3.通过id获取当前学生设置状态为已选择
                student.setStatus(1);
                studentService.updateSelectStudent(student);
            }
            String jsonString = JSON.toJSONString(new Result(addflag ? Code.UPDATE_OK : Code.UPDATE_ERR, addflag));
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write(jsonString);
        }

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

        Teacher teacher = JSON.parseObject(params, Teacher.class);

        //2. 调用service查询
        PageBean<Teacher> pageBean = teacherService.selectByPageAndCondition(currentPage, pageSize, teacher);

        //2. 转为JSON
        String jsonString = JSON.toJSONString(pageBean);
        //3. 写数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    //保存数据
    public void saveTeacher(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        String jsonstr = reader.readLine();
        Teacher teacher = JSON.parseObject(jsonstr, Teacher.class);
        System.out.println(teacher);
        boolean flag = teacherService.saveTeacher(teacher);

        String jsonString = JSON.toJSONString(new Result(flag ? Code.SAVE_OK : Code.SAVE_ERR, flag));
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    //更新数据
    public void updateTeacher(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        String jsonstr = reader.readLine();
        Teacher teacher = JSON.parseObject(jsonstr, Teacher.class);
//        teacher.setTpassword(MD5Util.encode(teacher.getTpassword()));
        boolean flag = teacherService.updateTeacher(teacher);

        String jsonString = JSON.toJSONString(new Result(flag ? Code.UPDATE_OK : Code.UPDATE_ERR, flag));
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    //删除数据
    public void deleteById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        boolean flag = teacherService.deleteTeacher(Integer.parseInt(id));

        String jsonString = JSON.toJSONString(new Result(flag ? Code.DELETE_OK : Code.DELETE_ERR, flag));
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    //查询所有老师信息
    public void selectAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Teacher> teachers = teacherService.queryAllTeacher();
        String msg = teachers != null ? "查询成功" : "查询错误,请重试!";
        Integer code = teachers != null ? Code.QUERY_OK : Code.QUERY_ERR;

        String jsonString = JSON.toJSONString(new Result(code, teachers, msg));

        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    //数据回显
    public void selectById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        Teacher teacher = teacherService.selectById(Integer.parseInt(id));

        String msg = teacher != null ? "查询成功" : "查询错误,请重试!";
        Integer code = teacher != null ? Code.QUERY_OK : Code.QUERY_ERR;

        String jsonString = JSON.toJSONString(new Result(code, teacher, msg));
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }
}
