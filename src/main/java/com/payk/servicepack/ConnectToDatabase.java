package com.payk.servicepack;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by 1 on 21.06.2017.
 */
public class ConnectToDatabase
{
    private static String USER_NAME;
    private static String PASSWORD;
    private static String URL;

    public static void getDataForConnection()
    {
        Properties properties = new Properties();
        FileInputStream propertiesLoader;
        try
        {
            propertiesLoader = new FileInputStream("C:\\Users\\1\\IdeaProjects\\payk\\src\\main\\resources\\mysql.properies");
            properties.load(propertiesLoader);

            propertiesLoader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        URL = properties.getProperty("URL");
        USER_NAME = properties.getProperty("USER_NAME");
        PASSWORD = properties.getProperty("PASSWORD");


    }
    public static Boolean tryDrivaerForDatabase()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("driver ok");
            return true;
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("no class jdbc");
        }
        return false;
    }

    public static Connection getConnection()
    {
        if (tryDrivaerForDatabase())
        {
            Connection connection = null;
            getDataForConnection();
            try
            {
                connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
                System.out.println("connection ok");
            } catch (SQLException e)
            {
                System.out.println("can`t connect to database");
            }
            return connection;
        }
        else
        {
            return null;
        }
    }
}
