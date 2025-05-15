package com.team4.shoestore.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardForm extends JPanel {
    // Colors
    private static final Color BACKGROUND_COLOR = new Color(30, 30, 30);
    private static final Color CARD_BACKGROUND = new Color(40, 40, 40);
    private static final Color TEXT_COLOR = new Color(221, 221, 221);
    private static final Color REVENUE_TODAY_COLOR = new Color(46, 204, 113); // #2ecc71
    private static final Color ORDERS_TODAY_COLOR = new Color(52, 152, 219);  // #3498db
    private static final Color REVENUE_MONTH_COLOR = new Color(241, 196, 15); // #f1c40f
    private static final Color ORDERS_MONTH_COLOR = new Color(155, 89, 182);  // #9b59b6
    
    // Components
    private JPanel infoCardsPanel;
    private JPanel tablePanel;
    private JTable revenueTable;
    private DefaultTableModel tableModel;
    private JTextField txtSearchDate;
    private JButton btnSearch;
    
    public DashboardForm() {
        initComponents();
        initEvent();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        
        // Initialize info cards
        initInfoCards();
        
        // Initialize table
        initTable();
        
        // Add components to main panel
        add(infoCardsPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
    }
    
    private void initInfoCards() {
        infoCardsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        infoCardsPanel.setBackground(BACKGROUND_COLOR);
        
        // Revenue Today Card
        JPanel revenueTodayCard = createInfoCard(
            "💰 Doanh thu hôm nay",
            "1.200.000 VNĐ",
            REVENUE_TODAY_COLOR
        );
        
        // Orders Today Card
        JPanel ordersTodayCard = createInfoCard(
            "🧾 Đơn hàng hôm nay",
            "15 đơn",
            ORDERS_TODAY_COLOR
        );
        
        // Revenue Month Card
        JPanel revenueMonthCard = createInfoCard(
            "💰 Doanh thu tháng",
            "30.500.000 VNĐ",
            REVENUE_MONTH_COLOR
        );
        
        // Orders Month Card
        JPanel ordersMonthCard = createInfoCard(
            "📦 Đơn hàng tháng",
            "220 đơn",
            ORDERS_MONTH_COLOR
        );
        
        infoCardsPanel.add(revenueTodayCard);
        infoCardsPanel.add(ordersTodayCard);
        infoCardsPanel.add(revenueMonthCard);
        infoCardsPanel.add(ordersMonthCard);
    }
    
    private JPanel createInfoCard(String title, String value, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_BACKGROUND);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 1),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(TEXT_COLOR);
        
        // Value
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(color);
        
        // Add components
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(valueLabel);
        
        // Add hover effect
        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(50, 50, 50));
            }
            public void mouseExited(MouseEvent e) {
                card.setBackground(CARD_BACKGROUND);
            }
        });
        
        return card;
    }
    
    private void initTable() {
        tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(BACKGROUND_COLOR);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(BACKGROUND_COLOR);
        
        JLabel lblSearch = new JLabel("Tìm theo ngày:");
        lblSearch.setForeground(TEXT_COLOR);
        lblSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        txtSearchDate = new JTextField(10);
        txtSearchDate.setPreferredSize(new Dimension(150, 30));
        txtSearchDate.setBackground(CARD_BACKGROUND);
        txtSearchDate.setForeground(TEXT_COLOR);
        txtSearchDate.setCaretColor(TEXT_COLOR);
        txtSearchDate.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSearchDate.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        btnSearch = new JButton("Tìm kiếm");
        btnSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnSearch.setForeground(TEXT_COLOR);
        btnSearch.setBackground(new Color(64, 64, 64));
        btnSearch.setBorderPainted(false);
        btnSearch.setFocusPainted(false);
        
        // Add hover effect for search button
        btnSearch.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnSearch.setBackground(new Color(80, 80, 80));
            }
            public void mouseExited(MouseEvent e) {
                btnSearch.setBackground(new Color(64, 64, 64));
            }
        });
        
        searchPanel.add(lblSearch);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(txtSearchDate);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(btnSearch);
        
        // Table
        String[] columnNames = {"Ngày", "Số đơn hàng", "Tổng doanh thu", "Tổng số đơn"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        revenueTable = new JTable(tableModel);
        
        // Customize table appearance
        revenueTable.setBackground(CARD_BACKGROUND);
        revenueTable.setForeground(TEXT_COLOR);
        revenueTable.setGridColor(new Color(60, 60, 60));
        revenueTable.setSelectionBackground(new Color(70, 70, 70));
        revenueTable.setSelectionForeground(TEXT_COLOR);
        revenueTable.setRowHeight(35);
        revenueTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        revenueTable.setFillsViewportHeight(true);
        revenueTable.setBorder(null);
        
        // Customize header
        revenueTable.getTableHeader().setBackground(BACKGROUND_COLOR);
        revenueTable.getTableHeader().setForeground(TEXT_COLOR);
        revenueTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        revenueTable.getTableHeader().setBorder(null);
        revenueTable.getTableHeader().setPreferredSize(new Dimension(revenueTable.getTableHeader().getWidth(), 40));
        
        // Customize cell renderer
        revenueTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            {
                setBackground(CARD_BACKGROUND);
                setForeground(TEXT_COLOR);
                setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 5));
                setFont(new Font("Segoe UI", Font.PLAIN, 14));
            }
        });
        
        // Add sample data
        addSampleData();
        
        JScrollPane scrollPane = new JScrollPane(revenueTable);
        scrollPane.setBackground(CARD_BACKGROUND);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60)));
        
        // Customize scrollbar
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setPreferredSize(new Dimension(10, 0));
        verticalScrollBar.setBackground(BACKGROUND_COLOR);
        verticalScrollBar.setForeground(new Color(60, 60, 60));
        
        tablePanel.add(searchPanel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
    }
    
    private void addSampleData() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dateFormat.format(new Date());
        
        Object[][] data = {
            {currentDate, "15", "1.200.000 VNĐ", "45"},
            {"01/01/2024", "12", "950.000 VNĐ", "38"},
            {"02/01/2024", "18", "1.500.000 VNĐ", "52"},
            {"03/01/2024", "20", "1.800.000 VNĐ", "65"},
            {"04/01/2024", "16", "1.300.000 VNĐ", "48"},
            {"05/01/2024", "14", "1.100.000 VNĐ", "42"},
            {"06/01/2024", "22", "2.000.000 VNĐ", "70"},
            {"07/01/2024", "19", "1.700.000 VNĐ", "58"}
        };
        
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }
    
    private void initEvent() {
        btnSearch.addActionListener(ee -> {
            String searchDate = txtSearchDate.getText().trim();
            if (searchDate.isEmpty()) {
                // If search is empty, show all data
                tableModel.setRowCount(0);
                addSampleData();
                return;
            }
            
            // Filter table data based on search date
            tableModel.setRowCount(0);
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String date = (String) tableModel.getValueAt(i, 0);
                if (date.contains(searchDate)) {
                    Object[] row = new Object[4];
                    for (int j = 0; j < 4; j++) {
                        row[j] = tableModel.getValueAt(i, j);
                    }
                    tableModel.addRow(row);
                }
            }
        });
    }
    
} 