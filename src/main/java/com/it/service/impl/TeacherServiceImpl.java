package com.it.service.impl;

import com.it.dao.impl.TeacherDaoImpl;
import com.it.pojo.PageBean;
import com.it.pojo.Teacher;
import com.it.service.api.TeacherService;

import java.util.List;

public class TeacherServiceImpl implements TeacherService {

    private TeacherDaoImpl teacherDao = new TeacherDaoImpl();

    public PageBean<Teacher> selectByPageAndCondition(int currentPage, int pageSize, Teacher teacher){
        int begin = (currentPage - 1) * pageSize;
        int size = pageSize;

        String tnum = teacher.getTnum();
        String tusername = teacher.getTusername();
        if (tnum != null && tnum.length() > 0){
            teacher.setTnum("%" + tnum + "%");
        }
        if (tusername != null && tusername.length() > 0){
            teacher.setTusername("%" + tusername + "%");
        }

        List<Teacher> teachers = teacherDao.selectByPageAndCondition(begin, size, teacher);
        int total = teacherDao.selectTotalCountByCondition(teacher);

        PageBean<Teacher> pageBean = new PageBean<>();
        pageBean.setRows(teachers);
        pageBean.setTotalCount(total);

        return pageBean;
    }

    @Override
    public Teacher selectById(Integer id) {
        return teacherDao.selectById(id);
    }

    @Override
    public boolean saveTeacher(Teacher teacher) {
        return teacherDao.saveTeacher(teacher) > 0;
    }

    @Override
    public boolean deleteTeacher(Integer id) {
        return teacherDao.deleteTeacher(id) > 0;
    }

    @Override
    public boolean updateTeacher(Teacher teacher) {
        return teacherDao.updateTeacher(teacher) > 0;
    }

    @Override
    public List<Teacher> queryAllTeacher() {
        return teacherDao.queryAllTeacher();
    }
}
