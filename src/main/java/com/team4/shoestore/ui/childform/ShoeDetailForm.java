package com.team4.shoestore.ui.childform;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class ShoeDetailForm extends JDialog {
    private JLabel lblImage;
    private JLabel lblId;
    private JLabel lblName;
    private JLabel lblBrand;
    private JLabel lblCategory;
    private JLabel lblPrice;
    private JComboBox<String> cboStatus;
    private JTable tblSizes;
    private DefaultTableModel tableModel;
    private JButton btnClose;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnUploadImage;
    private JButton btnSave;
    
    // Colors
    private static final Color BACKGROUND_COLOR = new Color(30, 30, 30);
    private static final Color TEXT_COLOR = new Color(221, 221, 221);
    private static final Color BUTTON_COLOR = new Color(64, 64, 64);
    private static final Color BUTTON_HOVER_COLOR = new Color(80, 80, 80);
    private static final Color TABLE_HEADER_COLOR = new Color(45, 45, 45);
    private static final Color TABLE_ROW_COLOR = new Color(35, 35, 35);
    private static final Color TABLE_BORDER_COLOR = new Color(60, 60, 60);
    
    public ShoeDetailForm(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initEvent();
    }
    
    private void initComponents() {
        setTitle("Chi tiết giày");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(BACKGROUND_COLOR);
        
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Left panel for image and basic info
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(BACKGROUND_COLOR);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        
        // Image panel
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        imagePanel.setBackground(BACKGROUND_COLOR);
        lblImage = new JLabel();
        lblImage.setPreferredSize(new Dimension(300, 300));
        lblImage.setBorder(BorderFactory.createLineBorder(TABLE_BORDER_COLOR));
        imagePanel.add(lblImage);
        
        // Upload button panel
        JPanel uploadPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        uploadPanel.setBackground(BACKGROUND_COLOR);
        btnUploadImage = createButton("Upload ảnh");
        uploadPanel.add(btnUploadImage);
        
        // Basic info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        infoPanel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(createLabel("ID:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        lblId = createInfoLabel();
        infoPanel.add(lblId, gbc);
        
        // Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        infoPanel.add(createLabel("Tên giày:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        lblName = createInfoLabel();
        infoPanel.add(lblName, gbc);
        
        // Brand
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        infoPanel.add(createLabel("Thương hiệu:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        lblBrand = createInfoLabel();
        infoPanel.add(lblBrand, gbc);
        
        // Category
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        infoPanel.add(createLabel("Thể loại:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        lblCategory = createInfoLabel();
        infoPanel.add(lblCategory, gbc);
        
        // Price
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        infoPanel.add(createLabel("Giá:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        lblPrice = createInfoLabel();
        infoPanel.add(lblPrice, gbc);
        
        // Status
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.0;
        infoPanel.add(createLabel("Trạng thái:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        String[] statuses = {"Còn hàng", "Hết hàng"};
        cboStatus = new JComboBox<>(statuses);
        cboStatus.setBackground(BACKGROUND_COLOR);
        cboStatus.setForeground(TEXT_COLOR);
        cboStatus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cboStatus.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(TABLE_BORDER_COLOR),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        cboStatus.setPreferredSize(new Dimension(200, 30));
        
        // Customize combobox appearance
        cboStatus.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBackground(isSelected ? new Color(70, 70, 70) : BACKGROUND_COLOR);
                setForeground(TEXT_COLOR);
                setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                return this;
            }
        });
        
        infoPanel.add(cboStatus, gbc);
        
        leftPanel.add(imagePanel);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(uploadPanel);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(infoPanel);
        
        // Right panel for size/color/quantity table
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(BACKGROUND_COLOR);
        
        // Table
        String[] columns = {"Size", "Màu sắc", "Số lượng"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        tblSizes = new JTable(tableModel);
        tblSizes.setRowHeight(30);
        tblSizes.setShowGrid(false);
        tblSizes.setBackground(TABLE_ROW_COLOR);
        tblSizes.setForeground(TEXT_COLOR);
        tblSizes.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        // Customize table header
        JTableHeader header = tblSizes.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        header.setBackground(TABLE_HEADER_COLOR);
        header.setForeground(TEXT_COLOR);
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        // Center align all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setBackground(TABLE_ROW_COLOR);
        centerRenderer.setForeground(TEXT_COLOR);
        for (int i = 0; i < tblSizes.getColumnCount(); i++) {
            tblSizes.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        // Set column widths
        tblSizes.getColumnModel().getColumn(0).setPreferredWidth(100);
        tblSizes.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblSizes.getColumnModel().getColumn(2).setPreferredWidth(100);
        
        JScrollPane scrollPane = new JScrollPane(tblSizes);
        scrollPane.setBackground(BACKGROUND_COLOR);
        scrollPane.setBorder(BorderFactory.createLineBorder(TABLE_BORDER_COLOR));
        scrollPane.getViewport().setBackground(TABLE_ROW_COLOR);
        
        // Customize scrollbar
        JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
        scrollBar.setBackground(TABLE_HEADER_COLOR);
        scrollBar.setForeground(TABLE_BORDER_COLOR);
        
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Button panel with padding
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Add top padding
        
        btnAdd = createButton("Thêm");
        btnEdit = createButton("Sửa");
        btnDelete = createButton("Xóa");
        btnSave = createButton("Lưu");
        btnClose = createButton("Đóng");
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnClose);
        
        // Add panels to main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // Add sample data
        addSampleData();
        
    }
    
    private void addSampleData() {
        Object[][] data = {
            {"38", "Đen", "10"},
            {"39", "Đen", "15"},
            {"40", "Đen", "20"},
            {"41", "Đen", "15"},
            {"42", "Đen", "10"},
            {"38", "Trắng", "10"},
            {"39", "Trắng", "15"},
            {"40", "Trắng", "20"},
            {"41", "Trắng", "15"},
            {"42", "Trắng", "10"}
        };
        
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }
    

    public void addSizeColorRow(String size, String color, String quantity) {
        Object[] row = {size, color, quantity};
        tableModel.addRow(row);
    }
    
    public void updateSizeColorRow(String size, String color, String quantity) {
        int selectedRow = tblSizes.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.setValueAt(size, selectedRow, 0);
            tableModel.setValueAt(color, selectedRow, 1);
            tableModel.setValueAt(quantity, selectedRow, 2);
        }
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
        btnAdd.addActionListener(_ -> {
            AddEditSizeColorForm form = new AddEditSizeColorForm(this, true);
            form.setVisible(true);
        });
        
        btnEdit.addActionListener(_ -> {
            int selectedRow = tblSizes.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn size/màu cần sửa",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String size = (String) tblSizes.getValueAt(selectedRow, 0);
            String color = (String) tblSizes.getValueAt(selectedRow, 1);
            String quantity = (String) tblSizes.getValueAt(selectedRow, 2);
            
            AddEditSizeColorForm form = new AddEditSizeColorForm(this, true, size, color, quantity);
            form.setVisible(true);
        });
        
        btnDelete.addActionListener(_ -> {
            int selectedRow = tblSizes.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn size/màu cần xóa",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa size/màu này?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                tableModel.removeRow(selectedRow);
            }
        });
        
        btnClose.addActionListener(_ -> dispose());
        
        btnUploadImage.addActionListener(_ -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                public boolean accept(java.io.File f) {
                    return f.isDirectory() || f.getName().toLowerCase().endsWith(".png") 
                        || f.getName().toLowerCase().endsWith(".jpg") 
                        || f.getName().toLowerCase().endsWith(".jpeg");
                }
                public String getDescription() {
                    return "Image files (*.png, *.jpg, *.jpeg)";
                }
            });
            
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                java.io.File selectedFile = fileChooser.getSelectedFile();
                try {
                    ImageIcon originalIcon = new ImageIcon(selectedFile.getAbsolutePath());
                    Image image = originalIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                    lblImage.setIcon(new ImageIcon(image));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                        "Không thể tải ảnh: " + ex.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    
    public void setShoeDetails(String id, String name, String brand, String category, String price, String status, String imagePath) {
        lblId.setText(id);
        lblName.setText(name);
        lblBrand.setText(brand);
        lblCategory.setText(category);
        lblPrice.setText(price);
        cboStatus.setSelectedItem(status);
        
        // Load and resize image
        try {
            ImageIcon originalIcon = new ImageIcon(imagePath);
            Image image = originalIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            lblImage.setIcon(new ImageIcon(image));
        } catch (Exception e) {
            lblImage.setText("Không tìm thấy ảnh");
        }
    }
    
    public String getStatus() {
        return (String) cboStatus.getSelectedItem();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ShoeDetailForm form = new ShoeDetailForm(null, true);
            form.setShoeDetails("SH001", "Nike Air Max", "Nike", "Sneaker", "$100", "In Stock", "View/images/sneaker1.png");
            form.setVisible(true);
        });
    }
} 