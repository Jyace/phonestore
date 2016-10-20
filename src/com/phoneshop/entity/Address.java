package com.phoneshop.entity;

public class Address {
	private String username,trueName,phone,address;

	public Address(String username, String trueName, String phone,
			String address) {
		super();
		this.username = username;
		this.trueName = trueName;
		this.phone = phone;
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Address [username=" + username + ", trueName=" + trueName
				+ ", phone=" + phone + ", address=" + address + "]";
	}
	
	
}
