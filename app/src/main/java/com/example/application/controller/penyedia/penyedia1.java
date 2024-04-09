package com.example.application.controller.penyedia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.model.API.APISQL;
import com.example.application.model.manager.Retroserver;
import com.example.application.R;
import com.example.application.model.manager.SessionManager;
import com.example.application.model.adapter.adapterpenyedia;
import com.example.application.controller.awaluser.login;
import com.example.application.model.phpmodel.ResponsModel;
import com.example.application.model.phpmodel.SelectModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class penyedia1 extends AppCompatActivity {
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<SelectModel> mItems = new ArrayList<>();
    SessionManager sm;
    TextView keys;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.penyedia1);
        Button ajukan = (Button) findViewById(R.id.Ajukanwisata);
        pd = new ProgressDialog(this);
        ajukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ajukanwisata = new Intent(penyedia1.this, penyedia2.class);
                startActivity(ajukanwisata);
            }
        });
        mRecycler = (RecyclerView) findViewById(R.id.penyedia1);
        mManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mManager);

        pd.setMessage("Loading ...");
        pd.setCancelable(false);
        pd.show();

        APISQL api = Retroserver.getClient().create(APISQL.class);
        Call<ResponsModel> getdata = api.getBiodata2();
        getdata.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                pd.hide();
                Log.d("RETRO", "RESPONSE : " + response.body().getKode());
                mItems = response.body().getResult();

                mAdapter = new adapterpenyedia(penyedia1.this,mItems);
                mRecycler.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                pd.hide();
                Log.d("RETRO", "FAILED : respon gagal ");
            }
        });
    }
    public void backpenyedia1 (View v){
        Intent back = new Intent(penyedia1.this, login.class);
        startActivity(back);
    }
}
