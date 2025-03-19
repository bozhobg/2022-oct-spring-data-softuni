import java.sql.*;

public class P02 {
    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection(DBConstants.DB_URL, DBConstants.getProperties());

        PreparedStatement preparedStmt = conn.prepareStatement("""
                SELECT
                    v.`name`,
                    COUNT(mv.`minion_id`) as `minion_count`
                FROM `villains` as `v`
                JOIN `minions_villains` as `mv`
                ON v.`id` = mv.`villain_id`
                GROUP BY v.`id`
                HAVING `minion_count` > 15
                ORDER BY `minion_count` DESC;
                """);

        ResultSet rs = preparedStmt.executeQuery();

        while (rs.next()) {
            System.out.printf(
                    "%s %s%n",
                    rs.getString("name"),
                    rs.getInt("minion_count")
            );
        }

        conn.close();
    }
}