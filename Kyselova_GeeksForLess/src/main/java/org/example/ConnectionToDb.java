package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ConnectionToDb {

    private static final String jdbcURL  = "jdbc:postgresql://localhost:5433/equation";
    private static final String username = "postgres";
    private static final String password = "root";
    Connection connection = null;


    public Connection getConnectionToDb() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(jdbcURL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public void createEquationsTable() {
        try (Connection connection = getConnectionToDb()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS equations (" +
                    "id SERIAL PRIMARY KEY, " +
                    "equation TEXT, " +
                    "root DOUBLE)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(createTableQuery)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void saveEquation(String equation) {
        createEquationsTable(); // Call the method to create the table if it doesn't exist
        try (Connection connection = getConnectionToDb()) {
            String insertQuery = "INSERT INTO equations (equation) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, equation);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isRootColumnExists() {
        try (Connection connection = getConnectionToDb()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet columns = metaData.getColumns(null, null, "equations", "root");
            return columns.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void saveEquation(String equation, Double root) {
        createEquationsTable(); // Call the method to create the table if it doesn't exist
        try (Connection connection = getConnectionToDb()) {
            String insertQuery = "INSERT INTO equations (equation, root) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, equation);
                if (root != null) {
                    preparedStatement.setDouble(2, root);
                } else {
                    preparedStatement.setNull(2, Types.DOUBLE);
                }
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateEquationRoot(String equation, double root) {
        if (isRootColumnExists()) {
            try (Connection connection = getConnectionToDb()) {
                String updateQuery = "UPDATE equations SET root = ? WHERE equation = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                    preparedStatement.setDouble(1, root);
                    preparedStatement.setString(2, equation);
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Root column does not exist. Cannot update root value.");
        }
    }

    public List<String> searchEquationsByRoot(double root) {
        List<String> equations = new ArrayList<>();
        try (Connection connection = getConnectionToDb()) {
            String searchQuery = "SELECT equation FROM equations WHERE root = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(searchQuery)) {
                preparedStatement.setDouble(1, root);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String equation = resultSet.getString("equation");
                        equations.add(equation);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equations;
    }

}
