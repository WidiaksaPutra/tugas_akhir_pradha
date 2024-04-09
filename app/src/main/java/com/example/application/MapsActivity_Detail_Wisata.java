package com.example.application;

import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity_Detail_Wisata extends FragmentActivity implements OnMapReadyCallback{

  private GoogleMap mMap;
  LocationManager locationManager;
  TextView Latitude,Longitude;
  private static final int REQUEST_LOCATION_PERMISSION = 1;
  Marker marker;
  Double lat,lang,latitude,longitude;
  String nama;
  LocationListener locationListener;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//    locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//      ActivityCompat.requestPermissions(this, new String[] {
//              Manifest.permission.ACCESS_FINE_LOCATION
//      }, REQUEST_LOCATION_PERMISSION);
//    }
//    else{
//      locationListener = new LocationListener() {
//        @Override
//        public void onLocationChanged(Location location) {
//          latitude = location.getLatitude();
//          longitude = location.getLongitude();
//          //get the location name from latitude and longitude
//          Geocoder geocoder = new Geocoder(getApplicationContext());
//          try {
//            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
//            String result = addresses.get(0).getLocality()+":";
//            result += addresses.get(0).getCountryName();
//            LatLng latLng = new LatLng(latitude, longitude);
//            if (marker != null){
//              marker.remove();
//            }
//            marker = mMap.addMarker(new MarkerOptions().position(latLng).title(result));
//            mMap.setMaxZoomPreference(20);
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.0f));
//
//          } catch (IOException e) {
//            e.printStackTrace();
//          }
//        }
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//
//        }
//
//        @Override
//        public void onProviderEnabled(String provider) {
//
//        }
//
//        @Override
//        public void onProviderDisabled(String provider) {
//
//        }
//      };
//      locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
//      locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//    }
  }

//  @Override
//  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
//      locationListener = new LocationListener() {
//        @Override
//        public void onLocationChanged(Location location) {
//          latitude = location.getLatitude();
//          longitude = location.getLongitude();
//          //get the location name from latitude and longitude
//          Geocoder geocoder = new Geocoder(getApplicationContext());
//          try {
//            List<Address> addresses =
//                    geocoder.getFromLocation(latitude, longitude, 1);
//            String result = addresses.get(0).getLocality()+":";
//            result += addresses.get(0).getCountryName();
//            LatLng latLng = new LatLng(latitude, longitude);
//            if (marker != null){
//              marker.remove();
//            }
//            marker = mMap.addMarker(new MarkerOptions().position(latLng).title(result));
//            mMap.setMaxZoomPreference(20);
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.0f));
//          } catch (IOException e) {
//            e.printStackTrace();
//          }
//        }
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//
//        }
//
//        @Override
//        public void onProviderEnabled(String provider) {
//
//        }
//
//        @Override
//        public void onProviderDisabled(String provider) {
//
//        }
//      };
//      locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
//      locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//    }
//  }
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
    Bundle bundle = getIntent().getExtras();
    lat = Double.valueOf(String.valueOf(bundle.getString("latitude"))).doubleValue();
    lang = Double.valueOf(String.valueOf(bundle.getString("longitude"))).doubleValue();
    nama = bundle.getString("namawisata");
    LatLng sydney = new LatLng(lat, lang);
    mMap.addMarker(new MarkerOptions().position(sydney).title(nama));
    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,9));
  }
//
//  @Override
//  protected void onStop() {
//    super.onStop();
//    locationManager.removeUpdates(locationListener);
//  }
}