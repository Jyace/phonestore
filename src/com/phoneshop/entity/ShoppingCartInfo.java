package com.phoneshop.entity;

public class ShoppingCartInfo {
	private String username;
	private String phoneName;
	private String phoneUrl;
	private int phonePrice;
	private int phoneNumber;
	private int isChecked;
	private int stockNum;
	private int orderNum;
	public ShoppingCartInfo() {
		// TODO Auto-generated constructor stub
	}
	public ShoppingCartInfo(String username, String phoneName, String phoneUrl,
			int phonePrice, int phoneNumber, int isChecked,int stockNum,int orderNum) {
		super();
		this.username = username;
		this.phoneName = phoneName;
		this.phoneUrl = phoneUrl;
		this.phonePrice = phonePrice;
		this.phoneNumber = phoneNumber;
		this.isChecked = isChecked;
		this.stockNum = stockNum;
		this.orderNum = orderNum;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhoneName() {
		return phoneName;
	}
	public void setPhoneName(String phoneName) {
		this.phoneName = phoneName;
	}
	public String getPhoneUrl() {
		return phoneUrl;
	}
	public void setPhoneUrl(String phoneUrl) {
		this.phoneUrl = phoneUrl;
	}
	public int getPhonePrice() {
		return phonePrice;
	}
	public void setPhonePrice(int phonePrice) {
		this.phonePrice = phonePrice;
	}
	public int getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public int getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(int isChecked) {
		this.isChecked = isChecked;
	}
	public int getStockNum() {
		return stockNum;
	}
	public void setStockNum(int stockNum) {
		this.stockNum = stockNum;
	}
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	@Override
	public String toString() {
		return "ShoppingCartInfo [username=" + username + ", phoneName="
				+ phoneName + ", phoneUrl=" + phoneUrl + ", phonePrice="
				+ phonePrice + ", phoneNumber=" + phoneNumber + ", isChecked="
				+ isChecked + ", stockNum=" + stockNum + ", orderNum="
				+ orderNum + "]";
	}
	
}
