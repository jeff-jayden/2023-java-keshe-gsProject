package com.it.dao.impl;

import com.it.dao.api.UserDao;
import com.it.pojo.User;
import com.it.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    Connection conn = DBUtil.getConnection();

    @Override
    public int saveUser(User user) {
        return 0;
    }

    @Override
    public User selectUser(String username, String password) {
        String sql = "select * from tb_user where username = ? and password = ?";

        User u = null;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,username);
            pstmt.setString(2,password);
            ResultSet re = pstmt.executeQuery();
            while (re.next()){
                u = new User(re.getString(1),re.getString(2));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return u;
    }
}
