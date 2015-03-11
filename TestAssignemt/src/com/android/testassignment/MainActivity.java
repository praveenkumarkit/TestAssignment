package com.android.testassignment;

import org.apache.http.HttpResponse;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.testassignemt.R;
import com.android.testassignment.adapter.NewsAdapter;
import com.android.testassignment.connection.URLConnect;
import com.android.testassignment.objects.Facts;

/** Activity to get list of facts from the URL and loads it in the UI **/
public class MainActivity extends Activity implements OnRefreshListener {

	private ListView factsListView;
	private Context context;
	private String factURL = "https://dl.dropboxusercontent.com/u/746330/facts.json";
	private ProgressDialog progress;
	private SwipeRefreshLayout swipeLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		factsListView = (ListView) findViewById(R.id.FactsListView);
		progress = new ProgressDialog(this);

		swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		swipeLayout.setOnRefreshListener(this);
		swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);

		// List Load Thread loads the fact items in the list using NewsAdapter
		new ListLoadThread().execute(factURL);
		showProgressDialog("Fetching facts");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		if (id == R.id.action_refresh) {
			showProgressDialog("Fetching facts");
			new ListLoadThread().execute(factURL);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	@Override
	public void onRefresh() {
		new ListLoadThread().execute(factURL);
	}

	/**
	 * Progress dialog will be shown once it starts loading list view during
	 * page start and click of refresh button. Takes argument message that need
	 * to be shown to user
	 **/
	private void showProgressDialog(String message) {

		progress.setMessage(message);
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.setIndeterminate(false);
		progress.show();
	}

	/**
	 * Hide the progress dialog once the list gets loaded with data
	 **/
	private void hideProgressDialog() {
		if (progress.isShowing())
			progress.hide();
		if (swipeLayout.isRefreshing())
			swipeLayout.setRefreshing(false);
	}

	/**
	 * Thread that runs asynchronously to fetch JSON data from URL and loads it
	 * in list
	 **/
	public class ListLoadThread extends AsyncTask<String, Integer, Facts> {
		NewsAdapter newsAdapter = null;

		@Override
		protected void onPreExecute() {
			factsListView.setAdapter(null);
		}

		@Override
		protected Facts doInBackground(String... params) {
			Facts facts = new Facts();
			// Get the JSON data from the URL
			URLConnect urlConnect = new URLConnect();
			HttpResponse response = urlConnect.accessURL(params[0]);
			// Parse the response
			facts = urlConnect.parseResponse(response);
			return facts;
		}

		@Override
		protected void onPostExecute(Facts facts) {
			hideProgressDialog();
			// Using ViewHolder Pattern for making the list view to scroll
			// smoothly
			// Lazy List Load Library used for loading of images
			// https://github.com/thest1/LazyList
			newsAdapter = new NewsAdapter(context, facts.getNews());
			factsListView.setAdapter(newsAdapter);
			setTitle(facts.getTitle());
		}
	}

}
