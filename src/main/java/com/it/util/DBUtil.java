package com.it.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {

    private static final DataSource dataSource;

    static {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("src/main/resources/druid.properties"));
            dataSource = DruidDataSourceFactory.createDataSource(prop);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static DataSource getDatasource(){
        return dataSource;
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
