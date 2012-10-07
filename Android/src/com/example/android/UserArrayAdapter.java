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

	private Context context;
	private List<User> users;
	public LayoutInflater inflater;
	
	public UserArrayAdapter(Context context, int textViewResourceId, List<User> users) {
		super(context, textViewResourceId, users);
		this.users = users;
	}
	
	@Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    View rowView = inflater.inflate(R.layout.show_user_highscore, null);
	    
	    ImageView imageView = (ImageView) rowView.findViewById(R.id.user_img);
	    TextView userNameView = (TextView) rowView.findViewById(R.id.username);
	    TextView userHighScoreView = (TextView) rowView.findViewById(R.id.user_highscore);
		
	    User user = users.get(position);
	    imageView.setImageResource(R.drawable.ic_launcher);
	    userNameView.setText(user.getName());
	    userHighScoreView.setText("Highscore: " + user.getHighScore());
	    
	    return rowView;
	}
	 	
}
