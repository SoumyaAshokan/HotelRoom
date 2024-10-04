package com.example.hotelroom.model;

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
	private Long booking_id;
	
	@ManyToOne
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name="room_id",nullable=false)
	private Room room;
	
	private LocalDate check_in;
	private LocalDate check_out;
	
	@Column(name="bcp")
	private int booked_occuppancy;
	
	private boolean status;
	
	public Booking() {
		super();
	}

	public Booking(User user, Room room, LocalDate check_in, LocalDate check_out, int booked_occuppancy,
			boolean status) {
		super();
		this.user = user;
		this.room = room;
		this.check_in = check_in;
		this.check_out = check_out;
		this.booked_occuppancy = booked_occuppancy;
		this.status = status;
	}

	public Long getBooking_id() {
		return booking_id;
	}

	public void setBooking_id(Long booking_id) {
		this.booking_id = booking_id;
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

	public LocalDate getCheck_in() {
		return check_in;
	}

	public void setCheck_in(LocalDate check_in) {
		this.check_in = check_in;
	}

	public LocalDate getCheck_out() {
		return check_out;
	}

	public void setCheck_out(LocalDate check_out) {
		this.check_out = check_out;
	}

	public int getBooked_occuppancy() {
		return booked_occuppancy;
	}

	public void setBooked_occuppancy(int booked_occuppancy) {
		this.booked_occuppancy = booked_occuppancy;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
}
