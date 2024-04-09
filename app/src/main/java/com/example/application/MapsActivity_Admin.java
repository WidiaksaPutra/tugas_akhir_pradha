package com.example.application;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.application.controller.penyedia.rekomendasi_wisata;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity_Admin extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap mMap;
    SupportMapFragment mapFragment;
    SearchView searchView;
    Marker marker, maker1;
    LocationListener locationListener;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    LocationManager locationManager;
    float latitude,longitude;
    TextView lat, lon;
    String lati, longi;

    public static final String session = "session";

    ProgressDialog pd;
    SharedPreferences pref;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps_rekomendasi_wisata);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        searchView = findViewById(R.id.sv_location);
        mapFragment.getMapAsync(this);
        pd = new ProgressDialog(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, REQUEST_LOCATION_PERMISSION);
        }
        else{
                locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        latitude = (float) location.getLatitude();
                        longitude = (float) location.getLongitude();
                        //get the location name from latitude and longitude
                        Geocoder geocoder = new Geocoder(getApplicationContext());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                            String result = addresses.get(0).getLocality()+":";
                            result += addresses.get(0).getCountryName();
                            LatLng latLng = new LatLng(latitude, longitude);
                            if (marker != null){
                                marker.remove();
                                mMap.clear();
                            }
                            mMap.setMaxZoomPreference(20);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.0f));
                            marker = mMap.addMarker(new MarkerOptions().position(latLng).title(result));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                };
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(marker != null) {
                    marker.remove();
                    mMap.clear();
                    locationManager.removeUpdates(locationListener);
                    LatLng latLng = null;
                }
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;
                Geocoder geocoder = new Geocoder(MapsActivity_Admin.this);
                try{
                    addressList = geocoder.getFromLocationName(location, 1);
                }catch (IOException e){
                    e.printStackTrace();
                }
                if (addressList != null) {
                    latitude = (float) addressList.get(0).getLatitude();
                    longitude = (float) addressList.get(0).getLongitude();
                    LatLng latLng = new LatLng(latitude, longitude);
                    marker = mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    mMap.setMaxZoomPreference(20);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(marker != null) {
                        marker.remove();
                        mMap.clear();
                        locationManager.removeUpdates(locationListener);
                }
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                latitude = (float) latLng.latitude;
                longitude = (float) latLng.longitude;
                markerOptions.title(latitude + " : " + longitude);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                marker = mMap.addMarker(markerOptions);
                maker1 =  mMap.addMarker(markerOptions);
            }
        });
    }

    public void Centang(View v){
        pd.setMessage("send data ...");
        pd.setCancelable(false);
        pd.show();

        pref = getSharedPreferences(session, MODE_PRIVATE);
        editor = pref.edit();
        lat =  (TextView) findViewById(R.id.la);
        lon = (TextView) findViewById(R.id.lo);
        lat.setText(String.valueOf(latitude));
        lon.setText(String.valueOf(longitude));
        lati = lat.getText().toString();
        longi = lon.getText().toString();
        lon.getText().toString();
        editor.putFloat("getlatitude", Float.parseFloat(lati));
        editor.putFloat("getlongitude", Float.parseFloat(longi));
        editor.apply();
        Intent moveactivity = new Intent(MapsActivity_Admin.this, rekomendasi_wisata.class);
        startActivity(moveactivity);
    }
}