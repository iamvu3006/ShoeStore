package com.team4.shoestore.ui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class FormInvoice extends JFrame {
    private JTable tblInvoice;
    private DefaultTableModel tableModel;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnConfirmPayment;
    private JButton btnViewDetails;
    private JTextField txtSearch;
    private JComboBox<String> cboStatus;
    
    // Colors
    private static final Color BACKGROUND_COLOR = new Color(30, 30, 30);
    private static final Color TEXT_COLOR = new Color(221, 221, 221);
    private static final Color BUTTON_COLOR = new Color(64, 64, 64);
    private static final Color BUTTON_HOVER_COLOR = new Color(80, 80, 80);
    private static final Color TABLE_HEADER_COLOR = new Color(45, 45, 45);
    private static final Color TABLE_ROW_COLOR = new Color(35, 35, 35);
    private static final Color TABLE_BORDER_COLOR = new Color(60, 60, 60);
    
    public FormInvoice() {
        initComponents();
        initEvent();
    }
    
    private void initComponents() {
        setTitle("Quản lý hóa đơn");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(BACKGROUND_COLOR);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Top panel for search and filter
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(BACKGROUND_COLOR);
        
        txtSearch = createTextField();
        txtSearch.setPreferredSize(new Dimension(200, 30));
        topPanel.add(createLabel("Tìm kiếm:"));
        topPanel.add(txtSearch);
        
        String[] statuses = {"Tất cả", "Chờ xác nhận", "Đã xác nhận", "Đã thanh toán", "Đã hủy"};
        cboStatus = createComboBox(statuses);
        topPanel.add(createLabel("Trạng thái:"));
        topPanel.add(cboStatus);
        
        // Table
        String[] columns = {"Mã hóa đơn", "Ngày tạo", "Khách hàng", "Số điện thoại", "Tổng tiền", "Trạng thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblInvoice = new JTable(tableModel);
        tblInvoice.setRowHeight(30);
        tblInvoice.setShowGrid(false);
        tblInvoice.setBackground(TABLE_ROW_COLOR);
        tblInvoice.setForeground(TEXT_COLOR);
        tblInvoice.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        // Customize table header
        JTableHeader header = tblInvoice.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        header.setBackground(TABLE_HEADER_COLOR);
        header.setForeground(TEXT_COLOR);
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        // Center align all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setBackground(TABLE_ROW_COLOR);
        centerRenderer.setForeground(TEXT_COLOR);
        for (int i = 0; i < tblInvoice.getColumnCount(); i++) {
            tblInvoice.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        JScrollPane scrollPane = new JScrollPane(tblInvoice);
        scrollPane.setBackground(BACKGROUND_COLOR);
        scrollPane.setBorder(BorderFactory.createLineBorder(TABLE_BORDER_COLOR));
        scrollPane.getViewport().setBackground(TABLE_ROW_COLOR);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        
        btnAdd = createButton("Thêm");
        btnEdit = createButton("Sửa");
        btnDelete = createButton("Xóa");
        btnConfirmPayment = createButton("Xác nhận thanh toán");
        btnViewDetails = createButton("Xem chi tiết");
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnConfirmPayment);
        buttonPanel.add(btnViewDetails);
        
        // Add panels to main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // Add sample data
        addSampleData();
    }
    
    private void initEvent() {
        btnAdd.addActionListener(ee -> {
            
        });
        
        btnEdit.addActionListener(ee -> {
            int selectedRow = tblInvoice.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn hóa đơn cần sửa",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
           
        });
        
        btnDelete.addActionListener(e -> {
            int selectedRow = tblInvoice.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn hóa đơn cần xóa",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa hóa đơn này?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                
            }
        });
        
        btnConfirmPayment.addActionListener(ee -> {
            int selectedRow = tblInvoice.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn hóa đơn cần xác nhận thanh toán",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String status = (String) tblInvoice.getValueAt(selectedRow, 5);
            if (!status.equals("Đã xác nhận")) {
                JOptionPane.showMessageDialog(this,
                    "Chỉ có thể xác nhận thanh toán cho hóa đơn đã được xác nhận",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xác nhận thanh toán cho hóa đơn này?",
                "Xác nhận thanh toán",
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                
                tableModel.setValueAt("Đã thanh toán", selectedRow, 5);
            }
        });
        
        
        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
        });
        
        cboStatus.addActionListener(ee -> filterTable());
    }
    
    private void filterTable() {

        
      
    }
    
    private void addSampleData() {
        Object[][] data = {
            {"INV001", "01/01/2024", "Nguyễn Văn A", "0123456789", "2,500,000 VNĐ", "Chờ xác nhận"},
            {"INV002", "02/01/2024", "Trần Thị B", "0987654321", "1,800,000 VNĐ", "Đã xác nhận"},
            {"INV003", "03/01/2024", "Lê Văn C", "0123987456", "3,200,000 VNĐ", "Đã thanh toán"},
            {"INV004", "04/01/2024", "Phạm Thị D", "0654789321", "1,500,000 VNĐ", "Đã hủy"}
        };
        
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_COLOR);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
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
        return textField;
    }
    
    private JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setBackground(BACKGROUND_COLOR);
        comboBox.setForeground(TEXT_COLOR);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        comboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(TABLE_BORDER_COLOR),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        // Customize combobox appearance
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBackground(isSelected ? new Color(70, 70, 70) : BACKGROUND_COLOR);
                setForeground(TEXT_COLOR);
                setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                return this;
            }
        });
        
        return comboBox;
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
    
} 