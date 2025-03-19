import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class P07 {
    private final static Scanner SC = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection(DBConstants.DB_URL, DBConstants.getProperties());

        PreparedStatement prStmt = conn.prepareStatement("""
                SELECT `name`
                FROM `minions`
                """);
        ResultSet rs = prStmt.executeQuery();
        List<String> minionNames = new ArrayList<>();

        while (rs.next()) {
            minionNames.add(rs.getString("name"));
        }

        int count = minionNames.size();
        int remainder = count % 2;
        int mid = count / 2;

        for (int i = 0; i < mid; i++) {
            System.out.println(i + " " +minionNames.get(i));
            int oppositeIndex = count - i - 1;
            System.out.println(oppositeIndex + " " + minionNames.get(oppositeIndex));
        }

        if (remainder == 1) {
            System.out.println(mid + " " + minionNames.get(mid));
        }
    }
}
