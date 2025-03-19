import java.sql.*;
import java.util.Scanner;

public class P04 {
    private record MinionData(String name, int age, String town, String villainName) {
    }

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection(DBConstants.DB_URL, DBConstants.getProperties());

        System.out.print("Minion: ");
        String minionInputData = SC.nextLine();
        System.out.print("Villain: ");
        String villainName = SC.nextLine();

        String[] minionDataArray = minionInputData.split(" ");
        MinionData minionData = new MinionData(
                minionDataArray[0],
                Integer.parseInt(minionDataArray[1]),
                minionDataArray[2],
                villainName
        );

        addMinion(conn, minionData);
        conn.close();
    }

    private static void addMinion(Connection conn, MinionData minionData) throws SQLException {
        int townId = getTownId(conn, minionData.town());
        int villainId = getVillainId(conn, minionData.villainName);

        PreparedStatement insertMinionStmt = conn.prepareStatement("""
                INSERT INTO `minions`(`name`, `age`, `town_id`)
                VALUES (?, ?, ?)
                """,
                Statement.RETURN_GENERATED_KEYS);

        insertMinionStmt.setString(1, minionData.name());
        insertMinionStmt.setInt(2, minionData.age());
        insertMinionStmt.setInt(3, townId);
        insertMinionStmt.executeUpdate();

        ResultSet generatedKeys = insertMinionStmt.getGeneratedKeys();
        generatedKeys.next();
        int minionId = generatedKeys.getInt(1);

        insertMinionStmt.close();

        PreparedStatement insertMinionVillainStmt = conn.prepareStatement("""
                INSERT INTO `minions_villains`(`minion_id`, `villain_id`)
                VALUES (?, ?)
                """);
        insertMinionVillainStmt.setInt(1, minionId);
        insertMinionVillainStmt.setInt(2, villainId);
        insertMinionVillainStmt.executeUpdate();

        insertMinionVillainStmt.close();

        System.out.printf("Successfully added %s to be minion of %s.", minionData.name(), minionData.villainName());
    }

    private static int getMinionId(Connection conn, String name) throws SQLException {

        PreparedStatement minionIdStmt = conn.prepareStatement("""
                SELECT `id`
                FROM `minions`
                WHERE `name` = ?
                ORDER BY `id` DESC
                LIMIT 1
                """);

        minionIdStmt.setString(1, name);
        ResultSet rs = minionIdStmt.executeQuery();
        rs.next();
        int minionId = rs.getInt("id");

        minionIdStmt.close();

        return minionId;
    }

    private static int getVillainId(Connection conn, String villainName) throws SQLException {
        int villainId;

        PreparedStatement villainIdStmt = conn.prepareStatement("""
                SELECT `id`
                FROM `villains`
                WHERE `name` = ?
                """);

        villainIdStmt.setString(1, villainName);
        ResultSet rs = villainIdStmt.executeQuery();

        if (!rs.next()) {
            villainId = insertVillain(conn, villainName);
//            villainId = getVillainId(conn, villainName);
        } else {
            villainId = rs.getInt("id");
        }

        villainIdStmt.close();

        return villainId;
    }

    private static int insertVillain(Connection conn, String villainName) throws SQLException {
        PreparedStatement insertVillainStmt = conn.prepareStatement("""
                INSERT `villains`(`name`, `evilness_factor`)
                VALUES(?, 'evil')
                """,
                Statement.RETURN_GENERATED_KEYS);
        insertVillainStmt.setString(1, villainName);
        insertVillainStmt.executeUpdate();
        ResultSet generatedKeys = insertVillainStmt.getGeneratedKeys();
        generatedKeys.next();
        int villainId = generatedKeys.getInt(1);
        insertVillainStmt.close();

        System.out.printf("Villain %s was added to the database.%n", villainName);

        return villainId;
    }

    private static int getTownId(Connection conn, String townName) throws SQLException {

        int townId;

        PreparedStatement townIdStmt = conn.prepareStatement("""
                SELECT `id`
                FROM `towns`
                WHERE `name` = ?
                """);

        townIdStmt.setString(1, townName);
        ResultSet rs = townIdStmt.executeQuery();

        if (!rs.next()) {
            townId = insertTown(conn, townName);
//            townId = getTownId(conn, townName);
        } else {
            townId = rs.getInt("id");
        }

        townIdStmt.close();

        return townId;
    }

    private static int insertTown(Connection conn, String townName) throws SQLException {
        PreparedStatement insertTownStmt = conn.prepareStatement("""
                INSERT INTO `towns`(`name`)
                VALUES(?);
                """,
                Statement.RETURN_GENERATED_KEYS);

        insertTownStmt.setString(1, townName);
        insertTownStmt.executeUpdate();
        ResultSet generatedKeys = insertTownStmt.getGeneratedKeys();
        generatedKeys.next();
        int townId = generatedKeys.getInt(1);
        insertTownStmt.close();

        System.out.printf("Town %s was added to the database.%n", townName);

        return townId;
    }
}
