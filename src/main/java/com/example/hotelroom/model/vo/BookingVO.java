package com.example.hotelroom.model.vo;

import java.time.LocalDate;

public class BookingVO {

	private String bookingNo;
	private String roomNo;
	private LocalDate checkIn;
	private LocalDate checkOut;
	private String category;
	private int bookedOccupancy;
	
	public String getBookingNo() {
		return bookingNo;
	}
	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
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
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public int getBookedOccupancy() {
		return bookedOccupancy;
	}
	public void setBookedOccupancy(int bookedOccupancy) {
		this.bookedOccupancy = bookedOccupancy;
	}
	
}
