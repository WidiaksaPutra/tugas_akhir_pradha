package com.example.application.model.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
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
import com.example.application.controller.card.wisatawanbudaya_detail_wisata;
import com.example.application.controller.wisata.wisatabudaya;
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


public class adapterdata1 extends RecyclerView.Adapter<adapterdata1.HolderData> {

    private List<SelectModel> mList;
    private Context ctx;
    Integer Jumlah;
    protected Cursor cursor;

    public adapterdata1 (Context ctx, List<SelectModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardmenu1,parent,false);
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
        Glide.with(ctx).load(dm.getGambar()).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.Gambar);
        HashMap<String, String> map1 = holder.sm.getUserLocation();
        holder.LatitudeUser.setText(map1.get(holder.sm.UserLatitude));
        holder.LongitudeUser.setText(map1.get(holder.sm.UserLongitude));
        holder.LatitudeWisata.setText(dm.getLatitude());
        holder.LongitudeWisata.setText(dm.getLongitude());

//        lat = Double.valueOf(String.valueOf(bundle.getString("latitude"))).doubleValue();
        holder.latUse = Double.valueOf(String.valueOf(map1.get(holder.sm.UserLatitude))).doubleValue();
        holder.langUse = Double.valueOf(String.valueOf(map1.get(holder.sm.UserLongitude))).doubleValue();

        holder.latWisata = Double.valueOf(String.valueOf(dm.getLatitude())).doubleValue();
        holder.langWisata = Double.valueOf(String.valueOf(dm.getLongitude())).doubleValue();

        Location locationCurrent = new Location("Lokasi Sekarang");
        locationCurrent.setLatitude(holder.latUse);
        locationCurrent.setLongitude(holder.langUse);

        Location locationB = new Location("Lokasi Tujuan");
        locationB.setLatitude(holder.latWisata);
        locationB.setLongitude(holder.langWisata);

        holder.distance = locationCurrent.distanceTo(locationB) / 1000;
        holder.distance = (double) (Math.round(holder.distance * 100)) / 100;

        String Distance = String.valueOf(Double.valueOf(holder.distance));
        holder.Jarak.setText(String.valueOf(Distance));
        holder.dm = dm;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class HolderData extends RecyclerView.ViewHolder{
        TextView Nama_tempat,Waktu,Harga,keys2,keys1,LatitudeUser,LongitudeUser,LatitudeWisata,LongitudeWisata,Jarak,keys3,jumlah;
        ImageView Gambar;
        double latUse,langUse,latWisata,langWisata,distance;
        SessionManager sm;
        public SelectModel dm;
//        sm = new SessionManager(ctx);
//        HashMap<String, String> map = sm.getDetailLogin();

        public HolderData (View v){
            super(v);
            Nama_tempat = (TextView) v.findViewById(R.id.card_name);
            Waktu = (TextView) v.findViewById(R.id.card_waktu);
//            Harga = (TextView) v.findViewById(R.id.card_harga);
            Gambar = (ImageView) v.findViewById(R.id.img_item_photo);
            keys2 = (TextView) v.findViewById(R.id.key);
            keys1 = (TextView) v.findViewById(R.id.key1);
            LatitudeUser = (TextView) v.findViewById(R.id.userlatitude);
            LongitudeUser = (TextView) v.findViewById(R.id.userlongitude);

            LatitudeWisata = (TextView) v.findViewById(R.id.latitudeWisata);
            LongitudeWisata = (TextView) v.findViewById(R.id.longitudeWisata);
            Jarak = (TextView) v.findViewById(R.id.card_jarak);

            jumlah = (TextView) v.findViewById(R.id.jumlah);


            Button detail = (Button) v.findViewById(R.id.detail);
            detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detaill = new Intent(ctx, wisatawanbudaya_detail_wisata.class);
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
            Button dell = (Button) v.findViewById(R.id.boking);
            dell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String key1 = keys1.getText().toString();
                    String key2 = keys2.getText().toString();

                    APISQL api = Retroserver.getClient().create(APISQL.class);
                    Call<ResponsModel> boking = api.bokingTempat(key1,key2);
                    boking.enqueue(new Callback<ResponsModel>() {
                        @Override
                        public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                            Log.d("RETRO", "response : " + response.body().toString());
                            String kode = response.body().getKode();
                            if (kode.equals("1")) {
                                Intent refresh = new Intent(ctx, wisatabudaya.class);
                                ctx.startActivity(refresh);
                            }
                            else if(kode.equals("3")){
                                Toast.makeText(ctx, "The data it's been booking..", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponsModel> call, Throwable t) {
                            Log.d("RETRO", "Falure : " + "Riquest Not Falid");
                        }
                    });
                }
            });
        }
    }
}
