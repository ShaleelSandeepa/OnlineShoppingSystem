package com.online.shoppingsystem.system;

import java.sql.*;
import java.util.logging.Logger;

public class OnlineShoppingSystem {

    private static final Logger logger = Logger.getLogger(OnlineShoppingSystem.class.getName());

    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:sqlserver://SHALEEL:1433;encrypt=true;trustServerCertificate=true;database=onlineShoppingSystem;connectTimeout=5";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "sa";

    public boolean insertCustomerWithAddress(
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            String street,
            String city,
            String province,
            String postalCode
    ) throws SQLException {
        Connection conn = null;
        boolean status = false;
        try {
            // get connection
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // Prepare the stored procedure call
            String sql = "{call InsertCustomerWithAddress(?, ?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement callableStatement = conn.prepareCall(sql);

            // Set the input parameters
            callableStatement.setString(1, firstName); // @FirstName
            callableStatement.setString(2, lastName); // @LastName
            callableStatement.setString(3, email); // @Email
            callableStatement.setString(4, phoneNumber); // @PhoneNumber
            callableStatement.setString(5, street); // @Street
            callableStatement.setString(6, city); // @City
            callableStatement.setString(7, province); // @Province
            callableStatement.setString(8, postalCode); // @PostalCode

            // Execute the stored procedure
            callableStatement.executeUpdate();

            logger.info("Executed successfully !");
            status = true;
        } catch (Exception e) {
            logger.severe("Execution failed !");
            logger.severe(e.toString());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return status;
    }

    public boolean insertProduct(
            String name,
            int qty,
            float price
    ) throws SQLException {
        Connection conn = null;
        boolean status = false;
        try {
            // get connection
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // Prepare the stored procedure call
            String sql = "{call InsertNewProduct(?, ?, ?)}";
            CallableStatement callableStatement = conn.prepareCall(sql);

            // Set the input parameters
            callableStatement.setString(1, name); // @Name
            callableStatement.setInt(2, qty); // @AvailableQty
            callableStatement.setFloat(3, price); // @Price

            // Execute the stored procedure
            callableStatement.executeUpdate();

            logger.info("Executed successfully !");
            status = true;
        } catch (Exception e) {
            logger.severe("Execution failed !");
            logger.severe(e.toString());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return status;
    }

    public int makeOrder(
            int customerID,
            float totalAmount
    ) throws SQLException {
        Connection conn = null;
        int orderIDOutput = 0;
        try {
            // get connection
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // Prepare the stored procedure call
            String sql = "{call MakeOrder(?, ?, ?)}";
            CallableStatement callableStatement = conn.prepareCall(sql);

            // Set the input parameters
            callableStatement.setInt(1, customerID); // @customerID
            callableStatement.setFloat(2, totalAmount); // @totalAmount
            callableStatement.registerOutParameter(3, Types.INTEGER);

            // Execute the stored procedure
            callableStatement.execute();

            // Retrieve the output parameter value
            orderIDOutput = callableStatement.getInt(3);

            logger.info("Executed successfully !");
        } catch (Exception e) {
            logger.severe("Execution failed !");
            logger.severe(e.toString());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return orderIDOutput;
    }

    public Object[] addItem(
            int OrderID,
            int ProductID,
            int Quantity
    ) throws SQLException {
        Connection conn = null;
        float subtotal = 0;
        int orderDetailID = 0;
        Object[] output = new Object[2];
        try {
            // get connection
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // Prepare the stored procedure call
            String sql = "{call AddOrderDetails(?, ?, ?, ?, ?)}";
            CallableStatement callableStatement = conn.prepareCall(sql);

            // Set the input parameters
            callableStatement.setInt(1, OrderID); // @customerID
            callableStatement.setFloat(2, ProductID); // @totalAmount
            callableStatement.setFloat(3, Quantity); // @totalAmount
            callableStatement.registerOutParameter(4, Types.FLOAT);
            callableStatement.registerOutParameter(5, Types.INTEGER);

            // Execute the stored procedure
            callableStatement.execute();

            // Retrieve the output parameter value
            subtotal = callableStatement.getInt(4);
            orderDetailID = callableStatement.getInt(5);

            output[0] = subtotal;
            output[1] = orderDetailID;

            logger.info("Executed successfully !");
        } catch (Exception e) {
            logger.severe("Execution failed !");
            logger.severe(e.toString());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return output;
    }

    public boolean makeOrderPayment(
            int customerID,
            int OrderID,
            float amount
    ) throws SQLException {
        Connection conn = null;
        boolean status = false;
        try {
            // get connection
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // Prepare the stored procedure call
            String sql = "{call MakePayment(?, ?, ?)}";
            CallableStatement callableStatement = conn.prepareCall(sql);

            // Set the input parameters
            callableStatement.setInt(1, customerID); // @customerID
            callableStatement.setInt(2, OrderID); // @OrderID
            callableStatement.setFloat(3, amount); // @totalAmount

            // Execute the stored procedure
            callableStatement.execute();

            // Retrieve the output parameter value
            status = true;

            logger.info("Executed successfully !");
        } catch (Exception e) {
            logger.severe("Execution failed !");
            logger.severe(e.toString());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return status;
    }

    public boolean clearOrder(
            int OrderID
    ) throws SQLException {
        Connection conn = null;
        boolean status = false;
        try {
            // get connection
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // Prepare the stored procedure call
            String sql = "{call ClearOrder(?)}";
            CallableStatement callableStatement = conn.prepareCall(sql);

            // Set the input parameters
            callableStatement.setInt(1, OrderID); // @customerID

            // Execute the stored procedure
            callableStatement.execute();

            // Retrieve the output parameter value
            status = true;

            logger.info("Executed successfully !");
        } catch (Exception e) {
            logger.severe("Execution failed !");
            logger.severe(e.toString());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return status;
    }

    public float getTotalOrderAmountByOrderID(
            int OrderID
    ) throws SQLException {
        Connection conn = null;
        float totalAmount = -1;
        try {
            // get connection
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // Prepare the stored procedure call
            String sql = "{ ? = call GetTotalOrderAmountByOrder(?)}";
            CallableStatement callableStatement = conn.prepareCall(sql);

            // Set the input parameters
            callableStatement.setInt(2, OrderID); // @customerID
            callableStatement.registerOutParameter(1, Types.REAL);

            // Execute the stored procedure
            callableStatement.execute();

            // Retrieve the output parameter value
            totalAmount = callableStatement.getFloat(1);

            logger.info("Executed successfully !");
        } catch (Exception e) {
            logger.severe("Execution failed !");
            logger.severe(e.toString());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return totalAmount;
    }

    public float getTotalOrderAmountByCustomerID(
            int customerID
    ) throws SQLException {
        Connection conn = null;
        float totalAmount = -1;
        try {
            // get connection
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // Prepare the stored procedure call
            String sql = "{call GetTotalOrderAmountByCustomer(?, ?)}";
            CallableStatement callableStatement = conn.prepareCall(sql);

            // Set the input parameters
            callableStatement.setInt(1, customerID); // @customerID
            callableStatement.registerOutParameter(2, Types.REAL);

            // Execute the stored procedure
            callableStatement.execute();

            // Retrieve the output parameter value
            totalAmount = callableStatement.getFloat(2);

            logger.info("Executed successfully !");
        } catch (Exception e) {
            logger.severe("Execution failed !");
            logger.severe(e.toString());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return totalAmount;
    }

    public Object[] getProductDetails(
            int productID
    ) throws SQLException {
        Connection conn = null;
        Object[] output = new Object[4];
        try {
            // get connection
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // Prepare the stored procedure call
            String sql = "{call GetProductDetails(?, ?, ?, ?)}";
            CallableStatement callableStatement = conn.prepareCall(sql);

            // Set the input parameters
            callableStatement.setInt(1, productID); // @productID
            callableStatement.registerOutParameter(2, Types.VARCHAR); //@Name
            callableStatement.registerOutParameter(3, Types.INTEGER); // @AvailableQty
            callableStatement.registerOutParameter(4, Types.REAL); //@Price

            // Execute the stored procedure
            callableStatement.execute();

            // Retrieve the output parameter value
            String name = callableStatement.getString(2);
            int availableQty = callableStatement.getInt(3);
            float price = callableStatement.getFloat(4);

            output[0] = productID;
            output[1] = name;
            output[2] = availableQty;
            output[3] = price;

            logger.info("Executed successfully !");
        } catch (Exception e) {
            logger.severe("Execution failed !");
            logger.severe(e.toString());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return output;
    }

}
