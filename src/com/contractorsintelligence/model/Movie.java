package com.contractorsintelligence.model;

public class Movie {
	private String sn, name, thumbnail,androidURL;


	public Movie() {
	}

	public Movie(String sn,String name, String thumbnailUrl,String androidURL) {
		this.sn = sn;
		this.name = name;
		this.thumbnail = thumbnailUrl;
		this.androidURL = androidURL;
	}

	public String getSN() {
		return sn;
	}

	public void setSN(String sn) {
		this.sn = sn;
	}

	public String getTitle() {
		return name;
	}
	public void setTitle(String name) {
		this.name = name;
	}

	public String getThumbnailUrl() {
		return thumbnail;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnail = thumbnailUrl;
	}

	public String getAndroidURL() {
		return androidURL;
	}

	public void setAndroidURL(String androidURL) {
		this.androidURL = androidURL;
	}

}
