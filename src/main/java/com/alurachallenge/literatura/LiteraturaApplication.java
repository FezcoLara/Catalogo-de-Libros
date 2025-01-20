package com.alurachallenge.literatura;

import com.alurachallenge.literatura.principal.Principal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration; // Importa esta clase

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class LiteraturaApplication {
	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
		Principal principal = new Principal();
		principal.muestraElMenu();
	}
}
