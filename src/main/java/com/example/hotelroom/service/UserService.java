package com.example.hotelroom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotelroom.model.User;
import com.example.hotelroom.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userrepo;

	public List<User> getUsers() {
		return userrepo.findAll();
	}
}