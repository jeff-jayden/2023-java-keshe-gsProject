package com.it.service.api;

import com.it.pojo.PageBean;
import com.it.pojo.Teacher;

import java.util.List;

public interface TeacherService {

    PageBean<Teacher> selectByPageAndCondition(int currentPage, int pageSize, Teacher teacher);

    Teacher selectById(Integer id);

    //增

    boolean saveTeacher(Teacher teacher);
    //删

    boolean deleteTeacher(Integer id);
    //改

    boolean updateTeacher(Teacher teacher);
    //查

    List<Teacher> queryAllTeacher();
}
