package com.example.application.controller.awaluser;
//adb connect localhost:21503

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.application.R;
import com.example.application.controller.penyedia.penyedia1;
import com.example.application.controller.wisata.wisataumum;
import com.example.application.model.API.APISQL;
import com.example.application.model.manager.Retroserver;
import com.example.application.model.manager.SessionManager;
import com.example.application.model.phpmodel.ResponsModel;
import com.example.application.model.phpmodel.SelectModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {

    private EditText Username, Password;
    private RadioGroup Radiogroup;
    private RadioButton Radiobutton;
    private ProgressDialog pd;
    private SessionManager sm;

    LocationManager locationManager;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    double latitudeUser,longitudeUser;
    LocationListener locationListener;
    Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Username = (EditText) findViewById(R.id.username_log);
        Password = (EditText) findViewById(R.id.password_log);
        Radiogroup = (RadioGroup) findViewById(R.id.radiogroup_log);
        sm = new SessionManager(login.this);
        pd = new ProgressDialog(login.this);

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
                    latitudeUser = location.getLatitude();
                    longitudeUser = location.getLongitude();

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

    private boolean username(){
        String username_log = Username.getText().toString();
        if(username_log.isEmpty()){
            Username.setError("username tidak boleh kosong");
            return false;
        }
        else{
            Username.setError(null);
            return true;
        }
    }

    private boolean password(){
        String passwords_log = Password.getText().toString();
        if(passwords_log.isEmpty()){
            Password.setError("password tidak boleh kosong");
            return false;
        }
        else{
            Password.setError(null);
            return true;
        }
    }

    public void LoginUser(View v){
        if(!username() | !password()){
            return;
        }
        pd.setMessage("send data ...");
        pd.setCancelable(false);
        pd.show();
        String username_log = Username.getText().toString();
        String passwords_log = Password.getText().toString();
        APISQL apilogin = Retroserver.getClient().create(APISQL.class);
        int radiobutton = Radiogroup.getCheckedRadioButtonId();
        Radiobutton = (RadioButton) findViewById(radiobutton);
        String radiogroup_log = Radiobutton.getText().toString();
        Call<ResponsModel> login = apilogin.sendLoginUser(username_log, passwords_log, radiogroup_log);

        login.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                pd.hide();
                Log.d("RETRO", "response : " + response.body().toString());
                ResponsModel res = response.body();
                List<SelectModel> user = res.getResult();
                String kode = response.body().getKode();
                String lat = String.valueOf(Double.valueOf(latitudeUser));
                String lang = String.valueOf(Double.valueOf(longitudeUser));
                if (kode.equals("5")) {
                    sm.storeLogin(user.get(0).getKeypradha1());
                    sm.storeUserLocation(lat, lang);
//                    Bundle userlocation = new Bundle();
//                    userlocation.putDouble("latitudeUser", latitudeUser);
//                    userlocation.putDouble("longitudeUser", longitudeUser);
                    Toast.makeText(login.this, "data berhasil..", Toast.LENGTH_SHORT).show();
                    Intent wisataumum = new Intent(login.this, wisataumum.class);
//                    wisataumum.putExtras(userlocation);
                    startActivity(wisataumum);
                }
                else if(kode.equals("6")) {
                    sm.storeLogin(user.get(0).getKeypradha1());
                    Toast.makeText(login.this, "data berhasil..", Toast.LENGTH_SHORT).show();
                    Intent rekomendasi = new Intent(login.this, penyedia1.class);
                    startActivity(rekomendasi);
                }
                else if (kode.equals("2")){
                    Toast.makeText(login.this, "password salah..", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(login.this, "Data gagal..", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
               // pd.setMessage("gagal ...");
                //pd.setCancelable(false);
                //pd.show();
                Log.d("RETRO", "Falure : " + "Riquest Tidak Falid");
            }
        });
    }

    public void RegistrasiUser(View v){
        Intent regis_log = new Intent(login.this, registrasi1.class);
        startActivity(regis_log);
    }
}