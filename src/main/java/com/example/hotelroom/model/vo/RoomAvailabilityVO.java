package com.example.hotelroom.model.vo;

import java.time.LocalDate;

public class RoomAvailabilityVO {

	private LocalDate checkIn;
	private LocalDate checkOut;
	private int bookedOccupancy;
	private String category;
	
	
	public LocalDate getCheckIn() {
		return checkIn;
	}
	public void setCheckIn(LocalDate checkIn) {
		this.checkIn = checkIn;
	}
	public LocalDate getCheckOut() {
		return checkOut;
	}
	public void setCheckOut(LocalDate checkOut) {
		this.checkOut = checkOut;
	}
	public int getBookedOccupancy() {
		return bookedOccupancy;
	}
	public void setBookedOccupancy(int bookedOccupancy) {
		this.bookedOccupancy = bookedOccupancy;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
}
