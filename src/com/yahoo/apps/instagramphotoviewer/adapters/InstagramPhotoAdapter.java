package com.yahoo.apps.instagramphotoviewer.adapters;

import java.util.Calendar;
import java.util.List;

import com.squareup.picasso.Picasso;
import com.yahoo.apps.instagramphotoviewer.R;
import com.yahoo.apps.instagramphotoviewer.models.InstagramPhoto;
import com.yahoo.apps.instagramphotoviewer.widgets.CircularImageView;
import com.yahoo.apps.instagramphotoviewer.widgets.RoundedImageView;

import android.content.Context;
import android.text.format.DateUtils;
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
		TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
		TextView tvTimestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);
		ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.imgPhoto);
		CircularImageView imgUserProfile = (CircularImageView) convertView.findViewById(R.id.imgUserProfile);
		
		// Populate the subview (textfield, imageview) with the correct data 
		tvCaption.setText(photo.caption);
		tvUsername.setText(photo.username);
		tvTimestamp.setText(DateUtils.getRelativeTimeSpanString(photo.postTimestamp, Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS));
		
		// Set the image height before image loading
		//imgPhoto.getLayoutParams().height = 320;
		//imgUserProfile.getLayoutParams().height = 60;
		
		// Set the image from the recycled view
		imgPhoto.setImageResource(0);
		imgUserProfile.setImageResource(0);
		
		// Download the image and insert the image i nto the image view
		Picasso.with(this.getContext()).load(photo.imageUrl).placeholder(R.drawable.placeholder_256).into(imgPhoto);		
		Picasso.with(this.getContext()).load(photo.userProfileImageUrl).into(imgUserProfile);
		
		// Return the view for that data item
		return convertView;
	}	
}
