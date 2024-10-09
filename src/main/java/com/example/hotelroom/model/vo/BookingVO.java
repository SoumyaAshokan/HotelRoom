package com.example.hotelroom.model.vo;

import java.time.LocalDate;

public class BookingVO {

	private Long bookingId;
	private Long userId;
	private Long roomId;
	private LocalDate checkIn;
	private LocalDate checkOut;
	private int bookedOccupancy;
	private boolean status;
	
	
	public BookingVO() {
		super();
	}

	public BookingVO(Long bookingId, Long userId, Long roomId, LocalDate checkIn, LocalDate checkOut,
					 int bookedOccupancy, boolean status) {
		super();
		this.bookingId = bookingId;
		this.userId = userId;
		this.roomId = roomId;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.bookedOccupancy = bookedOccupancy;
		this.status = status;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
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

	public int getBookedOccupancy() {
		return bookedOccupancy;
	}

	public void setBookedOccupancy(int bookedOccupancy) {
		this.bookedOccupancy = bookedOccupancy;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
}
