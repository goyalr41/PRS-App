package com.example.PRS;

import org.json.JSONException;
import org.json.JSONObject;

import library.BookingFunctions;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Delivery extends Activity {
	Button b;
	EditText mob,add;
	String num;
	String address;
	
	private static String KEY_SUCCESS = "success";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delivery);

        final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
        final ActionBar.Action otherAction = new ActionBar.IntentAction(this, new Intent(this, MenuPage.class), R.drawable.ic_action_back);
        actionBar.addAction(otherAction);
        actionBar.setHomeAction(new ActionBar.IntentAction(this, MenuPage.createIntent(this), R.drawable.ic_title_home_default));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Home Delivery");
		
		b = (Button)findViewById(R.id.bt);
		mob = (EditText)findViewById(R.id.mobile);
		add = (EditText)findViewById(R.id.address);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
					num = mob.getText().toString();
					address = add.getText().toString();
					
					BookingFunctions bk = new BookingFunctions();
					JSONObject json = bk.Delivery(num,address);
					
					Intent i = new Intent(getApplicationContext(),Menu_list.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    @Override
    public void onPause() {
        super.onPause();
        finish();
    }

}
