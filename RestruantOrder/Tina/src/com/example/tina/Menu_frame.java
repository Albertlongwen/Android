package com.example.tina;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class Menu_frame extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_frame);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_frame, menu);
		return true;
	}

}
