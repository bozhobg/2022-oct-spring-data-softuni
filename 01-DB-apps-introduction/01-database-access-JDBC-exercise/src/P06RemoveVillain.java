import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class P06RemoveVillain {
    private static final String GET_VILLAIN_NAME = "SELECT `name` FROM villains WHERE id = ?;";
    private static final String DELETE_VILLAIN_BY_ID = "DELETE FROM villains WHERE id = ?;";
    private static final String DELETE_MINION_VILLAIN_ENTRY_FOR_VILLAIN_ID =
            "DELETE FROM minions_villains WHERE villain_id = ?;";

    private static final String PRINT_NO_VILLAIN_FOR_ID = "No such villain was found";
    private static final String FORMAT_DELETED_VILLAIN =
            "%s was deleted%n" +
                    "%d minions released%n";

    public static void main(String[] args) throws SQLException {

        final int villainId = new Scanner(System.in).nextInt();

        Connection connection = ConnectionUtils.getSQLConnection();

        PreparedStatement stmtVillainNameById = connection.prepareStatement(GET_VILLAIN_NAME);
        stmtVillainNameById.setInt(1, villainId);

        ResultSet rsVillainName = stmtVillainNameById.executeQuery();


        if (!rsVillainName.next()) {
            System.out.println(PRINT_NO_VILLAIN_FOR_ID);
            connection.close();
            return;
        }

        final String villainName = rsVillainName.getString(Constants.COLUMN_LABEL_NAME);

        connection.setAutoCommit(false);

        try (
                PreparedStatement stmtRemoveMappingForVillainId =
                        connection.prepareStatement(DELETE_MINION_VILLAIN_ENTRY_FOR_VILLAIN_ID);
                PreparedStatement stmtDeleteVillain = connection.prepareStatement(DELETE_VILLAIN_BY_ID)
        ) {

            stmtRemoveMappingForVillainId.setInt(1, villainId);
            final int minionsReleasedCount = stmtRemoveMappingForVillainId.executeUpdate();

            stmtDeleteVillain.setInt(1, villainId);
            stmtDeleteVillain.executeUpdate();

            System.out.printf(FORMAT_DELETED_VILLAIN, villainName, minionsReleasedCount);

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();

            connection.rollback();
        }


        connection.close();
    }
}
