import java.sql.*;
import java.util.Scanner;

public class P03 {
    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        int villainId = Integer.parseInt(SC.nextLine());

        Connection conn = DriverManager.getConnection(DBConstants.DB_URL, DBConstants.getProperties());
        PreparedStatement prStmt = conn.prepareStatement("""
                SELECT v.`name`
                FROM `villains` AS `v`
                WHERE v.`id` = ?;
                """);

        prStmt.setInt(1, villainId);
        ResultSet rs = prStmt.executeQuery();

        if (!rs.next()) {
            System.out.printf("No villain with ID %d exists in the database.%n", villainId);
            conn.close();
            return;
        }
        String villainUsername = rs.getString("name");
        prStmt.close();

        prStmt = conn.prepareStatement("""
                SELECT
                    m.`name`,
                    m.`age`
                FROM `minions` as `m`
                JOIN `minions_villains` as `mv`
                ON m.`id` = mv.`minion_id`
                where mv.`villain_id` = ?;
                """);

        StringBuilder resultString = new StringBuilder("Villain: " + villainUsername);
        prStmt.setInt(1, villainId);
        rs = prStmt.executeQuery();
        int counter = 0;

        while (rs.next()) {
            resultString.append(
                    String.format(
                            "%n%d. %s %d",
                            ++counter,
                            rs.getString("name"),
                            rs.getInt("age")
                    ));
        }

        System.out.println(resultString);

        conn.close();
    }
}
