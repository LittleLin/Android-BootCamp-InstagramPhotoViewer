package com.yahoo.apps.instagramphotoviewer.adapters;

import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yahoo.apps.instagramphotoviewer.R;
import com.yahoo.apps.instagramphotoviewer.models.InstagramComment;
import com.yahoo.apps.instagramphotoviewer.models.InstagramPhoto;
import com.yahoo.apps.instagramphotoviewer.widgets.CircularImageView;

public class CommentAdapter extends ArrayAdapter<InstagramComment> {
	public CommentAdapter(Context context, List<InstagramComment> photos) {
		super(context, android.R.layout.simple_list_item_1, photos);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		// Take the data source at position		
		// Get the data item
		InstagramComment comment = this.getItem(position);
		
		// Check if we are using the recycled view  
		if (convertView == null) {
			convertView = LayoutInflater.from(this.	getContext()).inflate(R.layout.item_comment , parent, false);
		}
		
		TextView tvCommentUsername = (TextView) convertView.findViewById(R.id.tvCommentUsername);
		TextView tvCommentMessage = (TextView) convertView.findViewById(R.id.tvCommentMessage);
		TextView tvCommentTimestamp = (TextView) convertView.findViewById(R.id.tvCommentTimestamp);
		CircularImageView imgCommentUserProfile = (CircularImageView) convertView.findViewById(R.id.imgCommentUserProfile);
		
		tvCommentUsername.setText(comment.username);
		tvCommentMessage.setText(comment.text);
		tvCommentTimestamp.setText(DateUtils.getRelativeTimeSpanString(comment.postTimestamp * 1000, Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS));
		Picasso.with(this.getContext()).load(comment.profileImageUrl).into(imgCommentUserProfile);
		
		// Return the view for that data item
		return convertView;
	}
}
