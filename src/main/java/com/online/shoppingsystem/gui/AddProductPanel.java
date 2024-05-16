package com.online.shoppingsystem.gui;

import com.online.shoppingsystem.OnlineShoppingGUI;
import com.online.shoppingsystem.system.OnlineShoppingSystem;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddProductPanel extends JFrame {

    //insert product panel
    private JTextField nameField;
    private JTextField qtyField;
    private JTextField priceField;

    DisplayPanel mainDisplayPanel = new DisplayPanel();

    public AddProductPanel(DisplayPanel mainDisplayPanel) {
        this.mainDisplayPanel = mainDisplayPanel;
    }

    public AddProductPanel() {

    }

    public JPanel addProductGUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        // Create a titled border with a margin
        Border border = BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Add Product"),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );
        panel.setBorder(border); // Set the border for the panel

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Available Quantity:"));
        qtyField = new JTextField();
        panel.add(qtyField);

        panel.add(new JLabel("Price:"));
        priceField = new JTextField();
        panel.add(priceField);

        JButton insertButton = new JButton("Add Product");
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertProduct();
            }
        });
        panel.add(insertButton);
        return panel;
    }

    private void insertProduct() {

        try {

            String name = nameField.getText();
            int qty = Integer.parseInt(qtyField.getText());
            float price = Float.parseFloat(priceField.getText());

            OnlineShoppingSystem onlineShoppingSystem = new OnlineShoppingSystem();
            boolean status = onlineShoppingSystem.insertProduct(name, qty, price);
            if (status) {
                OnlineShoppingGUI.mainDisplayPanel.addMessage("Product : "+name+" - "+qty+" items add successful!");
                JOptionPane.showMessageDialog(this, "Product inserted successfully!");
                nameField.setText("");
                qtyField.setText("");
                priceField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Product insertion failed!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
