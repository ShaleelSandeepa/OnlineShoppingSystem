package com.online.shoppingsystem;

import java.sql.*;

public class ShoppingDBExample {

    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:sqlserver://SHALEEL-DESKTOP:1433;encrypt=true;trustServerCertificate=true;database=onlineShoppingSystem;connectTimeout=5";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "sa";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "{call InsertNewProduct(?, ?, ?)}";
            CallableStatement callableStatement = conn.prepareCall(sql);
            callableStatement.setString(1, "firstName"); // @Name
            callableStatement.setInt(2, 10); // @AvailableQty
            callableStatement.setFloat(3, 10.5f); // @Price
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void callStoredProcedure(Connection conn, int customerId) {
        try (CallableStatement stmt = conn.prepareCall("{call GetTotalOrderAmountByCustomer(?)}")) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Total Order Amount for CustomerID " + customerId + ": " + rs.getBigDecimal(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void callFunction(Connection conn, int orderId) {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT dbo.GetTotalOrderAmountByOrder(?)")) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Total Order Amount for OrderID " + orderId + ": " + rs.getBigDecimal(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
