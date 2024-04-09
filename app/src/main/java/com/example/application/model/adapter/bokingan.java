package com.example.application.model.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.application.R;
import com.example.application.controller.card.boking_detail_wisata;
import com.example.application.controller.wisata.boking;
import com.example.application.controller.wisata.rute;
import com.example.application.model.API.APISQL;
import com.example.application.model.manager.Retroserver;
import com.example.application.model.manager.SessionManager;
import com.example.application.model.phpmodel.ResponsModel;
import com.example.application.model.phpmodel.SelectModel;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class bokingan extends RecyclerView.Adapter<bokingan.HolderData> {

    private List<SelectModel> mList;
    private Context ctx;

    public bokingan (Context ctx, List<SelectModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardmenu2,parent,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
            SelectModel dm = mList.get(position);
            holder.Nama_tempat.setText(dm.getNama_tempat());
            holder.Waktu.setText(dm.getWaktu());
            holder.sm = new SessionManager(ctx);
            HashMap<String, String> map = holder.sm.getDetailLogin();
            holder.keys1.setText(map.get(holder.sm.KEY));
            holder.keys2.setText(dm.getKeypradha2());
            HashMap<String, String> map1 = holder.sm.getUserLocation();
            holder.uselat.setText(map1.get(holder.sm.UserLatitude));
            holder.uselong.setText(map1.get(holder.sm.UserLongitude));
            Glide.with(ctx).load(dm.getGambar()).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.Gambar);
            holder.latitude.setText(dm.getLatitude());
            holder.longitude.setText(dm.getLongitude());
            holder.dm = dm;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView Nama_tempat,Waktu,Jarak,keys1,keys2,latitude,longitude,uselat,uselong;
        ImageView Gambar;
        SessionManager sm;
        public SelectModel dm;
        public HolderData (View v){
            super(v);
            Nama_tempat = (TextView) v.findViewById(R.id.card_name);
            Waktu = (TextView) v.findViewById(R.id.card_waktu);
            Jarak = (TextView) v.findViewById(R.id.card_deskripsi);
            Gambar = (ImageView) v.findViewById(R.id.img_item_photo);
            keys1 = (TextView) v.findViewById(R.id.key1);
            keys2 = (TextView) v.findViewById(R.id.key2);
            latitude = (TextView) v.findViewById(R.id.lat);
            longitude = (TextView) v.findViewById(R.id.lang);
            uselat = (TextView) v.findViewById(R.id.uselat);
            uselong = (TextView) v.findViewById(R.id.uselang);

            Button cancel = (Button) v.findViewById(R.id.cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String key1 = keys1.getText().toString();
                    String key2 = keys2.getText().toString();
                    APISQL api = Retroserver.getClient().create(APISQL.class);
                    Call<ResponsModel> cancell = api.cancelBooking(key1,key2);
                    cancell.enqueue(new Callback<ResponsModel>() {
                        @Override
                        public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                            Log.d("RETRO", "response : " + response.body().toString());
                            String kode = response.body().getKode();
                            if (kode.equals("1")) {
                                    Toast.makeText(ctx, "Data terhapus", Toast.LENGTH_SHORT).show();
                                    Intent refresh = new Intent(ctx, boking.class);
                                    ctx.startActivity(refresh);
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponsModel> call, Throwable t) {
                            Log.d("RETRO", "Falure : " + "Riquest Failed");
                        }
                    });
                }
            });
            Button detail = (Button) v.findViewById(R.id.detail);
            detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detaill = new Intent(ctx, boking_detail_wisata.class);
                    detaill.putExtra("keypradha2", dm.getKeypradha2());
                    detaill.putExtra("Nama_tempat", dm.getNama_tempat());
                    detaill.putExtra("Deskripsi", dm.getDeskripsi());
                    detaill.putExtra("Waktu", dm.getWaktu());
                    detaill.putExtra("Alamat_Lokasi", dm.getAlamat_Lokasi());
                    detaill.putExtra("Harga", dm.getHarga());
                    detaill.putExtra("Persyaratan", dm.getPersyaratan());
                    detaill.putExtra("Jenis_Tempat", dm.getJenis_Tempat());
                    detaill.putExtra("Gambar", dm.getGambar());
                    detaill.putExtra("latitude", dm.getLatitude());
                    detaill.putExtra("longitude", dm.getLongitude());
                    ctx.startActivity(detaill);
                }
            });
            Button akhir = (Button) v.findViewById(R.id.akhir);
            akhir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String key1 = keys1.getText().toString();
                    String key2 = keys2.getText().toString();
                    APISQL api = Retroserver.getClient().create(APISQL.class);
                    Call<ResponsModel> cancell = api.akhir(key1,key2);
                    cancell.enqueue(new Callback<ResponsModel>() {
                        @Override
                        public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                            Log.d("RETRO", "response : " + response.body().toString());
                            String kode = response.body().getKode();
                            if (kode.equals("1")) {
//                                Boolean checkdeleteuserdata = databaseHelper.deleteuserdata(key2);
//                                if(checkdeleteuserdata == true){
                                Toast.makeText(ctx, "Berhasil", Toast.LENGTH_SHORT).show();
                                Intent refresh = new Intent(ctx, rute.class);
                                ctx.startActivity(refresh);
//                                    Toast.makeText(ctx, "Success Cancel..", Toast.LENGTH_SHORT).show();
//                                }
//                                else{
//                                    Toast.makeText(ctx, "Data gagal", Toast.LENGTH_SHORT).show();
//                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponsModel> call, Throwable t) {
                            Log.d("RETRO", "Falure : " + "Riquest Failed");
                        }
                    });
                }
            });
        }
    }
}
