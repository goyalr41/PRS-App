package com.example.PRS;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;


public class FoodDescActivity extends Activity {


    String position = "1";
    String city = "";
    String weather = "";
    String temperature = "";
    String windSpeed = "";
    String iconfile = "";
    ImageButton imgWeatherIcon;
    Drawable dimgbutton;

    TextView tvcity;
    TextView tvtemp;
    TextView tvwindspeed;
    TextView tvCondition;
    Button addtocart;
    EditText editTextQuantity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fooddescrip);
        final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
        String my1 =  getIntent().getStringExtra("type");
        Intent igh1 = new Intent();
        igh1.setClass(this, ShoppingCartActivity.class);
        igh1.putExtra("type",my1 );
        final ActionBar.Action shareAction = new ActionBar.IntentAction(this, igh1, R.drawable.shopping_cart);
        actionBar.addAction(shareAction);
        String my =  getIntent().getStringExtra("type");
        Intent igh = new Intent();
        igh.setClass(this, FoodDataAvtivity.class);
        igh.putExtra("type",my );
        final ActionBar.Action otherAction = new ActionBar.IntentAction(this, igh, R.drawable.ic_action_back);
      actionBar.addAction(otherAction);

        actionBar.setHomeAction(new ActionBar.IntentAction(this, MenuPage.createIntent(this), R.drawable.ic_title_home_default));
       actionBar.setDisplayHomeAsUpEnabled(true);


        try {

            //handle for the UI elements
            imgWeatherIcon = (ImageButton) findViewById(R.id.imageButtonAlpha);
            //Text fields
            tvcity = (TextView) findViewById(R.id.textViewCity);
            tvtemp = (TextView) findViewById(R.id.textViewTemperature);
            //tvwindspeed = (TextView) findViewById(R.id.textViewWindSpeed);
            tvCondition = (TextView) findViewById(R.id.textViewCondition);
            editTextQuantity = (EditText) findViewById(R.id.editTextquant);
            Typeface tf = Typeface.createFromAsset(getAssets(),
                    "Fonts/FRSCRIPT.TTF");
            tvcity.setTypeface(tf);
            Typeface tf1 = Typeface.createFromAsset(getAssets(),
                    "Fonts/HARNGTON.TTF");

            Typeface tf2 = Typeface.createFromAsset(getAssets(),
                    "Fonts/POORICH.TTF");
            tvCondition.setTypeface(tf2);
            tvtemp.setTypeface(tf1);


            addtocart  = (Button) findViewById(R.id.addtocartbutton);

            // Get position to display
            Intent i = getIntent();

            this.position = i.getStringExtra("position");
            this.city = i.getStringExtra("city");
            this.weather=	i.getStringExtra("weather");
            this.temperature =  i.getStringExtra("temperature");
            this.windSpeed =  i.getStringExtra("windspeed");
            this.iconfile = i.getStringExtra("icon");

            System.out.println(position+city+weather+temperature+windSpeed+iconfile);
            String uri = "drawable/" + iconfile;
            int imageBtnResource = getResources().getIdentifier(uri, null, getPackageName());
            dimgbutton = getResources().getDrawable(imageBtnResource);


            //text elements
            tvcity.setText(city);
            tvtemp.setText(temperature);
            //tvwindspeed.setText(windSpeed);
            tvCondition.setText(weather);
            Product p = new Product(city.trim(),dimgbutton,weather.trim(),Double.parseDouble(temperature.substring(3,temperature.length()).trim()));
            int quan = ShoppingCartHelper.getProductQuantity(p);
            String s = "";
            s = s + quan;
            editTextQuantity.setText(s);
            //thumb_image.setImageDrawable(image);
            imgWeatherIcon.setImageDrawable(dimgbutton);
            actionBar.setTitle(city);

            TableLayout layoutView = (TableLayout) findViewById(R.id.fooddesclayout);
            layoutView.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(editTextQuantity.getWindowToken(), 0);
                    return true;
                }
            });

            addtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     Product p = new Product(city.trim(),dimgbutton,weather.trim(),Double.parseDouble(temperature.substring(3,temperature.length()).trim()));
                    int s = p.hashCode();
                   // System.out.println("Printing hashcode" + s) ;
                    // Check to see that a valid quantity was entered
                    int quantity = 0;
                    try {
                        quantity = Integer.parseInt(editTextQuantity.getText().toString().trim());

                        if (quantity < 0) {
                            Toast.makeText(getBaseContext(),
                                    "Please enter a quantity of 0 or higher",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                    } catch (Exception e) {
                        Toast.makeText(getBaseContext(),
                                "Please enter a numeric quantity",
                                Toast.LENGTH_SHORT).show();

                        return;
                    }

                    // If we make it here, a valid quantity was entered
                    ShoppingCartHelper.setQuantity(p, quantity);

                    // Close the activity
                    finish();
                }
            });


        }

        catch (Exception ex) {
            Log.e("Error", "Loading exception");
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
