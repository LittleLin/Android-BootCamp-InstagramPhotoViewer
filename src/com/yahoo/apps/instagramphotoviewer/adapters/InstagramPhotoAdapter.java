package com.yahoo.apps.instagramphotoviewer.adapters;

import java.util.List;

import com.squareup.picasso.Picasso;
import com.yahoo.apps.instagramphotoviewer.R;
import com.yahoo.apps.instagramphotoviewer.models.InstagramPhoto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class InstagramPhotoAdapter extends ArrayAdapter<InstagramPhoto> {
	public InstagramPhotoAdapter(Context context, List<InstagramPhoto> photos) {
		super(context, android.R.layout.simple_list_item_1, photos);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		// Take the data source at position		
		// Get the data item
		InstagramPhoto photo = this.getItem(position);
		
		// Check if we are using the recycled view  
		if (convertView == null) {
			convertView = LayoutInflater.from(this.	getContext()).inflate(R.layout.item_photo , parent, false);
		}
		
		// Lookup the subview within the template
		TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
		ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.imgPhoto);
		
		// Populate the subview (textfield, imageview) with the correct data 
		tvCaption.setText(photo.caption);
		
		// Set the image height before image loading
		imgPhoto.getLayoutParams().height = photo.imageHeight;
		
		// Set the image from the recycled view
		imgPhoto.setImageResource(0);
		
		// Download the image and insert the image i nto the image view
		Picasso.with(this.getContext()).load(photo.imageUrl).into(imgPhoto);
		
		// Return the view for that data item
		return convertView;
	}	
}
