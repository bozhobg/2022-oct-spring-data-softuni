import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class P04AddMinion {
    private static final Scanner SC = new Scanner(System.in);

    private static final String QUERY_GET_TOWN_ID_BY_NAME =
            "SELECT `id` FROM `towns` WHERE `name` = ?;";
    private static final String QUERY_INSERT_NEW_TOWN =
            "INSERT INTO `towns`(`name`) VALUES(?);";
    private static final String FORMAT_TOWN_ADDED = "Town %s was added to the database.%n";

    private static final String QUERY_GET_VILLAIN_ID_BY_NAME =
            "SELECT `id` FROM `villains` WHERE `name` = ?;";
    private static final String QUERY_INSERT_NEW_VILLAIN =
            "INSERT INTO `villains`(`name`, `evilness_factor`) VALUES(?, ?)";
    private static final String FORMAT_VILLAIN_ADDED = "Villain %s was added to the database.%n";

    private static final String QUERY_INSERT_NEW_MINION =
            "INSERT INTO minions(name, age, town_id) VALUES(?, ?, ?)";
    private static final String QUERY_GET_LAST_MINION_ID = "SELECT id FROM minions ORDER BY id DESC LIMIT 1";
    private static final String QUERY_INSERT_NEW_MAPPING_ENTRY_MINION_VILLAIN =
            "INSERT INTO minions_villains(minion_id, villain_id) VALUES(?,?)";
    private static final String FORMAT_MINION_ADDED =
            "Successfully added %s to be minion of %s.%n";


    public static void main(String[] args) throws SQLException {
        Connection connection = ConnectionUtils.getSQLConnection();

        String[] tokensMinion = SC.nextLine().split("\s+");
        String minionName = tokensMinion[1];
        int minionAge = Integer.parseInt(tokensMinion[2]);
        String minionTown = tokensMinion[3];

        String villainName = SC.nextLine().split("\s+")[1];

        int townId = getEntryIdByName(
                connection,
                minionTown,
                QUERY_GET_TOWN_ID_BY_NAME,
                QUERY_INSERT_NEW_TOWN,
                List.of(minionTown),
                FORMAT_TOWN_ADDED
        );

        int villainId = getEntryIdByName(
                connection,
                villainName,
                QUERY_GET_VILLAIN_ID_BY_NAME,
                QUERY_INSERT_NEW_VILLAIN,
                List.of(villainName, "evil"),
                FORMAT_VILLAIN_ADDED
        );

        PreparedStatement stmtInsertMinion = connection.prepareStatement(QUERY_INSERT_NEW_MINION);
        stmtInsertMinion.setString(1, minionName);
        stmtInsertMinion.setInt(2, minionAge);
        stmtInsertMinion.setInt(3, townId);

        stmtInsertMinion.executeUpdate();

        PreparedStatement stmtLastMinionId = connection.prepareStatement(QUERY_GET_LAST_MINION_ID);
        ResultSet rsLastMinionId = stmtLastMinionId.executeQuery();
        rsLastMinionId.next();
        int minionId = rsLastMinionId.getInt(Constants.COLUMN_LABEL_ID);

        PreparedStatement stmtInsertMappingMinionsVillains =
                connection.prepareStatement(QUERY_INSERT_NEW_MAPPING_ENTRY_MINION_VILLAIN);
        stmtInsertMappingMinionsVillains.setInt(1, minionId);
        stmtInsertMappingMinionsVillains.setInt(2, villainId);
        stmtInsertMappingMinionsVillains.executeUpdate();

        System.out.printf(FORMAT_MINION_ADDED, minionName, villainName);

        connection.close();
    }

    private static int getEntryIdByName(
            Connection connection,
            String entryName,
            String queryGetIdByName,
            String queryInsertEntry,
            List<String> listQueryParams,
            String formatNewInsert) throws SQLException {

        PreparedStatement entryIdByName = connection.prepareStatement(queryGetIdByName);
        entryIdByName.setString(1, entryName);
        ResultSet rsId = entryIdByName.executeQuery();

        if (!rsId.next()) {
            PreparedStatement stmtInsert = connection.prepareStatement(queryInsertEntry);

            for (int index = 1; index <= listQueryParams.size(); index++) {
                stmtInsert.setString(index, listQueryParams.get(index - 1));
            }

            stmtInsert.executeUpdate();

            System.out.printf(formatNewInsert, entryName);
        }

        rsId = entryIdByName.executeQuery();
        rsId.next();

        return rsId.getInt(Constants.COLUMN_LABEL_ID);
    }

}
