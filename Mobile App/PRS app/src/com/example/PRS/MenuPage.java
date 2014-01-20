package com.example.PRS;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.*;
import android.widget.ViewFlipper;

import java.io.InputStream;

import static com.example.PRS.R.*;

public class MenuPage extends Activity implements View.OnTouchListener {
    /**
     * Called when the activity is first created.
     */
     private float downXValue;
    Thread th;
    public int[] mThumbIds = {

            R.drawable.restau1

    };
    RelativeLayout layoutView;
    ImageView iv;
    Bitmap bmp;
    Button menuse;
    Button homedel;
    Button tablebook;
    Button eventbook;
    Button gmap;
    Button newsletter;
    Button video;
    Button callme;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.menu);

    layoutView = (RelativeLayout)findViewById(id.relativelay2);
        final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
        final ActionBar.Action shareAction = new ActionBar.IntentAction(this,  new Intent(this, ShoppingCartActivity.class), R.drawable.shopping_cart);
        actionBar.addAction(shareAction);
        final ActionBar.Action otherAction = new ActionBar.IntentAction(this, new Intent(this, FoodDataAvtivity.class), R.drawable.ic_action_back);
        actionBar.addAction(otherAction);
        actionBar.setHomeAction(new ActionBar.IntentAction(this, MenuPage.createIntent(this), R.drawable.ic_title_home_default));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Home");

        layoutView.setOnTouchListener((View.OnTouchListener)this);
        //iv = (ImageView) findViewById(id.imageView);

        menuse = (Button) findViewById(id.quickmenubutton);
        homedel = (Button) findViewById(id.homedelbutton);
        tablebook = (Button) findViewById(id.tablebookbutton);
        eventbook = (Button) findViewById(id.eventbookbutton);
        gmap = (Button) findViewById(id.gmapbutton);
        newsletter = (Button) findViewById(id.newsletterbutton);
        video = (Button) findViewById(id.videobutton);
        callme = (Button) findViewById(id.callmebutton);

        menuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ourIntent   = new Intent(MenuPage.this, Delivery.class);
                startActivity(ourIntent);
            }
        });

        homedel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ourIntent   = new Intent(MenuPage.this, Table.class);
                startActivity(ourIntent);
            }
        });

        tablebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ourIntent   = new Intent(MenuPage.this, Delivery.class);
                startActivity(ourIntent);
            }
        });

        eventbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ourIntent   = new Intent(MenuPage.this, Event.class);
                startActivity(ourIntent);
            }
        });

        gmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ourIntent   = new Intent(MenuPage.this, MyMapActivity.class);
                startActivity(ourIntent);
            }
        });

        newsletter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ourIntent   = new Intent(MenuPage.this, Newsletter.class);
                startActivity(ourIntent);
            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=FyXXgpPqe6w")));
                Log.i("Video", "Video Playing....");
            }
        });

        // add PhoneStateListener
        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener,
                PhoneStateListener.LISTEN_CALL_STATE);

        // add button listener
        callme.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:8765736009"));
                startActivity(callIntent);

            }

        });

    }

    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        String LOG_TAG = "LOGGING 123";

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                Log.i(LOG_TAG, "OFFHOOK");

                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended, need detect flag
                // from CALL_STATE_OFFHOOK
                Log.i(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    Log.i(LOG_TAG, "restart app");

                    // restart app
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(
                                    getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                    isPhoneCalling = false;
                }

            }
        }
    }


        /*BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        o.inDither = false; // Disable Dithering mode

        o.inPurgeable = true; // Tell to gc that whether it needs free memory,
        // the Bitmap can be cleared

        o.inInputShareable = true; // Which kind of reference will be used to
        // recover the Bitmap data after being
        // clear, when it will be used in the future


        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int i=0;

            public void run() {
                int size = 1;
                if(i == 0) {

                    InputStream is =   getResources().openRawResource(drawable.restau1);
                    bmp = BitmapFactory.decodeStream(is);
                    Bitmap bitmapsimplesize = Bitmap.createScaledBitmap(bmp,bmp.getWidth() / size, bmp.getHeight() / size, true);
                    bmp.recycle();
                    iv.setImageBitmap(bitmapsimplesize);
                }
                if(i == 1) {
                    InputStream is =   getResources().openRawResource(drawable.restau2);
                    bmp = BitmapFactory.decodeStream(is);
                    Bitmap bitmapsimplesize = Bitmap.createScaledBitmap(bmp,bmp.getWidth() / size, bmp.getHeight() / size, true);
                    bmp.recycle();
                    iv.setImageBitmap(bitmapsimplesize);
                }
                if(i == 2) {
                    InputStream is =   getResources().openRawResource(drawable.restau3);
                    bmp = BitmapFactory.decodeStream(is);
                    Bitmap bitmapsimplesize = Bitmap.createScaledBitmap(bmp,bmp.getWidth() / size, bmp.getHeight() / size, true);
                    bmp.recycle();
                    iv.setImageBitmap(bitmapsimplesize);
                }
                if(i == 3) {
                    InputStream is =   getResources().openRawResource(drawable.restau4);
                    bmp = BitmapFactory.decodeStream(is);
                    Bitmap bitmapsimplesize = Bitmap.createScaledBitmap(bmp,bmp.getWidth() / size, bmp.getHeight() / size, true);
                    bmp.recycle();
                    iv.setImageBitmap(bitmapsimplesize);
                }
                i++;
                if(i > 3)
                {
                    i=0;
                }
                handler.postDelayed(this, 3000);  //for interval...

            }

        };
        handler.postDelayed(runnable, 2000); //for initial delay..    */


    @Override
    public void onPause() {
        super.onPause();
    }

    public boolean onTouch(View arg0, MotionEvent arg1) {
        // Get the action that was done on this touch event
        switch (arg1.getAction()) {
            case MotionEvent.ACTION_DOWN:

            {
                // store the X value when the user's finger was pressed down
                downXValue = arg1.getX();
                break;
            }

            case MotionEvent.ACTION_UP:  {
                // Get the X value when the user released his/her finger
                float currentX = arg1.getX();
                // going backwards: pushing stuff to the right
                if (downXValue < currentX)  {
                    // Get a reference to the ViewFlipper
                    ViewFlipper vf = (ViewFlipper) findViewById(id.viewFlipper);
                    ViewFlipper vf1 = (ViewFlipper) findViewById(id.viewFlipper1);
                    // Set the animation
                    vf.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
                    // Flip!


                    vf1.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
                    vf.showNext();
                    // Flip!
                    vf1.showNext();
                }
                // going forwards: pushing stuff to the left
                if (downXValue > currentX)  {
                    // Get a reference to the ViewFlipper
                    ViewFlipper vf = (ViewFlipper) findViewById(id.viewFlipper);
                    ViewFlipper vf1 = (ViewFlipper) findViewById(id.viewFlipper1);
                    // Set the animation
                    vf.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
                    // Flip!

                    vf1.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
                    vf.showPrevious();
                    vf1.showPrevious();
                }
                break;
            }
        }
        // if you return false, these actions will not be recorded
        return true;
    }
    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, MenuPage.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }
}



    /*public void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }   */


