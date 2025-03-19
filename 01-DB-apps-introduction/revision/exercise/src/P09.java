import java.sql.*;
import java.util.Scanner;

public class P09 {

    private final static Scanner SC = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        int minionId = Integer.parseInt(SC.nextLine());
        Connection conn = DriverManager.getConnection(DBConstants.DB_URL, DBConstants.getProperties());

        CallableStatement callStmt = conn.prepareCall("""
                CALL minions_db.usp_get_older(?);
                """);
        callStmt.setInt(1, minionId);
        callStmt.execute();
        callStmt.close();

        PreparedStatement prStmt = conn.prepareStatement("""
                SELECT 
                    `name`,
                    `age`
                FROM `minions`
                WHERE `id` = ?;
                """);

        prStmt.setInt(1, minionId);
        ResultSet rs = prStmt.executeQuery();
        rs.next();
        System.out.println(rs.getString(1) + " " + rs.getInt("age"));

        conn.close();
    }
}
