package com.phoneshop.entity;

import java.io.Serializable;

public class PhoneInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;   		//产品名称
	private String brand;  		//产品品牌
	private int price;	   		//产品价格
	private String url;   		//产品图片地址
	private String screenSize;  //产品屏幕大小
	private String thick;       //产品厚度
	private String timeToMarket;//产品上市时间
	private String pixel;		//产品像素
	private String cpu; 		//产品核心数
	private String netSize;		//网络类型
	private String system;		//产品系统
	private String describe;	//产品描述
	public PhoneInfo() {
		// TODO Auto-generated constructor stub
	}
	public PhoneInfo(String name, String brand, int price, String url,
			String screenSize, String thick, String timeToMarket, String pixel,
			String cpu, String netSize, String system, String describe) {
		super();
		this.name = name;
		this.brand = brand;
		this.price = price;
		this.url = url;
		this.screenSize = screenSize;
		this.thick = thick;
		this.timeToMarket = timeToMarket;
		this.pixel = pixel;
		this.cpu = cpu;
		this.netSize = netSize;
		this.system = system;
		this.describe = describe;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getScreenSize() {
		return screenSize;
	}
	public void setScreenSize(String screenSize) {
		this.screenSize = screenSize;
	}
	public String getThick() {
		return thick;
	}
	public void setThick(String thick) {
		this.thick = thick;
	}
	public String getTimeToMarket() {
		return timeToMarket;
	}
	public void setTimeToMarket(String timeToMarket) {
		this.timeToMarket = timeToMarket;
	}
	public String getPixel() {
		return pixel;
	}
	public void setPixel(String pixel) {
		this.pixel = pixel;
	}
	public String getCpu() {
		return cpu;
	}
	public void setCpu(String cpu) {
		this.cpu = cpu;
	}
	public String getNetSize() {
		return netSize;
	}
	public void setNetSize(String netSize) {
		this.netSize = netSize;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	@Override
	public String toString() {
		return "PhoneInfo [name=" + name + ", brand=" + brand + ", price="
				+ price + ", url=" + url + ", screenSize=" + screenSize
				+ ", thick=" + thick + ", timeToMarket=" + timeToMarket
				+ ", pixel=" + pixel + ", cpu=" + cpu + ", netSize=" + netSize
				+ ", system=" + system + ", describe=" + describe + "]";
	}
}
