package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;


public class FormBrands extends JPanel {
    // Components
    private JPanel headerPanel;
    private JPanel searchPanel;
    private JPanel buttonPanel;
    private JTable brandTable;
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
    private static final Color TABLE_ROW_COLOR = new Color(35, 35, 35);
    private static final Color TABLE_SELECTION_COLOR = new Color(60, 60, 60);
    private static final Color TABLE_BORDER_COLOR = new Color(50, 50, 50);
    private static final Color TABLE_HEADER_TEXT_COLOR = new Color(255, 215, 0);
    
    public FormBrands() {
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
        
        JLabel titleLabel = new JLabel("Quản lý thương hiệu");
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
        String[] filterTypes = {"Tên thương hiệu", "Quốc gia"};
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
        
        btnSearch = createButton("🔍 Tìm kiếm");
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
        String[] columnNames = {"ID", "Tên thương hiệu", "Quốc gia"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        brandTable = new JTable(tableModel);
        
        // Set column widths
        brandTable.getColumnModel().getColumn(0).setPreferredWidth(50);   // ID
        brandTable.getColumnModel().getColumn(1).setPreferredWidth(300);  // Tên thương hiệu
        brandTable.getColumnModel().getColumn(2).setPreferredWidth(200);  // Quốc gia
        
        // Customize table appearance
        brandTable.setRowHeight(40);
        brandTable.setShowGrid(false);
        brandTable.setIntercellSpacing(new Dimension(0, 0));
        brandTable.getTableHeader().setPreferredSize(new Dimension(0, 45));
        brandTable.getTableHeader().setBackground(TABLE_HEADER_COLOR);
        brandTable.getTableHeader().setForeground(TABLE_HEADER_TEXT_COLOR);
        brandTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        brandTable.setBackground(TABLE_ROW_COLOR);
        brandTable.setForeground(TEXT_COLOR);
        brandTable.setSelectionBackground(TABLE_SELECTION_COLOR);
        brandTable.setSelectionForeground(TEXT_COLOR);
        brandTable.setFont(new Font("Arial", Font.PLAIN, 14));
        brandTable.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(TABLE_BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        // Center align all columns with custom renderer
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            {
                setHorizontalAlignment(SwingConstants.CENTER);
                setBackground(TABLE_ROW_COLOR);
                setForeground(TEXT_COLOR);
                setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, TABLE_BORDER_COLOR),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
            }
        };
        
        for (int i = 0; i < brandTable.getColumnCount(); i++) {
            brandTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        // Add sample data
        addSampleData();
        
        JScrollPane scrollPane = new JScrollPane(brandTable);
        scrollPane.setBackground(TABLE_ROW_COLOR);
        scrollPane.getViewport().setBackground(TABLE_ROW_COLOR);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(TABLE_BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        scrollPane.setPreferredSize(new Dimension(800, 300));
        
        // Customize scrollbar
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setPreferredSize(new Dimension(10, 0));
        verticalScrollBar.setBackground(TABLE_ROW_COLOR);
        verticalScrollBar.setForeground(TABLE_BORDER_COLOR);
        
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
        
        btnEdit = createButton("✏️ Sửa");
                btnDelete = createButton("Xóa");
        
        // Set button sizes
        Dimension buttonSize = new Dimension(100, 35);
        btnEdit.setPreferredSize(buttonSize);
        btnDelete.setPreferredSize(buttonSize);
        
        // Add buttons to panel
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
            {"1", "Nike", "Mỹ"},
            {"2", "Adidas", "Đức"},
            {"3", "Puma", "Đức"}
        };
        
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }
    
    private void initEvent() {

    }
} 