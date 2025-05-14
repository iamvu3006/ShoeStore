package com.team4.shoestore.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class FormShoes extends JPanel {
    // Components
    private JPanel headerPanel;
    private JPanel searchPanel;
    private JPanel buttonPanel;
    private JTable shoeTable;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;
    private JButton btnSearch;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JComboBox<String> cboFilterType;
    
    // Colors
    private static final Color BACKGROUND_COLOR = new Color(30, 30, 30);
    private static final Color PANEL_COLOR = new Color(40, 40, 40);
    private static final Color TEXT_COLOR = new Color(221, 221, 221);
    private static final Color BUTTON_COLOR = new Color(64, 64, 64);
    private static final Color BUTTON_HOVER_COLOR = new Color(80, 80, 80);
    private static final Color TABLE_HEADER_COLOR = new Color(30, 30, 30);
    private static final Color TABLE_ROW_COLOR = new Color(40, 40, 40);
    private static final Color TABLE_SELECTION_COLOR = new Color(70, 70, 70);
    
    public FormShoes() {
        initComponents();
        initEvent();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        
        // Initialize header
        initHeader();
        
        // Initialize search panel
        initSearchPanel();
        
        // Initialize button panel
        initButtonPanel();
        
        // Add components to main panel
        add(headerPanel, BorderLayout.NORTH);
        add(searchPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
    }
    
    private void initHeader() {
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Quản lý giày");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(TEXT_COLOR);
        headerPanel.add(titleLabel, BorderLayout.WEST);
    }
    
    private void initSearchPanel() {
        searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(BACKGROUND_COLOR);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        
        // Search panel with buttons
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BACKGROUND_COLOR);
        
        // Left panel for search
        JPanel searchBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchBoxPanel.setBackground(BACKGROUND_COLOR);
        
        // Filter type combobox
        String[] filterTypes = {"Tên giày", "Thương hiệu","Thể loại"};
        cboFilterType = new JComboBox<>(filterTypes);
        cboFilterType.setPreferredSize(new Dimension(150, 35));
        cboFilterType.setBackground(PANEL_COLOR);
        cboFilterType.setForeground(TEXT_COLOR);
        cboFilterType.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cboFilterType.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        // Customize combobox appearance
        cboFilterType.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBackground(isSelected ? TABLE_SELECTION_COLOR : PANEL_COLOR);
                setForeground(TEXT_COLOR);
                setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                return this;
            }
        });
        
        txtSearch = new JTextField(20);
        txtSearch.setPreferredSize(new Dimension(200, 35));
        txtSearch.setBackground(PANEL_COLOR);
        txtSearch.setForeground(TEXT_COLOR);
        txtSearch.setCaretColor(TEXT_COLOR);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        btnSearch = createButton("Tìm kiếm");
        btnSearch.setPreferredSize(new Dimension(120, 35));
        
        searchBoxPanel.add(cboFilterType);
        searchBoxPanel.add(Box.createHorizontalStrut(10));
        searchBoxPanel.add(txtSearch);
        searchBoxPanel.add(Box.createHorizontalStrut(10));
        searchBoxPanel.add(btnSearch);
        
        // Right panel for Add button
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addButtonPanel.setBackground(BACKGROUND_COLOR);
        btnAdd = createButton("Thêm mới");
        btnAdd.setPreferredSize(new Dimension(120, 35));
        addButtonPanel.add(btnAdd);
        
        topPanel.add(searchBoxPanel, BorderLayout.WEST);
        topPanel.add(addButtonPanel, BorderLayout.EAST);
        
        // Table
        String[] columnNames = {"ID", "Tên giày", "Thương hiệu", "Thể loại", "Giá", "Ảnh", "Trạng thái"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        shoeTable = new JTable(tableModel);
        
        // Set column widths
        shoeTable.getColumnModel().getColumn(0).setPreferredWidth(50);   // ID
        shoeTable.getColumnModel().getColumn(1).setPreferredWidth(200);  // Tên giày
        shoeTable.getColumnModel().getColumn(2).setPreferredWidth(120);  // Thương hiệu
        shoeTable.getColumnModel().getColumn(3).setPreferredWidth(120);  // Thể loại
        shoeTable.getColumnModel().getColumn(4).setPreferredWidth(120);  // Giá
        shoeTable.getColumnModel().getColumn(5).setPreferredWidth(90);   // Ảnh
        shoeTable.getColumnModel().getColumn(6).setPreferredWidth(100);  // Trạng thái
        
        // Customize table appearance
        shoeTable.setBackground(TABLE_ROW_COLOR);
        shoeTable.setForeground(TEXT_COLOR);
        shoeTable.setGridColor(new Color(60, 60, 60));
        shoeTable.setSelectionBackground(TABLE_SELECTION_COLOR);
        shoeTable.setSelectionForeground(TEXT_COLOR);
        shoeTable.setRowHeight(90); // Tăng chiều cao hàng để hiển thị ảnh
        shoeTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        shoeTable.setFillsViewportHeight(true);
        shoeTable.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60)));
        shoeTable.setShowGrid(true);
        shoeTable.setIntercellSpacing(new Dimension(0, 0));
        
        // Customize header
        shoeTable.getTableHeader().setBackground(TABLE_HEADER_COLOR);
        shoeTable.getTableHeader().setForeground(TEXT_COLOR);
        shoeTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        shoeTable.getTableHeader().setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60)),
            BorderFactory.createEmptyBorder(0, 0, 1, 0)
        ));
        shoeTable.getTableHeader().setPreferredSize(new Dimension(shoeTable.getTableHeader().getWidth(), 45));
        
        // Customize cell renderer with borders
        shoeTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            {
                setBackground(TABLE_ROW_COLOR);
                setForeground(TEXT_COLOR);
                setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(60, 60, 60)),
                    BorderFactory.createEmptyBorder(0, 15, 0, 5)
                ));
                setFont(new Font("Segoe UI", Font.PLAIN, 13));
                setHorizontalAlignment(SwingConstants.LEFT);
            }
        });
        
        // Center align ID column with borders
        shoeTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            {
                setHorizontalAlignment(SwingConstants.CENTER);
                setBackground(TABLE_ROW_COLOR);
                setForeground(TEXT_COLOR);
                setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(60, 60, 60)),
                    BorderFactory.createEmptyBorder(0, 5, 0, 5)
                ));
                setFont(new Font("Segoe UI", Font.PLAIN, 13));
            }
        });
        
        // Right align Price column with borders
        shoeTable.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            {
                setHorizontalAlignment(SwingConstants.RIGHT);
                setBackground(TABLE_ROW_COLOR);
                setForeground(TEXT_COLOR);
                setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(60, 60, 60)),
                    BorderFactory.createEmptyBorder(0, 5, 0, 15)
                ));
                setFont(new Font("Segoe UI", Font.PLAIN, 13));
            }
        });
        
        // Custom renderer for Status column
        shoeTable.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            {
                setHorizontalAlignment(SwingConstants.CENTER);
                setBackground(TABLE_ROW_COLOR);
                setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
                setFont(new Font("Segoe UI", Font.PLAIN, 13));
            }
            
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value != null) {
                    String status = value.toString();
                    if (status.equals("Còn hàng")) {
                        setForeground(new Color(46, 204, 113)); // Màu xanh lá
                    } else {
                        setForeground(new Color(231, 76, 60)); // Màu đỏ
                    }
                }
                return comp;
            }
        });
        
        // Customize Image column with image renderer
        shoeTable.getColumnModel().getColumn(5).setCellRenderer(new ImageRenderer());
        
        // Add sample data
        addSampleData();
        
        JScrollPane scrollPane = new JScrollPane(shoeTable);
        scrollPane.setBackground(TABLE_ROW_COLOR);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60)));
        scrollPane.setPreferredSize(new Dimension(800, 300));
        
        // Customize scrollbar
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setPreferredSize(new Dimension(10, 0));
        verticalScrollBar.setBackground(BACKGROUND_COLOR);
        verticalScrollBar.setForeground(new Color(60, 60, 60));
        
        // Add padding between search panel and table
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(BACKGROUND_COLOR);
        tableContainer.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        
        searchPanel.add(topPanel, BorderLayout.NORTH);
        searchPanel.add(tableContainer, BorderLayout.CENTER);
    }
    
    private void initButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        
        btnEdit = createButton("Sửa");
        btnDelete = createButton("Xóa");
        JButton btnDetail = createButton("Chi tiết");
        
        // Set button sizes
        Dimension buttonSize = new Dimension(150, 35);
        btnEdit.setPreferredSize(buttonSize);
        btnDelete.setPreferredSize(buttonSize);
        btnDetail.setPreferredSize(buttonSize);
        
        // Add buttons to panel
        buttonPanel.add(btnDetail);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(btnEdit);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(btnDelete);
        
    }
    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setForeground(TEXT_COLOR);
        button.setBackground(BUTTON_COLOR);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add hover effect
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
    
    private void addSampleData() {
        Object[][] data = {
            {"1", "Nike Air Max", "Nike", "Chạy bộ", "2,500,000", "View/images/sneaker1.png", "Còn hàng"},
            {"2", "Adidas Ultraboost", "Adidas", "Thể thao", "3,200,000", "View/images/sneaker2.png", "Hết hàng"},
            {"3", "Puma RS-X", "Puma", "Thời trang", "1,800,000", "View/images/sneaker3.png", "Còn hàng"}
        };
        
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }
    
    private void initEvent() {
        // Add event listener for search button
    }
    
    // Add image renderer class
    private class ImageRenderer extends DefaultTableCellRenderer {
        private static final int IMAGE_WIDTH = 60;
        private static final int IMAGE_HEIGHT = 60;
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = new JLabel();
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setBackground(isSelected ? TABLE_SELECTION_COLOR : TABLE_ROW_COLOR);
            label.setOpaque(true);
            
            // Add border to match other cells
            label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(60, 60, 60)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
            
            if (value != null) {
                try {
                    // Load image from file
                    String imagePath = value.toString();
                    File imageFile = new File(imagePath);
                    if (imageFile.exists()) {
                        Image img = ImageIO.read(imageFile);
                        // Scale image to fit cell
                        Image scaledImg = img.getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
                        label.setIcon(new ImageIcon(scaledImg));
                    } else {
                        System.out.println("Image not found: " + imagePath);
                        label.setText("No Image");
                    }
                } catch (IOException e) {
                    System.out.println("Error loading image: " + e.getMessage());
                    label.setText("Error");
                }
            }
            
            return label;
        }
    }
}