package View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FormLogin extends JFrame {
    // Các thành phần của giao diện
    private JPanel pnlLeft;        // Panel bên trái chứa poster
    private JPanel pnlRight;       // Panel bên phải chứa form login
    private JLabel lblPoster;      // Label chứa ảnh poster
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    
    public FormLogin() {
        initComponents();
        initEvent();
    }
    
    public void initComponents() {
        
        //Set form
        setTitle("Đăng Nhập");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);  
        setLocationRelativeTo(null);
        
        // tao mainPanel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(33, 33, 33));  // Màu nền tối cho panel chính
        
        // panel left
        pnlLeft = new JPanel();
        pnlLeft.setPreferredSize(new Dimension(450, 600));
        pnlLeft.setBackground(new Color(33, 33, 33));  // Màu nền tối cho panel trái
        lblPoster = new JLabel();
        lblPoster.setHorizontalAlignment(JLabel.CENTER);
        lblPoster.setVerticalAlignment(JLabel.CENTER);
        
        // xu ly anh trong panel left 
        ImageIcon imageIcon = new ImageIcon("View/images/mainposter.jpg");
        Image image = imageIcon.getImage();
        Image resizeImage = image.getScaledInstance(450, 600, Image.SCALE_SMOOTH);
        lblPoster.setIcon(new ImageIcon(resizeImage));
        pnlLeft.add(lblPoster);
        
        // Tạo panel bên phải chứa form login
        pnlRight = new JPanel();
        pnlRight.setLayout(new GridBagLayout());
        pnlRight.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        pnlRight.setBackground(new Color(33, 33, 33));  // Màu nền tối cho panel phải
        
        // Tạo panel con chứa form login
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(45, 45, 45));
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Thêm label Yan Sneaker
        JLabel lblBrand = new JLabel("Yan Sneaker");
        lblBrand.setFont(new Font("Arial", Font.BOLD, 32));
        lblBrand.setForeground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 30, 0);
        loginPanel.add(lblBrand, gbc);
        
        // Thêm tiêu đề
        JLabel lblTitle = new JLabel("ĐĂNG NHẬP");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        loginPanel.add(lblTitle, gbc);
        
        // Thêm trường Username
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblUsername = new JLabel("Tên đăng nhập:");
        lblUsername.setForeground(Color.WHITE);  // Chữ màu trắng
        loginPanel.add(lblUsername, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        txtUsername = new JTextField(20);
        txtUsername.setBackground(new Color(60, 60, 60));  // Màu nền tối cho textfield
        txtUsername.setForeground(Color.WHITE);  // Chữ màu trắng
        txtUsername.setCaretColor(Color.WHITE);  // Con trỏ màu trắng
        loginPanel.add(txtUsername, gbc);
        
        // Thêm trường Password
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        JLabel lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setForeground(Color.WHITE);  // Chữ màu trắng
        loginPanel.add(lblPassword, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        txtPassword = new JPasswordField(20);
        txtPassword.setBackground(new Color(60, 60, 60));  // Màu nền tối cho password field
        txtPassword.setForeground(Color.WHITE);  // Chữ màu trắng
        txtPassword.setCaretColor(Color.WHITE);  // Con trỏ màu trắng
        loginPanel.add(txtPassword, gbc);
        
        // Thêm panel chứa nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(45, 45, 45));  // Màu nền tối cho panel nút
        btnLogin = new JButton("Đăng nhập");
        btnCancel = new JButton("Hủy");
        
        // Tùy chỉnh style cho nút
        btnLogin.setPreferredSize(new Dimension(100, 35));
        btnCancel.setPreferredSize(new Dimension(100, 35));
        btnLogin.setBackground(new Color(0, 120, 212));  // Màu xanh dương đậm
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnCancel.setBackground(new Color(60, 60, 60));  // Màu xám đậm
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFocusPainted(false);
        
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnCancel);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 0, 0);
        loginPanel.add(buttonPanel, gbc);
        
        // Thêm loginPanel vào pnlRight
        pnlRight.add(loginPanel);
        
        // Thêm các panel vào mainPanel
        mainPanel.add(pnlLeft, BorderLayout.WEST);
        mainPanel.add(pnlRight, BorderLayout.CENTER);
        
        // Thêm mainPanel vào frame
        add(mainPanel);
    }
    
    public void initEvent() {
        // Sự kiện cho nút đăng nhập
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //----------hem ham xu ly dang nhap ow day---------------//

                //----------hem ham xu ly dang nhap ow day---------------//
            }
        });
        
        // Sự kiện cho nút hủy
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    
    public static void main(String args[]) {
        // Chạy ứng dụng trong Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FormLogin().setVisible(true);
            }
        });
    }
}
