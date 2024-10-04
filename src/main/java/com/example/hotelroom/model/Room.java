package com.example.hotelroom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="rooms")
public class Room {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long room_id;
	
	private String room_no;
	private String category;
	private int capacity;
	
	public Room() {
		super();
	}
	
	public Room(String room_no, String category, int capacity) {
		super();
		this.room_no = room_no;
		this.category = category;
		this.capacity = capacity;
	}

	public Long getRoom_id() {
		return room_id;
	}
	public void setRoom_id(Long room_id) {
		this.room_id = room_id;
	}
	public String getRoom_no() {
		return room_no;
	}
	public void setRoom_no(String room_no) {
		this.room_no = room_no;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
}
