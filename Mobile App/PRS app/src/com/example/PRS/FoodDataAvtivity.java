package com.example.PRS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import android.widget.Button;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class FoodDataAvtivity extends Activity {

    // XML node keys
    static final String KEY_TAG = "fooddata"; // parent node
    static final String KEY_ID = "id";
    static final String KEY_CITY = "city";
    static final String KEY_TEMP_C = "tempc";
    static final String KEY_TEMP_F = "tempf";
    static final String KEY_CONDN = "condition";
    static final String KEY_SPEED = "windspeed";
    static final String KEY_ICON = "icon";

    // List items 
    ListView list;
    BinderData adapter = null;
    List<HashMap<String,String>> fooddataCollection;
    Button opencart;
    String myfile;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fooditemlist);
        opencart = (Button) findViewById(R.id.opencart);

        final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
        String my =  getIntent().getStringExtra("type");
        Intent igh = new Intent();
        igh.setClass(this, ShoppingCartActivity.class);
        igh.putExtra("type",my );
        final ActionBar.Action shareAction = new ActionBar.IntentAction(this,  igh , R.drawable.shopping_cart);
        actionBar.addAction(shareAction);
        final ActionBar.Action otherAction = new ActionBar.IntentAction(this, new Intent(this, Menu_list.class), R.drawable.ic_action_back);
        actionBar.addAction(otherAction);


        actionBar.setHomeAction(new ActionBar.IntentAction(this, MenuPage.createIntent(this), R.drawable.ic_title_home_default));
        actionBar.setDisplayHomeAsUpEnabled(true);

        try {

            myfile = getIntent().getStringExtra("type").trim();
            actionBar.setTitle(myfile);
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (getAssets().open(myfile+".xml"));
            //System.out.println(myfile + "  ye h file name");

            fooddataCollection = new ArrayList<HashMap<String,String>>();

            // normalize text representation
            doc.getDocumentElement ().normalize ();

            NodeList foodList = doc.getElementsByTagName(myfile);

            HashMap<String,String> map = null;

            for (int i = 0; i < foodList.getLength(); i++) {

                map = new HashMap<String,String>();

                Node firstWeatherNode = foodList.item(i);

                if(firstWeatherNode.getNodeType() == Node.ELEMENT_NODE){

                    Element firstWeatherElement = (Element)firstWeatherNode;
                    //-------
                    NodeList idList = firstWeatherElement.getElementsByTagName(KEY_ID);
                    Element firstIdElement = (Element)idList.item(0);
                    NodeList textIdList = firstIdElement.getChildNodes();
                    //--id
                    map.put(KEY_ID, ((Node)textIdList.item(0)).getNodeValue().trim());

                    //2.-------
                    NodeList cityList = firstWeatherElement.getElementsByTagName(KEY_CITY);
                    Element firstCityElement = (Element)cityList.item(0);
                    NodeList textCityList = firstCityElement.getChildNodes();
                    //--city
                    map.put(KEY_CITY, ((Node)textCityList.item(0)).getNodeValue().trim());

                    //3.-------
                    NodeList tempList = firstWeatherElement.getElementsByTagName(KEY_TEMP_C);
                    Element firstTempElement = (Element)tempList.item(0);
                    NodeList textTempList = firstTempElement.getChildNodes();
                    //--city
                    map.put(KEY_TEMP_C, ((Node)textTempList.item(0)).getNodeValue().trim());

                    //4.-------
                    NodeList condList = firstWeatherElement.getElementsByTagName(KEY_CONDN);
                    Element firstCondElement = (Element)condList.item(0);
                    NodeList textCondList = firstCondElement.getChildNodes();
                    //--city
                    map.put(KEY_CONDN, ((Node)textCondList.item(0)).getNodeValue().trim());

                    //5.-------
                    NodeList speedList = firstWeatherElement.getElementsByTagName(KEY_SPEED);
                    Element firstSpeedElement = (Element)speedList.item(0);
                    NodeList textSpeedList = firstSpeedElement.getChildNodes();
                    //--city
                    map.put(KEY_SPEED, ((Node)textSpeedList.item(0)).getNodeValue().trim());

                    //6.-------
                    NodeList iconList = firstWeatherElement.getElementsByTagName(KEY_ICON);
                    Element firstIconElement = (Element)iconList.item(0);
                    NodeList textIconList = firstIconElement.getChildNodes();
                    //--city
                    map.put(KEY_ICON, ((Node)textIconList.item(0)).getNodeValue().trim());

                    //Add to the Arraylist
                    fooddataCollection.add(map);
                }
            }

            opencart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent ourIntent   = new Intent(FoodDataAvtivity.this, MyMapActivity.class);
                    startActivity(ourIntent);
                }
            });

            BinderData bindingData = new BinderData(this,fooddataCollection);


            list = (ListView) findViewById(R.id.list);

            Log.i("BEFORE", "<<------------- Before SetAdapter-------------->>");

            list.setAdapter(bindingData);

            Log.i("AFTER", "<<------------- After SetAdapter-------------->>");

            // Click event for single list row
            list.setOnItemClickListener(new OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Intent i = new Intent();
                    i.setClass(FoodDataAvtivity.this, FoodDescActivity.class);

                    // parameters
                    i.putExtra("position", String.valueOf(position + 1));
					
					/* selected item parameters
					 * 1.	City name
					 * 2.	Weather
					 * 3.	Wind speed
					 * 4.	Temperature
					 * 5.	Weather icon   
					 */

                    i.putExtra("city", fooddataCollection.get(position).get(KEY_CITY));
                    i.putExtra("weather", fooddataCollection.get(position).get(KEY_CONDN));
                    i.putExtra("windspeed", fooddataCollection.get(position).get(KEY_SPEED));
                    i.putExtra("temperature", fooddataCollection.get(position).get(KEY_TEMP_C));
                    i.putExtra("icon", fooddataCollection.get(position).get(KEY_ICON));
                    i.putExtra("type",myfile);

                    // start the sample activity
                    startActivity(i);
                    finish();
                }
            });

        }

        catch (IOException ex) {
            Log.e("Error", ex.getMessage());
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
        getMenuInflater().inflate(R.menu.main , menu);
        return true;
    }
}
