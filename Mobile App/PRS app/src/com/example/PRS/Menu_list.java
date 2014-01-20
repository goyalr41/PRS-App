package com.example.PRS;

import library.ActivitySwitcher; 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Menu_list extends Activity{
	ImageView i1,i2,i3,i4,i5,i6,i7,i8,i9;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_list);
		ActivitySwitcher.animationIn(findViewById(R.id.menulayout), getWindowManager());

        final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
        final ActionBar.Action otherAction = new ActionBar.IntentAction(this, new Intent(this, Event.class), R.drawable.ic_action_back);
        actionBar.addAction(otherAction);
        actionBar.setHomeAction(new ActionBar.IntentAction(this, MenuPage.createIntent(this), R.drawable.ic_title_home_default));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Menu");
		
		i1 = (ImageView)findViewById(R.id.imageView1);
		i2 = (ImageView)findViewById(R.id.imageView2);
		i3 = (ImageView)findViewById(R.id.imageView3);
		i4 = (ImageView)findViewById(R.id.imageView4);
		i5 = (ImageView)findViewById(R.id.imageView5);
		i6 = (ImageView)findViewById(R.id.imageView6);
		i7 = (ImageView)findViewById(R.id.imageView7);
		i8 = (ImageView)findViewById(R.id.imageView8);
		i9 = (ImageView)findViewById(R.id.imageView9);


        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ourIntent   = new Intent();
                ourIntent.setClass(Menu_list.this, FoodDataAvtivity.class);
                ourIntent.putExtra("type","bread" );
                startActivity(ourIntent);
                finish();
            }
        });

        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ourIntent   = new Intent();
                ourIntent.setClass(Menu_list.this, FoodDataAvtivity.class);
                ourIntent.putExtra("type","nonvegfood" );
                startActivity(ourIntent);
                finish();
            }
        });

        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ourIntent   = new Intent();
                ourIntent.setClass(Menu_list.this, FoodDataAvtivity.class);
                ourIntent.putExtra("type","beverage" );
                startActivity(ourIntent);
                finish();
            }
        });

        i4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ourIntent   = new Intent();
                ourIntent.setClass(Menu_list.this, FoodDataAvtivity.class);
                ourIntent.putExtra("type","desert" );
                startActivity(ourIntent);
                finish();
            }
        });

        i5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ourIntent   = new Intent();
                ourIntent.setClass(Menu_list.this, FoodDataAvtivity.class);
                ourIntent.putExtra("type","fastfood" );
                startActivity(ourIntent);
                finish();
            }
        });

        i6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ourIntent   = new Intent();
                ourIntent.setClass(Menu_list.this, FoodDataAvtivity.class);
                ourIntent.putExtra("type","startersfood" );
                startActivity(ourIntent);
                finish();
            }
        });

        i7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ourIntent   = new Intent();
                ourIntent.setClass(Menu_list.this, FoodDataAvtivity.class);
                ourIntent.putExtra("type","salad" );
                startActivity(ourIntent);
                finish();
            }
        });

        i8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ourIntent   = new Intent();
                ourIntent.setClass(Menu_list.this, FoodDataAvtivity.class);
                ourIntent.putExtra("type","curry" );
                startActivity(ourIntent);
                finish();
            }
        });

        i9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ourIntent   = new Intent();
                ourIntent.setClass(Menu_list.this, FoodDataAvtivity.class);
                ourIntent.putExtra("type","special" );
                startActivity(ourIntent);
                finish();
            }
        });
		

	}
	
	protected void onResume() {
		// animateIn this activity
		ActivitySwitcher.animationIn(findViewById(R.id.menulayout), getWindowManager());
		super.onResume();
	}


    @Override
    public void onPause() {
        super.onPause();
        finish();
    }
 
	private void animatedStartActivity() {
		// we only animateOut this activity here.
		// The new activity will animateIn from its onResume() - be sure to implement it.
		final Intent intent = new Intent(getApplicationContext(), Table.class);
		// disable default animation for new intent
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		ActivitySwitcher.animationOut(findViewById(R.id.menulayout), getWindowManager(), new ActivitySwitcher.AnimationFinishedListener() {
			@Override
			public void onAnimationFinished() {
				startActivity(intent);
			}
		});
	}
}
