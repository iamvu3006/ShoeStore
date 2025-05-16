package com.team4.shoestore.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.toedter.calendar.JDateChooser;
import java.util.Calendar;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.team4.shoestore.service.OrderService;
import com.team4.shoestore.model.Order;
import java.util.List;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.LocalDate;
import java.math.BigDecimal;
import jakarta.annotation.PostConstruct;

@Component
public class DashboardForm extends JPanel {
    // Colors
    private static final Color BACKGROUND_COLOR = new Color(30, 30, 30);
    private static final Color CARD_BACKGROUND = new Color(40, 40, 40);
    private static final Color TEXT_COLOR = new Color(221, 221, 221);
    private static final Color REVENUE_TODAY_COLOR = new Color(46, 204, 113); // #2ecc71
    private static final Color ORDERS_TODAY_COLOR = new Color(52, 152, 219);  // #3498db
    private static final Color REVENUE_MONTH_COLOR = new Color(241, 196, 15); // #f1c40f
    private static final Color ORDERS_MONTH_COLOR = new Color(155, 89, 182);  // #9b59b6
    
    @Autowired
    private OrderService orderService;
    
    // Components
    private JPanel infoCardsPanel;
    private JPanel tablePanel;
    private JTable revenueTable;
    private DefaultTableModel tableModel;
    private JDateChooser dateChooser;
    private JButton btnSearch;
    private JButton btnReset;
    
    // Labels for dynamic update
    private JLabel revenueTodayValue;
    private JLabel ordersTodayValue;
    private JLabel revenueMonthValue;
    private JLabel ordersMonthValue;
    private boolean initialized = false;
    
    public DashboardForm() {
        // Empty constructor for Spring
    }

    @PostConstruct
    public void init() {
        // Do nothing here - initialization will be done in setupAndDisplay
    }

    public void setupAndDisplay() {
        if (!initialized) {
            initialized = true;
            initComponents();
            initEvent();
            // Move updateDashboardData after initialization
            SwingUtilities.invokeLater(() -> {
                updateDashboardData();
            });
        } else {
            // If already initialized, just update the data
            SwingUtilities.invokeLater(() -> {
                updateDashboardData();
            });
        }
    }

    private void updateDashboardData() {
        if (!initialized || tableModel == null) {
            return;
        }

        try {
            // Get today's data
            LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
            List<Order> todayOrders = orderService.getOrdersByDate(today);
            BigDecimal todayRevenue = calculateTotalRevenue(todayOrders);
            
            // Get this month's data
            LocalDateTime startOfMonth = today.withDayOfMonth(1);
            List<Order> monthOrders = orderService.getOrdersBetweenDates(startOfMonth, today.plusDays(1));
            BigDecimal monthRevenue = calculateTotalRevenue(monthOrders);
            
            // Update info cards
            SwingUtilities.invokeLater(() -> {
                revenueTodayValue.setText(String.format("%,.0f VNĐ", todayRevenue));
                ordersTodayValue.setText(todayOrders.size() + " đơn");
                revenueMonthValue.setText(String.format("%,.0f VNĐ", monthRevenue));
                ordersMonthValue.setText(monthOrders.size() + " đơn");
            });
            
            // Update table data
            updateTableData();
            
        } catch (Exception e) {
            e.printStackTrace();
            if (isDisplayable()) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this,
                        "Lỗi khi tải dữ liệu: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                });
            }
        }
    }
    
    private BigDecimal calculateTotalRevenue(List<Order> orders) {
        return orders.stream()
            .map(Order::getTotalAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    private void updateTableData() {
        try {
            tableModel.setRowCount(0);
            
            // Get orders for the last 7 days
            LocalDateTime endDate = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
            LocalDateTime startDate = endDate.minusDays(7);
            List<Order> orders = orderService.getOrdersBetweenDates(startDate, endDate);
            
            // Group orders by date and add to table
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            LocalDateTime currentDate = startDate;
            
            while (!currentDate.isAfter(endDate)) {
                final LocalDateTime date = currentDate;
                List<Order> dateOrders = orders.stream()
                    .filter(order -> order.getOrderDate().toLocalDate().equals(date.toLocalDate()))
                    .toList();
                
                BigDecimal dateRevenue = calculateTotalRevenue(dateOrders);
                
                Object[] row = new Object[]{
                    dateFormat.format(Date.from(date.atZone(ZoneId.systemDefault()).toInstant())),
                    String.valueOf(dateOrders.size()),
                    String.format("%,.0f VNĐ", dateRevenue),
                    String.valueOf(dateOrders.size())
                };
                
                tableModel.addRow(row);
                currentDate = currentDate.plusDays(1);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Lỗi khi tải dữ liệu bảng: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void initComponents() {
        // Set layout and background
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Initialize info cards first
        initInfoCards();
        
        // Initialize table panel
        initTable();
        
        // Add components to main panel with proper spacing
        JPanel contentPanel = new JPanel(new BorderLayout(0, 20));  // 20px vertical gap
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.add(infoCardsPanel, BorderLayout.NORTH);
        contentPanel.add(tablePanel, BorderLayout.CENTER);
        
        // Add content panel to main panel
        add(contentPanel, BorderLayout.CENTER);
        
        // Ensure all components are properly initialized
        revalidate();
        repaint();
    }
    
    private void initInfoCards() {
        infoCardsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        infoCardsPanel.setBackground(BACKGROUND_COLOR);
        
        // Revenue Today Card
        JPanel revenueTodayCard = createInfoCard(
            "💰 Doanh thu hôm nay",
            "0 VNĐ",
            REVENUE_TODAY_COLOR
        );
        revenueTodayValue = (JLabel) ((JPanel) revenueTodayCard.getComponent(2)).getComponent(0);
        
        // Orders Today Card
        JPanel ordersTodayCard = createInfoCard(
            "🧾 Đơn hàng hôm nay",
            "0 đơn",
            ORDERS_TODAY_COLOR
        );
        ordersTodayValue = (JLabel) ((JPanel) ordersTodayCard.getComponent(2)).getComponent(0);
        
        // Revenue Month Card
        JPanel revenueMonthCard = createInfoCard(
            "💰 Doanh thu tháng",
            "0 VNĐ",
            REVENUE_MONTH_COLOR
        );
        revenueMonthValue = (JLabel) ((JPanel) revenueMonthCard.getComponent(2)).getComponent(0);
        
        // Orders Month Card
        JPanel ordersMonthCard = createInfoCard(
            "📦 Đơn hàng tháng",
            "0 đơn",
            ORDERS_MONTH_COLOR
        );
        ordersMonthValue = (JLabel) ((JPanel) ordersMonthCard.getComponent(2)).getComponent(0);
        
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
        titleLabel.setAlignmentX(0.5f);  // Center alignment
        
        // Value
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(color);
        valueLabel.setAlignmentX(0.5f);  // Center alignment
        
        // Create panel for value label
        JPanel valuePanel = new JPanel();
        valuePanel.setLayout(new BoxLayout(valuePanel, BoxLayout.X_AXIS));
        valuePanel.setBackground(CARD_BACKGROUND);
        valuePanel.add(valueLabel);
        
        // Add components
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(valuePanel);
        
        // Add hover effect
        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(50, 50, 50));
                valuePanel.setBackground(new Color(50, 50, 50));
            }
            public void mouseExited(MouseEvent e) {
                card.setBackground(CARD_BACKGROUND);
                valuePanel.setBackground(CARD_BACKGROUND);
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
        
        // Initialize date chooser
        dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(150, 30));
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setBackground(CARD_BACKGROUND);
        dateChooser.setForeground(TEXT_COLOR);
        
        // Initialize table model first
        String[] columnNames = {"Ngày", "Số đơn hàng", "Tổng doanh thu", "Tổng số đơn"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Initialize table with model
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
        
        // Create buttons
        btnSearch = new JButton("Tìm kiếm");
        btnSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnSearch.setForeground(TEXT_COLOR);
        btnSearch.setBackground(new Color(64, 64, 64));
        btnSearch.setBorderPainted(false);
        btnSearch.setFocusPainted(false);
        
        btnReset = new JButton("Đặt lại");
        btnReset.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnReset.setForeground(TEXT_COLOR);
        btnReset.setBackground(new Color(64, 64, 64));
        btnReset.setBorderPainted(false);
        btnReset.setFocusPainted(false);
        
        // Add components to search panel
        searchPanel.add(lblSearch);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(dateChooser);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(btnSearch);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(btnReset);
        
        // Create scroll pane
        JScrollPane scrollPane = new JScrollPane(revenueTable);
        scrollPane.setBackground(CARD_BACKGROUND);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60)));
        
        // Add components to table panel
        tablePanel.add(searchPanel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
    }
    
    private void addSampleData() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        // Get dates for the last 7 days
        Calendar cal = Calendar.getInstance();
        Object[][] data = new Object[8][4];
        
        for (int i = 0; i < 8; i++) {
            String date = dateFormat.format(cal.getTime());
            data[i] = new Object[]{
                date,
                String.valueOf(15 + i),
                String.format("%,.0f VNĐ", 1200000.0 + (i * 100000)),
                String.valueOf(45 + (i * 3))
            };
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }
    
    private void initEvent() {
        btnSearch.addActionListener(e -> {
            Date selectedDate = dateChooser.getDate();
            if (selectedDate == null) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn ngày cần tìm!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                LocalDateTime searchDate = selectedDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
                    .withHour(0).withMinute(0).withSecond(0).withNano(0);
                
                List<Order> orders = orderService.getOrdersByDate(searchDate);
                
                tableModel.setRowCount(0);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                BigDecimal totalRevenue = calculateTotalRevenue(orders);
                
                if (orders.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                        "Không tìm thấy dữ liệu cho ngày " + sdf.format(selectedDate),
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                    updateTableData(); // Show all data again
                } else {
                    Object[] row = new Object[]{
                        sdf.format(selectedDate),
                        String.valueOf(orders.size()),
                        String.format("%,.0f VNĐ", totalRevenue),
                        String.valueOf(orders.size())
                    };
                    tableModel.addRow(row);
                }
                
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                    "Lỗi khi tìm kiếm: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnReset.addActionListener(e -> {
            dateChooser.setDate(null);
            updateTableData();
        });
        
        // Add window listener to refresh data when panel becomes visible
        addHierarchyListener(new HierarchyListener() {
            @Override
            public void hierarchyChanged(HierarchyEvent e) {
                if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
                    if (isShowing()) {
                        updateDashboardData();
                    }
                }
            }
        });
    }
} 