package com.android.testassignment.objects;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Facts {

	@SerializedName("title")
	public String title;
	@SerializedName("rows")
	List<News> news;
	
	public Facts(){
		this.title = "";
		this.news = new ArrayList<News>();
	}
	
	public Facts(String title, List<News> news) {
		this.title = title;
		this.news = news;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<News> getNews() {
		return news;
	}

	public void setNews(List<News> news) {
		this.news = news;
	}

}
