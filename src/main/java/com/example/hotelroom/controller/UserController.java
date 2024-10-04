package com.example.hotelroom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotelroom.model.User;
import com.example.hotelroom.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userservice;
	
	@GetMapping("/users")
	public List<User> getUsers() {
		return userservice.getUsers();
	}
	
}
