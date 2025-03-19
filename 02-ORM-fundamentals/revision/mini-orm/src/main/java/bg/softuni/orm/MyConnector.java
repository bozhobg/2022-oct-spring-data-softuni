package bg.softuni.orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MyConnector {

    private final static String DB_URL = "jdbc:mysql://localhost";
    private final static String DB_USER = "root";
    private final static String DB_PASSWORD = "1234";

    private static Connection conn;

    private static void createConnection() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", DB_USER);
        props.setProperty("password", DB_PASSWORD);

        conn = DriverManager.getConnection(DB_URL, props);
    }

    public static Connection getConnection() throws SQLException {
        if (conn == null) {
            createConnection();
        }

        return conn;
    }
}
