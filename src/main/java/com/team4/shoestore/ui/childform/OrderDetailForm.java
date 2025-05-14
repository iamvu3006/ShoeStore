package com.team4.shoestore.ui.childform;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class OrderDetailForm extends JDialog {
    private JLabel lblOrderId;
    private JLabel lblCustomerName;
    private JLabel lblTotalAmount;
    private JLabel lblStatus;
    private JTable tblItems;
    private DefaultTableModel tableModel;
    private JButton btnConfirm;
    private JButton btnCancel;
    private JTextField txtPhone;
    
    // Colors
    private static final Color BACKGROUND_COLOR = new Color(30, 30, 30);
    private static final Color TEXT_COLOR = new Color(221, 221, 221);
    private static final Color BUTTON_COLOR = new Color(64, 64, 64);
    private static final Color BUTTON_HOVER_COLOR = new Color(80, 80, 80);
    private static final Color TABLE_HEADER_COLOR = new Color(45, 45, 45);
    private static final Color TABLE_ROW_COLOR = new Color(35, 35, 35);
    private static final Color TABLE_BORDER_COLOR = new Color(60, 60, 60);
    
    public OrderDetailForm(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initEvent();
    }
    
    private void initComponents() {
        setTitle("Chi tiết đơn hàng");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(BACKGROUND_COLOR);
        
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        infoPanel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Order ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(createLabel("Mã đơn hàng:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        lblOrderId = createInfoLabel();
        infoPanel.add(lblOrderId, gbc);
        
        // Customer Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        infoPanel.add(createLabel("Tên khách hàng:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        lblCustomerName = createInfoLabel();
        infoPanel.add(lblCustomerName, gbc);
        
        // Phone
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        infoPanel.add(createLabel("Số điện thoại:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtPhone = createTextField();
        infoPanel.add(txtPhone, gbc);
        
        // Total Amount
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        infoPanel.add(createLabel("Tổng tiền:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        lblTotalAmount = createInfoLabel();
        infoPanel.add(lblTotalAmount, gbc);
        
        // Status
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        infoPanel.add(createLabel("Trạng thái:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        lblStatus = createInfoLabel();
        infoPanel.add(lblStatus, gbc);
        
        // Items table
        String[] columns = {"Tên giày", "Size", "Màu sắc", "Số lượng", "Đơn giá", "Thành tiền"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblItems = new JTable(tableModel);
        tblItems.setRowHeight(30);
        tblItems.setShowGrid(false);
        tblItems.setBackground(TABLE_ROW_COLOR);
        tblItems.setForeground(TEXT_COLOR);
        tblItems.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        // Customize table header
        JTableHeader header = tblItems.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        header.setBackground(TABLE_HEADER_COLOR);
        header.setForeground(TEXT_COLOR);
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        // Center align all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setBackground(TABLE_ROW_COLOR);
        centerRenderer.setForeground(TEXT_COLOR);
        for (int i = 0; i < tblItems.getColumnCount(); i++) {
            tblItems.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        JScrollPane scrollPane = new JScrollPane(tblItems);
        scrollPane.setBackground(BACKGROUND_COLOR);
        scrollPane.setBorder(BorderFactory.createLineBorder(TABLE_BORDER_COLOR));
        scrollPane.getViewport().setBackground(TABLE_ROW_COLOR);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        btnConfirm = createButton("Xác nhận thanh toán");
        btnCancel = createButton("Hủy");
        
        buttonPanel.add(btnConfirm);
        buttonPanel.add(btnCancel);
        
        // Add panels to main panel
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void initEvent() {

    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_COLOR);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        return label;
    }
    
    private JLabel createInfoLabel() {
        JLabel label = new JLabel();
        label.setForeground(TEXT_COLOR);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(TABLE_BORDER_COLOR),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        label.setPreferredSize(new Dimension(200, 30));
        return label;
    }
    
    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setBackground(BACKGROUND_COLOR);
        textField.setForeground(TEXT_COLOR);
        textField.setCaretColor(TEXT_COLOR);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(TABLE_BORDER_COLOR),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        textField.setPreferredSize(new Dimension(200, 30));
        return textField;
    }
    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 35));
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
    
    public void setOrderDetails(String orderId, String customerName, String totalAmount, String status) {
        lblOrderId.setText(orderId);
        lblCustomerName.setText(customerName);
        lblTotalAmount.setText(totalAmount);
        lblStatus.setText(status);
    }
    
    public void addOrderItem(String shoeName, String size, String color, int quantity, double price) {
        double total = quantity * price;
        Object[] row = {shoeName, size, color, quantity, String.format("%,.0f VNĐ", price), String.format("%,.0f VNĐ", total)};
        tableModel.addRow(row);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OrderDetailForm form = new OrderDetailForm(null, true);
            form.setOrderDetails("ORD001", "Nguyễn Văn A", "1,500,000 VNĐ", "Chờ thanh toán");
            form.addOrderItem("Nike Air Max", "40", "Đen", 2, 750000);
            form.setVisible(true);
        });
    }
} 