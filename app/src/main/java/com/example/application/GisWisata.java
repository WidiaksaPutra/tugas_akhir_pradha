package com.example.application;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.application.controller.map.detail_marker_budaya;
import com.example.application.model.API.APISQL;
import com.example.application.model.manager.Retroserver;
import com.example.application.model.manager.SessionManager;
import com.example.application.model.phpmodel.ResponsModel;
import com.example.application.model.phpmodel.SelectModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GisWisata extends FragmentActivity implements OnMapReadyCallback {
    private List<SelectModel> mItems = new ArrayList<>();
    private GoogleMap mMap;
    ArrayList<LatLng> arrayList = new ArrayList<LatLng>();
    ArrayList<String> title = new ArrayList<String>();
    ArrayList<String> jenis = new ArrayList<String>();
    ArrayList<String> gambar = new ArrayList<String>();
    ArrayList<String> key1 = new ArrayList<String>();
    Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mDialog = new Dialog(this);
//     Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent data = getIntent();
        Bundle data1 = getIntent().getExtras();

        Integer jum = data1.getInt("jumlah");
        for (int i = 0; i < jum; i++) {
            Double lat = Double.valueOf(String.valueOf(data.getStringExtra("Latitude" + i))).doubleValue();
            Double lang = Double.valueOf(String.valueOf(data.getStringExtra("Longitude" + i))).doubleValue();
            arrayList.add(new LatLng(lat, lang));
            title.add(data.getStringExtra("Nama_tempat" + i));
            jenis.add(data.getStringExtra("Jenis_Tempat" + i));
            gambar.add(data.getStringExtra("Gambar" + i));
            key1.add(data.getStringExtra("key1" + i));
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
        for (int i = 0; i < arrayList.size(); i++) {
            MarkerOptions options = new MarkerOptions().position(arrayList.get(i)).title(String.valueOf(title.get(i)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(arrayList.get(i), 9));

            if (String.valueOf(jenis.get(i)).equals("Budaya_Adat")) {
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                mMap.addMarker(options);
            } else if (String.valueOf(jenis.get(i)).equals("Umum_Bali")) {
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                mMap.addMarker(options);
            }


            mDialog = new Dialog(this);

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {
                    TextView Namatempat, Key, Key1;
                    ImageView Gambar;
                    Button Boking, Detail;
                    SessionManager sm;
                    String markertitle = marker.getTitle();
                    mDialog.setContentView(R.layout.popup_map);
                    Gambar = (ImageView) mDialog.findViewById(R.id.gambartempat);
                    Namatempat = (TextView) mDialog.findViewById(R.id.namatempat);
                    Key = (TextView) mDialog.findViewById(R.id.key);
                    Key1 = (TextView) mDialog.findViewById(R.id.key1);

                    sm = new SessionManager(GisWisata.this);
                    HashMap<String, String> map = sm.getDetailLogin();
                    Key.setText(map.get(sm.KEY));


                    for (int i = 0; i < arrayList.size(); i++) {
                        if (String.valueOf(title.get(i)).equals(markertitle)) {
                            Namatempat.setText(String.valueOf(title.get(i)));
                            Glide.with(GisWisata.this).load(String.valueOf(gambar.get(i))).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(Gambar);
                            Key1.setText(String.valueOf(key1.get(i)));
                        }
                    }

                    Boking = mDialog.findViewById(R.id.boking);
                    Boking.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String key2 = Key1.getText().toString();
                            String key1 = Key.getText().toString();
                            APISQL api = Retroserver.getClient().create(APISQL.class);
                            Call<ResponsModel> boking = api.bokingTempat(key1, key2);
                            boking.enqueue(new Callback<ResponsModel>() {
                                @Override
                                public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                                    Log.d("RETRO", "response : " + response.body().toString());
                                    String kode = response.body().getKode();
                                    if (kode.equals("1")) {
//                                    Intent refresh = new Intent(GisWisata.this, boking.class);
                                        Toast.makeText(GisWisata.this, "Success Booking..", Toast.LENGTH_SHORT).show();
//                                    startActivity(refresh);
                                    } else if (kode.equals("3")) {
                                        Toast.makeText(GisWisata.this, "The data it's been booking..", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponsModel> call, Throwable t) {
                                    Log.d("RETRO", "Falure : " + "Riquest Not Falid");
                                }
                            });
                        }
                    });

                    Detail = mDialog.findViewById(R.id.detail);
                    Detail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(GisWisata.this, detail_marker_budaya.class);
                            i.putExtra("Nama_tempat", markertitle);
                            startActivity(i);
                        }
                    });
                    mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    mDialog.show();
                    return false;
                }
            });
        }
    }
}