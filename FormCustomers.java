package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormCustomers extends JPanel {
    // Components
    private JPanel headerPanel;
    private JPanel searchPanel;
    private JPanel buttonPanel;
    private JTable customerTable;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;
    private JButton btnSearch;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JComboBox<String> cboFilterType;
    private JComboBox<String> cboDateFilter;
    
    public FormCustomers() {
        initComponents();
        initEvent();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 30));
        
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
        
        JLabel titleLabel = new JLabel("Quản lý khách hàng");
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
        
        // Filter type combobox
        String[] filterTypes = {"Tên khách hàng", "Số điện thoại", "Ngày đăng ký"};
        cboFilterType = new JComboBox<>(filterTypes);
        cboFilterType.setPreferredSize(new Dimension(150, 35));
        cboFilterType.setBackground(new Color(40, 40, 40));
        cboFilterType.setForeground(Color.WHITE);
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
                setBackground(isSelected ? new Color(70, 70, 70) : new Color(40, 40, 40));
                setForeground(Color.WHITE);
                setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                return this;
            }
        });
        
        // Date filter combobox (initially hidden)
        String[] dateFilters = {"Hôm nay", "Hôm qua", "7 ngày qua", "30 ngày qua"};
        cboDateFilter = new JComboBox<>(dateFilters);
        cboDateFilter.setPreferredSize(new Dimension(150, 35));
        cboDateFilter.setBackground(new Color(40, 40, 40));
        cboDateFilter.setForeground(Color.WHITE);
        cboDateFilter.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cboDateFilter.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        cboDateFilter.setVisible(false);
        
        // Customize date filter combobox appearance
        cboDateFilter.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBackground(isSelected ? new Color(70, 70, 70) : new Color(40, 40, 40));
                setForeground(Color.WHITE);
                setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                return this;
            }
        });
        
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
        btnSearch.setPreferredSize(new Dimension(120, 35));
        
        searchBoxPanel.add(cboFilterType);
        searchBoxPanel.add(Box.createHorizontalStrut(10));
        searchBoxPanel.add(txtSearch);
        searchBoxPanel.add(Box.createHorizontalStrut(10));
        searchBoxPanel.add(cboDateFilter);
        searchBoxPanel.add(Box.createHorizontalStrut(10));
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
        String[] columnNames = {"ID", "Họ tên", "Số điện thoại", "Ngày đăng ký"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        customerTable = new JTable(tableModel);
        
        // Customize table appearance
        customerTable.setBackground(new Color(40, 40, 40));
        customerTable.setForeground(Color.WHITE);
        customerTable.setGridColor(new Color(60, 60, 60));
        customerTable.setSelectionBackground(new Color(70, 70, 70));
        customerTable.setSelectionForeground(Color.WHITE);
        customerTable.setRowHeight(35);
        customerTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        customerTable.setFillsViewportHeight(true);
        customerTable.setBorder(null);
        
        // Customize header
        customerTable.getTableHeader().setBackground(new Color(30, 30, 30));
        customerTable.getTableHeader().setForeground(Color.WHITE);
        customerTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        customerTable.getTableHeader().setBorder(null);
        customerTable.getTableHeader().setPreferredSize(new Dimension(customerTable.getTableHeader().getWidth(), 40));
        
        // Customize cell renderer
        customerTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            {
                setBackground(new Color(40, 40, 40));
                setForeground(Color.WHITE);
                setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 5));
                setFont(new Font("Segoe UI", Font.PLAIN, 13));
            }
        });
        
        // Add sample data
        addSampleData();
        
        JScrollPane scrollPane = new JScrollPane(customerTable);
        scrollPane.setBackground(new Color(40, 40, 40));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60)));
        
        // Customize scrollbar
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setPreferredSize(new Dimension(10, 0));
        verticalScrollBar.setBackground(new Color(30, 30, 30));
        verticalScrollBar.setForeground(new Color(60, 60, 60));
        
        // Add padding between search panel and table
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(new Color(30, 30, 30));
        tableContainer.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String currentDate = dateFormat.format(new Date());
        
        Object[][] data = {
            {"1", "Nguyễn Văn A", "0123456789", currentDate},
            {"2", "Trần Thị B", "0987654321", currentDate},
            {"3", "Lê Văn C", "0369852147", currentDate}
        };
        
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }
    
    private void initEvent() {
        // Add event listeners here
        
        // Add event listener for filter type combobox
        cboFilterType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedFilter = (String) cboFilterType.getSelectedItem();
                if (selectedFilter.equals("Ngày đăng ký")) {
                    txtSearch.setVisible(false);
                    cboDateFilter.setVisible(true);
                } else {
                    txtSearch.setVisible(true);
                    cboDateFilter.setVisible(false);
                }
            }
        });

    }
} 