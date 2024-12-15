package com.it.dao.api;

import com.it.pojo.Teacher;

import java.util.List;

public interface TeacherDao {
    int selectTotalCountByCondition(Teacher teacher);
    List<Teacher> selectByPageAndCondition(int begin, int size, Teacher teacher);
    Teacher selectById(Integer id);
    Integer saveTeacher(Teacher t);
    Integer deleteTeacher(Integer id);
    Integer updateTeacher(Teacher teacher);
    List<Teacher> queryAllTeacher();

}
