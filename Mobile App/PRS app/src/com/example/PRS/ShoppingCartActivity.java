package com.example.PRS;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import library.BookingFunctions;
import org.json.JSONObject;

public class ShoppingCartActivity extends Activity {

    private List<Product> mCartList;
    public static ProductAdapter mProductAdapter;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppingcart);
        final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);

        Intent igh = new Intent();
        String my =  getIntent().getStringExtra("type");
        if(my != null) {

        igh.setClass(this, FoodDataAvtivity.class);
        igh.putExtra("type",my );
        }else {
            igh.setClass(this, Menu_list.class);
        }
        final ActionBar.Action otherAction = new ActionBar.IntentAction(this, igh, R.drawable.ic_action_back);
        actionBar.addAction(otherAction);

        actionBar.setHomeAction(new ActionBar.IntentAction(this, MenuPage.createIntent(this), R.drawable.ic_title_home_default));
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle("Food Cart");


        mCartList = ShoppingCartHelper.getCartList();

        // Make sure to clear the selections
        for(int i=0; i<mCartList.size(); i++) {
            mCartList.get(i).selected = false;
        }
        b1 = (Button) findViewById(R.id.checkout);
        if(mCartList.size() != 0){
             b1.isEnabled();
        }

        // Create the list
        final ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
        mProductAdapter = new ProductAdapter(mCartList, getLayoutInflater());
        listViewCatalog.setAdapter(mProductAdapter);

       listViewCatalog.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
               // Intent productDetailsIntent = new Intent(getBaseContext(),ProductDetailsActivity.class);
                //productDetailsIntent.putExtra(ShoppingCartHelper.PRODUCT_INDEX, position);
                //startActivity(productDetailsIntent);

            }
        });


        b1.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                displayAlert();
                return false;
            }

        });
    }
    public void displayAlert()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirmation");
        builder.setMessage("Do you want to place your order ?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do do my action here
                thankyou();

                BookingFunctions bk = new BookingFunctions();
                List<Product> cartList = ShoppingCartHelper.getCartList();
                for(Product p : cartList) {
                    String s = "";
                    s = s + p.price;
                    String s1 = "";
                    s1= s1 + ShoppingCartHelper.getProductQuantity(p);
                    JSONObject json = bk.Book_Food(p.title, s,s1 , globaltostor.booktype);
                }


                dialog.dismiss();

            }

        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // I do not need any action here you might
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
    public void thankyou()
    {
        new AlertDialog.Builder(this).setMessage("Thanks for placing the order")
                .setTitle("")
                .setCancelable(true)
                .setNeutralButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton){
                                finish();
                            }
                        })
                .show();
    }


    @Override
    public void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Refresh the data
        if(mProductAdapter != null) {
            mProductAdapter.notifyDataSetChanged();
        }
    }



}
