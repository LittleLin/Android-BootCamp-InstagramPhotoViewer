package com.yahoo.apps.instagramphotoviewer.models;

public class InstagramComment {

	public String text;
	
	public long postTimestamp;

	public String username;

	public String profileImageUrl;

	@Override
	public String toString() {
		return this.text;
	}
}
