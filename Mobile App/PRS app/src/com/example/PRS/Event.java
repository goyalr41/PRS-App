package com.example.PRS;

import java.text.DateFormat;
import java.util.Calendar;

import library.BookingFunctions;

//import library.BookingFunctions;

import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class Event extends Activity {
	EditText dat,tim,num_peop,extras;
	String mobile,location,date,time,type_event,people,extra;
	Button b;
	
	Spinner spin,spin1;
	
	String newdate;
	String newtime;


	
	DateFormat fmtDateAndTime = DateFormat.getDateTimeInstance();
	TextView lblDateAndTime;
	Calendar myCalendar = Calendar.getInstance();
	
	String[] locs = {"Chandigarh","HanumanGarh","Kanpur","Mumbai","Jalandhar"};
	String[] events = {"Meeting & Conferences", "Engangement Party", "B'Day Party"};
	
	DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
		myCalendar.set(Calendar.YEAR, year);
		myCalendar.set(Calendar.MONTH, monthOfYear);
		myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		newdate = "";
		int mon = monthOfYear + 1;
		newdate = newdate + year + "-" + mon + "-" + dayOfMonth;
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
		setContentView(R.layout.event);

        final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
        final ActionBar.Action otherAction = new ActionBar.IntentAction(this, new Intent(this, MenuPage.class), R.drawable.ic_action_back);
        actionBar.addAction(otherAction);
        actionBar.setHomeAction(new ActionBar.IntentAction(this, MenuPage.createIntent(this), R.drawable.ic_title_home_default));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Event Booking");



        //	mob = (EditText)findViewById(R.id.event_mobile);
	//	loc = (EditText)findViewById(R.id.event_loc);
		
		lblDateAndTime = (TextView) findViewById(R.id.displaydate);
		Button btnDate = (Button) findViewById(R.id.ed_date);
		btnDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new DatePickerDialog(Event.this, d, myCalendar
						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});

		Button btnTime = (Button) findViewById(R.id.ed_time);
		btnTime.setOnClickListener(new View.OnClickListener() {
			public  void onClick(View v) {
				new TimePickerDialog(Event.this, t, myCalendar
						.get(Calendar.HOUR_OF_DAY), myCalendar
						.get(Calendar.MINUTE), true).show();
			}
		});

		updateLabel();
	
		
		
	
	
		num_peop = (EditText)findViewById(R.id.event_num_people);
		extras = (EditText)findViewById(R.id.event_extras);
		b = (Button)findViewById(R.id.event_button);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,locs);
		spin = (Spinner)findViewById(R.id.spinner1);
		spin.setAdapter(adapter);

		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,events);
		spin1 = (Spinner)findViewById(R.id.spinner2);
		spin1.setAdapter(adapter1);

	b.setOnClickListener(new View.OnClickListener(){
		
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	//	mobile = mob.getText().toString();
		location = spin.getSelectedItem().toString();
		//String datetime = lblDateAndTime.getText().toString();
		
		//date = dat.getText().toString();
		//time = tim.getText().toString();
		Log.e("new date and time is", newdate);
		Log.e("new date and time is", newtime);
		//System.out.println("new date and time is " + newdate + "time is " +  newtime);
		//Toast.makeText( getApplicationContext(), newdate,
          //      Toast.LENGTH_LONG ).show();
		//Toast.makeText( getApplicationContext(), newtime,
          //      Toast.LENGTH_LONG ).show();
		date = newdate;
		time = newtime;
		type_event = spin1.getSelectedItem().toString();
		people = num_peop.getText().toString();
		extra = extras.getText().toString();
		
		BookingFunctions bk = new BookingFunctions();
		JSONObject json = bk.Event(mobile,location,date,time,type_event,people,extra);
		
		Intent i = new Intent(getApplicationContext(),Menu_list.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		finish(); 
	//	setContentView(R.layout.menu_list);
	}

	
	});
	}
    @Override
    public void onPause() {
        super.onPause();
        finish();
    }
}
