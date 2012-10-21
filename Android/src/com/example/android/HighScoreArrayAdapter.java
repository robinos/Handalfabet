package com.example.android;

/**
 * 	 Copyright© 2012, Grupp02
 * 
 *     This file is part of Handalfabetet.
 *
 *   Handalfabetet is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   Handalfabetet is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with Handalfabetet.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * The HighScoreArrayAdapter class is used to aid in displaying high scores.
 * 
 * @author : Grupp02
 * @version : 2012-10-19, v1.0
 */
public class HighScoreArrayAdapter extends ArrayAdapter<User> {

    private List<User> users;
    public LayoutInflater inflater;

    public HighScoreArrayAdapter(Context context, int textViewResourceId,
	    List<User> users) {
	super(context, textViewResourceId, users);
	this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	View rowView = inflater.inflate(R.layout.show_user_highscore, null);

	ImageView imageView = (ImageView) rowView.findViewById(R.id.user_img);
	TextView userNameView = (TextView) rowView.findViewById(R.id.username);
	TextView userHighScoreView = (TextView) rowView
		.findViewById(R.id.user_highscore);

	User user = users.get(position);
	imageView.setImageBitmap(user.getUserImg());
	userNameView.setText(user.getName());
	userHighScoreView.setText("Highscore: " + user.getHighScore());

	return rowView;
    }

}
