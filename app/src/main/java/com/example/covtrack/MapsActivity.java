package com.example.covtrack;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.math.*;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    Location currentLocation;
    Marker userLocation;
    MarkerOptions marker;
    double userLat = 43.6483112;
    double userLng = -79.39109;
    CircleOptions circle = new CircleOptions().center(new LatLng(43.65, -79.39)).radius(100).strokeColor(Color.BLACK);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //radius on toronto
        LatLng toronto = new LatLng(43, -80);
        mMap.addCircle(circle);
       // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(toronto, 1.2f));

        LatLng userLatLng = new LatLng(userLat, userLng);
        marker = new MarkerOptions().position(userLatLng).title("Your Location");
        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.user));
        userLocation = mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 16.2f));
        if(inArea(userLatLng,circle)==true){
            Toast toast = Toast.makeText(this, "WARNING!!! in proximity of someone with COVID-19 symptoms", Toast.LENGTH_SHORT);
            toast.show();
        }

    }
    public boolean inArea(LatLng user, CircleOptions circle){
        double radius = circle.getRadius();
        double longitudeC =Math.toRadians(circle.getCenter().longitude);
        double longitudeU = Math.toRadians(user.longitude);
        double latitudeC = Math.toRadians(circle.getCenter().latitude);
        double latitudeU = Math.toRadians(user.latitude);
        double longitudeDiff = Math.abs(longitudeC-longitudeU);
        double latitudeDiff = Math.abs(latitudeC-latitudeU);
        final double EARTH_RADIUS =6371000;
        //Inner Haversine formula
        double d1 = Math.pow(Math.sin(latitudeDiff/2),2)
                + Math.cos(latitudeU)*Math.cos(latitudeC)
                * Math.pow(Math.sin(latitudeDiff/2),2);
        //outer Haversine formula
        double distance = 2*EARTH_RADIUS * Math.asin(Math.sqrt(d1));

        double epsilion = 0.0000001;
        System.out.println(distance);
        System.out.println(radius);;
        if(distance-radius>epsilion){
            return false;
        }else{
            return true;
        }

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            userLocation.remove();
            userLng = userLng - 0.0005;
            LatLng userLatLng = new LatLng(userLat, userLng);
            marker = new MarkerOptions().position(userLatLng).title("Your Location");
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.user));
            userLocation = mMap.addMarker(marker);
            if(inArea(userLatLng,circle)==true){
                Toast toast = Toast.makeText(this, "WARNING!!! in proximity of someone with COVID-19 symptoms", Toast.LENGTH_SHORT);
                toast.show();
            }
        }else if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
            userLocation.remove();
            userLng = userLng + 0.0005;
            LatLng userLatLng = new LatLng(userLat, userLng);
            marker = new MarkerOptions().position(userLatLng).title("Your Location");
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.user));
            userLocation = mMap.addMarker(marker);
            if(inArea(userLatLng,circle)==true){
                Toast toast = Toast.makeText(this, "WARNING!!! in proximity of someone with COVID-19 symptoms", Toast.LENGTH_SHORT);
                toast.show();
            }
        }else if(keyCode == KeyEvent.KEYCODE_DPAD_DOWN){
            userLocation.remove();
            userLat = userLat - 0.0005;
            LatLng userLatLng = new LatLng(userLat, userLng);
            marker = new MarkerOptions().position(userLatLng).title("Your Location");
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.user));
            userLocation = mMap.addMarker(marker);
            if(inArea(userLatLng,circle)==true){
                Toast toast = Toast.makeText(this, "WARNING!!! in proximity of someone with COVID-19 symptoms", Toast.LENGTH_SHORT);
                toast.show();
            }
        }else if(keyCode == KeyEvent.KEYCODE_DPAD_UP){
            userLocation.remove();
            userLat = userLat + 0.0005;
            LatLng userLatLng = new LatLng(userLat, userLng);
            marker = new MarkerOptions().position(userLatLng).title("Your Location");
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.user));
            userLocation = mMap.addMarker(marker);
            if(inArea(userLatLng,circle)==true){
                Toast toast = Toast.makeText(this, "WARNING!!! in proximity of someone with COVID-19 symptoms", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        return true;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event){
        return super.onKeyUp(keyCode, event);
    }
}