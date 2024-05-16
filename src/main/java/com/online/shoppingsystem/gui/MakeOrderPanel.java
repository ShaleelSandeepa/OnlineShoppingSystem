package com.online.shoppingsystem.gui;

import com.online.shoppingsystem.dto.OrderDetailDTO;
import com.online.shoppingsystem.OnlineShoppingGUI;
import com.online.shoppingsystem.system.OnlineShoppingSystem;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MakeOrderPanel extends JFrame {

    private JTextField customerIdField;
    private JTextField totalAmountField;
    private JTextField orderIdField;
    private JTextField productIdField;
    private JTextField quantityField;
    private JTextField subtotalField;
    DisplayPanel displayPanel = new DisplayPanel(200, 150);

    DisplayPanel mainDisplayPanel = new DisplayPanel();

    List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();

    public MakeOrderPanel(DisplayPanel mainDisplayPanel) {
        this.mainDisplayPanel = mainDisplayPanel;
    }

    public MakeOrderPanel() {

    }

    public JPanel makeOrderGUI() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3));
        // Create a titled border with a margin
        Border border = BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Order Management"),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );
        panel.setBorder(border); // Set the border for the panel

        ////////////////////////////// Panel 1

        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(8, 3));

        // Create a titled border with a margin
        Border border1 = BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Open New Order"),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );
        panel1.setBorder(border1); // Set the border for the panel

        panel1.add(new JLabel("Customer ID:"));
        customerIdField = new JTextField();
        panel1.add(customerIdField);


        totalAmountField = new JTextField();
        totalAmountField.setEditable(false); // Make it read-only

        JButton makeOrderButton = new JButton("Open New Order");
        makeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                totalAmountField.setText("0");
                makeOrder();
            }
        });
        panel1.add(makeOrderButton);
        panel1.add(new JLabel("Total Amount:"));
        panel1.add(totalAmountField);
        panel1.add(new JLabel(" "));

        JButton submit = new JButton("Make Payment");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (orderDetailDTOList != null) {
                    for (OrderDetailDTO orderDetailDTO : orderDetailDTOList) {
                        submitOrderDetailsToDB(orderDetailDTO.getOrderID(), orderDetailDTO.getProductID(), orderDetailDTO.getQty());
                    }
                    makePayment();
                }
            }
        });
        JButton clear = new JButton("Clear Order");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearOrder();
            }
        });

        panel1.add(submit);
        panel1.add(clear);

        ////////////////////////////// Panel 2

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(6, 3));

        // Create a titled border with a margin
        Border border2 = BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Add Item"),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );
        panel2.setBorder(border2); // Set the border for the panel

        panel2.add(new JLabel("Order ID:"));
        orderIdField = new JTextField();
        orderIdField.setEditable(false);
        panel2.add(orderIdField);

        panel2.add(new JLabel("Product ID:"));
        productIdField = new JTextField();
        panel2.add(productIdField);

        panel2.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        panel2.add(quantityField);

        panel2.add(new JLabel("Subtotal:"));
        subtotalField = new JTextField();
        subtotalField.setEditable(false); // Make it read-only
        panel2.add(subtotalField);

        JButton addOrderDetailsButton = new JButton("Add Order Item");
        addOrderDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOrderDetailsToPanel();
            }
        });
        panel2.add(addOrderDetailsButton);

        JScrollPane displayPane = displayPanel.addDisplayPanel();

        panel.add(panel1);
        panel.add(panel2);
        panel.add(displayPane);

        return panel;
    }

    private void addOrderDetails() {
        try {

            int orderID = Integer.parseInt(orderIdField.getText());
            int productID = Integer.parseInt(productIdField.getText());
            int qty = Integer.parseInt(quantityField.getText());

            // add order item details as object to the list
            orderDetailDTOList.add(new OrderDetailDTO(orderID, productID, qty));

            OnlineShoppingSystem onlineShoppingSystem = new OnlineShoppingSystem();
            Object[] output = onlineShoppingSystem.addItem(orderID, productID, qty);

            if (output != null) {
                orderIdField.setText(String.valueOf(orderID));
                subtotalField.setText(String.valueOf(output[0]));
                //set total
                float total = Float.parseFloat(totalAmountField.getText()) + Float.parseFloat(output[0].toString());
                totalAmountField.setText(String.valueOf(total));
                displayPanel.addMessage("OrderDetailID[" + output[1].toString() + "] : " + output[0].toString());
                OnlineShoppingGUI.mainDisplayPanel.addMessage("OrderDetailID[" + output[1].toString() + "] : " + output[0].toString());
                JOptionPane.showMessageDialog(this, "Order item successfully added!");
            } else {
                JOptionPane.showMessageDialog(this, "Order item add failed!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addOrderDetailsToPanel() {
        try {

            int orderID = Integer.parseInt(orderIdField.getText());
            int productID = Integer.parseInt(productIdField.getText());
            int qty = Integer.parseInt(quantityField.getText());

            OnlineShoppingSystem onlineShoppingSystem = new OnlineShoppingSystem();
            Object[] output = onlineShoppingSystem.getProductDetails(productID);

            if (output != null) {
                if (Integer.parseInt(output[2].toString()) > qty) {

                    // add order item details as object to the list
                    orderDetailDTOList.add(new OrderDetailDTO(orderID, productID, qty));

                    orderIdField.setText(String.valueOf(orderID));
                    float subTotal = Float.parseFloat(String.valueOf(output[3]))*qty;
                    subtotalField.setText(String.valueOf(subTotal));

                    //set total
                    float total = Float.parseFloat(totalAmountField.getText()) + subTotal;
                    totalAmountField.setText(String.valueOf(total));
                    displayPanel.addMessage("ProductID[" + output[0].toString() + "] : Name:" + output[1].toString() +
                            " | Qty:" + qty + " | SubTotal:" + subTotal);
                    OnlineShoppingGUI.mainDisplayPanel.addMessage("ProductID[" + output[0].toString() + "] : Name:" + output[1].toString() +
                            " | Qty:" + qty + " | SubTotal:" + subTotal);
                    JOptionPane.showMessageDialog(this, "Order item successfully added!");
                    productIdField.setText("");
                    quantityField.setText("");
                    subtotalField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Product out of stock\nonly "+output[2].toString()+" Items available");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Order item add failed!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void submitOrderDetailsToDB(int orderID, int productID, int qty) {
        try {

            OnlineShoppingSystem onlineShoppingSystem = new OnlineShoppingSystem();
            Object[] output = onlineShoppingSystem.addItem(orderID, productID, qty);
            if (output != null) {
                orderIdField.setText(String.valueOf(orderID));
                subtotalField.setText(String.valueOf(output[0]));
                //set total
//                float total = Float.parseFloat(totalAmountField.getText()) + Float.parseFloat(output[0].toString());
//                totalAmountField.setText(String.valueOf(total));
                displayPanel.addMessage("OrderDetailID[" + output[1].toString() + "] : " + output[0].toString());
                OnlineShoppingGUI.mainDisplayPanel.addMessage("OrderDetailID[" + output[1].toString() + "] : " + output[0].toString());
//                JOptionPane.showMessageDialog(this, "Order item successfully added!");
            } else {
                JOptionPane.showMessageDialog(this, "Order item add failed!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void makePayment() {
        try {

            // submit all order items to orderDetails table in DB
//            submitOrderDetailsToDB();

            int customerID = Integer.parseInt(customerIdField.getText());
            int orderID = Integer.parseInt(orderIdField.getText());
            float amount = Float.parseFloat(totalAmountField.getText());

            OnlineShoppingSystem onlineShoppingSystem = new OnlineShoppingSystem();
            boolean output = onlineShoppingSystem.makeOrderPayment(customerID, orderID, amount);
            if (output) {
                displayPanel.addMessage("OrderID[" + orderID + "] " + "Payment Successful !");
                OnlineShoppingGUI.mainDisplayPanel.addMessage("OrderID[" + orderID + "] " + "Payment Successful !");
                JOptionPane.showMessageDialog(this, "Payment Successful !");
                customerIdField.setText("");
                totalAmountField.setText("");
                orderIdField.setText("");
                productIdField.setText("");
                quantityField.setText("");
                subtotalField.setText("");
                displayPanel.clearMessage();
                orderDetailDTOList.clear();
            } else {
                JOptionPane.showMessageDialog(this, "Payment failed!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void reverseOrderDetailQty() {

    }

    private void clearOrder() {
        try {

            orderDetailDTOList.clear();

            int orderID = Integer.parseInt(orderIdField.getText());

            OnlineShoppingSystem onlineShoppingSystem = new OnlineShoppingSystem();
            boolean output = onlineShoppingSystem.clearOrder(orderID);
            if (output) {
                displayPanel.addMessage("OrderID[" + orderID + "] " + "Order clear successful !");
                OnlineShoppingGUI.mainDisplayPanel.addMessage("OrderID[" + orderID + "] " + "Order clear successful !");
                JOptionPane.showMessageDialog(this, "Order clear Successful !");
                totalAmountField.setText("0");
                productIdField.setText("");
                quantityField.setText("");
                subtotalField.setText("");
                displayPanel.clearMessage();
            } else {
                JOptionPane.showMessageDialog(this, "Order clear failed!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void makeOrder() {
        try {

            int qty = Integer.parseInt(customerIdField.getText());
            float price = Float.parseFloat(totalAmountField.getText());

            OnlineShoppingSystem onlineShoppingSystem = new OnlineShoppingSystem();
            int orderID = onlineShoppingSystem.makeOrder(qty, price);
            if (orderID != 0) {
                displayPanel.clearMessage();
                orderIdField.setText(String.valueOf(orderID));
                displayPanel.addMessage("OrderID[" + orderID + "] " + "Opened successful !");
                OnlineShoppingGUI.mainDisplayPanel.addMessage("OrderID[" + orderID + "] " + "Opened successful !");
                JOptionPane.showMessageDialog(this, "Order successfully opened!");
            } else {
                OnlineShoppingGUI.mainDisplayPanel.addMessage("Order Opened failed !");
                JOptionPane.showMessageDialog(this, "Order opening failed!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
