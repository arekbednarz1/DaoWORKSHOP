package pl.coderslab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbUtil {
    private static final String DB_USER = "root";
    private static final String DB_PASS = "coderslab";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/workshop2?serverTimezone=UTC";

    public static Connection getConection() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        return connection;

    }

    public static void createTable(String query) {
        try (Connection connection = getConection();
             Statement stat = connection.createStatement()
        ) {
            stat.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
