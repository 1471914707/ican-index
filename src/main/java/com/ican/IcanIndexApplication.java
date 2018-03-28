package com.ican;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class IcanIndexApplication {

	public static void main(String[] args) {
		SpringApplication.run(IcanIndexApplication.class, args);
	}
}
