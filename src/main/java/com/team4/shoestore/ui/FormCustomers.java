package com.team4.shoestore.ui;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import java.util.List;
import com.team4.shoestore.model.Customer;

import com.team4.shoestore.service.CustomerService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;


@Component
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
    

    @Autowired
    private CustomerService customerService;

    public FormCustomers() {
        initComponents();
        initEvent();
    }
    

    @PostConstruct
    public void init(){
        loadCustomersFromDatabase();
    }

    public void loadCustomersFromDatabase(){
        try {
            System.out.println("Starting to load customers from database...");
            
            // Clear existing data
            tableModel.setRowCount(0);
            System.out.println("Cleared existing table data");
            
            // Get customers from database
            List<Customer> customers = customerService.getAllCustomers();
            System.out.println("Retrieved " + (customers != null ? customers.size() : 0) + " customers from database");
            
            // Add users to table
            if (customers != null) {
                for (Customer customer : customers) {
                    System.out.println("Adding user to table: " + customer.getName());
                    tableModel.addRow(new Object[]{
                        customer.getCustomerId(),
                        customer.getName(),
                        customer.getPhone(),
                        customer.getJoinDate()
                    });
                }
            }
            System.out.println("Finished loading customers");
        } catch (Exception e) {
            System.err.println("Error loading customers: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error loading customers: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
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
            public JLabel getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
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
            public JLabel getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
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
        button.setAlignmentX(0.5f);
        
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
    
    private void initEvent() {
        // Search button event
        btnSearch.addActionListener(e -> {
            String filterType = cboFilterType.getSelectedItem().toString();
            String searchText = txtSearch.getText().toLowerCase().trim();
            
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
            customerTable.setRowSorter(sorter);
            
            RowFilter<DefaultTableModel, Object> rowFilter = new RowFilter<DefaultTableModel, Object>() {
            @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                    if (filterType.equals("Ngày đăng ký")) {
                        String selectedDate = cboDateFilter.getSelectedItem().toString();
                        String joinDate = entry.getStringValue(3); // Ngày đăng ký ở cột 3
                        
                        // Implement date filtering logic based on selectedDate
                        switch (selectedDate) {
                            case "Hôm nay":
                                return joinDate.equals(java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                            case "Hôm qua":
                                return joinDate.equals(java.time.LocalDate.now().minusDays(1).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                            case "7 ngày qua":
                                java.time.LocalDate sevenDaysAgo = java.time.LocalDate.now().minusDays(7);
                                return java.time.LocalDate.parse(joinDate).isAfter(sevenDaysAgo);
                            case "30 ngày qua":
                                java.time.LocalDate thirtyDaysAgo = java.time.LocalDate.now().minusDays(30);
                                return java.time.LocalDate.parse(joinDate).isAfter(thirtyDaysAgo);
                            default:
                                return true;
                        }
                    } else if (filterType.equals("Tên khách hàng")) {
                        return entry.getStringValue(1).toLowerCase().contains(searchText);
                    } else if (filterType.equals("Số điện thoại")) {
                        return entry.getStringValue(2).toLowerCase().contains(searchText);
                    }
                    return true;
                }
            };
            
            sorter.setRowFilter(rowFilter);
        });

        // Add search on type
        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { btnSearch.doClick(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { btnSearch.doClick(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { btnSearch.doClick(); }
        });

        // Filter type change event
        cboFilterType.addActionListener(e -> {
                String selectedFilter = (String) cboFilterType.getSelectedItem();
                if (selectedFilter.equals("Ngày đăng ký")) {
                    txtSearch.setVisible(false);
                    cboDateFilter.setVisible(true);
                } else {
                    txtSearch.setVisible(true);
                    cboDateFilter.setVisible(false);
                }
            btnSearch.doClick(); // Trigger search when filter type changes
        });

        // Date filter change event
        cboDateFilter.addActionListener(e -> btnSearch.doClick());

        // Add new customer button event
        btnAdd.addActionListener(e -> {
            JDialog dialog = new JDialog();
            dialog.setTitle("Thêm khách hàng mới");
            dialog.setModal(true);
            dialog.setLayout(new GridLayout(4, 2, 10, 10));
            dialog.setSize(400, 200);
            
            JTextField txtName = new JTextField();
            JTextField txtPhone = new JTextField();
            
            dialog.add(new JLabel("Họ tên:"));
            dialog.add(txtName);
            dialog.add(new JLabel("Số điện thoại:"));
            dialog.add(txtPhone);
            
            JButton btnSave = new JButton("Lưu");
            btnSave.addActionListener(ev -> {
                try {
                    String name = txtName.getText().trim();
                    String phone = txtPhone.getText().trim();
                    
                    if (name.isEmpty() || phone.isEmpty()) {
                        throw new Exception("Vui lòng điền đầy đủ thông tin");
                    }
                    
                    Customer customer = new Customer();
                    customer.setName(name);
                    customer.setPhone(phone);
                    customer.setJoinDate(java.time.LocalDate.now());
                    
                    customerService.saveCustomer(customer);
                    loadCustomersFromDatabase();
                    dialog.dispose();
                    
                    JOptionPane.showMessageDialog(this,
                        "Đã thêm khách hàng thành công!",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog,
                        "Lỗi: " + ex.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                }
            });
            
            JButton btnCancel = new JButton("Hủy");
            btnCancel.addActionListener(ev -> dialog.dispose());
            
            dialog.add(btnSave);
            dialog.add(btnCancel);
            
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });

        // Edit customer button event
        btnEdit.addActionListener(e -> {
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn khách hàng cần sửa",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Convert view index to model index if table is sorted
            int modelRow = customerTable.convertRowIndexToModel(selectedRow);
            
            try {
                Integer customerId = Integer.parseInt(tableModel.getValueAt(modelRow, 0).toString());
                Customer customer = customerService.getCustomerById(customerId);
                
                if (customer != null) {
                    JDialog dialog = new JDialog();
                    dialog.setTitle("Sửa thông tin khách hàng");
                    dialog.setModal(true);
                    dialog.setLayout(new GridLayout(4, 2, 10, 10));
                    dialog.setSize(400, 200);
                    
                    JTextField txtName = new JTextField(customer.getName());
                    JTextField txtPhone = new JTextField(customer.getPhone());
                    
                    dialog.add(new JLabel("Họ tên:"));
                    dialog.add(txtName);
                    dialog.add(new JLabel("Số điện thoại:"));
                    dialog.add(txtPhone);
                    
                    JButton btnSave = new JButton("Lưu");
                    btnSave.addActionListener(ev -> {
                        try {
                            String name = txtName.getText().trim();
                            String phone = txtPhone.getText().trim();
                            
                            if (name.isEmpty() || phone.isEmpty()) {
                                throw new Exception("Vui lòng điền đầy đủ thông tin");
                            }
                            
                            customer.setName(name);
                            customer.setPhone(phone);
                            
                            customerService.saveCustomer(customer);
                            loadCustomersFromDatabase();
                            dialog.dispose();
                            
                            JOptionPane.showMessageDialog(this,
                                "Đã cập nhật thông tin khách hàng thành công!",
                                "Thành công",
                                JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(dialog,
                                "Lỗi: " + ex.getMessage(),
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        }
                    });
                    
                    JButton btnCancel = new JButton("Hủy");
                    btnCancel.addActionListener(ev -> dialog.dispose());
                    
                    dialog.add(btnSave);
                    dialog.add(btnCancel);
                    
                    dialog.setLocationRelativeTo(this);
                    dialog.setVisible(true);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Lỗi khi lấy thông tin khách hàng: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        // Delete customer button event
        btnDelete.addActionListener(e -> {
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn khách hàng cần xóa",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Convert view index to model index if table is sorted
            int modelRow = customerTable.convertRowIndexToModel(selectedRow);
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa khách hàng này?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Integer customerId = Integer.parseInt(tableModel.getValueAt(modelRow, 0).toString());
                    Customer customer = customerService.getCustomerById(customerId);
                    if (customer != null) {
                        customerService.deleteCustomer(customerId);
                        loadCustomersFromDatabase(); // Refresh table
                        JOptionPane.showMessageDialog(this,
                            "Đã xóa khách hàng thành công!",
                            "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        throw new Exception("Không tìm thấy thông tin khách hàng");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                        "Lỗi khi xóa khách hàng: " + ex.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
} 