package com.example.PRS;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*;
import library.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MyMapActivity extends FragmentActivity implements GoogleMap.OnMapClickListener , GoogleMap.OnInfoWindowClickListener{

    private static final String TAG = " ";
    GoogleMap googleMap;
    LocationManager locMgr;
    Marker userloc = null;
    MyLocationListener locLstnr;
    ArrayList<Marker> markers;
    Polyline poly;
    Route r = new Route();
    LatLng sfLatLng1 = new LatLng(30.745893,76.785096);

    double [] l = {30.745893,31.559815,18.947206,12.821175,28.652633};
    double [] l1 = {76.785096,75.595093,72.836494,80.22274,77.123623};
    String [] s = {"PRS Chandigarh","PRS Jalandhar","PRS Mumbai", "PRS Chennai","PRS Delhi"};
    String [] s1  = {"Block No. 9, Jan Marg, Chandigarh, India","Choti Baradari, Jalandhar, Punjab, India","Apollo Bandar, Mumbai, Maharashtra","Rajiv Gandhi IT Expressway,Chennai ","A-38, Vishal Enclave, Rajouri Garden, New Delhi,India"};

    private void getShaKey() {

        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.PRS",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.v( TAG, "KeyHash:" + Base64.encodeToString(md.digest(),
                        Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymap);



        //FragmentManager fragmentManager = getSupportFragmentManager();
        SupportMapFragment mapFragment =  (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        /*int checkGooglePlayServices =    GooglePlayServicesUtil.isGooglePlayServicesAvailable(mContext);
        if (checkGooglePlayServices != ConnectionResult.SUCCESS) {
            GooglePlayServicesUtil.getErrorDialog(checkGooglePlayServices, this, 1122).show();
        } */
        getShaKey();

         googleMap = mapFragment.getMap();

            if (googleMap != null) {
                googleMap.setOnMapClickListener(this);
            for(int i = 0; i < 5; i++) {
         LatLng sfLatLng = new LatLng(l[i],l1[i]);

        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                googleMap.addMarker(new MarkerOptions()
                .position(sfLatLng)
                .title(s[i])
                .snippet(s1[i])
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

          googleMap.setOnInfoWindowClickListener(this);
                //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sfLatLng, 4));
            }
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

            }

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sfLatLng1, 4));

        locMgr = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locLstnr = new MyLocationListener();
        locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locLstnr);



    }


    @Override
    public void onPause() {
        super.onPause();
        finish();
    }



    @Override
    public void onMapClick(LatLng point) {
        String hgg = "" + point.latitude;
        String cvb    = "" + point.longitude;
        String f = hgg + ", " + cvb ;
        //Toast.makeText( getApplicationContext(),
          //      f,
            //    Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        LatLng point = marker.getPosition();
        String hgg = "" + point.latitude;
        String cvb    = "" + point.longitude;
        String f = hgg + ", " + cvb ;
       r.drawRoute(googleMap,this,sfLatLng1,point,true,Route.LANGUAGE_ENGLISH);

    }

    class MyLocationListener implements LocationListener {
    @Override
    public void onLocationChanged(Location location) {
        //To change body of implemented methods use File | Settings | File Templates.
        //location.getLatitude();
        //location.getLongitude();
        String coordinates[] = {""+location.getLatitude(), ""+location.getLongitude()};
        double lat = Double.parseDouble(coordinates[0]);
        double lng = Double.parseDouble(coordinates[1]);
        if(googleMap != null) {
            if(userloc != null)    {
                    userloc.remove();
            }
            LatLng sfLatLng = new LatLng(lat,lng);
             userloc = googleMap.addMarker(new MarkerOptions()
                    .position(sfLatLng)
                    .title("My Location")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
              sfLatLng1 = sfLatLng;
        }

    }


    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText( getApplicationContext(),
                "GPS Enabled",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText( getApplicationContext(),
                "GPS Disabled",
                Toast.LENGTH_SHORT ).show();
    }


}

}
