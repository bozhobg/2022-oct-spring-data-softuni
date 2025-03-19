import com.sun.jdi.PathSearchingVirtualMachine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class P03GetMinionNames {
    private static final Scanner SC = new Scanner(System.in);
    private static final String QUERY_VILLAIN_NAME_FOR_ID = "SELECT `name` FROM `villains` WHERE `id` = ?;";
    private static final String QUERY_MINIONS_NAME_AGE_FOR_VILLAIN_ID =
            "SELECT DISTINCT `id`, `name`, `age`\n" +
                    "FROM `minions` AS m\n" +
                    "JOIN `minions_villains` AS mv ON m.`id` = mv.`minion_id`\n" +
                    "WHERE mv.`villain_id` = ?;";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_AGE = "age";
    private static final String FORMAT_VILLAIN = "Villain: %s";
    private static final String FORMAT_MINION_ROW = "%d. %s %d";
    private static final String FORMAT_NO_VILLAIN_FOR_ID = "No villain with ID %d exists in the database.%n";


    public static void main(String[] args) throws SQLException {
        int villainId = SC.nextInt();
        Connection connection = ConnectionUtils.getSQLConnection();

        PreparedStatement prepedStmt = connection.prepareStatement(QUERY_VILLAIN_NAME_FOR_ID);
        prepedStmt.setInt(1, villainId);

        ResultSet rs = prepedStmt.executeQuery();

        if (!rs.next()) {
            System.out.printf(FORMAT_NO_VILLAIN_FOR_ID, villainId);
            connection.close();
            return;
        }

        String villainName = rs.getString(1);

        StringBuilder output = new StringBuilder();
        output.append(String.format(FORMAT_VILLAIN, villainName)).append(System.lineSeparator());

        prepedStmt = connection.prepareStatement(QUERY_MINIONS_NAME_AGE_FOR_VILLAIN_ID);
        prepedStmt.setInt(1, villainId);

        rs = prepedStmt.executeQuery();

        for (int index = 1; rs.next(); index++) {
            String minionName = rs.getString(COLUMN_NAME);
            int minionAge = rs.getInt(COLUMN_AGE);

            output
                    .append(String.format(FORMAT_MINION_ROW, index, minionName, minionAge))
                    .append(System.lineSeparator());
        }

        System.out.println(output);
    }

}
