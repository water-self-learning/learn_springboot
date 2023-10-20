package com.example.customizeauthz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan
public class CustomizeAuthzApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomizeAuthzApplication.class, args);
	}

}
