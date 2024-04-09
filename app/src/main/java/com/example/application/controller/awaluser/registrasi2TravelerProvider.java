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

public class registrasi2TravelerProvider extends AppCompatActivity{
    EditText Age,Origin,Hp;
    RadioGroup Radiogroup1;
    RadioButton Radiobutton1;
    String ages, radiogroups1, asal, hp;
    ProgressDialog pd;
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrasi2travelprovider);

        Origin = (EditText) findViewById(R.id.origin);
        Age = (EditText) findViewById(R.id.age);
        Hp = (EditText) findViewById(R.id.hp);
        Radiogroup1 = (RadioGroup) findViewById(R.id.radiogroup1);

//        sm = new SessionManager(registrasi2Traveler.this);
//        HashMap<String, String> regis1 = sm.getUserRegis1();
//        Fullname.setText(regis1.get(sm.NamaLengkap));
//        Radiogroup.setText(regis1.get(sm.UserLogin));
        sm = new SessionManager(registrasi2TravelerProvider.this);
        pd = new ProgressDialog(this);
    }

    private boolean age(){
        ages = Age.getText().toString();
        if(ages.isEmpty()){
            Age.setError("umur tidak boleh kosong");
            return false;
        }
        else{
            Age.setError(null);
            return true;
        }
    }

    private boolean asal(){
        asal = Origin.getText().toString();
        if(asal.isEmpty()){
            Origin.setError("asal tidak boleh kosong");
            return false;
        }
        else{
            Origin.setError(null);
            return true;
        }
    }

    private boolean hp(){
        hp = Hp.getText().toString();
        if(hp.isEmpty()){
            Hp.setError("no kontak tidak boleh kosong");
            return false;
        }
        else{
            Hp.setError(null);
            return true;
        }
    }

    public void submit (View v){
        if(!age() | !asal() | !hp()){
            return;
        }
        pd.setMessage("send data ...");
        pd.setCancelable(false);
        pd.show();
        ages = Age.getText().toString();
        int radiobutton1 = Radiogroup1.getCheckedRadioButtonId();
        Radiobutton1 = (RadioButton) findViewById(radiobutton1);
        radiogroups1 = Radiobutton1.getText().toString();
        asal = Origin.getText().toString();
        hp = Hp.getText().toString();
        sm.storeUserRegis3(ages, radiogroups1, asal, hp);
        Toast.makeText(registrasi2TravelerProvider.this, "Lanjut", Toast.LENGTH_SHORT).show();
        Intent reg = new Intent(registrasi2TravelerProvider.this, registrasiUserTraveler1.class);
        startActivity(reg);
    }

    public void back_login (View v){
        Intent back = new Intent(registrasi2TravelerProvider.this, registrasi1.class);
        startActivity(back);
    }
}