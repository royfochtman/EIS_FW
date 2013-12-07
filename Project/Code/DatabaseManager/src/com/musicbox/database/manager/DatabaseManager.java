package com.musicbox.database.manager;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 07.12.13
 * Time: 16:09
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseManager {
    private Connection connect;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private static String connectionString;

    public static void setConnectionString(String conn){
        connectionString = conn;

    }

    private boolean testConnection() {
        try {
            connect = DriverManager.getConnection(connectionString);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }

    }


}
