package com.example.PRS;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class splash extends Activity{

    @Override
    protected void onCreate(Bundle heyya) {
        // TODO Auto-generated method stub
        super.onCreate(heyya);
        setContentView(R.layout.splash);

        Thread t = new Thread(){
            public void run()
            {
                try{
                    sleep(5000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent openStartingPoint = new Intent(splash.this,login.class);
                    startActivity(openStartingPoint);

                }
            }
        };
        t.start();
    }


}
