package com.example.PRS;

//import android.R;
import android.content.Intent;
import library.BookingFunctions;

import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Newsletter extends Activity {
	EditText email;
	TextView subscribe;
	String mobile;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newsletter);

        final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
        final ActionBar.Action otherAction = new ActionBar.IntentAction(this, new Intent(this, MenuPage.class), R.drawable.ic_action_back);
        actionBar.addAction(otherAction);
        actionBar.setHomeAction(new ActionBar.IntentAction(this, MenuPage.createIntent(this), R.drawable.ic_title_home_default));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Event Booking");
		
		email = (EditText)findViewById(R.id.email);
		subscribe = (TextView)findViewById(R.id.subs);
		mobile = "8004145814";
		subscribe.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				String email1 = email.getText().toString();
				//BookingFunctions b = new BookingFunctions();
				BookingFunctions bk = new BookingFunctions();
				JSONObject json = bk.newsletter(mobile,email1);
				
			}
				// TODO Auto-generated method s
			
		});	
		
	}

//	@Override
/*	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	} */

}
