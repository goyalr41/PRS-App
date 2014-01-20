package com.example.PRS;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

public class BinderData extends BaseAdapter {

    // XML node keys
    static final String KEY_TAG = "weatherdata"; // parent node
    static final String KEY_ID = "id";
    static final String KEY_CITY = "city";
    static final String KEY_TEMP_C = "tempc";
    static final String KEY_TEMP_F = "tempf";
    static final String KEY_CONDN = "condition";
    static final String KEY_SPEED = "windspeed";
    static final String KEY_ICON = "icon";

    LayoutInflater inflater;
    ImageView thumb_image;
    List<HashMap<String,String>> foodDataCollection;
    ViewHolder holder;
    public BinderData() {
        // TODO Auto-generated constructor stub
    }

    public BinderData(Activity act, List<HashMap<String,String>> map) {

        this.foodDataCollection = map;

        inflater = (LayoutInflater) act
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public int getCount() {
        // TODO Auto-generated method stub
//		return idlist.size();
        return foodDataCollection.size();
    }

    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View vi=convertView;
        final View vi1 = convertView;
        if(convertView==null){

            vi = inflater.inflate(R.layout.list_row, null);
            holder = new ViewHolder();

            holder.tvCity = (TextView)vi.findViewById(R.id.tvCity); // city name
            holder.tvWeather = (TextView)vi.findViewById(R.id.tvCondition); // city weather overview
            holder.tvTemperature =  (TextView)vi.findViewById(R.id.tvTemp); // city temperature
            holder.tvWeatherImage =(ImageView)vi.findViewById(R.id.list_image);// thumb image
            holder.tvAddImage = (ImageView)vi.findViewById(R.id.addimageView1);

            vi.setTag(holder);
        }
        else{

            holder = (ViewHolder)vi.getTag();
        }

        // Setting all values in listview

        holder.tvCity.setText(foodDataCollection.get(position).get(KEY_CITY));
        holder.tvWeather.setText(foodDataCollection.get(position).get(KEY_CONDN));
        holder.tvTemperature.setText(foodDataCollection.get(position).get(KEY_TEMP_C));

        //Setting an image
        String uri = "drawable/"+ foodDataCollection.get(position).get(KEY_ICON);
        int imageResource = vi.getContext().getApplicationContext().getResources().getIdentifier(uri, null, vi.getContext().getApplicationContext().getPackageName());
        Drawable image = vi.getContext().getResources().getDrawable(imageResource);
        final Drawable myimage = image;
        holder.tvWeatherImage.setImageDrawable(image);
        holder.tvAddImage.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_f = foodDataCollection.get(position).get(KEY_CITY).trim();
                String des =     foodDataCollection.get(position).get(KEY_CONDN).trim();
                String cost =        foodDataCollection.get(position).get(KEY_TEMP_C).trim();
                String uri = "drawable/"+ foodDataCollection.get(position).get(KEY_ICON);

                //int imageResource = vi1.getContext().getApplicationContext().getResources().getIdentifier(uri, null, vi1.getContext().getApplicationContext().getPackageName());
                //Drawable image = vi1.getContext().getResources().getDrawable(imageResource);
                Drawable image = myimage;
                Product product = new Product(name_f,image,des,Double.parseDouble(cost.substring(3,cost.length()).trim()));

                int s = product.hashCode();
                // System.out.println("Printing hashcode" + s) ;
                // Check to see that a valid quantity was entered
                int quantity = ShoppingCartHelper.getProductQuantity(product);


                ShoppingCartHelper.setQuantity(product, quantity + 1);



                // Close the activity
            }


    });


        return vi;
    }
    /*
     *
     * */
    static class ViewHolder{

        TextView tvCity;
        TextView tvTemperature;
        TextView tvWeather;
        ImageView tvWeatherImage;
        ImageView tvAddImage;
    }

}
