package com.android.testassignment.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.testassignemt.R;
import com.android.testassignment.objects.News;
import com.fedorvlasov.lazylist.ImageLoader;

/** Adapter that uses BaseAdapter for inflating the List View **/
public class NewsAdapter extends BaseAdapter {
	private List<News> newsList;
	private Context context;
	private ViewHolderItem viewHolder;
	private LayoutInflater inflater;
	private ImageLoader imageLoader;

	public NewsAdapter(Context context, List<News> newsList) {
		this.context = context;
		this.newsList = newsList;
		imageLoader = new ImageLoader(context);
	}

	@Override
	public int getCount() {
		return newsList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			// inflate the layout
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.row, parent, false);
			// Set up the ViewHolder for smooth scroll
			viewHolder = new ViewHolderItem();
			viewHolder.titleTextView = (TextView) convertView
					.findViewById(R.id.TitleTextView);
			viewHolder.descriptionTextView = (TextView) convertView
					.findViewById(R.id.DescriptionTextView);
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.FactImageView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolderItem) convertView.getTag();
		}

		News newsItem = newsList.get(position);
		if (newsItem != null) {
			// get the TextView from the ViewHolder and then set the text (item
			// name) and tag (item ID) values
			if (newsItem.getTitle() != null)
				viewHolder.titleTextView.setText(newsItem.getTitle());
			else
				viewHolder.titleTextView.setText("");
			if (newsItem.getDescription() != null)
				viewHolder.descriptionTextView.setText(newsItem
						.getDescription());
			else 
				viewHolder.descriptionTextView.setText("");
			if (newsItem.getImageURL() != null
					&& !newsItem.getImageURL().equals("")) {
				viewHolder.imageView.setVisibility(View.VISIBLE);
				// Lazy List Load Library used for loading of images
				// https://github.com/thest1/LazyList
				imageLoader.DisplayImage(newsItem.getImageURL(),
						viewHolder.imageView);
			} else {
				viewHolder.imageView.setVisibility(View.INVISIBLE);
			}
		}

		return convertView;
	}

	static class ViewHolderItem {
		TextView titleTextView;
		TextView descriptionTextView;
		ImageView imageView;
	}
}
