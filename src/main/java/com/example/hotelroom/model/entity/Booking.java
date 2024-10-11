package com.example.hotelroom.model.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="bookings")
public class Booking {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long bookingId;
	
	@ManyToOne
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name="room_id",nullable=false)
	private Room room;
	
	private LocalDate checkIn;
	private LocalDate checkOut;
	
	@Column(name="b_occupancy")
	private int bookedOccupancy;
	
	@Column(name="booking_no",nullable=false,unique=true)
	private String bookingNo;
	
	private boolean status;
	
	public Booking() {
		super();
	}

	public Booking(User user, Room room, LocalDate checkIn, LocalDate checkOut, int bookedOccupancy,String bookingNo,
			boolean status) {
		super();
		this.user = user;
		this.room = room;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.bookedOccupancy = bookedOccupancy;
		this.bookingNo=bookingNo;
		this.status = status;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
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

	public void setBookedOccuppancy(int bookedOccupancy) {
		this.bookedOccupancy = bookedOccupancy;
	}


	public String getBookingNo() {
		return bookingNo;
	}

	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	
}
