package com.online.shoppingsystem;

import com.online.shoppingsystem.gui.*;

import javax.swing.*;
import java.util.logging.Logger;

public class OnlineShoppingGUI extends JFrame {

    private static final Logger logger = Logger.getLogger(OnlineShoppingGUI.class.getName());
    public static DisplayPanel mainDisplayPanel = new DisplayPanel(600, 300);

    public OnlineShoppingGUI() {

        setTitle("Online Shopping System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 700);

        JPanel mainPanel = new JPanel();
//        mainPanel.setLayout(new GridLayout(3, 3));

        JPanel customerInsertPanel = new CustomerInsertPanel().customerInsertGUI();
        JPanel addProductPanel = new AddProductPanel().addProductGUI();
        JPanel addMakeOrderPanel = new MakeOrderPanel().makeOrderGUI();
        JPanel systemOperationPanel = new SystemOperationPanel().addSystemOperationPanel();

        JScrollPane displayPanel = mainDisplayPanel.addDisplayPanel();

        mainPanel.add(customerInsertPanel);
        mainPanel.add(addProductPanel);
        mainPanel.add(addMakeOrderPanel);
        mainPanel.add(systemOperationPanel);
        mainPanel.add(displayPanel);

        new CustomerInsertPanel(mainDisplayPanel);
        new AddProductPanel(mainDisplayPanel);
        new MakeOrderPanel(mainDisplayPanel);

        getContentPane().add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new OnlineShoppingGUI();
                logger.info("Welcome to Online Shopping System !");
            }
        });
    }


}
