package com.phoneshop.entity;

public class User {
public String username,password,phone,email,trueName,address;
boolean isLogin,isRegister,isSave;
public User(){}


public User(String username, String password) {
	super();
	this.username = username;
	this.password = password;
}

public User(String username, String password, String phone, String email,
		boolean isLogin, boolean isRegister) {
	super();
	this.username = username;
	this.password = password;
	this.phone = phone;
	this.email = email;
	this.isLogin = isLogin;
	this.isRegister = isRegister;
}



public User(String username, String password, String phone, String email) {
	super();
	this.username = username;
	this.password = password;
	this.phone = phone;
	this.email = email;
}

public User(String phone, String trueName, String address) {
	super();
	this.phone = phone;
	this.trueName = trueName;
	this.address = address;
}
public String getTrueName() {
	return trueName;
}
public void setTrueName(String trueName) {
	this.trueName = trueName;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public boolean isSave() {
	return isSave;
}
public void setSave(boolean isSave) {
	this.isSave = isSave;
}
public String getUsername() {
	return username;
}

public boolean isLogin() {
	return isLogin;
}

public void setLogin(boolean isLogin) {
	this.isLogin = isLogin;
}

public boolean isRegister() {
	return isRegister;
}

public void setRegister(boolean isRegister) {
	this.isRegister = isRegister;
}

public void setUsername(String username) {
	this.username = username;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getPhone() {
	return phone;
}

public void setPhone(String phone) {
	this.phone = phone;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

@Override
public String toString() {
	return "User [username=" + username + ", password=" + password + ", phone="
			+ phone + ", email=" + email + ", trueName=" + trueName
			+ ", address=" + address + ", isLogin=" + isLogin + ", isRegister="
			+ isRegister + ", isSave=" + isSave + "]";
}


}
