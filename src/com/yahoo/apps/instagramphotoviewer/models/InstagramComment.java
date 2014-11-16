package com.yahoo.apps.instagramphotoviewer.models;

import java.io.Serializable;

public class InstagramComment implements Serializable {

	public String text;
	
	public long postTimestamp;

	public String username;

	public String profileImageUrl;

	@Override
	public String toString() {
		return this.text;
	}
}
