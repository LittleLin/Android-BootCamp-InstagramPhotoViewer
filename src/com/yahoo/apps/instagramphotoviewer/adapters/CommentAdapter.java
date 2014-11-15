package com.yahoo.apps.instagramphotoviewer.adapters;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.yahoo.apps.instagramphotoviewer.models.InstagramComment;

public class CommentAdapter extends ArrayAdapter<InstagramComment> {
	public CommentAdapter(Context context, List<InstagramComment> photos) {
		super(context, android.R.layout.simple_list_item_1, photos);
	}
}
