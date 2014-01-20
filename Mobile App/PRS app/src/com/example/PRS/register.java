package com.example.PRS;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import org.json.JSONException;
import org.json.JSONObject;

import library.DatabaseHandler;
import library.UserFunctions;

public class register extends Activity {
    /**
     * Called when the activity is first created.
     */
    EditText mb;
    EditText pw;
    EditText name1;
    Button buttonreg;
    RelativeLayout layoutView;
    TextView aldreg;
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_MOBILE = "mobile";
    private static String KEY_CREATED_AT = "created_at";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mb = (EditText) findViewById(R.id.registermobileno);
        pw = (EditText) findViewById(R.id.resgisterpwd);
        name1 = (EditText) findViewById(R.id.Name);
        buttonreg = (Button) findViewById(R.id.registerbutton);
        aldreg = (TextView) findViewById(R.id.aldreg);
        final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
        actionBar.setTitle("Register");

        final ActionBar.Action otherAction = new ActionBar.IntentAction(this, new Intent(this, login.class), R.drawable.ic_action_back);
        actionBar.addAction(otherAction);


        layoutView = (RelativeLayout)findViewById(R.id.LayoutRegister);

        layoutView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(mb.getWindowToken(), 0);
                return true;
            }
        });

        buttonreg.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String name = name1.getText().toString();
                String mobile = mb.getText().toString();
                String password = pw.getText().toString();
                UserFunctions userFunction = new UserFunctions();
                JSONObject json = userFunction.registerUser(name, mobile, password);

                // fooddescrip for login response
                try {
                    if (json.getString(KEY_SUCCESS) != null) {
                        //registerErrorMsg.setText("");
                        String res = json.getString(KEY_SUCCESS);
                        if(Integer.parseInt(res) == 1){
                            // user successfully registred
                            // Store user details in SQLite Database
                            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                            JSONObject json_user = json.getJSONObject("user");

                            // Clear all previous data in database
                            userFunction.logoutUser(getApplicationContext());
                            db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_MOBILE), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));
                            // Launch Dashboard Screen
                            //Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
                            mytoast();
                            Intent ourIntent   = new Intent(register.this, MenuPage.class);
                            startActivity(ourIntent);
                            // Close all views before launching Dashboard
                            //dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //startActivity(dashboard);
                            // Close Registration Screen
                            finish();
                        }else{
                            // Error in registration
                            //registerErrorMsg.setText("Error occured in registration");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        aldreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //To change body of implemented methods use File | Settings | File Templates.
                //Toast.makeText(this,"Login Successful",Toast.LENGTH_LONG).show();
                Intent ourIntent   = new Intent(register.this, login.class);
                startActivity(ourIntent);
            }
        }) ;


    }

    public void mytoast(){
        Toast.makeText(this,"Registration Successful",Toast.LENGTH_LONG).show();
    }


    /*public void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }   */

}

