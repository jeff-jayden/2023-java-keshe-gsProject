package com.it.dao.api;

import com.it.pojo.Admin;
import com.it.pojo.Student;
import com.it.pojo.Teacher;

public interface LoginDao {
    Admin adminLogin(String username, String passwrod);

    Teacher teacherLogin(String username, String password);
    Student studentLogin(String username, String password);
}
