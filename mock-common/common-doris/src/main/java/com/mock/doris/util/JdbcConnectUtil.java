package com.mock.doris.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcConnectUtil {

    /**
     * 返回数据库连接
     * @param type 连接类型
     * @param password 密码
     * @param username 帐号
     * @param url 连接地址
     * @return
     */
    public static Connection getConnection(Integer type,String url,String username,String password){
        String driverClassName = "com.mysql.cj.jdbc.Driver";	//启动驱动
        Connection con = null;		//连接
        try {
            Class.forName(driverClassName); //执行驱动
            con = DriverManager.getConnection(url, username, password); //获取连接
            return con;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public static Connection getHiveConnection(Integer type,String url,String username,String password){
        String driverClassName = "org.apache.hive.jdbc.HiveDriver";	//启动驱动
        Connection con = null;		//连接
        try {
            Class.forName(driverClassName); //执行驱动
            con = DriverManager.getConnection(url, username, password); //获取连接
            return con;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
