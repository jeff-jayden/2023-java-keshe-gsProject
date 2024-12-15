package com.it.dao.api;

import com.it.pojo.User;

public interface UserDao {

    int saveUser(User user);

    User selectUser(String username, String password);
}
