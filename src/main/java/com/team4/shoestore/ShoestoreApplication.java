package com.team4.shoestore;

import com.team4.shoestore.ui.FormLogin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.swing.*;



@SpringBootApplication
public class ShoestoreApplication {
    public static void main(String[] args) {


		System.setProperty("java.awt.headless", "false");
        // Khởi động Spring
        ApplicationContext context = SpringApplication.run(ShoestoreApplication.class, args);

        // Lấy FormUsers từ Spring Container
        FormLogin formLogin = context.getBean(FormLogin.class);

        // Hiển thị form trên UI Thread
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Đăng Nhập");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setContentPane(formLogin);
            frame.setVisible(true);
        });
    }
}