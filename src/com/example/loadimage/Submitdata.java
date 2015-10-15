package com.example.loadimage;

public class Submitdata {
	int userid;
	int prodid;
	int qty;
	String type;
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getProdid() {
		return prodid;
	}
	public void setProdid(int prodid) {
		this.prodid = prodid;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Submitdata [userid=" + userid + ", prodid=" + prodid + ", qty="
				+ qty + ", type=" + type + "]";
	}
	
	
}
