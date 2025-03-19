import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Diablo {

    private static final String DB_URL = "jdbc:mysql://localhost:3312/diablo";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {

        Properties props = new Properties();
        props.setProperty("user", DB_USER.equals("") ? "root" : DB_USER);
        props.setProperty("password", DB_PASSWORD.equals("") ? "1234" : DB_PASSWORD);

        Connection conn = DriverManager.getConnection(DB_URL, props);

        String userName = SC.nextLine();

        PreparedStatement prepStmt = conn.prepareStatement("""
                SELECT
                    COUNT(*) as `games_count`,
                    u.`user_name`,
                    u.first_name,
                    u.last_name
                FROM `users` as `u`
                         JOIN `users_games` as `ug`
                              ON u.`id` = ug.`user_id`
                WHERE u.`user_name` = ?
                GROUP BY u.`id`;
                """);

        prepStmt.setString(1, userName);
        ResultSet rs = prepStmt.executeQuery();

        boolean userExists = rs.next();

        if (userExists) {
            System.out.printf(
                    "User: %s\n%s %s has played %d games",
                    rs.getString("user_name"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getInt("games_count")
            );
        } else {
            System.out.println("No such user exists");
        }

        conn.close();
    }
}
