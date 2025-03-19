package demo;

import java.sql.*;

public class P02GetVillainName {

    private static final String GET_VILLAIN_NAMES = "";
    private static final String COLUMN_LABEL_NAME = "name";
    private static final String COLUMN_LABEL_MINION_COUNT = "minions_count";
    private static final String PRINT_FORMAT = String.format("");

    public static void main(String[] args) throws SQLException {

        final Connection connection = Utils.getSQLConnection();

        final PreparedStatement prepStmt = connection.prepareStatement(GET_VILLAIN_NAMES);
        prepStmt.setInt(1, 15);
        final ResultSet resultSet = prepStmt.executeQuery();

        while (resultSet.next()) {
            final String villainName = resultSet.getString(COLUMN_LABEL_NAME);
            //final String minionCountPerVillain = resultSet.getInt(COLUMN_LABEL_MINION_COUNT);
        }

        System.out.println(PRINT_FORMAT);

        connection.close();
    }
}