import java.sql.*;
import java.util.Properties;

public class P02GetVillainsNames {

    private static final String COLUMN_LABEL_NAME = "name";
    private static final String COLUMN_LABEL_COUNT = "minions_count";

    private static final String QUERY_VILLAINS_MINIONS_COUNT_MORE_THAN_15 =
            "SELECT \n" +
                "v.`name`,\n" +
                "COUNT(DISTINCT `minion_id`) AS 'minions_count'\n" +
            "FROM `villains` AS v\n" +
            "JOIN `minions_villains` AS mv ON v.`id` = mv.`villain_id`\n" +
            "GROUP BY mv.`villain_id`\n" +
            "HAVING `minions_count` > 15\n" +
            "ORDER BY `minions_count` DESC;";

    public static void main(String[] args) throws SQLException {
        Connection connection = ConnectionUtils.getSQLConnection();

        PreparedStatement prepedStmt =
                connection.prepareStatement(QUERY_VILLAINS_MINIONS_COUNT_MORE_THAN_15);

        ResultSet rs = prepedStmt.executeQuery();

        while (rs.next()) {
            System.out.printf("%s %d%n", rs.getString(COLUMN_LABEL_NAME), rs.getInt(2));
        }

        connection.close();
    }
}
