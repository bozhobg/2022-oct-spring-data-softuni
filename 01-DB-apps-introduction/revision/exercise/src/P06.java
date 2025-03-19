import java.sql.*;
import java.util.Scanner;

public class P06 {
    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {

        Connection conn = DriverManager.getConnection(DBConstants.DB_URL, DBConstants.getProperties());
        conn.setAutoCommit(false);

        int villainId = Integer.parseInt(SC.nextLine());

        try {
            PreparedStatement villainNameStmt = conn.prepareStatement("""
                    SELECT `name`
                    FROM `villains` 
                    WHERE `id` = ?;
                    """);
            villainNameStmt.setInt(1, villainId);
            ResultSet rs = villainNameStmt.executeQuery();

            conn.commit();

            if (!rs.next()) {
                System.out.println("No such villain was found");
            }

            String villainName = rs.getString(1);

            PreparedStatement deleteMinionsOfVillainStmt = conn.prepareStatement("""
                    DELETE FROM `minions_villains` as `mv`
                    WHERE `villain_id` = ?;
                    """);
            deleteMinionsOfVillainStmt.setInt(1, villainId);
            int minionsReleasedCount = deleteMinionsOfVillainStmt.executeUpdate();

            PreparedStatement deleteVillainStmt = conn.prepareStatement("""
                    DELETE FROM `villains`
                    WHERE `id` = ?;
                    """);
            deleteVillainStmt.setInt(1, villainId);
            int villainsDeletedCount = deleteVillainStmt.executeUpdate();

            StringBuilder sb = new StringBuilder();

            sb.append(villainName).append(" was deleted")
                    .append(System.lineSeparator())
                    .append(minionsReleasedCount).append(" minions released");

            System.out.println(sb);

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        }


    }
}
