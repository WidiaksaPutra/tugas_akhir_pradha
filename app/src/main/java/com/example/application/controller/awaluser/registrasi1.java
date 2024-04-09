package com.example.application.controller.awaluser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application.R;
import com.example.application.model.manager.SessionManager;

public class registrasi1 extends AppCompatActivity{
    EditText Fullname;
    RadioGroup Radiogroup;
    RadioButton Radiobutton;
    String fullnames, radiogroups;
    SessionManager sm;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrasi1);

        Fullname = (EditText) findViewById(R.id.fullname);
        Radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        sm = new SessionManager(registrasi1.this);
        pd = new ProgressDialog(this);
    }

    private boolean fullname(){
        fullnames = Fullname.getText().toString();
        if(fullnames.isEmpty()){
            Fullname.setError("nama lengkap tidak boleh kosong");
            return false;
        }
        else{
            Fullname.setError(null);
            return true;
        }
    }

    public void submit (View v){
        if(!fullname()){
            return;
        }
        pd.setMessage("send data ...");
        pd.setCancelable(false);
        pd.show();
        fullnames = Fullname.getText().toString();
        int radiobutton = Radiogroup.getCheckedRadioButtonId();
        Radiobutton = (RadioButton) findViewById(radiobutton);
        radiogroups = Radiobutton.getText().toString();

        sm.storeUserRegis1(fullnames, radiogroups);
        if(radiogroups.equals("Traveler")){
            Toast.makeText(registrasi1.this, "Traveler", Toast.LENGTH_SHORT).show();
            Intent reg = new Intent(registrasi1.this, registrasi2Traveler.class);
            startActivity(reg);
        }
        else if(radiogroups.equals("Travel_Provider")){
            Toast.makeText(registrasi1.this, "Travel_Provider", Toast.LENGTH_SHORT).show();
            Intent reg1 = new Intent(registrasi1.this, registrasi2TravelerProvider.class);
            startActivity(reg1);
        }
        else{
            Toast.makeText(registrasi1.this, "Pilihan Gagal", Toast.LENGTH_SHORT).show();
        }
    }

    public void back_login (View v){
        Intent back = new Intent(registrasi1.this, login.class);
        startActivity(back);
    }
}