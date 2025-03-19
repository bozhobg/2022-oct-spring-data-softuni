import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class P05ChangeTownNamesCasings {

    private static final String UPDATE_TOWN_NAMES_UPPER_BY_COUNTRY =
            "UPDATE towns SET `name` = UPPER(`name`) WHERE country = ?;";
    private static final String GET_AFFECTED_TOWNS =
            "SELECT t.name FROM towns AS t WHERE country = ?";
    private static final String FORMAT_NO_TOWN_NAMES_AFFECTED = "No town names were affected.";
    private static final String FORMAT_TOWN_NAMES_AFFECTED = "%d town names were affected.%n%s";

    public static void main(String[] args) throws SQLException {
        Connection connection = ConnectionUtils.getSQLConnection();

        final String country = new Scanner(System.in).nextLine();

        PreparedStatement stmt = connection.prepareStatement(UPDATE_TOWN_NAMES_UPPER_BY_COUNTRY);
        stmt.setString(1, country);

        int updatedRows = stmt.executeUpdate();

        if (updatedRows == 0) {
            System.out.println(FORMAT_NO_TOWN_NAMES_AFFECTED);
            connection.close();
            return;
        }

        PreparedStatement stmtAffectedTowns = connection.prepareStatement(GET_AFFECTED_TOWNS);
        stmtAffectedTowns.setString(1, country);

        ResultSet rsAffectedTowns = stmtAffectedTowns.executeQuery();

        List<String> listAffectedTowns = new ArrayList<>();

        while (rsAffectedTowns.next()){
            String currentTown = rsAffectedTowns.getString(Constants.COLUMN_LABEL_NAME);
            listAffectedTowns.add(currentTown);
        }

        System.out.printf(FORMAT_TOWN_NAMES_AFFECTED, listAffectedTowns.size(), listAffectedTowns);

        connection.close();
    }
}
