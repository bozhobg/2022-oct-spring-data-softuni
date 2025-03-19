import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class SoftUni {

    private final static String DB_TYPE = "mysql";
    private final static String HOST = "localhost";
    private final static String PORT = "3312";
    private final static String DB_NAME = "soft_uni";

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        System.out.println("Enter username (default root):");
        String user = SC.nextLine();
        user = user.equals("") ? "root" : user;

        System.out.println("Enter password (default empty):");
        String password = SC.nextLine();
        password = password.equals("") ? "1234" : password;

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        Connection connection = DriverManager.getConnection(
                String.format("jdbc:%s://%s:%s/%s",
                        DB_TYPE,
                        HOST,
                        PORT,
                        DB_NAME
                ),
                props);
        PreparedStatement preparedStatement = connection.prepareStatement(
                """
                                        SELECT *
                                        FROM `employees` as `e`
                                        WHERE e.`salary` > ?;
                        """
        );

        System.out.println("Salary above:");
        String salary = SC.nextLine();
        preparedStatement.setDouble(1, Double.parseDouble(salary));
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            System.out.printf(
                    "%s %s %.2f \n",
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getBigDecimal("salary")
            );
        }
        ;


    }
}