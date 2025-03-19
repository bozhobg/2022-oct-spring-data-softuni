package com.jdbcdemo;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Diablo {

    private static final Scanner SC = new Scanner(System.in);
    private static final String QUERY_USERNAME_COUNT_GAMES =
            "SELECT COUNT(*) AS \"games_count\"\n" +
                    "FROM `users_games` AS ug\n" +
                    "JOIN `users` AS u ON u.`id` = ug.`user_id`\n" +
                    "WHERE u.`user_name` = ?\n" +
                    "GROUP BY ug.`user_id`;";

    private static final String QUERY_USER_FULLNAME =
            "SELECT CONCAT_WS(\" \", `first_name`, `last_name`) AS \"full_name\"\n" +
            "FROM `users` \n" +
            "WHERE `user_name`  = ?;";

    public static void main(String[] args) throws SQLException {

        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "1234");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/diablo", props);

        PreparedStatement prepedStmt = connection.prepareStatement(QUERY_USERNAME_COUNT_GAMES);
        String userName = SC.nextLine();
        prepedStmt.setString(1, userName);

        ResultSet rs = prepedStmt.executeQuery();

        if (rs.next()) {
            int gamesCount = rs.getInt("games_count");

            prepedStmt = connection.prepareStatement(QUERY_USER_FULLNAME);
            prepedStmt.setString(1, userName);
            rs = prepedStmt.executeQuery();
            rs.next();
            String userFullName = rs.getString("full_name");

            System.out.printf(
                    "User: %s%n" +
                    "%s has played %d games%n"
                        , userName, userFullName, gamesCount);
        } else {
            System.out.println("No such user exists");
        }

        connection.close();
    }
}
