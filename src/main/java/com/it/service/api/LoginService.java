package com.it.service.api;

import com.it.pojo.Admin;
import com.it.pojo.Student;
import com.it.pojo.Teacher;

public interface LoginService {
    //登录
    Admin adminLogin(String username, String password);

    Teacher teacherLogin(String username, String password);

    Student studentLogin(String username, String password);
}
