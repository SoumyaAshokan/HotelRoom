package com.example.hotelroom.model.vo;

public class UserVO {

	private Long userId;
	private String userName;
	private String role;

	public UserVO() {
		super();
	}

	public UserVO(Long userId, String userName, String role) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.role = role;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
