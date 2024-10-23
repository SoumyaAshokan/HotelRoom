package com.example.hotelroom.model.vo;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

public class BookingVO {

	private String bookingNo;
	private String roomNo;
	private LocalDate checkIn;
	private LocalDate checkOut;
	private String category;
	private int bookedOccupancy;
	private String userName;
	private Double roomRatePerDay;
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private int additionalGuest;
	private Boolean status;

	public BookingVO() {
		super();
	}

	public BookingVO(String bookingNo, String roomNo, LocalDate checkIn, LocalDate checkOut, String category,
			int bookedOccupancy, String userName, Double roomRatePerDay, Boolean status) {
		super();
		this.bookingNo = bookingNo;
		this.roomNo = roomNo;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.category = category;
		this.bookedOccupancy = bookedOccupancy;
		this.userName = userName;
		this.roomRatePerDay = roomRatePerDay;
		this.status = status;
	}

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

	public int getAdditionalGuest() {
		return additionalGuest;
	}

	public void setAdditionalGuest(int additionalGuest) {
		this.additionalGuest = additionalGuest;
	}

	public Boolean isStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

}
