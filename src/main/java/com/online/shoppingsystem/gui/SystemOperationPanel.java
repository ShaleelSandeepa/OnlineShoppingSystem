package com.online.shoppingsystem.gui;

import com.online.shoppingsystem.OnlineShoppingGUI;
import com.online.shoppingsystem.system.OnlineShoppingSystem;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SystemOperationPanel extends JFrame {

    private JTextField customerID;
    private JTextField orderID;

    public JPanel addSystemOperationPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));
        // Create a titled border with a margin
        Border border = BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("System Operations"),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );
        panel.setBorder(border); // Set the border for the panel

        panel.add(new JLabel("Get Total Order Amount By CustomerID"));
        customerID = new JTextField();
        panel.add(customerID);

        JButton Search = new JButton("Search");
        Search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getAmountByCustomerID(Integer.parseInt(customerID.getText()));
            }
        });
        panel.add(Search);

        panel.add(new JLabel("Get Total Order Amount By OrderID"));
        orderID = new JTextField();
        panel.add(orderID);

        JButton Search2 = new JButton("Search");
        Search2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getAmountByOrderID(Integer.parseInt(orderID.getText()));
            }
        });
        panel.add(Search2);

        return panel;
    }

    private void getAmountByCustomerID(int id) {

        try {

            OnlineShoppingSystem onlineShoppingSystem = new OnlineShoppingSystem();
            float output = onlineShoppingSystem.getTotalOrderAmountByCustomerID(id);
            if (output >= 0) {
                OnlineShoppingGUI.mainDisplayPanel.addMessage("Total Amount of CustomerID[" + id + "] : " + output);
                JOptionPane.showMessageDialog(this, "Execute Successful !");
                customerID.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Execute failed!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void getAmountByOrderID(int id) {

        try {

            OnlineShoppingSystem onlineShoppingSystem = new OnlineShoppingSystem();
            float output = onlineShoppingSystem.getTotalOrderAmountByOrderID(id);
            if (output >= 0) {
                OnlineShoppingGUI.mainDisplayPanel.addMessage("Total Amount of OrderID[" + id + "] : " + output);
                JOptionPane.showMessageDialog(this, "Execute Successful !");
                orderID.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Execute failed!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

}
