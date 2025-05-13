package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;

public class FormUsers extends JPanel {
    // Components
    private JPanel headerPanel;
    private JPanel searchPanel;
    private JPanel buttonPanel;
    private JTable userTable;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;
    private JButton btnSearch;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    
    public FormUsers() {
        initComponents();
        initEvent();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 30)); // Dark background
        
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
        headerPanel.setBackground(new Color(30, 30, 30));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Quản lý người dùng");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);
    }
    
    private void initSearchPanel() {
        searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(new Color(30, 30, 30));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        
        // Search panel with buttons
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(30, 30, 30));
        
        // Left panel for search
        JPanel searchBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchBoxPanel.setBackground(new Color(30, 30, 30));
        
        txtSearch = new JTextField(20);
        txtSearch.setPreferredSize(new Dimension(200, 35));
        txtSearch.setBackground(new Color(40, 40, 40));
        txtSearch.setForeground(Color.WHITE);
        txtSearch.setCaretColor(Color.WHITE);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        btnSearch = createButton("Tìm kiếm");
        btnSearch.setPreferredSize(new Dimension(100, 35));
        
        searchBoxPanel.add(txtSearch);
        searchBoxPanel.add(btnSearch);
        
        // Right panel for Add button
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addButtonPanel.setBackground(new Color(30, 30, 30));
        btnAdd = createButton("Thêm mới");
        btnAdd.setPreferredSize(new Dimension(100, 35));
        addButtonPanel.add(btnAdd);
        
        topPanel.add(searchBoxPanel, BorderLayout.WEST);
        topPanel.add(addButtonPanel, BorderLayout.EAST);
        
        // Table
        String[] columnNames = {"ID", "Tên đăng nhập", "Mật khẩu", "Vai trò"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        userTable = new JTable(tableModel);
        
        // Customize table appearance
        userTable.setBackground(new Color(40, 40, 40));
        userTable.setForeground(Color.WHITE);
        userTable.setGridColor(new Color(60, 60, 60));
        userTable.setSelectionBackground(new Color(70, 70, 70));
        userTable.setSelectionForeground(Color.WHITE);
        userTable.setRowHeight(35);
        userTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        userTable.setFillsViewportHeight(true);
        userTable.setBorder(null);
        
        // Customize header
        userTable.getTableHeader().setBackground(new Color(30, 30, 30));
        userTable.getTableHeader().setForeground(Color.WHITE);
        userTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        userTable.getTableHeader().setBorder(null);
        userTable.getTableHeader().setPreferredSize(new Dimension(userTable.getTableHeader().getWidth(), 40));
        
        // Customize cell renderer
        userTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            {
                setBackground(new Color(40, 40, 40));
                setForeground(Color.WHITE);
                setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 5));
                setFont(new Font("Segoe UI", Font.PLAIN, 13));
            }
        });
        
        // Add sample data
        addSampleData();
        
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBackground(new Color(40, 40, 40)); // Set scroll pane background
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60)));
        
        // Customize scrollbar
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setPreferredSize(new Dimension(10, 0));
        verticalScrollBar.setBackground(new Color(30, 30, 30));
        verticalScrollBar.setForeground(new Color(60, 60, 60));
        
        // Add padding between search panel and table
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(new Color(30, 30, 30));
        tableContainer.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Top padding
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        
        searchPanel.add(topPanel, BorderLayout.NORTH);
        searchPanel.add(tableContainer, BorderLayout.CENTER);
    }
    
    private void initButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(30, 30, 30));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        
        btnEdit = createButton("Sửa");
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
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(64, 64, 64));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(80, 80, 80));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(64, 64, 64));
            }
        });
        
        return button;
    }
    
    private void addSampleData() {
        // Add some sample data to the table
        Object[][] data = {
            {"1", "admin", "admin@", "Admin"},
            {"2", "user1", "user1@", "User"},
            {"3", "user2", "user2@", "User"}
        };
        
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }
    
    private void initEvent() {
        // Add event listeners here
    }
} 