package com.example.hotelroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.hotelroom.repository")
@EntityScan(basePackages = "com.example.hotelroom.model")
@ComponentScan(basePackages = "com.example.hotelroom")
@ComponentScan(basePackages = "com.example.hotelroom.service")
@ComponentScan(basePackages = "com.example.hotelroom.controller")
@ComponentScan(basePackages = "com.example.hotelroom.repository.impl")
public class HotelroomApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelroomApplication.class, args);
	}

}
