import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class P08 {

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        String minionIdsInput = SC.nextLine();
        int[] minionIdsArray = Arrays.stream(minionIdsInput.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        Connection conn = DriverManager.getConnection(DBConstants.DB_URL, DBConstants.getProperties());
        String sql = String.format("""
                        UPDATE `minions`
                        SET 
                            `name` = LOWER(`name`),
                            `age` = `age` + 1
                        WHERE `id` IN (%s);
                        """,
                Arrays.stream(minionIdsArray)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(", ")));

        PreparedStatement updateStmt = conn.prepareStatement(sql);
        updateStmt.executeUpdate();
        updateStmt.close();

        PreparedStatement allMinionsNameIdStmt = conn.prepareStatement("""
                SELECT 
                    `name`,
                    `age`
                FROM `minions`;
                """);

        ResultSet rs = allMinionsNameIdStmt.executeQuery();
        List<String> formattedLines = new ArrayList<>();

        while (rs.next()) {
            formattedLines.add(String.format(
                    "%s %d",
                    rs.getString("name"),
                    rs.getInt("age")));
        }

        formattedLines.forEach(System.out::println);

        conn.close();
    }
}
