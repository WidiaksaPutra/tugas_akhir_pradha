package com.example.application.controller.awaluser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application.R;
import com.example.application.model.API.APISQL;
import com.example.application.model.manager.Retroserver;
import com.example.application.model.manager.SessionManager;
import com.example.application.model.phpmodel.ResponsModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class registrasiUserTraveler extends AppCompatActivity{
    EditText Username,Password;
    ProgressDialog pd;
    String usernames, passwords, fullnames, ages, radiogroups, gender, alam, budaya, buatan;
    TextView Fullnames, Ages, Radiogroups, Gender, Alam, Budaya, Buatan;
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrasiuser);
        Fullnames = (TextView) findViewById(R.id.fullname);
        Ages = (TextView) findViewById(R.id.age);
        Radiogroups = (TextView) findViewById(R.id.radiogroup);
        Gender = (TextView) findViewById(R.id.gender);
        Alam = (TextView) findViewById(R.id.alam);
        Budaya = (TextView) findViewById(R.id.budaya);
        Buatan = (TextView) findViewById(R.id.buatan);

        Username = (EditText) findViewById(R.id.username);
        Password = (EditText) findViewById(R.id.password);
        pd = new ProgressDialog(this);
    }

    private boolean username(){
        usernames = Username.getText().toString();
        if(usernames.isEmpty()){
            Username.setError("username tidak boleh kosong");
            return false;
        }
        else{
            Username.setError(null);
            return true;
        }
    }

    private boolean password(){
        passwords = Password.getText().toString();
        if(passwords.isEmpty()){
            Password.setError("password tidak boleh kosong");
            return false;
        }
        else{
            Password.setError(null);
            return true;
        }
    }

    public void submit (View v){
        if(!username() | !password()){
            return;
        }
        pd.setMessage("send data ...");
        pd.setCancelable(false);
        pd.show();

        sm = new SessionManager(registrasiUserTraveler.this);
        HashMap<String, String> regis1 = sm.getUserRegis1();
        Fullnames.setText(regis1.get(sm.NamaLengkap));
        Radiogroups.setText(regis1.get(sm.UserLogin));

        HashMap<String, String> regis2 = sm.getUserRegis2();
        Ages.setText(regis2.get(sm.Umur));
        Gender.setText(regis2.get(sm.Gender));
        Alam.setText(regis2.get(sm.WisataAlam));
        Buatan.setText(regis2.get(sm.WisataBuatan));
        Budaya.setText(regis2.get(sm.WisataBudaya));

        usernames = Username.getText().toString();
        passwords = Password.getText().toString();
        fullnames = Fullnames.getText().toString();
        radiogroups = Radiogroups.getText().toString();
        ages = Ages.getText().toString();
        gender = Gender.getText().toString();
        alam =  Alam.getText().toString();
        budaya =  Budaya.getText().toString();
        buatan =  Buatan.getText().toString();

        APISQL API = Retroserver.getClient().create(APISQL.class);
        Call<ResponsModel> sendbio = API.sendBiodata(fullnames,ages,usernames,passwords,radiogroups,gender,alam,budaya,buatan);
        sendbio.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                pd.hide();
                Log.d("RETRO", "response : " + response.body().toString());
                String kode = response.body().getKode();
                if (kode.equals("1")) {

                    Toast.makeText(registrasiUserTraveler.this, "Sukses Registrasi..", Toast.LENGTH_SHORT).show();
                    Intent back = new Intent(registrasiUserTraveler.this, login.class);
                    startActivity(back);
                }
                else if (kode.equals("3")) {
                    Toast.makeText(registrasiUserTraveler.this, "Username sudah terdaftar..", Toast.LENGTH_SHORT).show();
                }
                else if (kode.equals("5")){
                    Toast.makeText(registrasiUserTraveler.this, "Umur tidak valid..", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(registrasiUserTraveler.this, "Data gagal ditambahkan..", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                pd.hide();
                Log.d("RETRO", "Falure : " + "Riquest Tidak Falid");
            }
        });
    }

    public void back_login (View v){
        Intent back = new Intent(registrasiUserTraveler.this, registrasi2Traveler.class);
        startActivity(back);
    }
}