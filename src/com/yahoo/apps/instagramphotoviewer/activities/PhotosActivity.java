package com.yahoo.apps.instagramphotoviewer.activities;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.apps.instagramphotoviewer.R;
import com.yahoo.apps.instagramphotoviewer.adapters.InstagramPhotoAdapter;
import com.yahoo.apps.instagramphotoviewer.fragments.CommentsDialog;
import com.yahoo.apps.instagramphotoviewer.models.InstagramComment;
import com.yahoo.apps.instagramphotoviewer.models.InstagramPhoto;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class PhotosActivity extends FragmentActivity {
	public static final String CLIENT_ID="62b395714aa348e591fa0c2eef903e7d";
	private ArrayList<InstagramPhoto> popularPhotos;
	private InstagramPhotoAdapter aPhotos;
	private SwipeRefreshLayout swipeContainer;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photos);
		
		// set background color
		getWindow().getDecorView().setBackgroundColor(Color.GRAY);
		
		// fetch the default data
		fetchPopularPhotos();		
		
		swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        
		// Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
            	fetchPopularPhotos();
            } 
        });
        
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright, 
                android.R.color.holo_green_light, 
                android.R.color.holo_orange_light, 
                android.R.color.holo_red_light);
	}

	

	private void fetchPopularPhotos() {
		this.popularPhotos = new ArrayList<InstagramPhoto>();
		
		aPhotos = new InstagramPhotoAdapter(this, popularPhotos);
			
		ListView lvPhotos = (ListView) this.findViewById(R.id.lvPhotos);
		lvPhotos.setAdapter(aPhotos);
				
		// https://api.instagram.com/v1/media/popular?client_id=<clientid>		
		// Setup popular photo url endpoint
		String popularUrl = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID + "&count=50";
		
		// Create the network client
		AsyncHttpClient client = new AsyncHttpClient();
		
		// Trigger the network request
		client.get(popularUrl, new JsonHttpResponseHandler() {

			// define success and failure callbacks
			// Handle the successful response (popular photos JSON)			
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				//response.g
				Log.i("INFO", response.toString());
				
				JSONArray photosJSON = null;
				
				try {
					photosJSON = response.getJSONArray("data");					
					for (int i = 0; i < photosJSON.length(); i++) {
						try {
							JSONObject photoJSON = photosJSON.getJSONObject(i);
							InstagramPhoto photo = new InstagramPhoto();
							photo.username = photoJSON.getJSONObject("user").getString("username");
							photo.userProfileImageUrl = photoJSON.getJSONObject("user").getString("profile_picture");
							photo.caption = photoJSON.getJSONObject("caption").getString("text");
							photo.postTimestamp = photoJSON.getJSONObject("caption").getLong("created_time") * 1000l;

							photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
							photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
							photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");
							photo.commentsCount = photoJSON.getJSONObject("comments").getInt("count");

							// Comments handling
							ArrayList<InstagramComment> comments = this.commentExtract(photoJSON);
							photo.comments = comments;

							popularPhotos.add(photo);
						} catch (Exception ex) {
							continue;
						}
					}
					
					aPhotos.notifyDataSetChanged();
					swipeContainer.setRefreshing(false);
				} catch (JSONException e) {
					// Fired if things fail, json parsing is invalid
					e.printStackTrace();
				}
			}

			// Comment extraction
			private ArrayList<InstagramComment> commentExtract(JSONObject photoJSON) {
				ArrayList<InstagramComment> comments = new ArrayList<InstagramComment>();				
				try {
					JSONArray commentsJSON = photoJSON.getJSONObject("comments").getJSONArray("data");
					for (int j = 0; j < commentsJSON.length(); j++) {
						JSONObject commentJSON = commentsJSON.getJSONObject(j);
						
						InstagramComment comment = new InstagramComment();
						comment.username = commentJSON.getJSONObject("from").getString("username");
						comment.profileImageUrl = commentJSON.getJSONObject("from").getString("profile_picture");
						comment.postTimestamp = commentJSON.getLong("created_time");
						comment.text = commentJSON.getString("text");
						comments.add(comment);															
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				Collections.reverse(comments);
				return comments;
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
			}									
		});				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.photos, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.		
		return super.onOptionsItemSelected(item);
	}
}
