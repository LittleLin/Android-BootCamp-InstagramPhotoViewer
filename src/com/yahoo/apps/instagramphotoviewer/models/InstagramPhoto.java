package com.yahoo.apps.instagramphotoviewer.models;

import java.util.ArrayList;
import java.util.List;

public class InstagramPhoto {
	public String username;
	
	public String userProfileImageUrl;
	
	public String caption;
	
	public long postTimestamp; 
	
	public String imageUrl;
	
	public int imageHeight;
		
	public int likesCount;
	public int commentsCount;
	
	public List<InstagramComment> comments;
	
	public InstagramPhoto() {
		this.comments = new ArrayList<InstagramComment>();
	}
}
