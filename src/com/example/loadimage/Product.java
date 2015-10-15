package com.example.loadimage;

import java.util.Arrays;

public class Product {
	int ProductID;
	String productname;
	String[] type;
	int[] price;
	public int getProductID() {
		return ProductID;
	}
	public void setProductID(int productID) {
		ProductID = productID;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String[] getType() {
		return type;
	}
	public void setType(String[] type) {
		this.type = type;
	}
	public int[] getPrice() {
		return price;
	}
	public void setPrice(int[] price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Product [ProductID=" + ProductID + ", productname="
				+ productname + ", type=" + Arrays.toString(type) + ", price="
				+ Arrays.toString(price) + "]";
	}
	
	
}
