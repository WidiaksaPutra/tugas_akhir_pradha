package com.example.application.controller.awaluser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.application.R;
import com.example.application.model.manager.SessionManager;

import java.util.ArrayList;

public class registrasi2Traveler extends AppCompatActivity implements WisataAlam.onMultiChoiceListener, WisataBudaya.onMultiChoiceListener, WisataBuatan.onMultiChoiceListener{
    EditText Age;
    RadioGroup Radiogroup1;
    RadioButton Radiobutton1;
    String ages, radiogroups1,data,data1,data2;
    ProgressDialog pd;
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrasi2traveler);
        Age = (EditText) findViewById(R.id.age);
        Radiogroup1 = (RadioGroup) findViewById(R.id.radiogroup1);

        Button Alam = findViewById(R.id.alam);
        Alam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment multiChoiceDialog = new WisataAlam();
                multiChoiceDialog.setCancelable(false);
                multiChoiceDialog.show(getSupportFragmentManager(), "Wisata Alam");
            }
        });

        Button Budaya = findViewById(R.id.budaya);
        Budaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment multiChoiceDialog = new WisataBudaya();
                multiChoiceDialog.setCancelable(false);
                multiChoiceDialog.show(getSupportFragmentManager(), "Wisata Budaya");
            }
        });

        Button Buatan = findViewById(R.id.buatan);
        Buatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment multiChoiceDialog = new WisataBuatan();
                multiChoiceDialog.setCancelable(false);
                multiChoiceDialog.show(getSupportFragmentManager(), "Wisata Budaya");
            }
        });

//        sm = new SessionManager(registrasi2Traveler.this);
//        HashMap<String, String> regis1 = sm.getUserRegis1();
//        Fullname.setText(regis1.get(sm.NamaLengkap));
//        Radiogroup.setText(regis1.get(sm.UserLogin));
        sm = new SessionManager(registrasi2Traveler.this);
        pd = new ProgressDialog(this);
    }

    @Override
    public void onPositiveButtonClicked(String[] list, ArrayList<String> selectedWisataAlam) {
        data = selectedWisataAlam.toString();
        Toast.makeText(this,data, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPositiveButtonClicked1(String[] list, ArrayList<String> selectedWisataBudaya) {
        data1 = selectedWisataBudaya.toString();
        Toast.makeText(this,data1, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPositiveButtonClicked2(String[] list, ArrayList<String> selectedWisataBuatan) {
        data2 = selectedWisataBuatan.toString();
        Toast.makeText(this,data2, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNegativeButtonClicked() {
        Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
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

    public void submit (View v){
        if(!age()){
            return;
        }
        pd.setMessage("send data ...");
        pd.setCancelable(false);
        pd.show();
        ages = Age.getText().toString();
        int radiobutton1 = Radiogroup1.getCheckedRadioButtonId();
        Radiobutton1 = (RadioButton) findViewById(radiobutton1);
        radiogroups1 = Radiobutton1.getText().toString();
        sm.storeUserRegis2(ages, radiogroups1, data, data2, data1);
        Toast.makeText(registrasi2Traveler.this, "Lanjut", Toast.LENGTH_SHORT).show();
        Intent reg = new Intent(registrasi2Traveler.this, registrasiUserTraveler.class);
        startActivity(reg);
    }

    public void back_login (View v){
        Intent back = new Intent(registrasi2Traveler.this, registrasi1.class);
        startActivity(back);
    }
}