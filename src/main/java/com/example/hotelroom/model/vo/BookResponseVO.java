package com.example.hotelroom.model.vo;

public class BookResponseVO {

	private String bookingNo;
	private String roomNo;
	private double totalRoomRate;

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

	public double getTotalRoomRate() {
		return totalRoomRate;
	}

	public void setTotalRoomRate(double totalRoomRate) {
		this.totalRoomRate = totalRoomRate;
	}

}
