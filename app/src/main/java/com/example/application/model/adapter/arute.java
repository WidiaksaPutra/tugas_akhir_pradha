package com.example.application.model.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.application.MapsCih;
import com.example.application.R;
import com.example.application.controller.card.boking_detail_wisata2;
import com.example.application.model.manager.SessionManager;
import com.example.application.model.phpmodel.SelectModel;

import java.util.HashMap;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

public class arute extends RecyclerView.Adapter<arute.HolderData> {

    private List<SelectModel> mList;
    private Context ctx;

    public arute (Context ctx, List<SelectModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardmenu4,parent,false);
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
            holder.dm = dm;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView Nama_tempat,Waktu,Jarak,keys1,keys2;
        ImageView Gambar;

        LocationManager locationManager;
        private static final int REQUEST_LOCATION_PERMISSION = 1;
        double latitudeUser,longitudeUser;
        LocationListener locationListener;
        String lat,lang;

        SessionManager sm;
        public SelectModel dm,cm;
        public HolderData (View v){
            super(v);
                Nama_tempat = (TextView) v.findViewById(R.id.card_name);
                Waktu = (TextView) v.findViewById(R.id.card_waktu);
                Jarak = (TextView) v.findViewById(R.id.card_deskripsi);
                Gambar = (ImageView) v.findViewById(R.id.img_item_photo);
                keys1 = (TextView) v.findViewById(R.id.key1);
                keys2 = (TextView) v.findViewById(R.id.key2);
                sm = new SessionManager(ctx);

            locationManager = (LocationManager) ctx.getSystemService(LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) ctx, new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, REQUEST_LOCATION_PERMISSION);
            }
            else{
                locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        latitudeUser = location.getLatitude();
                        longitudeUser = location.getLongitude();
                        lat = String.valueOf(Double.valueOf(latitudeUser));
                        lang = String.valueOf(Double.valueOf(longitudeUser));
                        sm.storeUserLocation(lat, lang);

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

                Button detail = (Button) v.findViewById(R.id.detail);
                detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent detaill = new Intent(ctx, boking_detail_wisata2.class);
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

                Button rute = (Button) v.findViewById(R.id.rute);
                rute.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String, String> map1 = sm.getUserLocation();
                        Intent rutee = new Intent(ctx, MapsCih.class);
                        rutee.putExtra("latitude", dm.getLatitude());
                        rutee.putExtra("longitude", dm.getLongitude());
                        rutee.putExtra("latitudeUser", map1.get(sm.UserLatitude));
                        rutee.putExtra("langitudeUser", map1.get(sm.UserLongitude));
                        ctx.startActivity(rutee);
                    }
                });
        }
    }
}
