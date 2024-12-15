package com.it.dao.impl;

import com.it.dao.api.LoginDao;
import com.it.pojo.Admin;
import com.it.pojo.Student;
import com.it.pojo.Teacher;
import com.it.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDaoImpl implements LoginDao {
    Connection conn = DBUtil.getConnection();

    @Override
    public Admin adminLogin(String username, String passwrod) {
        String sql =  "select * from tb_admin where username = ? and password = ?";
        Admin admin = null;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,username);
            pstmt.setString(2,passwrod);
            ResultSet re = pstmt.executeQuery();
            while (re.next()){
                admin = new Admin(re.getInt(1),re.getString(2),
                        re.getString(3));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return admin;
    }

    @Override
    public Teacher teacherLogin(String username, String password) {
        String sql =  "select * from tb_teacher where Tusername = ? and Tpassword = ?";
        Teacher teacher = null;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,username);
            pstmt.setString(2,password);
            ResultSet re = pstmt.executeQuery();
            while (re.next()){
                teacher = new Teacher(re.getInt(1), re.getString(2),
                        re.getString(3), re.getString(4),
                        re.getString(5), re.getString(6),
                        re.getString(7));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return teacher;
    }

    @Override
    public Student studentLogin(String username, String password) {
        String sql = "select * from tb_student where Susername = ? and Spassword = ?";
        Student student = null;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,username);
            pstmt.setString(2,password);
            ResultSet re = pstmt.executeQuery();
            while (re.next()){
                student = new Student(re.getInt(1),re.getString(2),
                        re.getString(3),re.getString(4),
                        re.getString(5),re.getString(6),
                        re.getString(7),re.getInt(8),
                        re.getInt(9));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return student;
    }
}
