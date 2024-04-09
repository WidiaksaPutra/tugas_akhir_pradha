package com.example.application;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application.controller.wisata.rute;
import com.example.application.model.manager.SessionManager;

import java.util.ArrayList;

public class MapsCih extends AppCompatActivity {
    ArrayList<TextView> arrayList1=new ArrayList<TextView>();
    ArrayList<String> arrayList2=new ArrayList<String>();
    TextView Latitude,Longitude,latitudeUser,langitudeUser;
    SessionManager sm;
    String LatUse, LangUse, Lat, Lang, sSource, sSource1,sDestination,sDestination1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Latitude = (TextView) findViewById(R.id.latitude);
        Longitude = (TextView) findViewById(R.id.longitude);
        latitudeUser = (TextView) findViewById(R.id.latuse);
        langitudeUser = (TextView) findViewById(R.id.longuse);
//
        Intent back = new Intent(MapsCih.this, rute.class);
        startActivity(back);

        Intent data = getIntent();
        Latitude.setText(data.getStringExtra("latitude"));
        Longitude.setText(data.getStringExtra("longitude"));
//        arrayList1.add(Latitude);
//        Integer jum1 = Integer.valueOf(arrayList1.size());
//        System.out.println(jum1);
//        if(jum1.equals(1)){
            latitudeUser.setText(data.getStringExtra("latitudeUser"));
            langitudeUser.setText(data.getStringExtra("langitudeUser"));
            LatUse = latitudeUser.getText().toString();
            LangUse = langitudeUser.getText().toString();
            Lat = Latitude.getText().toString();
            Lang = Longitude.getText().toString();
            sSource = String.valueOf(LatUse);
            sSource1 = String.valueOf(LangUse);
            sDestination = String.valueOf(Lat);
            sDestination1 = String.valueOf(Lang);
//        }

//Double latUse = Double.valueOf(String.valueOf(latitudeUser)).doubleValue();
//            Double langUse = Double.valueOf(String.valueOf(langitudeUser)).doubleValue();

//        if(arrayList1.isEmpty()){
//            arrayList1.add(new LatLng(latUse,langUse));
//        }
//        else{
//            arrayList1.add(new LatLng(lat,lang));
//        }
//
//
//        String sSource = String.valueOf(arrayList1.get(0));
//        String sDestination = String.valueOf(arrayList2.get(0));




//        String sSource = "-8.283626";
//        String sSource1 = "115.16635";
//        String sDestination = "-8.810423";
//        String sDestination1 = "115.167595";
        DisplayTrack(sSource,sSource1,sDestination,sDestination1);
    }

    private void DisplayTrack(String sSource, String sSource1, String sDestination, String sDestination1) {
        try{
            Uri uri = Uri.parse("https://www.google.co.id/maps/dir/" + sSource+","+sSource1 + "/" + sDestination+","+sDestination1);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}