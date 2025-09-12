package jm.task.core.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public final class UtilProperties {

    private static final Properties PROPERTIES = new Properties();

    private UtilProperties() {}

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream inputStream = UtilProperties.class.getClassLoader().getResourceAsStream("app.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }
}
