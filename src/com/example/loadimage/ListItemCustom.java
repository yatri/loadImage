package com.example.loadimage;

public class ListItemCustom {

	private int productID;
	private String headline;
	private String date;
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	@Override
	public String toString() {
		return "ListItemCustom [headline=" + headline + ", date=" + date
				+ ", url=" + url + "]";
	}
	
	
}
