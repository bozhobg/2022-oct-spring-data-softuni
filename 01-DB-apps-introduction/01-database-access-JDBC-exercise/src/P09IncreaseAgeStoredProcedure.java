import java.sql.*;
import java.util.Scanner;

public class P09IncreaseAgeStoredProcedure {

    private static final String CALL_GET_OLDER_BY_ID = "CALL ups_get_older(?);";
    private static final String GET_MINION_NAME_AGE_BY_ID = "SELECT `name`, age FROM minions WHERE id = ?;";
    private static final String FORMAT_MINION_OUTPUT = "%s %d%n";


    public static void main(String[] args) throws SQLException {
        Connection connection = ConnectionUtils.getSQLConnection();

        final int minionId = new Scanner(System.in).nextInt();

        final CallableStatement callGetOlder = connection.prepareCall(CALL_GET_OLDER_BY_ID);
        callGetOlder.setInt(1, minionId);
        callGetOlder.executeUpdate();

        final PreparedStatement stmtMinionById = connection.prepareStatement(GET_MINION_NAME_AGE_BY_ID);
        stmtMinionById.setInt(1, minionId);

        final ResultSet rsMinionAffected = stmtMinionById.executeQuery();
        rsMinionAffected.next();

        final String minionName = rsMinionAffected.getString(Constants.COLUMN_LABEL_NAME);
        final int minionAge = rsMinionAffected.getInt(Constants.COLUMN_LABEL_AGE);

        System.out.printf(FORMAT_MINION_OUTPUT, minionName, minionAge);
    }

}
