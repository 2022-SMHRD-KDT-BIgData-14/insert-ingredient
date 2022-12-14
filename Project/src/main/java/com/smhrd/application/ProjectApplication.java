package com.smhrd.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.smhrd.controller")
@MapperScan("com.smhrd.mapper")
@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) {
		
		
		System.out.println("웹 서버 구동!!!!!!");
		SpringApplication.run(ProjectApplication.class, args);
	}

}
