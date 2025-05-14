package com.team4.shoestore.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FormHome extends JFrame {
    // Components
    private JPanel sidebarPanel;
    private JPanel panelLoader; // Panel to load other forms
    
    public FormHome() {
        initComponents();
        initEvent();
    }
    
    private void initComponents() {
        // Set up the main frame
        setTitle("Yan Sneaker - Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 800);
        setLocationRelativeTo(null);
        
        // Create main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(33, 33, 33)); // Dark background
        
        // Initialize sidebar
        initSidebar();
        
        // Initialize panel loader
        initPanelLoader();
        
        // Add panels to main panel
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(panelLoader, BorderLayout.CENTER);
        
        // Add main panel to frame
        add(mainPanel);
    }
    
    private void initSidebar() {
        sidebarPanel = new JPanel();
        sidebarPanel.setPreferredSize(new Dimension(200, 0));
        sidebarPanel.setBackground(new Color(45, 45, 45)); // Darker sidebar
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        
        // Add user profile panel at the top
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        userPanel.setBackground(new Color(35, 35, 35)); // Darker background for user panel
        userPanel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        userPanel.setMaximumSize(new Dimension(200, 160));
        userPanel.setPreferredSize(new Dimension(200, 160));
        
        // User icon
        JLabel userIcon = new JLabel();
        try {
            ImageIcon icon = new ImageIcon("View/images/icon/user.png");
            Image image = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            userIcon.setIcon(new ImageIcon(image));
        } catch (Exception e) {
            userIcon.setText("👤");
            userIcon.setFont(new Font("Arial", Font.PLAIN, 40));
        }
        userIcon.setForeground(Color.WHITE);
        userIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Username label
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add components to user panel
        userPanel.add(userIcon);
        userPanel.add(Box.createVerticalStrut(15));
        userPanel.add(usernameLabel);
        
        // Add user panel to sidebar
        sidebarPanel.add(userPanel);
        sidebarPanel.add(Box.createVerticalStrut(20));
        
        // Create navigation buttons
        String[] navItems = {
            "Menu", "Invoice", "Shoes", "Brand", "Customers", "Users", 
            "Dashboard"
        };
        
        // Add main navigation buttons
        for (String item : navItems) {
            JButton navButton = createNavButton(item);
            sidebarPanel.add(navButton);
            sidebarPanel.add(Box.createVerticalStrut(10));
        }
        
        // Add flexible space to push logout button to bottom
        sidebarPanel.add(Box.createVerticalGlue());
        
        // Add logout button at the bottom
        JButton logoutButton = createNavButton("Logout");
        logoutButton.setBackground(new Color(200, 50, 50)); // Red color for logout
        logoutButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                logoutButton.setBackground(new Color(220, 70, 70));
            }
            public void mouseExited(MouseEvent e) {
                logoutButton.setBackground(new Color(200, 50, 50));
            }
        });
        sidebarPanel.add(logoutButton);
        sidebarPanel.add(Box.createVerticalStrut(20)); // Bottom padding
    }
    
    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(60, 60, 60)); // Dark button background
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(180, 40));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(80, 80, 80));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(60, 60, 60));
            }
        });
        
        return button;
    }
    
    private void initPanelLoader() {
        panelLoader = new JPanel(new BorderLayout());
        panelLoader.setBackground(new Color(33, 33, 33));
    }
    
    private void initEvent() {
        // Add event listeners for navigation buttons
        Component[] components = sidebarPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String buttonText = button.getText();
                        switch (buttonText) {
                            case "Menu":
                                loadForm(new FormMenu());
                                break;
                            case "Invoice":
                                loadForm(new FormInvoices());
                                break;
                            case "Shoes":
                                loadForm(new FormShoes());
                                break;
                            case "Brand":
                                loadForm(new FormBrands());
                                break;
                            case "Customers":
                                loadForm(new FormCustomers());
                                break;
                            case "Users":
                                loadForm(new FormUsers());
                                break;
                            case "Dashboard":
                                loadForm(new DashboardForm());
                                break;
                            case "Logout":
                                // Handle logout
                                break;
                        }
                    }
                });
            }
        }
    }
    
    private void loadForm(JPanel form) {
        panelLoader.removeAll();
        panelLoader.add(form, BorderLayout.CENTER);
        panelLoader.revalidate();
        panelLoader.repaint();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FormHome().setVisible(true);
        });
    }
}
