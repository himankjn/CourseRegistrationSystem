/**
 * 
 */
package com.wibmo.utils;


import java.io.*;
import java.sql.*;
import java.util.*;

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
//            	Properties prop = new Properties();
//                InputStream inputStream = DBUtils.class.getClassLoader().getResourceAsStream("./config.properties");
//                prop.load(inputStream);
//                String driver = prop.getProperty("driver");
//                String url = prop.getProperty("url");
//                String user = prop.getProperty("user");
//                String password = prop.getProperty("password");
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wibmo_crs","root","micromaxQ3!");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } 
//            } catch (FileNotFoundException e) {
//            	System.out.println("Not found");
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            return connection;
        }

    }


}