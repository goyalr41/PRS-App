package com.example.PRS;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import library.DatabaseHandler;
import library.UserFunctions;
import org.json.JSONException;
import org.json.JSONObject;


public class login extends Activity {
    /**
     * Called when the activity is first created.
     */
    EditText mb;
    EditText pw;
    Button buttonlogin;
    RelativeLayout layoutView;
    TextView notreg;
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
        setContentView(R.layout.main);
        mb = (EditText) findViewById(R.id.MobileNumber);
        pw = (EditText) findViewById(R.id.Pass);
        buttonlogin = (Button) findViewById(R.id.button);
        notreg = (TextView)  findViewById(R.id.notregister);

        final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
        actionBar.setTitle("Login");

        final ActionBar.Action otherAction = new ActionBar.IntentAction(this, new Intent(this, register.class), R.drawable.ic_action_front);
        actionBar.addAction(otherAction);

        layoutView = (RelativeLayout)findViewById(R.id.relativelay);

        layoutView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(mb.getWindowToken(), 0);
                return true;
            }
        });

        buttonlogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String mobile = mb.getText().toString();
                String password = pw.getText().toString();
                UserFunctions userFunction = new UserFunctions();
                Log.d("Button", "Login");
                JSONObject json = userFunction.loginUser(mobile, password);

                // fooddescrip for login response
                try {
                    if (json.getString(KEY_SUCCESS) != null) {
                        //loginErrorMsg.setText("");
                        String res = json.getString(KEY_SUCCESS);
                        if(Integer.parseInt(res) == 1){
                            // user successfully logged in
                            // Store user details in SQLite Database
                            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                            JSONObject json_user = json.getJSONObject("user");

                            // Clear all previous data in database
                            userFunction.logoutUser(getApplicationContext());
                            db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_MOBILE), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));

                            // Launch Dashboard Screen
                           // Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
                            mytoast();
                            Intent ourIntent   = new Intent(login.this, MenuPage.class);
                            startActivity(ourIntent);

                            // Close all views before launching Dashboard
                            //dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //startActivity(dashboard);

                            // Close Login Screen
                            finish();
                        }else{
                            // Error in login
                            //loginErrorMsg.setText("Incorrect username/password");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        });
        notreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //To change body of implemented methods use File | Settings | File Templates.
                //Toast.makeText(this,"Login Successful",Toast.LENGTH_LONG).show();
                Intent ourIntent   = new Intent(login.this, register.class);
                startActivity(ourIntent);
            }
        }) ;

    }
    public void mytoast(){
        Toast.makeText(this,"Login Successful",Toast.LENGTH_LONG).show();
    }





    /*public void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }   */

}

