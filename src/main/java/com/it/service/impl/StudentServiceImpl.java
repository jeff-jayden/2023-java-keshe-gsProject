package com.it.service.impl;

import com.it.dao.api.StudentDao;
import com.it.dao.impl.StudentDaoImpl;
import com.it.pojo.PageBean;
import com.it.pojo.Student;
import com.it.service.api.StudentService;

import java.util.List;

public class StudentServiceImpl implements StudentService {
    private StudentDao studentDao = new StudentDaoImpl();
    public PageBean<Student> selectByPageAndCondition(int currentPage, int pageSize, Student student){
        int begin = (currentPage - 1) * pageSize;
        int size = pageSize;

        String snum = student.getSnum();
        String susername = student.getSusername();
        if (snum != null && snum.length() > 0){
            student.setSnum("%" + snum + "%");
        }
        if (susername != null && susername.length() > 0){
            student.setSusername("%" + susername + "%");
        }

        List<Student> students = studentDao.selectByPageAndCondition(begin, size, student);
        int total = studentDao.selectTotalCountByCondition(student);

        PageBean<Student> pageBean = new PageBean<>();
        pageBean.setRows(students);
        pageBean.setTotalCount(total);

        return pageBean;
    }

    @Override
    public Student selectById(Integer id) {
        return studentDao.selectById(id);
    }

    @Override
    public boolean saveStudent(Student student) {
        return studentDao.saveStudent(student) > 0;
    }

    @Override
    public boolean deleteStudent(Integer id) {
        return studentDao.deleteStudent(id) > 0;
    }

    @Override
    public boolean updateStudent(Student student) {
        return studentDao.updateStudent(student) > 0;
    }

    @Override
    public boolean updateSelectStudent(Student student) {
        return studentDao.updateSelectStudent(student) > 0;
    }

    @Override
    public List<Student> queryAllStudent() {
        return studentDao.selectAll();
    }

}
