import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class P08IncreaseMinionAge {

    private static final String UPDATE_MINION_AGE_FOR_ID =
            "UPDATE minions SET age = age + 1, `name` = LOWER(`name`) WHERE id = ?;";
    private static final String GET_ALL_MINIONS_NAME_AGE =
            "SELECT `name`, age FROM minions;";

    private static final String FORMAT_ID_NAME = "%s %d%n";


    public static void main(String[] args) throws SQLException {
        final Connection connection = ConnectionUtils.getSQLConnection();

        int[] minionIds = Arrays.stream(new Scanner(System.in).nextLine().split("\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();

        final PreparedStatement stmtUpdateAgeName = connection.prepareStatement(UPDATE_MINION_AGE_FOR_ID);

        for (int currentId : minionIds) {
            stmtUpdateAgeName.setInt(1, currentId);
            stmtUpdateAgeName.executeUpdate();
        }

        final PreparedStatement stmtGetAllMinions = connection.prepareStatement(GET_ALL_MINIONS_NAME_AGE);
        final ResultSet rsGetAllMinions = stmtGetAllMinions.executeQuery();

        final StringBuilder outputAllMinions = new StringBuilder();

        while (rsGetAllMinions.next()) {
            String currentName = rsGetAllMinions.getString(Constants.COLUMN_LABEL_NAME);
            int currentAge = rsGetAllMinions.getInt(Constants.COLUMN_LABEL_AGE);

            outputAllMinions.append(String.format(FORMAT_ID_NAME, currentName, currentAge));
        }

        System.out.println(outputAllMinions);

        connection.close();
    }
}
