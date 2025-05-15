package com.team4.shoestore.ui;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import com.team4.shoestore.service.ShoeService;
import com.team4.shoestore.model.Shoe;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.util.List;
import java.net.URL;
import java.math.BigDecimal;
import java.util.ArrayList;


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

    // Product class to store product information
    private class Product {
        String name;
        URL imagePath;
        String category;
        BigDecimal price;
        
        Product(String name, URL imagePath, String category, BigDecimal price) {
            this.name = name;
            this.imagePath = imagePath;
            this.category = category;
            this.price = price;
        }
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
                        
                        // Create and add product
                        Product product = new Product(shoe.getName(), imageUrl, shoe.getCategory(), shoe.getPrice());
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
        
        int count = 0;
        for (Product product : products) {
            if (category.equals("Tất cả") || product.category.equals(category)) {
                JPanel productCard = createProductCard(product.name, product.imagePath, product.price);
                productsPanel.add(productCard);
                count++;
                System.out.println("Added card for product: " + product.name);
            }
        }
        
        System.out.println("Added " + count + " product cards to panel");
        productsPanel.revalidate();
        productsPanel.repaint();
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
        
        // Add hover effect for add to cart button
        addToCartButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                addToCartButton.setBackground(new Color(80, 80, 80));
            }
            public void mouseExited(MouseEvent e) {
                addToCartButton.setBackground(new Color(60, 60, 60));
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
        // Add category filter event listener
        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) categoryComboBox.getSelectedItem();
                System.out.println("Category selected: " + selectedCategory);
                displayProducts(selectedCategory);
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
}
