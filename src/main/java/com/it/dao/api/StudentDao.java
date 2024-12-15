package com.it.dao.api;

import com.it.pojo.Student;

import java.util.List;

public interface StudentDao {
    int selectTotalCountByCondition(Student student);
    List<Student> selectByPageAndCondition(int begin, int size, Student student);
    Student selectById(Integer id);
    Integer saveStudent(Student t);
    Integer deleteStudent(Integer id);
    Integer updateStudent(Student student);
    Integer updateSelectStudent(Student student);

    List<Student> selectAll();
}
