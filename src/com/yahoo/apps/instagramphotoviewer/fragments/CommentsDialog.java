package com.yahoo.apps.instagramphotoviewer.fragments;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.yahoo.apps.instagramphotoviewer.R;
import com.yahoo.apps.instagramphotoviewer.adapters.CommentAdapter;
import com.yahoo.apps.instagramphotoviewer.models.InstagramComment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;

public class CommentsDialog extends DialogFragment {
	public CommentsDialog() {
		// Empty constructor required for DialogFragment
	}
	
	public static CommentsDialog newInstance(String title, List<InstagramComment> comments) {
		CommentsDialog frag = new CommentsDialog();
		Bundle args = new Bundle();
		args.putString("title", title);				
		args.putSerializable("comments", (Serializable) comments);
		
		frag.setArguments(args);
		return frag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_comments, container, true);
		String title = getArguments().getString("title", "Comments");
		getDialog().setTitle(title);
		
		getDialog().requestWindowFeature(DialogFragment.STYLE_NO_TITLE | DialogFragment.STYLE_NORMAL);		
		getDialog().setCancelable(true);
		getDialog().setCanceledOnTouchOutside(true);
		
		List<InstagramComment> comments = (List<InstagramComment>) getArguments().getSerializable("comments"); 
		ListView lvComments = (ListView) view.findViewById(R.id.lvComments);
				
		CommentAdapter adapter = new CommentAdapter(view.getContext(), comments);
		lvComments.setAdapter(adapter);
		
		return view;
	}
}
