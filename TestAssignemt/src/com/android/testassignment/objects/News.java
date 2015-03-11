package com.android.testassignment.objects;

import com.google.gson.annotations.SerializedName;

public class News {
	@SerializedName("title")
	public String title = "";
	@SerializedName("description")
	public String description = "";
	@SerializedName("imageHref")
	public String imageURL = "";

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
}
