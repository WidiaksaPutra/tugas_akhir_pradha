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
import com.example.application.model.API.APISQL;
import com.example.application.model.manager.Retroserver;
import com.example.application.R;
import com.example.application.model.manager.SessionManager;
import com.example.application.controller.card.update_rekomendasi;
import com.example.application.model.phpmodel.ResponsModel;
import com.example.application.model.phpmodel.SelectModel;
import com.example.application.controller.penyedia.monitoring;
import com.example.application.controller.penyedia.penyedia2;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class adapteradmin1 extends RecyclerView.Adapter<adapteradmin1.HolderData>{

    List<SelectModel> mList;
    Context ctx;

    public adapteradmin1 (Context ctx, List<SelectModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardpenyedia2,parent,false);
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
        /*String url = "http://192.168.1.104/web/pradha/upload/img3693.jpg";
        Glide.with(ctx).load(url).centerCrop().into(holder.Gambar);*/
        holder.dm = dm;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class HolderData extends RecyclerView.ViewHolder{
        TextView Nama_tempat,Waktu,keys2,keys1;
        ImageView Gambar;
        SessionManager sm;
        SelectModel dm;
        public HolderData (View v){
            super(v);
            Nama_tempat = (TextView) v.findViewById(R.id.card_name);
            Waktu = (TextView) v.findViewById(R.id.card_waktu);
            Gambar = (ImageView) v.findViewById(R.id.img_item_photo);
            keys2 = (TextView) v.findViewById(R.id.key2);
            keys1 = (TextView) v.findViewById(R.id.key1);
            Button detail = (Button) v.findViewById(R.id.update);
            detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detail = new Intent(ctx, update_rekomendasi.class);
                    detail.putExtra("keypradha2", dm.getKeypradha2());
                    detail.putExtra("Nama_tempat", dm.getNama_tempat());
                    detail.putExtra("Deskripsi", dm.getDeskripsi());
                    detail.putExtra("Waktu", dm.getWaktu());
                    detail.putExtra("Alamat_Lokasi", dm.getAlamat_Lokasi());
                    detail.putExtra("Harga", dm.getHarga());
                    detail.putExtra("Persyaratan", dm.getPersyaratan());
                    detail.putExtra("Jenis_Tempat", dm.getJenis_Tempat());
                    detail.putExtra("Gambar", dm.getGambar());
                    ctx.startActivity(detail);
                }
            });
            Button dell = (Button) v.findViewById(R.id.delete);
            dell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String key = keys2.getText().toString();
                    APISQL api = Retroserver.getClient().create(APISQL.class);
                    Call<ResponsModel> dellete = api.sendDeleteTempat(key);
                    dellete.enqueue(new Callback<ResponsModel>() {
                        @Override
                        public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                            Log.d("RETRO", "response : " + response.body().toString());
                            String kode = response.body().getKode();
                            if (kode.equals("1")) {
                                Intent refresh = new Intent(ctx, penyedia2.class);
                                ctx.startActivity(refresh);
                                Toast.makeText(ctx, "Sukses Dellete..", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponsModel> call, Throwable t) {
                            Log.d("RETRO", "Falure : " + "Riquest Tidak Falid");
                        }
                    });
                }
            });
            Button mon = (Button) v.findViewById(R.id.pengunjung);
            mon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent monitor = new Intent(ctx, monitoring.class);
                    monitor.putExtra("keypradha1", dm.getKeypradha1());
                    monitor.putExtra("keypradha2", dm.getKeypradha2());
                    ctx.startActivity(monitor);
                }
            });
        }
    }
}
