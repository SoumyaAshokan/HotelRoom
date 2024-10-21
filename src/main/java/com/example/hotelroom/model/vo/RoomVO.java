package com.example.hotelroom.model.vo;

public class RoomVO {
	
	private Long roomId;
	private String roomNo;
	private String category;
	private int capacity;
	private Double roomRatePerDay;

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
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

	public Double getRoomRatePerDay() {
		return roomRatePerDay;
	}

	public void setRoomRatePerDay(Double roomRatePerDay) {
		this.roomRatePerDay = roomRatePerDay;
	}
	
	
}
