package demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class P03GetMinionNames {

    private static String GET_MINIONS_NAME_AND_AGE_BY_VILLAIN_ID =
            "SELECT m.`name`, m.`age`" +
            " FROM `minions` AS m" +
            " JOIN `minions_villains` AS mv ON m.`id` = mv.`minion_id`" +
            " JOIN `villains` AS v ON v.`id` = mv.`villain_id`" +
            " WHERE v.`id` = ?;";
    private static final String NO_VILLAIN_FORMAT = "No villain with ID %s exists in the database.";
    private static final String FORMAT_VILLAIN_NAME = "Villain: %s%n";
    private static final String FORMAT_MINIONS = "%d. %s %d%n";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection();

        final int villainId = new Scanner(System.in).nextInt();
        PreparedStatement prepStmtVillain = connection.prepareStatement(GET_MINIONS_NAME_AND_AGE_BY_VILLAIN_ID);
        prepStmtVillain.setInt(1, villainId);

        final ResultSet resultSetVillain = prepStmtVillain.executeQuery();

        if (!resultSetVillain.next()) {
            System.out.printf(NO_VILLAIN_FORMAT, villainId);
            connection.close();
            return;
        }



    }
}

