package com.team4.shoestore.ui.childform;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddEditCustomerForm extends JDialog {
    private JTextField txtFullName;
    private JTextField txtPhone;
    private JButton btnSave;
    private JButton btnCancel;
    
    // Colors
    private static final Color BACKGROUND_COLOR = new Color(30, 30, 30);
    private static final Color PANEL_COLOR = new Color(40, 40, 40);
    private static final Color TEXT_COLOR = new Color(221, 221, 221);
    private static final Color BUTTON_COLOR = new Color(64, 64, 64);
    private static final Color BUTTON_HOVER_COLOR = new Color(80, 80, 80);
    
    public AddEditCustomerForm(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initEvent();
    }
    
    private void initComponents() {
        setTitle("Thêm/Sửa khách hàng");
        setSize(500, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(BACKGROUND_COLOR);
        
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Full name
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(createLabel("Họ và tên:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtFullName = createTextField();
        formPanel.add(txtFullName, gbc);
        
        // Phone
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        formPanel.add(createLabel("Số điện thoại:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtPhone = createTextField();
        formPanel.add(txtPhone, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        
        btnSave = createButton("Lưu");
        btnCancel = createButton("Hủy");
        
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_COLOR);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        return label;
    }
    
    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 30));
        textField.setBackground(PANEL_COLOR);
        textField.setForeground(TEXT_COLOR);
        textField.setCaretColor(TEXT_COLOR);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return textField;
    }
    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 35));
        button.setBackground(BUTTON_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(BUTTON_HOVER_COLOR);
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(BUTTON_COLOR);
            }
        });
        
        return button;
    }
    
    private void initEvent() {
        
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AddEditCustomerForm(null, true).setVisible(true);
        });
    }
} 