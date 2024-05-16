package com.online.shoppingsystem.gui;

import com.online.shoppingsystem.OnlineShoppingGUI;
import com.online.shoppingsystem.system.OnlineShoppingSystem;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerInsertPanel extends JFrame {

    // insert customer panel
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField phoneNumberField;
    private JTextField streetField;
    private JTextField cityField;
    private JTextField provinceField;
    private JTextField postalCodeField;

    DisplayPanel mainDisplayPanel = new DisplayPanel();

    public CustomerInsertPanel(DisplayPanel mainDisplayPanel) {
        this.mainDisplayPanel = mainDisplayPanel;
    }

    public CustomerInsertPanel() {

    }

    public JPanel customerInsertGUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2));

        // Create a titled border with a margin
        Border border = BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Add Customer"),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );
        panel.setBorder(border); // Set the border for the panel

        panel.add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        panel.add(firstNameField);

        panel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        panel.add(lastNameField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Phone Number:"));
        phoneNumberField = new JTextField();
        panel.add(phoneNumberField);

        panel.add(new JLabel("Street:"));
        streetField = new JTextField();
        panel.add(streetField);

        panel.add(new JLabel("City:"));
        cityField = new JTextField();
        panel.add(cityField);

        panel.add(new JLabel("Province:"));
        provinceField = new JTextField();
        panel.add(provinceField);

        panel.add(new JLabel("Postal Code:"));
        postalCodeField = new JTextField();
        panel.add(postalCodeField);

        JButton insertCustomer = new JButton("Add Customer");
        insertCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertCustomer();
            }
        });
        panel.add(insertCustomer);
        return panel;
    }

    private void insertCustomer() {

        try {

            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String phoneNumber = phoneNumberField.getText();
            String street = streetField.getText();
            String city = cityField.getText();
            String province = provinceField.getText();
            String postalCode = postalCodeField.getText();

            OnlineShoppingSystem onlineShoppingSystem = new OnlineShoppingSystem();
            boolean status = onlineShoppingSystem.insertCustomerWithAddress(firstName, lastName, email, phoneNumber, street, city, province, postalCode);
            if (status) {
                OnlineShoppingGUI.mainDisplayPanel.addMessage("Customer : "+firstName+" "+lastName+" add successful!");
                JOptionPane.showMessageDialog(this, "Customer inserted successfully!");
                firstNameField.setText("");
                lastNameField.setText("");
                emailField.setText("");
                phoneNumberField.setText("");
                streetField.setText("");
                cityField.setText("");
                provinceField.setText("");
                postalCodeField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Customer insertion failed!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
