package com.example.PRS;

import java.text.DateFormat;
import java.util.Calendar;

import library.ActivitySwitcher;
import library.BookingFunctions;

//import library.ActivitySwitcher;
//import library.BookingFunctions;

import org.json.JSONException;
import org.json.JSONObject;

//import com.example.delivery.R;
//import com.example.delivery.CardFlipActivity.CardBackFragment;
//import com.example.delivery.CardFlipActivity.CardFrontFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class Table extends Activity  {
	
	Spinner spin;
	String[] locs = {"Chandigarh","HanumanGarh","Kanpur"};
	
	EditText dat,tim,num_peop,extras;
	String mobile,location,date,time,people,extra;
	Button b;
	
	String newdate;
	String newtime;
	
	
	DateFormat fmtDateAndTime = DateFormat.getDateTimeInstance();
	TextView lblDateAndTime;
	Calendar myCalendar = Calendar.getInstance();
	
		
	DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
		myCalendar.set(Calendar.YEAR, year);
		myCalendar.set(Calendar.MONTH, monthOfYear);
		myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		newdate = "";
		int month = monthOfYear + 1;
		newdate = newdate + year + "-" + month + "-" + dayOfMonth;
		updateLabel();
		}
		};

		TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
			myCalendar.set(Calendar.MINUTE, minute);
			newtime = "";
			newtime = newtime + hourOfDay + ":" + minute;
			updateLabel();
		}
		};
		
		private void updateLabel() {
			lblDateAndTime.setText(fmtDateAndTime.format(myCalendar.getTime()));
			
		}
		
		
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.table);
	//	ActivitySwitcher.animationIn(findViewById(R.id.container), getWindowManager());
        final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
        final ActionBar.Action otherAction = new ActionBar.IntentAction(this, new Intent(this, MenuPage.class), R.drawable.ic_action_back);
        actionBar.addAction(otherAction);
        actionBar.setHomeAction(new ActionBar.IntentAction(this, MenuPage.createIntent(this), R.drawable.ic_title_home_default));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Table Booking");
		
		lblDateAndTime = (TextView) findViewById(R.id.displaydate1);
		Button btnDate = (Button) findViewById(R.id.ed_date1);
		btnDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new DatePickerDialog(Table.this, d, myCalendar
						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});

		Button btnTime = (Button) findViewById(R.id.ed_time1);
		btnTime.setOnClickListener(new View.OnClickListener() {
			public  void onClick(View v) {
				new TimePickerDialog(Table.this, t, myCalendar
						.get(Calendar.HOUR_OF_DAY), myCalendar
						.get(Calendar.MINUTE), true).show();
			}
		});

		updateLabel();
	
	//	mob = (EditText)findViewById(R.id.e1);
		//loc = (EditText)findViewById(R.id.e2);
		//dat = (EditText)findViewById(R.id.editText3);
		//tim = (EditText)findViewById(R.id.editText4);
		
		num_peop = (EditText)findViewById(R.id.editText5);
		extras = (EditText)findViewById(R.id.editText6);
		b = (Button)findViewById(R.id.button1);
		
	ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,locs);
	spin = (Spinner)findViewById(R.id.spin1);
	spin.setAdapter(adapter);
	//spin.setOnItemClickListener(this);

	
	b.setOnClickListener(new View.OnClickListener(){
		
	@Override
	public void onClick(View v) {

		Log.e("new date and time is", newdate);
		Log.e("new date and time is", newtime);
		
		
		if (newdate == "") {
			Toast.makeText( getApplicationContext(), "SETDATE AND TIME",
			                Toast.LENGTH_LONG ).show();
		}
		date = newdate;
		time = newtime;
		people = num_peop.getText().toString();
		extra = extras.getText().toString();
		location = spin.getSelectedItem().toString();
		BookingFunctions bk = new BookingFunctions();
		JSONObject json = bk.Book_table(mobile,location,date,time,people,extra);
		
	
		try {
		//	Log.e("check",json.getString("success"));
			String s = json.getString("success");
			int x = Integer.parseInt(s);
			if(x == 1){
				animatedStartActivity();
			}else{
				mytoast();
				//animatedStartActivity();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finish(); 
	}
	});
	}

    @Override
    public void onPause() {
        super.onPause();
        finish();
    }

	public void mytoast()
	{
		Toast.makeText(this,"Sorry we already full",Toast.LENGTH_LONG).show();
	}
/*	protected void onResume() {
		// animateIn this activity
		//ActivitySwitcher.animationIn(findViewById(R.id.container), getWindowManager());
		super.onResume();
	}*/
	
	private void animatedStartActivity() {
		// we only animateOut this activity here.
		// The new activity will animateIn from its onResume() - be sure to implement it.
		final Intent intent = new Intent(getApplicationContext(), Menu_list.class);
		// disable default animation for new intent
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		
		ActivitySwitcher.animationOut(findViewById(R.id.table), getWindowManager(), new ActivitySwitcher.AnimationFinishedListener() {
			@Override
			public void onAnimationFinished() {
				startActivity(intent);
			}
		});
	}
}


