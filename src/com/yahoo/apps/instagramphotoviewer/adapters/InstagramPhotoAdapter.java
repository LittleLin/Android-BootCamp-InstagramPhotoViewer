package com.yahoo.apps.instagramphotoviewer.adapters;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import com.squareup.picasso.Picasso;
import com.yahoo.apps.instagramphotoviewer.R;
import com.yahoo.apps.instagramphotoviewer.fragments.CommentsDialog;
import com.yahoo.apps.instagramphotoviewer.models.InstagramComment;
import com.yahoo.apps.instagramphotoviewer.models.InstagramPhoto;
import com.yahoo.apps.instagramphotoviewer.widgets.CircularImageView;
import com.yahoo.apps.instagramphotoviewer.widgets.RoundedImageView;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
		TextView tvLikesCount = (TextView) convertView.findViewById(R.id.tvLikesCount);
		TextView tvCommentsCount = (TextView) convertView.findViewById(R.id.tvCommentsCount);
		RelativeLayout layoutComments = (RelativeLayout) convertView.findViewById(R.id.layoutComments);
		
		TextView tvShowMoreComments = (TextView) convertView.findViewById(R.id.tvShowMoreComments);
		
		CircularImageView imgUserProfile = (CircularImageView) convertView.findViewById(R.id.imgUserProfile);
		
		// Populate the subview (textfield, imageview) with the correct data 
		tvCaption.setText(photo.caption);
		tvUsername.setText(photo.username);
		tvTimestamp.setText(DateUtils.getRelativeTimeSpanString(photo.postTimestamp, Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS));
				
		DecimalFormat formatter = new DecimalFormat("#,###,###");
		String likesCountDisplayText = String.format("%s Likes", formatter.format(photo.likesCount)); 
		tvLikesCount.setText(likesCountDisplayText);		
		String likesCommentsDisplayText = String.format("%s Comments", formatter.format(photo.commentsCount)); 
		tvCommentsCount.setText(likesCommentsDisplayText);
				
		tvShowMoreComments.setOnClickListener(new CommentsClickListener(photo));
		
		// Set the image from the recycled view
		imgPhoto.setImageResource(0);
		imgUserProfile.setImageResource(0);
		
		// Download the image and insert the image i nto the image view
		Picasso.with(this.getContext()).load(photo.imageUrl).placeholder(R.drawable.placeholder_256).into(imgPhoto);		
		Picasso.with(this.getContext()).load(photo.userProfileImageUrl).into(imgUserProfile);		

		// Populate the comments
		this.populateComments(layoutComments, photo.comments);
		
		// Return the view for that data item
		return convertView;
	}

	private void populateComments(RelativeLayout layoutComments, List<InstagramComment> comments) {
		TextView tvCommentUsername1 = (TextView) layoutComments.findViewById(R.id.tvCommentUsername1);
		TextView tvCommentMessage1 = (TextView) layoutComments.findViewById(R.id.tvCommentMessage1);
		TextView tvCommentTimestamp1 = (TextView) layoutComments.findViewById(R.id.tvCommentTimestamp1);
		CircularImageView imgCommentUserProfile1 = (CircularImageView) layoutComments.findViewById(R.id.imgCommentUserProfile1);
		
		TextView tvCommentUsername2 = (TextView) layoutComments.findViewById(R.id.tvCommentUsername2);
		TextView tvCommentMessage2 = (TextView) layoutComments.findViewById(R.id.tvCommentMessage2);
		TextView tvCommentTimestamp2 = (TextView) layoutComments.findViewById(R.id.tvCommentTimestamp2);
		CircularImageView imgCommentUserProfile2 = (CircularImageView) layoutComments.findViewById(R.id.imgCommentUserProfile2);
		
		
		if (comments.size() >= 1) {
			InstagramComment firstComment = comments.get(0);
			tvCommentUsername1.setText(firstComment.username);
			tvCommentMessage1.setText(firstComment.text);
			tvCommentTimestamp1.setText(DateUtils.getRelativeTimeSpanString(firstComment.postTimestamp * 1000, Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS));
			Picasso.with(this.getContext()).load(firstComment.profileImageUrl).into(imgCommentUserProfile1);
		}
		
		if (comments.size() >= 2) {
			InstagramComment secondComment = comments.get(1);
			tvCommentUsername2.setText(secondComment.username);
			tvCommentMessage2.setText(secondComment.text);
			tvCommentTimestamp2.setText(DateUtils.getRelativeTimeSpanString(secondComment.postTimestamp * 1000, Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS));
			Picasso.with(this.getContext()).load(secondComment.profileImageUrl).into(imgCommentUserProfile2);
		}
	}	
	
	private class CommentsClickListener implements OnClickListener {
		private InstagramPhoto photo;
		
		public CommentsClickListener(InstagramPhoto photo) {
			this.photo = photo;
		}

		@Override
		public void onClick(View v) {
			FragmentActivity activity = (FragmentActivity)(getContext());
			FragmentManager fm = activity.getSupportFragmentManager();
		    CommentsDialog commentsDialog = CommentsDialog.newInstance("Comments", this.photo.comments);
		    commentsDialog.show(fm, "fragment_comments");			
		}
	}
}
