package com.team4.shoestore.ui.childform;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.team4.shoestore.service.UserService;
import com.team4.shoestore.model.User;
import com.team4.shoestore.model.User.Role;
import jakarta.annotation.PostConstruct;

@Component
public class AddEditUserForm extends JDialog {
    @Autowired
    private UserService userService;
    
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<Role> cboRole;
    private JButton btnSave;
    private JButton btnCancel;
    private boolean isAddMode;
    private int userId;
    
    // Colors
    private static final Color BACKGROUND_COLOR = new Color(30, 30, 30);
    private static final Color PANEL_COLOR = new Color(40, 40, 40);
    private static final Color TEXT_COLOR = new Color(221, 221, 221);
    private static final Color BUTTON_COLOR = new Color(64, 64, 64);
    private static final Color BUTTON_HOVER_COLOR = new Color(80, 80, 80);

    public AddEditUserForm() {
        // Default constructor for Spring
    }
    @PostConstruct
    public void init() {
        initComponents();
        initEvent();
    }

    public void setupDialog(Frame parent, boolean modal, int userId) {
        super.setModal(modal);
        setLocationRelativeTo(parent);
        this.userId = userId;
        this.isAddMode = (userId < 0);

        if (!isAddMode) {
            loadUserData();
        }
    }

    private void loadUserData() {
        try {
            User user = userService.getUserById(userId);
            txtUsername.setText(user.getUsername());
            txtPassword.setText(user.getPassword());
            cboRole.setSelectedItem(user.getRole());
            setTitle("Sửa người dùng");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Lỗi khi tải thông tin người dùng: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }
    
    private void initComponents() {
        setTitle("Thêm/Sửa người dùng");
        setSize(500, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(BACKGROUND_COLOR);
        
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(createLabel("Tên đăng nhập:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtUsername = createTextField();
        formPanel.add(txtUsername, gbc);
        
        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        formPanel.add(createLabel("Mật khẩu:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtPassword = new JPasswordField();
        txtPassword.setPreferredSize(new Dimension(200, 30));
        txtPassword.setBackground(PANEL_COLOR);
        txtPassword.setForeground(TEXT_COLOR);
        txtPassword.setCaretColor(TEXT_COLOR);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        formPanel.add(txtPassword, gbc);
        
        // Role
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        formPanel.add(createLabel("Vai trò:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        cboRole = new JComboBox<>(Role.values());
        cboRole.setPreferredSize(new Dimension(200, 30));
        cboRole.setBackground(PANEL_COLOR);
        cboRole.setForeground(TEXT_COLOR);
        cboRole.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cboRole.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        formPanel.add(cboRole, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        
        btnSave = createButton("Lưu");
        btnCancel = createButton("Hủy");
        
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_COLOR);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        return label;
    }
    
    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 30));
        textField.setBackground(PANEL_COLOR);
        textField.setForeground(TEXT_COLOR);
        textField.setCaretColor(TEXT_COLOR);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return textField;
    }
    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 35));
        button.setBackground(BUTTON_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        
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
    
    private void initEvent() {
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    saveUser();
                }
            }
        });
        
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private boolean validateInput() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng nhập tên đăng nhập",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            txtUsername.requestFocus();
            return false;
        }
        
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng nhập mật khẩu",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            txtPassword.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void saveUser() {
        try {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();
            Role role = (Role) cboRole.getSelectedItem();
            
            User user;
            if (isAddMode) {
                user = new User();
            } else {
                user = userService.getUserById(userId);
            }
            
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(role);
            
            if (isAddMode) {
                userService.AddNewUser(user);
            } else {
                userService.UpdateUser(user);
            }
            
            JOptionPane.showMessageDialog(this,
                isAddMode ? "Thêm người dùng thành công" : "Cập nhật người dùng thành công",
                "Thành công",
                JOptionPane.INFORMATION_MESSAGE);
                
            dispose();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Lỗi khi lưu người dùng: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }
} 