import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class P07PrintAllMinionNames {
    private static final String GET_ALL_MINION_NAMES = "SELECT `name` FROM minions";

    public static void main(String[] args) throws SQLException {
        Connection connection = ConnectionUtils.getSQLConnection();

        PreparedStatement stmt = connection.prepareStatement(GET_ALL_MINION_NAMES);
        ResultSet rs = stmt.executeQuery();

        List<String> listMinionNames = new ArrayList<>();

        while (rs.next()) {
            String currentMinionName = rs.getString(Constants.COLUMN_LABEL_NAME);
            listMinionNames.add(currentMinionName);
        }

        final boolean countOdd = listMinionNames.size() % 2 != 0;
        final int minionsCount = listMinionNames.size();

        for (int i = 0; i < minionsCount / 2; i++) {
            System.out.println(listMinionNames.get(i));
            System.out.println(listMinionNames.get(minionsCount - i - 1));

            if(i == minionsCount / 2  - 1 && countOdd) {
                System.out.println(listMinionNames.get(i + 1));
            }
        }

        connection.close();
    }
}
