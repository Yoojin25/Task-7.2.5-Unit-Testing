package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static String dbURL = "jdbc:mysql://localhost:3306/mysql";
    private static String dbUsername = "root";
    private static String dbPassword = "root1";
    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
//            System.out.println("Connection succesfull!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection failed");
        }
        return connection;
    }
}
