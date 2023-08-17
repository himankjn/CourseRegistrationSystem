/**
 * 
 */
package com.wibmo.utils;


import java.io.*;
import java.sql.*;
import java.util.*;

import com.wibmo.utils.DBUtils;

public class DBUtils {
	
	private static Connection connection = null;
	
	public static Connection getConnection() {
		
        if (connection != null) {
        	try {
        		if (connection.isClosed()) {
                	connection = null;
                	return getConnection();
                } else {
                	return connection;
                }
        	} catch (SQLException e) {
        		System.out.println("Error2345: " + e.getMessage());
        		e.printStackTrace();
        		connection = null;
        		return getConnection();
        	}
        } else {
            try {
            	// Properties class is used to read file using file API from particular resource/location.
//                Properties prop = new Properties();
//                InputStream inputStream = DBUtils.class.getClassLoader().getResourceAsStream("config.properties");
//                System.out.println("Hlelo");
//                prop.load(inputStream);
//                System.out.println(inputStream);
//                String driver = prop.getProperty("driver");
//                String url = prop.getProperty("url");
//                String user = prop.getProperty("user");
//                String password = prop.getProperty("password");
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wibmo_crs", "root", "micromaxQ3!");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return connection;
        }

    }


}