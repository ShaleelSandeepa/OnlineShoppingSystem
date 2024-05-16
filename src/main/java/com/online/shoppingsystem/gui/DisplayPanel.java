package com.online.shoppingsystem.gui;

import javax.swing.*;
import java.awt.*;

public class DisplayPanel extends JFrame {

    private JTextArea messageArea;
    int width;
    int height;

    public DisplayPanel(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public DisplayPanel() {

    }

    public JScrollPane addDisplayPanel() {
        setLayout(new BorderLayout());

        // Create a JTextArea to display messages
        messageArea = new JTextArea();
        messageArea.setEditable(false); // Make it read-only
        messageArea.setLineWrap(true); // Enable line wrapping
        messageArea.setWrapStyleWord(true); // Wrap at word boundaries

        // Create a JScrollPane to scroll through the messages
        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(width, height));

        // Add the JScrollPane to the panel
        add(scrollPane, BorderLayout.CENTER);
        return scrollPane;
    }

    // Method to add a message to the panel
    public void addMessage(String message) {
        messageArea.append(message + "\n"); // Append the message with a newline
    }

    public void clearMessage() {
        messageArea.setText("");
    }
}
