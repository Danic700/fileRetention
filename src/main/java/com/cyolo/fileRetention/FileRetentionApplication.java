package com.cyolo.fileRetention;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class FileRetentionApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileRetentionApplication.class, args);
	}

}
