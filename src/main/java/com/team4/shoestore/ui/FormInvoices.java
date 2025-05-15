package com.team4.shoestore.ui;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.team4.shoestore.service.OrderService;
import com.team4.shoestore.model.Order;
import jakarta.annotation.PostConstruct;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;

@Component
public class FormInvoices extends JPanel {
    // Components
    private JPanel headerPanel;
    private JPanel searchPanel;
    private JTable invoiceTable;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;
    private JButton btnSearch;
    private JComboBox<String> cboFilterType;
    private JComboBox<String> cboDateFilter;
    private JButton btnConfirmPayment;
    private JButton btnViewDetails;
    
    @Autowired
    private OrderService orderService;

    public FormInvoices() {
        initComponents();
        initEvent();
    }
    @PostConstruct
    public void init(){
        LoadAllOrders();
    }



    public void LoadAllOrders() {
        try {
            System.out.println("Starting to load orders from database...");
            
            // Clear existing data
            tableModel.setRowCount(0);
            System.out.println("Cleared existing table data");
            
            // Get orders from database
            List<Order> orders = orderService.getAllOrders();
            System.out.println("Retrieved " + (orders != null ? orders.size() : 0) + " orders from database");
            
            // Add orders to table
            if (orders != null) {
                for (Order order : orders) {
                    System.out.println("Adding order to table: " + order.getOrderId());
                    tableModel.addRow(new Object[]{
                        order.getOrderId(),
                        order.getCustomer().getCustomerId(),
                        order.getOrderDate(),
                        order.getCustomer().getName(),
                        order.getTotalAmount(),
                        order.getPaymentMethod(),
                        order.isPaymentStatus()? "Đã thanh toán" : "Chưa thanh toán"
                    });
                }
            }
            System.out.println("Finished loading orders");
        } catch (Exception e) {
            System.err.println("Error loading orders: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error loading orders: " + e.getMessage(),
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
    }
    
    private void initHeader() {
        headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(30, 30, 30));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(30, 30, 30));
        
        JLabel titleLabel = new JLabel("Quản lý hóa đơn");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        // Search panel
        JPanel searchBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchBoxPanel.setBackground(new Color(30, 30, 30));
        searchBoxPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        // Filter type combobox
        String[] filterTypes = {"Tên khách hàng", "Số tiền", "Ngày"};
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
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBackground(isSelected ? new Color(70, 70, 70) : new Color(40, 40, 40));
                label.setForeground(Color.WHITE);
                label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                return label;
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
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBackground(isSelected ? new Color(70, 70, 70) : new Color(40, 40, 40));
                label.setForeground(Color.WHITE);
                label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                return label;
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
        btnSearch.setPreferredSize(new Dimension(100, 35));
        
        searchBoxPanel.add(cboFilterType);
        searchBoxPanel.add(Box.createHorizontalStrut(10));
        searchBoxPanel.add(txtSearch);
        searchBoxPanel.add(Box.createHorizontalStrut(10));
        searchBoxPanel.add(cboDateFilter);
        searchBoxPanel.add(Box.createHorizontalStrut(10));
        searchBoxPanel.add(btnSearch);
        
        headerPanel.add(titlePanel);
        headerPanel.add(searchBoxPanel);
    }
    
    private void initSearchPanel() {
        searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(new Color(30, 30, 30));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        
        // Table
        String[] columnNames = {"ID","Mã KH", "Ngày tạo", "Khách hàng", "Tổng tiền", "Phương thức thanh toán", "Trạng thái"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        invoiceTable = new JTable(tableModel);
        
        // Customize table appearance
        invoiceTable.setBackground(new Color(40, 40, 40));
        invoiceTable.setForeground(Color.WHITE);
        invoiceTable.setGridColor(new Color(60, 60, 60));
        invoiceTable.setSelectionBackground(new Color(70, 70, 70));
        invoiceTable.setSelectionForeground(Color.WHITE);
        invoiceTable.setRowHeight(35);
        invoiceTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        invoiceTable.setFillsViewportHeight(true);
        invoiceTable.setBorder(null);
        
        // Customize header
        invoiceTable.getTableHeader().setBackground(new Color(30, 30, 30));
        invoiceTable.getTableHeader().setForeground(Color.WHITE);
        invoiceTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        invoiceTable.getTableHeader().setBorder(null);
        invoiceTable.getTableHeader().setPreferredSize(new Dimension(invoiceTable.getTableHeader().getWidth(), 40));
        
        // Customize cell renderer
        invoiceTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            {
                setBackground(new Color(40, 40, 40));
                setForeground(Color.WHITE);
                setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 5));
                setFont(new Font("Segoe UI", Font.PLAIN, 13));
            }
        });
        
 
        JScrollPane scrollPane = new JScrollPane(invoiceTable);
        scrollPane.setBackground(new Color(40, 40, 40));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60)));
        
        // Customize scrollbar
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setPreferredSize(new Dimension(10, 0));
        verticalScrollBar.setBackground(new Color(30, 30, 30));
        verticalScrollBar.setForeground(new Color(60, 60, 60));
        
        searchPanel.add(scrollPane, BorderLayout.CENTER);
    }
    
    private void initButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(new Color(30, 30, 30));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        btnConfirmPayment = createButton("Xác nhận thanh toán");
        btnViewDetails = createButton("Xem chi tiết");
        
        buttonPanel.add(btnConfirmPayment);
        buttonPanel.add(btnViewDetails);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(64, 64, 64));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        
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
        // Add event listener for filter type combobox
        cboFilterType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedFilter = (String) cboFilterType.getSelectedItem();
                if (selectedFilter.equals("Ngày")) {
                    txtSearch.setVisible(false);
                    cboDateFilter.setVisible(true);
                } else {
                    txtSearch.setVisible(true);
                    cboDateFilter.setVisible(false);
                }
            }
        });
        
        // Add event listener for confirm payment button
        btnConfirmPayment.addActionListener(e -> {
            int selectedRow = invoiceTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn hóa đơn cần xác nhận thanh toán",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String status = (String) invoiceTable.getValueAt(selectedRow, 6);
            if (!status.equals("Chờ xác nhận")) {
                JOptionPane.showMessageDialog(this,
                    "Chỉ có thể xác nhận thanh toán cho hóa đơn đang chờ xác nhận",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xác nhận thanh toán cho hóa đơn này?",
                "Xác nhận thanh toán",
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                
                tableModel.setValueAt("Đã thanh toán", selectedRow, 6);
            }
        });
        
        
    }
}
