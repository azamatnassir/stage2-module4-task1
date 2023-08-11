package com.mjc.stage2.impl;

import com.mjc.stage2.ConnectionFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class H2ConnectionFactory implements ConnectionFactory {

    @Override
    public Connection createConnection() {
        Properties properties = new Properties();
        ClassLoader loader = H2ConnectionFactory.class.getClassLoader();
        try (InputStream stream = loader.getResourceAsStream("h2database.properties")) {
            if (stream == null) {
                throw new FileNotFoundException();
            }
            properties.load(stream);
        } catch (IOException e) {
            e.fillInStackTrace();
        }

        String driver = properties.getProperty("jdbc_driver");
        String url = properties.getProperty("db_url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.fillInStackTrace();
        }
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return null;
    }
}

