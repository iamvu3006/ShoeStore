package com.team4.shoestore;

import com.team4.shoestore.ui.FormLogin;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ShoestoreApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(ShoestoreApplication.class)
				.headless(false)
				.run(args);
		
		// Mở form đăng nhập khi ứng dụng khởi động
		java.awt.EventQueue.invokeLater(() -> {
			FormLogin loginForm = context.getBean(FormLogin.class);
			loginForm.setVisible(true);
		});
	}
}
