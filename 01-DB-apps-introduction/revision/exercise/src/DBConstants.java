import java.util.Properties;

public record DBConstants() {

    public static String DB_URL = "jdbc:mysql://localhost:3312/minions_db";
    public static String DB_USER = "root";
    public static String DB_PASSWORD = "1234";

    public static Properties getProperties() {

        Properties props = new Properties();
        props.setProperty("user", DB_USER);
        props.setProperty("password", DB_PASSWORD);

        return props;
    }
}
