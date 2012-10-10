package com.example.android;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UserArrayAdapter extends ArrayAdapter<User> {

	private List<User> users;
	public LayoutInflater inflater;
	
	public UserArrayAdapter(Context context, int textViewResourceId, List<User> users) {
		super(context, textViewResourceId, users);
		this.users = users;
	}
	
	@Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    View rowView = inflater.inflate(R.layout.activity_user_array_adapter, null);
	    
	    ImageView imageView = (ImageView) rowView.findViewById(R.id.user_pic);
	    TextView userNameView = (TextView) rowView.findViewById(R.id.user_name);
		 
	    User user = users.get(position);
	    imageView.setImageBitmap(user.getUserImg());
	    userNameView.setText(user.getName());
	   
	    return rowView;
	}
	 	
}
