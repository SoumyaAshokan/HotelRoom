package com.example.hotelroom.model.vo;

import java.time.LocalDate;

public class ViewVO {

	private String bookingNo;
	private String roomNo;
	private LocalDate checkIn;
	private LocalDate checkOut;
	private String category;
	private int bookedOccupancy;
	private String userName;
	private Double roomRatePerDay;
	private Boolean status;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Double getRoomRatePerDay() {
		return roomRatePerDay;
	}
	public void setRoomRatePerDay(Double roomRatePerDay) {
		this.roomRatePerDay = roomRatePerDay;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	
}
