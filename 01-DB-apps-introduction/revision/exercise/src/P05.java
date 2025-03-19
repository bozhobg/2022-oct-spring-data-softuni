import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class P05 {

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {

        Connection conn = DriverManager.getConnection(DBConstants.DB_URL, DBConstants.getProperties());

        String countryInput = SC.nextLine();
        PreparedStatement prStmt = conn.prepareStatement("""
                UPDATE `towns`
                SET `name` = UPPER(`name`)
                WHERE `country` = ?;
                """);

        prStmt.setString(1, countryInput);
        int updateCount = prStmt.executeUpdate();

        StringBuilder sb = new StringBuilder();

        if (updateCount > 0) {
            String townNames = getUpdatedCityNames(conn, countryInput)
                    .stream()
                    .collect(Collectors.joining(", "));

            sb.append(updateCount).append(" towns were affected.")
                    .append(System.lineSeparator())
                    .append("[").append(townNames).append("]");
        } else {
            sb.append("No town names were affected.");
        }

        System.out.println(sb);

        conn.close();

    }

    private static List<String> getUpdatedCityNames(Connection conn, String country) throws SQLException {

        PreparedStatement prStmt = conn.prepareStatement("""
                SELECT `name`
                FROM `towns`
                WHERE `country` = ?
                """);

        prStmt.setString(1, country);
        ResultSet rs = prStmt.executeQuery();

        List<String> townNames = new ArrayList<>();

        while (rs.next()) {
            String townName = rs.getString("name");
            townNames.add(townName);
        }

        return townNames;
    }
}
