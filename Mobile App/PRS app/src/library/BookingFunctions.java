package library;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.util.Log;

public class BookingFunctions {
	private JSONParser jsonParser;
	
	public BookingFunctions(){
		jsonParser = new JSONParser();
	}
	
	private static String DeliveryURL = "http://172.20.1.193/prs1/";
	private static String delivery_tag = "delivery";
	private static String event_tag = "event";
	private static String book_tag = "table_booking";
	private static String food_tag = "food_booking";
    private static String letter_tag = "newsletter";
	public JSONObject Delivery(String mobile, String address){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", delivery_tag));
		params.add(new BasicNameValuePair("mobile", mobile));
		params.add(new BasicNameValuePair("address", address));
		JSONObject json = jsonParser.getJSONFromUrl(DeliveryURL, params);
		// return json
		 Log.e("JSON", json.toString());
		return json;
	}

    public JSONObject newsletter(String mobile,String email)
    {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", letter_tag));
        params.add(new BasicNameValuePair("mobile", mobile));
        params.add(new BasicNameValuePair("email", email));

        JSONObject json = jsonParser.getJSONFromUrl(DeliveryURL, params);
        return json;
    }
	
	public JSONObject Event(String mobile,String location,String date,String time,String event_type,String num_people,String extras)
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", event_tag));
		params.add(new BasicNameValuePair("mobile", mobile));
		params.add(new BasicNameValuePair("location", location));
		params.add(new BasicNameValuePair("date", date));
		params.add(new BasicNameValuePair("time", time));
		params.add(new BasicNameValuePair("event_type", event_type));
		params.add(new BasicNameValuePair("num_people", num_people));
		params.add(new BasicNameValuePair("extras", extras));
		JSONObject json = jsonParser.getJSONFromUrl(DeliveryURL, params);
		return json;
	}
	public JSONObject Book_table(String mobile,String location,String date,String time,String num_people,String extras)
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", book_tag));
		params.add(new BasicNameValuePair("mobile", mobile));
		params.add(new BasicNameValuePair("location", location));
		params.add(new BasicNameValuePair("date", date));
		params.add(new BasicNameValuePair("time", time));
		//params.add(new BasicNameValuePair("event_type", event_type));
		params.add(new BasicNameValuePair("num_people", num_people));
		params.add(new BasicNameValuePair("extras", extras));
		JSONObject json = jsonParser.getJSONFromUrl(DeliveryURL, params);
		return json;
	}
	public JSONObject Book_Food(String foo, String cos, String quantity, String type)
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", food_tag));
		
		params.add(new BasicNameValuePair("food_name", foo));
		params.add(new BasicNameValuePair("quantity", quantity));
		params.add(new BasicNameValuePair("cost", cos));
		params.add(new BasicNameValuePair("type", type));
		//params.add(new BasicNameValuePair("event_type", event_type));
		//params.add(new BasicNameValuePair("num_people", num_people));
		//params.add(new BasicNameValuePair("extras", extras));
		JSONObject json = jsonParser.getJSONFromUrl(DeliveryURL, params);
		return json;
	}
	
	
}