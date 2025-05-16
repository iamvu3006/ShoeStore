package com.team4.shoestore.ui;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import com.team4.shoestore.service.ShoeService;
import com.team4.shoestore.service.OrderService;
import com.team4.shoestore.service.VariantService;
import com.team4.shoestore.service.CustomerService;
import com.team4.shoestore.model.Shoe;
import com.team4.shoestore.model.Order;
import com.team4.shoestore.model.OrderItem;
import com.team4.shoestore.model.Variant;
import com.team4.shoestore.model.Customer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.util.List;
import java.net.URL;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.time.LocalDateTime;
import com.team4.shoestore.model.Order.PaymentMethod;


@Component
public class FormMenu extends JPanel {
    // Components
    private JPanel mainContentPanel;
    private JPanel cartPanel;
    private JTable cartTable;
    private DefaultTableModel cartTableModel;
    private JLabel totalPriceLabel;
    private JPanel productsPanel;
    private JComboBox<String> categoryComboBox;
    private List<Product> products;
    

    @Autowired
    private ShoeService shoeService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private VariantService variantService;

    @Autowired
    private CustomerService customerService;

    // Product class to store product information
    private class Product {
        String name;
        URL imagePath;
        String category;
        BigDecimal price;
        Integer shoeId; // Thêm ID để lưu trữ
        
        Product(String name, URL imagePath, String category, BigDecimal price, Integer shoeId) {
            this.name = name;
            this.imagePath = imagePath;
            this.category = category;
            this.price = price;
            this.shoeId = shoeId;
        }
    }
    
    // Thêm biến để theo dõi tổng tiền
    private BigDecimal totalPrice = BigDecimal.ZERO;
    
    private void updateTotalPrice() {
        totalPrice = BigDecimal.ZERO;
        for (int i = 0; i < cartTableModel.getRowCount(); i++) {
            BigDecimal price = (BigDecimal) cartTableModel.getValueAt(i, 3);
            Integer quantity = (Integer) cartTableModel.getValueAt(i, 2);
            totalPrice = totalPrice.add(price.multiply(new BigDecimal(quantity)));
        }
        totalPriceLabel.setText("Tổng tiền: " + String.format("%,.0f VNĐ", totalPrice));
    }
    
    public FormMenu() {
        products = new ArrayList<>();
        initComponents();
        initEvent();
    }

    @PostConstruct
    public void init() {
        LoadMenu();
    }

    public void LoadMenu() {
        try {
            System.out.println("Starting to load menu items...");
            
            // Clear existing products
            products.clear();
            productsPanel.removeAll();
            System.out.println("Cleared existing products and panel");
            
            // Get shoes from database
            List<Shoe> shoes = shoeService.getAllShoes();
            System.out.println("Retrieved " + (shoes != null ? shoes.size() : 0) + " shoes from database");
            
            // Add shoes to products list
            if (shoes != null) {
                for (Shoe shoe : shoes) {
                    try {
                        System.out.println("Processing shoe: " + shoe.getName());
                        // Get image resource
                        URL imageUrl = getClass().getResource(shoe.getImageUrl());
                        if (imageUrl == null) {
                            System.out.println("Warning: Image not found for shoe: " + shoe.getName() + ", URL: " + shoe.getImageUrl());
                            continue;
                        }
                        
                        // Create and add product with ID
                        Product product = new Product(
                            shoe.getName(), 
                            imageUrl, 
                            shoe.getCategory(), 
                            shoe.getPrice(),
                            shoe.getShoeId()
                        );
                        products.add(product);
                        System.out.println("Added product to list: " + product.name);
                        
                    } catch (Exception e) {
                        System.err.println("Error adding shoe to menu: " + shoe.getName());
                        e.printStackTrace();
                    }
                }
            }
            
            // Display products
            displayProducts("Tất cả");
            System.out.println("Finished loading menu");
            
        } catch (Exception e) {
            System.err.println("Error loading menu: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error loading menu: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(33, 33, 33)); // Dark background
        
        
        // Initialize main content
        initMainContent();
        
        // Initialize cart panel
        initCartPanel();
        
        // Add panels to main panel
        add(mainContentPanel, BorderLayout.CENTER);
        add(cartPanel, BorderLayout.EAST);
    }
    
    
    private void initMainContent() {
        mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBackground(new Color(33, 33, 33)); // Dark background
        
        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(33, 33, 33));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Yan Sneaker !!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Category filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        filterPanel.setBackground(new Color(33, 33, 33));
        
        JLabel categoryLabel = new JLabel("Thể loại:");
        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        categoryLabel.setForeground(Color.WHITE);
        
        String[] categories = {"Tất cả", "Thể thao", "Chạy bộ", "Thời trang"};
        categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.setPreferredSize(new Dimension(150, 30));
        categoryComboBox.setBackground(new Color(45, 45, 45));
        categoryComboBox.setForeground(Color.WHITE);
        categoryComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        
        filterPanel.add(categoryLabel);
        filterPanel.add(Box.createHorizontalStrut(10));
        filterPanel.add(categoryComboBox);
        
        headerPanel.add(filterPanel, BorderLayout.EAST);
        
        // Products grid panel
        productsPanel = new JPanel();
        productsPanel.setLayout(new GridLayout(0, 3, 20, 20));
        productsPanel.setBackground(new Color(33, 33, 33));
        productsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create custom scroll pane
        JScrollPane scrollPane = new JScrollPane(productsPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);
        scrollPane.setBackground(new Color(33, 33, 33));
        
        // Customize scrollbar
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setPreferredSize(new Dimension(10, 0));
        verticalScrollBar.setBackground(new Color(35, 35, 35));
        verticalScrollBar.setForeground(new Color(50, 50, 50));
        
        // Customize scrollbar appearance
        UIManager.put("ScrollBar.thumb", new Color(50, 50, 50));
        UIManager.put("ScrollBar.track", new Color(35, 35, 35));
        UIManager.put("ScrollBar.width", 10);
        verticalScrollBar.updateUI();
        
        // Add panels to main content
        mainContentPanel.add(headerPanel, BorderLayout.NORTH);
        mainContentPanel.add(scrollPane, BorderLayout.CENTER);
    }
    
    private void displayProducts(String category) {
        System.out.println("Displaying products for category: " + category);
        productsPanel.removeAll();
        
        // Lọc sản phẩm theo category
        List<Product> filteredProducts = new ArrayList<>();
        if (category.equals("Tất cả")) {
            filteredProducts.addAll(products);
        } else {
            for (Product product : products) {
                if (product.category.equals(category)) {
                    filteredProducts.add(product);
                }
            }
        }
        
        // Hiển thị thông báo nếu không có sản phẩm
        if (filteredProducts.isEmpty()) {
            JLabel noProductsLabel = new JLabel("Không có sản phẩm nào trong danh mục này");
            noProductsLabel.setFont(new Font("Arial", Font.BOLD, 16));
            noProductsLabel.setForeground(Color.WHITE);
            noProductsLabel.setHorizontalAlignment(JLabel.CENTER);
            productsPanel.add(noProductsLabel);
        } else {
            // Hiển thị các sản phẩm đã lọc
            for (Product product : filteredProducts) {
                JPanel productCard = createProductCard(product.name, product.imagePath, product.price);
                productsPanel.add(productCard);
            }
        }
        
        // Cập nhật giao diện
        productsPanel.revalidate();
        productsPanel.repaint();
        
        // Log số lượng sản phẩm được hiển thị
        System.out.println("Displayed " + filteredProducts.size() + " products for category: " + category);
    }
    
    private JPanel createProductCard(String name, URL imagePath, BigDecimal price) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(45, 45, 45)); // Dark card background
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Product image container
        JPanel imageContainer = new JPanel();
        imageContainer.setPreferredSize(new Dimension(200, 200));
        imageContainer.setBackground(new Color(35, 35, 35));
        imageContainer.setLayout(new BorderLayout());
        
        // Product image
        JLabel imageLabel = new JLabel();
        try {
            ImageIcon imageIcon = new ImageIcon(imagePath);
            Image image = imageIcon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(image));
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imageLabel.setVerticalAlignment(JLabel.CENTER);
        } catch (Exception e) {
            System.err.println("Error loading image for product: " + name);
            e.printStackTrace();
            imageLabel.setText("No Image");
            imageLabel.setForeground(Color.WHITE);
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imageLabel.setVerticalAlignment(JLabel.CENTER);
        }
        imageContainer.add(imageLabel, BorderLayout.CENTER);
        
        // Product name
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(0.5f);
        
        // Product price
        JLabel priceLabel = new JLabel(price.toString() + " VNĐ");
        priceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        priceLabel.setForeground(new Color(255, 215, 0)); // Gold color for price
        priceLabel.setAlignmentX(0.5f);
        
        // Add to cart button
        JButton addToCartButton = new JButton("Thêm vào giỏ");
        addToCartButton.setAlignmentX(0.5f);
        addToCartButton.setBackground(new Color(60, 60, 60));
        addToCartButton.setForeground(Color.WHITE);
        addToCartButton.setBorderPainted(false);
        addToCartButton.setFocusPainted(false);
        addToCartButton.setMaximumSize(new Dimension(150, 35));
        
        // Add hover effect and click event for add to cart button
        addToCartButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                addToCartButton.setBackground(new Color(80, 80, 80));
            }
            public void mouseExited(MouseEvent e) {
                addToCartButton.setBackground(new Color(60, 60, 60));
            }
        });

        // Add to cart action
        addToCartButton.addActionListener(e -> {
            // Find the product in the products list
            Product selectedProduct = products.stream()
                .filter(p -> p.name.equals(name))
                .findFirst()
                .orElse(null);

            if (selectedProduct != null) {
                // Check if product already exists in cart
                boolean found = false;
                for (int i = 0; i < cartTableModel.getRowCount(); i++) {
                    if (cartTableModel.getValueAt(i, 1).equals(name)) {
                        // Increment quantity
                        Integer currentQty = (Integer) cartTableModel.getValueAt(i, 2);
                        cartTableModel.setValueAt(currentQty + 1, i, 2);
                        found = true;
                        break;
                    }
                }

                // If product not in cart, add new row
                if (!found) {
                    cartTableModel.addRow(new Object[]{
                        selectedProduct.shoeId,
                        name,
                        1, // Initial quantity
                        price
                    });
                }

                updateTotalPrice();
                JOptionPane.showMessageDialog(this,
                    "Đã thêm " + name + " vào giỏ hàng!",
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        // Add components to card
        card.add(imageContainer);
        card.add(Box.createVerticalStrut(15));
        card.add(nameLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(priceLabel);
        card.add(Box.createVerticalStrut(15));
        card.add(addToCartButton);
        
        return card;
    }
    
    private void initCartPanel() {
        cartPanel = new JPanel(new BorderLayout());
        cartPanel.setPreferredSize(new Dimension(300, 0));
        cartPanel.setBackground(new Color(45, 45, 45)); // Dark background
        cartPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Cart title
        JLabel cartTitle = new JLabel("Giỏ hàng");
        cartTitle.setFont(new Font("Arial", Font.BOLD, 18));
        cartTitle.setForeground(Color.WHITE);
        cartPanel.add(cartTitle, BorderLayout.NORTH);
        
        // Cart table
        String[] columnNames = {"ID", "Tên", "Số lượng", "Giá"};
        cartTableModel = new DefaultTableModel(columnNames, 0);
        cartTable = new JTable(cartTableModel);
        cartTable.setBackground(new Color(45, 45, 45));
        cartTable.setForeground(Color.WHITE);
        cartTable.setGridColor(new Color(60, 60, 60));
        cartTable.getTableHeader().setBackground(new Color(60, 60, 60));
        cartTable.getTableHeader().setForeground(Color.WHITE);
        cartTable.setFillsViewportHeight(true);
        
        JScrollPane tableScrollPane = new JScrollPane(cartTable);
        tableScrollPane.getViewport().setBackground(new Color(45, 45, 45));
        cartPanel.add(tableScrollPane, BorderLayout.CENTER);
        
        // Total price and buttons panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(45, 45, 45));
        
        totalPriceLabel = new JLabel("Tổng tiền: 0 VNĐ");
        totalPriceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalPriceLabel.setForeground(Color.WHITE);
        bottomPanel.add(totalPriceLabel, BorderLayout.NORTH);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(45, 45, 45));
        
        JButton clearButton = new JButton("Xóa giỏ hàng");
        JButton confirmButton = new JButton("Xác nhận");
        
        // Clear cart action
        clearButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa toàn bộ giỏ hàng?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                cartTableModel.setRowCount(0);
                updateTotalPrice();
                JOptionPane.showMessageDialog(this,
                    "Đã xóa giỏ hàng!",
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Confirm order action
        confirmButton.addActionListener(e -> {
            if (cartTableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,
                    "Giỏ hàng đang trống!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            showOrderDialog();
        });
        
        // Style buttons
        for (JButton button : new JButton[]{clearButton, confirmButton}) {
            button.setBackground(new Color(60, 60, 60));
            button.setForeground(Color.WHITE);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            
            // Add hover effect
            button.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(new Color(80, 80, 80));
                }
                public void mouseExited(MouseEvent e) {
                    button.setBackground(new Color(60, 60, 60));
                }
            });
        }
        
        buttonPanel.add(clearButton);
        buttonPanel.add(confirmButton);
        
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        cartPanel.add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void initEvent() {
        // Xử lý sự kiện thay đổi category
        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) categoryComboBox.getSelectedItem();
                System.out.println("Category selected: " + selectedCategory);
                
                // Hiển thị loading message
                productsPanel.removeAll();
                JLabel loadingLabel = new JLabel("Đang tải sản phẩm...");
                loadingLabel.setFont(new Font("Arial", Font.BOLD, 16));
                loadingLabel.setForeground(Color.WHITE);
                loadingLabel.setHorizontalAlignment(JLabel.CENTER);
                productsPanel.add(loadingLabel);
                productsPanel.revalidate();
                productsPanel.repaint();
                
                // Sử dụng SwingWorker để tải sản phẩm trong background
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        // Tải lại danh sách sản phẩm nếu cần
                        if (products.isEmpty()) {
                            LoadMenu();
                        }
                        return null;
                    }
                    
                    @Override
                    protected void done() {
                        // Hiển thị sản phẩm sau khi tải xong
                        displayProducts(selectedCategory);
                    }
                };
                worker.execute();
            }
        });
        
        // Add window listener to refresh display when window becomes visible
        addHierarchyListener(new HierarchyListener() {
            @Override
            public void hierarchyChanged(HierarchyEvent e) {
                if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
                    if (isShowing()) {
                        System.out.println("FormMenu became visible, refreshing display");
                        LoadMenu();
                    }
                }
            }
        });
    }

    private void showOrderDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Thông tin đặt hàng");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Thêm các trường nhập liệu
        JTextField phoneField = new JTextField();
        String[] paymentMethods = {"Tiền mặt", "Thẻ"};
        JComboBox<String> paymentMethodCombo = new JComboBox<>(paymentMethods);

        inputPanel.add(new JLabel("Số điện thoại khách hàng:"));
        inputPanel.add(phoneField);
        inputPanel.add(new JLabel("Phương thức thanh toán:"));
        inputPanel.add(paymentMethodCombo);

        JPanel dialogButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton confirmDialogButton = new JButton("Xác nhận");
        JButton cancelDialogButton = new JButton("Hủy");

        confirmDialogButton.addActionListener(dialogEvent -> {
            String phone = phoneField.getText().trim();
            String selectedPayment = (String) paymentMethodCombo.getSelectedItem();

            if (phone.isEmpty()) {
                JOptionPane.showMessageDialog(dialog,
                    "Vui lòng nhập số điện thoại khách hàng!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Tìm khách hàng theo số điện thoại
                List<Customer> customers = customerService.findCustomersByPhone(phone);
                if (customers.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog,
                        "Không tìm thấy khách hàng với số điện thoại này!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Customer customer = customers.get(0);

                // Create order items list
                List<OrderItem> orderItems = new ArrayList<>();
                for (int i = 0; i < cartTableModel.getRowCount(); i++) {
                    OrderItem item = new OrderItem();
                    Integer shoeId = (Integer) cartTableModel.getValueAt(i, 0);
                    Variant variant = variantService.getDefaultVariantForShoe(shoeId);
                    if (variant == null) {
                        throw new RuntimeException("Không tìm thấy biến thể cho sản phẩm " + cartTableModel.getValueAt(i, 1));
                    }
                    item.setVariant(variant);
                    item.setQuantity((Integer) cartTableModel.getValueAt(i, 2));
                    item.setPrice((BigDecimal) cartTableModel.getValueAt(i, 3));
                    orderItems.add(item);
                }

                // Create new order
                Order order = new Order();
                order.setCustomer(customer);
                order.setOrderDate(LocalDateTime.now());
                order.setTotalAmount(totalPrice);
                order.setPaymentMethod(selectedPayment.equals("Tiền mặt") ? PaymentMethod.cash : PaymentMethod.card);
                order.setPaymentStatus(false); // Chưa thanh toán

                // Save order and its items
                Order savedOrder = orderService.createOrderWithItems(order, orderItems);

                dialog.dispose();
                cartTableModel.setRowCount(0);
                updateTotalPrice();
                JOptionPane.showMessageDialog(this,
                    "Đặt hàng thành công! Mã hóa đơn: " + savedOrder.getOrderId(),
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog,
                    "Lỗi khi đặt hàng: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelDialogButton.addActionListener(dialogEvent -> dialog.dispose());

        dialogButtonPanel.add(confirmDialogButton);
        dialogButtonPanel.add(cancelDialogButton);

        dialog.add(inputPanel, BorderLayout.CENTER);
        dialog.add(dialogButtonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}