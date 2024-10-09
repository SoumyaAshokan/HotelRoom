package com.example.hotelroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotelroom.model.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {

}
