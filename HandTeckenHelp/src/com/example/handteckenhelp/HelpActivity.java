package com.example.handteckenhelp;

//http://www.aktivitetsbanken.se/aktivitetsbanken/images/1/14/Handalfabet.JPG

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class HelpActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_help, menu);
        return true;
    }
}
