package com.android.testassignment.connection;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import com.android.testassignment.objects.Facts;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class URLConnect {
	public HttpResponse accessURL(String url) {
		//Access the URL and get the response
		HttpResponse response = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			response = httpClient.execute(httpGet);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return response;	
	}
	
	public Facts parseResponse(HttpResponse response) {
		//Parse the JSON response and convert into JSONObject and list for convenience using the library GSON 
		Facts facts = new Facts();
		try {
			HttpEntity entity = response.getEntity();
			InputStream content = entity.getContent();
			// Read the server response and attempt to parse it as JSON
			Reader reader = new InputStreamReader(content);

			GsonBuilder gsonBuilder = new GsonBuilder();
			Gson gson = gsonBuilder.create();

			facts = gson.fromJson(reader, Facts.class);
			content.close();
		} catch (Exception e) {
			Log.e("TAG", "Failed to parse JSON due to: " + e);
			e.printStackTrace();
		}
		return facts;
	}
}
