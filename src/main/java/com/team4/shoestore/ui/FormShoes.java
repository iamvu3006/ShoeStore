package com.team4.shoestore.ui;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import java.util.List;
import com.team4.shoestore.model.Shoe;
import com.team4.shoestore.service.ShoeService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.RowFilter;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

@Component
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
    private JButton btnDetail;
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
    

    @Autowired
    private ShoeService shoeService;
    public FormShoes() {
        initComponents();
        initEvent();
    }
    @PostConstruct
    public void init(){
        LoadAllShoes();
    }

    public void LoadAllShoes() {
        try {
            System.out.println("Starting to load shoes from database...");
            
            // Clear existing data
            tableModel.setRowCount(0);
            System.out.println("Cleared existing table data");
            
            // Get shoes from database
            List<Shoe> shoes = shoeService.getAllShoes();
            System.out.println("Retrieved " + (shoes != null ? shoes.size() : 0) + " shoes from database");
            
            // Add shoes to table
            if (shoes != null) {
                for (Shoe shoe : shoes) {
                    System.out.println("Adding shoe to table: " + shoe.getName());
                    // Get image resource
                    URL imageUrl = getClass().getResource(shoe.getImageUrl());
                    if (imageUrl == null) {
                        System.out.println("Warning: Image not found for shoe: " + shoe.getName() + ", URL: " + shoe.getImageUrl());
                    }
                    tableModel.addRow(new Object[]{
                        shoe.getShoeId(),
                        shoe.getName(),
                        shoe.getBrand().getName(),
                        shoe.getCategory(),
                        shoe.getPrice(),
                        imageUrl,
                        shoe.isStatus() ? "Còn hàng" : "Hết hàng"
                    });
                }
            }
            System.out.println("Finished loading shoes");
        } catch (Exception e) {
            System.err.println("Error loading shoes: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error loading shoes: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
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
            public JLabel getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
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
        shoeTable.getColumnModel().getColumn(4).setPreferredWidth(100);  // Giá
        shoeTable.getColumnModel().getColumn(5).setPreferredWidth(100);  // Ảnh
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
            public JLabel getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel comp = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value != null) {
                    String status = value.toString();
                    if (status.equals("Còn hàng")) {
                        comp.setForeground(new Color(46, 204, 113)); // Màu xanh lá
                    } else {
                        comp.setForeground(new Color(231, 76, 60)); // Màu đỏ
                    }
                }
                return comp;
            }
        });
        
        // Customize Image column with image renderer
        shoeTable.getColumnModel().getColumn(5).setCellRenderer(new ImageRenderer());
        
        
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
        btnDetail = createButton("Chi tiết");
        
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
        button.setAlignmentX(0.5f);
        
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
    
    
    private void initEvent() {
        // Search button event
        btnSearch.addActionListener(e -> {
            String searchText = txtSearch.getText().toLowerCase().trim();
            String filterType = cboFilterType.getSelectedItem().toString();
            
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
            shoeTable.setRowSorter(sorter);
            
            if (searchText.isEmpty()) {
                sorter.setRowFilter(null);
            } else {
                RowFilter<DefaultTableModel, Object> rowFilter = new RowFilter<DefaultTableModel, Object>() {
                    @Override
                    public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                        switch (filterType) {
                            case "Tên giày":
                                return entry.getStringValue(1).toLowerCase().contains(searchText);
                            case "Thương hiệu":
                                return entry.getStringValue(2).toLowerCase().contains(searchText);
                            case "Thể loại":
                                return entry.getStringValue(3).toLowerCase().contains(searchText);
                            default:
                                return true;
                        }
                    }
                };
                sorter.setRowFilter(rowFilter);
            }
        });

        // Add new shoe button event
        btnAdd.addActionListener(e -> {
            // TODO: Implement add shoe dialog
            JOptionPane.showMessageDialog(this,
                "Chức năng thêm mới giày đang được phát triển",
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE);
        });

        // View details button event
        btnDetail.addActionListener(e -> {
            int selectedRow = shoeTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn giày để xem chi tiết",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Convert view index to model index if table is sorted
            int modelRow = shoeTable.convertRowIndexToModel(selectedRow);
            
            try {
                Integer shoeId = Integer.parseInt(tableModel.getValueAt(modelRow, 0).toString());
                Shoe shoe = shoeService.getShoeById(shoeId);
                
                if (shoe != null) {
                    String details = String.format("""
                        Chi tiết giày:
                        ID: %d
                        Tên: %s
                        Thương hiệu: %s
                        Thể loại: %s
                        Giá: %s
                        Trạng thái: %s
                        """,
                        shoe.getShoeId(),
                        shoe.getName(),
                        shoe.getBrand().getName(),
                        shoe.getCategory(),
                        shoe.getPrice(),
                        shoe.isStatus() ? "Còn hàng" : "Hết hàng"
                    );
                    
                    JOptionPane.showMessageDialog(this,
                        details,
                        "Chi tiết giày",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Lỗi khi lấy thông tin giày: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        // Edit button event
        btnEdit.addActionListener(e -> {
            int selectedRow = shoeTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn giày cần sửa",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Convert view index to model index if table is sorted
            int modelRow = shoeTable.convertRowIndexToModel(selectedRow);
            
            try {
                Integer shoeId = Integer.parseInt(tableModel.getValueAt(modelRow, 0).toString());
                Shoe shoe = shoeService.getShoeById(shoeId);
                
                if (shoe != null) {
                    // TODO: Implement edit shoe dialog
                    JOptionPane.showMessageDialog(this,
                        "Chức năng sửa thông tin giày đang được phát triển",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Lỗi khi lấy thông tin giày: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        // Delete button event
        btnDelete.addActionListener(e -> {
            int selectedRow = shoeTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn giày cần xóa",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Convert view index to model index if table is sorted
            int modelRow = shoeTable.convertRowIndexToModel(selectedRow);
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa giày này?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Integer shoeId = Integer.parseInt(tableModel.getValueAt(modelRow, 0).toString());
                    shoeService.deleteShoe(shoeId);
                    LoadAllShoes(); // Refresh table
                    JOptionPane.showMessageDialog(this,
                        "Đã xóa giày thành công!",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                        "Lỗi khi xóa giày: " + ex.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add search on type
        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { btnSearch.doClick(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { btnSearch.doClick(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { btnSearch.doClick(); }
        });
    }
    
    // Add image renderer class
    private class ImageRenderer extends DefaultTableCellRenderer {
        private static final int IMAGE_WIDTH = 60;
        private static final int IMAGE_HEIGHT = 60;
        
        @Override
        public JLabel getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
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
                    // Load image from resource
                    URL imageUrl = (URL) value;
                    if (imageUrl != null) {
                        Image img = ImageIO.read(imageUrl);
                        // Scale image to fit cell
                        Image scaledImg = img.getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
                        label.setIcon(new ImageIcon(scaledImg));
                    } else {
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