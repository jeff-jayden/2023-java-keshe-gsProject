package com.it.service.api;

import com.it.pojo.PageBean;
import com.it.pojo.Student;
import com.it.pojo.Teacher;

import java.util.List;


public interface StudentService {
    PageBean<Student> selectByPageAndCondition(int currentPage, int pageSize, Student student);

    Student selectById(Integer id);
    //增
    boolean saveStudent(Student student);
    //删
    boolean deleteStudent(Integer id);
    //改
    boolean updateStudent(Student student);
    boolean updateSelectStudent(Student student);

    List<Student> queryAllStudent();
}
