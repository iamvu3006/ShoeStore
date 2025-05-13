package View.ChildForm;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class SelectShoeForm extends JDialog {
    private JLabel lblImage;
    private JLabel lblShoeName;
    private JLabel lblBrand;
    private JLabel lblCategory;
    private JLabel lblPrice;
    private JTable tblSizes;
    private DefaultTableModel sizeTableModel;
    private JSpinner spnQuantity;
    private JButton btnSelect;
    private JButton btnCancel;
    
    // Colors
    private static final Color BACKGROUND_COLOR = new Color(30, 30, 30);
    private static final Color TEXT_COLOR = new Color(221, 221, 221);
    private static final Color BUTTON_COLOR = new Color(64, 64, 64);
    private static final Color BUTTON_HOVER_COLOR = new Color(80, 80, 80);
    private static final Color TABLE_HEADER_COLOR = new Color(45, 45, 45);
    private static final Color TABLE_ROW_COLOR = new Color(35, 35, 35);
    private static final Color TABLE_BORDER_COLOR = new Color(60, 60, 60);
    
    public SelectShoeForm(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initEvent();
    }
    
    private void initComponents() {
        setTitle("Chọn giày");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(BACKGROUND_COLOR);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Left panel for image and basic info
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(BACKGROUND_COLOR);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        
        // Image panel
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imagePanel.setBackground(BACKGROUND_COLOR);
        lblImage = new JLabel();
        lblImage.setPreferredSize(new Dimension(300, 300));
        lblImage.setBorder(BorderFactory.createLineBorder(TABLE_BORDER_COLOR));
        imagePanel.add(lblImage);
        
        // Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        infoPanel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Shoe Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(createLabel("Tên giày:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        lblShoeName = createInfoLabel();
        infoPanel.add(lblShoeName, gbc);
        
        // Brand
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        infoPanel.add(createLabel("Thương hiệu:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        lblBrand = createInfoLabel();
        infoPanel.add(lblBrand, gbc);
        
        // Category
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        infoPanel.add(createLabel("Thể loại:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        lblCategory = createInfoLabel();
        infoPanel.add(lblCategory, gbc);
        
        // Price
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        infoPanel.add(createLabel("Giá:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        lblPrice = createInfoLabel();
        infoPanel.add(lblPrice, gbc);
        
        leftPanel.add(imagePanel);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(infoPanel);
        
        // Right panel for size/color selection
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(BACKGROUND_COLOR);
        
        // Sizes table
        String[] columns = {"Size", "Màu sắc", "Số lượng"};
        sizeTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblSizes = new JTable(sizeTableModel);
        tblSizes.setRowHeight(30);
        tblSizes.setShowGrid(false);
        tblSizes.setBackground(TABLE_ROW_COLOR);
        tblSizes.setForeground(TEXT_COLOR);
        tblSizes.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        // Customize table header
        JTableHeader header = tblSizes.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        header.setBackground(TABLE_HEADER_COLOR);
        header.setForeground(TEXT_COLOR);
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        // Center align all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setBackground(TABLE_ROW_COLOR);
        centerRenderer.setForeground(TEXT_COLOR);
        for (int i = 0; i < tblSizes.getColumnCount(); i++) {
            tblSizes.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        JScrollPane scrollPane = new JScrollPane(tblSizes);
        scrollPane.setBackground(BACKGROUND_COLOR);
        scrollPane.setBorder(BorderFactory.createLineBorder(TABLE_BORDER_COLOR));
        scrollPane.getViewport().setBackground(TABLE_ROW_COLOR);
        
        // Selection panel
        JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectionPanel.setBackground(BACKGROUND_COLOR);
        
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
        spnQuantity = new JSpinner(spinnerModel);
        spnQuantity.setPreferredSize(new Dimension(80, 30));
        spnQuantity.setBackground(BACKGROUND_COLOR);
        spnQuantity.setForeground(TEXT_COLOR);
        spnQuantity.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        spnQuantity.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(TABLE_BORDER_COLOR),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        selectionPanel.add(createLabel("Số lượng:"));
        selectionPanel.add(spnQuantity);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        
        btnSelect = createButton("Chọn");
        btnCancel = createButton("Hủy");
        
        buttonPanel.add(btnSelect);
        buttonPanel.add(btnCancel);
        
        // Add components to right panel
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        rightPanel.add(selectionPanel, BorderLayout.SOUTH);
        
        // Add panels to main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void initEvent() {

    }
    
    public void setShoeDetails(String shoeId, String shoeName, String brand, String category, String price, String imagePath) {
        lblShoeName.setText(shoeName);
        lblBrand.setText(brand);
        lblCategory.setText(category);
        lblPrice.setText(price);
        
        // Load shoe image
        try {
            ImageIcon originalIcon = new ImageIcon(imagePath);
            Image image = originalIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            lblImage.setIcon(new ImageIcon(image));
        } catch (Exception ex) {
            lblImage.setText("Không tìm thấy ảnh");
        }
        
        // Load size/color data
        loadSizeColorData(shoeId);
    }
    
    private void loadSizeColorData(String shoeId) {
        // Clear existing data
        sizeTableModel.setRowCount(0);
        
       
        // Sample data
        Object[][] data = {
            {"38", "Đen", "10"},
            {"39", "Đen", "15"},
            {"40", "Đen", "20"},
            {"41", "Đen", "15"},
            {"42", "Đen", "10"},
            {"38", "Trắng", "10"},
            {"39", "Trắng", "15"},
            {"40", "Trắng", "20"},
            {"41", "Trắng", "15"},
            {"42", "Trắng", "10"}
        };
        
        for (Object[] row : data) {
            sizeTableModel.addRow(row);
        }
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_COLOR);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        return label;
    }
    
    private JLabel createInfoLabel() {
        JLabel label = new JLabel();
        label.setForeground(TEXT_COLOR);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(TABLE_BORDER_COLOR),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        label.setPreferredSize(new Dimension(200, 30));
        return label;
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
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SelectShoeForm form = new SelectShoeForm(null, true);
            form.setShoeDetails("SH001", "Nike Air Max", "Nike", "Sneaker", "1,500,000 VNĐ", "View/images/sneaker1.png");
            form.setVisible(true);
        });
    }
} 