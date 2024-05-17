package com.online.shoppingsystem;

import java.sql.*;

public class ShoppingSystemTest {

    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:sqlserver://SHALEEL:1433;encrypt=true;trustServerCertificate=true;database=onlineShoppingSystem;connectTimeout=5";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "sa";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {

            ShoppingSystemTest shoppingSystemTest = new ShoppingSystemTest();
            shoppingSystemTest.callFunction(conn, 3);
            shoppingSystemTest.callStoredProcedure(conn, 102);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void callStoredProcedure(Connection conn, int customerId) {
        try (CallableStatement callableStatement = conn.prepareCall("{call GetTotalOrderAmountByCustomer(?, ?)}")) {

            // Set the input parameters
            callableStatement.setInt(1, customerId); // @customerID
            callableStatement.registerOutParameter(2, Types.REAL);

            // Execute the stored procedure
            callableStatement.execute();

            // Retrieve the output parameter value
            System.out.println("Total Order Amount for CustomerID " + customerId + ": " + callableStatement.getFloat(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void callFunction(Connection conn, int orderId) {
        try (CallableStatement callableStatement = conn.prepareCall("{ ? = call GetTotalOrderAmountByOrder(?)}")) {
            // Set the input parameters
            callableStatement.setInt(2, orderId); // @customerID
            callableStatement.registerOutParameter(1, Types.REAL);

            // Execute the stored procedure
            callableStatement.execute();

            // Retrieve the output parameter value);
            System.out.println("Total Order Amount for OrderID " + orderId + ": " + callableStatement.getFloat(1));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
