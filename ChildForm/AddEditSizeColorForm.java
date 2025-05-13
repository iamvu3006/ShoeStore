package View.ChildForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddEditSizeColorForm extends JDialog {
    // Components
    private JPanel mainPanel;
    private JComboBox<String> cboSize;
    private JComboBox<String> cboColor;
    private JTextField txtQuantity;
    private JButton btnSave;
    private JButton btnCancel;
    
    // Colors
    private static final Color BACKGROUND_COLOR = new Color(30, 30, 30);
    private static final Color PANEL_COLOR = new Color(40, 40, 40);
    private static final Color TEXT_COLOR = new Color(221, 221, 221);
    private static final Color BUTTON_COLOR = new Color(64, 64, 64);
    private static final Color BUTTON_HOVER_COLOR = new Color(80, 80, 80);
    
    // Data
    private String[] sizes = {"36", "37", "38", "39", "40", "41", "42", "43", "44", "45"};
    private String[] colors = {"Đen", "Trắng", "Đỏ", "Xanh dương", "Xanh lá", "Vàng", "Hồng", "Nâu", "Xám"};
    private boolean isEditMode = false;
    private String selectedSize;
    private String selectedColor;
    private String selectedQuantity;
    private JDialog parentForm;
    
    public AddEditSizeColorForm(JDialog parent, boolean modal) {
        super(parent, modal);
        this.parentForm = parent;
        initComponents();
    }
    
    public AddEditSizeColorForm(JDialog parent, boolean modal, String size, String color, String quantity) {
        super(parent, modal);
        this.parentForm = parent;
        this.isEditMode = true;
        this.selectedSize = size;
        this.selectedColor = color;
        this.selectedQuantity = quantity;
        initComponents();
    }
    
    private void initComponents() {
        setTitle(isEditMode ? "Sửa thông tin size/màu" : "Thêm size/màu mới");
        setSize(400, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(PANEL_COLOR);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Size ComboBox
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createLabel("Size:"), gbc);
        gbc.gridx = 1;
        cboSize = createComboBox(sizes);
        if (isEditMode) {
            cboSize.setSelectedItem(selectedSize);
        }
        formPanel.add(cboSize, gbc);
        
        // Color ComboBox
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(createLabel("Màu sắc:"), gbc);
        gbc.gridx = 1;
        cboColor = createComboBox(colors);
        if (isEditMode) {
            cboColor.setSelectedItem(selectedColor);
        }
        formPanel.add(cboColor, gbc);
        
        // Quantity TextField
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(createLabel("Số lượng:"), gbc);
        gbc.gridx = 1;
        txtQuantity = createTextField();
        if (isEditMode) {
            txtQuantity.setText(selectedQuantity);
        }
        formPanel.add(txtQuantity, gbc);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(PANEL_COLOR);
        
        btnSave = createButton("Lưu");
        btnCancel = createButton("Hủy");
        
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        
        // Add panels to main panel
        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(buttonPanel);
        
        // Add main panel to dialog
        add(mainPanel);
        
        // Add event listeners
        initEvent();
    }
    
    private void initEvent() {
        btnSave.addActionListener(_ -> {
            String size = (String) cboSize.getSelectedItem();
            String color = (String) cboColor.getSelectedItem();
            String quantity = txtQuantity.getText().trim();
            
            if (quantity.isEmpty() || !quantity.matches("\\d+")) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập số lượng hợp lệ",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (parentForm instanceof ShoeDetailForm) {
                ShoeDetailForm shoeDetailForm = (ShoeDetailForm) parentForm;
                if (isEditMode) {
                    shoeDetailForm.updateSizeColorRow(size, color, quantity);
                } else {
                    shoeDetailForm.addSizeColorRow(size, color, quantity);
                }
            }
            
            dispose();
        });
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_COLOR);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        return label;
    }
    
    private JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setBackground(BACKGROUND_COLOR);
        comboBox.setForeground(TEXT_COLOR);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        comboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        // Customize combobox appearance
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBackground(isSelected ? new Color(70, 70, 70) : BACKGROUND_COLOR);
                setForeground(TEXT_COLOR);
                setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                return this;
            }
        });
        
        return comboBox;
    }
    
    private JTextField createTextField() {
        JTextField textField = new JTextField(20);
        textField.setBackground(BACKGROUND_COLOR);
        textField.setForeground(TEXT_COLOR);
        textField.setCaretColor(TEXT_COLOR);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return textField;
    }
    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setForeground(TEXT_COLOR);
        button.setBackground(BUTTON_COLOR);
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
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AddEditSizeColorForm form = new AddEditSizeColorForm(null, true);
            form.setVisible(true);
        });
    }
} 