package jm.task.core.jdbc.util;

import jm.task.core.jdbc.util.exception.DatabaseException;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class Util {
    // реализуйте настройку соеденения с БД

    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";

    private Util() {}

    public static Connection open() {
        try {
            return DriverManager.getConnection(
                    UtilProperties.getProperty(URL_KEY),
                    UtilProperties.getProperty(USERNAME_KEY),
                    UtilProperties.getProperty(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new DatabaseException("db connection error", e);
        }
    }
}