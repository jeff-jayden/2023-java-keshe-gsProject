package com.it.service.impl;

import com.it.dao.api.AdminDao;
import com.it.dao.api.LoginDao;
import com.it.dao.impl.AdminDaoImpl;
import com.it.dao.impl.LoginDaoImpl;
import com.it.pojo.Admin;
import com.it.pojo.Student;
import com.it.pojo.Teacher;
import com.it.service.api.LoginService;
import com.it.util.MD5Util;

public class LoginServiceImpl implements LoginService {

    private LoginDao loginDao = new LoginDaoImpl();

    @Override
    public Admin adminLogin(String username, String password) {
        return loginDao.adminLogin(username, password);
    }

    @Override
    public Teacher teacherLogin(String username, String password) {
        /*String encode = MD5Util.encode(password);
        System.out.println(username);
        System.out.println(encode);*/
        return loginDao.teacherLogin(username, password);
    }

    @Override
    public Student studentLogin(String username, String password) {
        /*String encode = MD5Util.encode(password);
        System.out.println(encode);*/
        return loginDao.studentLogin(username, password);
    }

}
