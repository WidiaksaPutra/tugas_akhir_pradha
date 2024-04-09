package com.example.application.model.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.application.R;
import com.example.application.controller.card.penyedia_detail_wisata;
import com.example.application.model.phpmodel.SelectModel;

import java.util.List;

public class adapterpenyedia extends RecyclerView.Adapter<adapterpenyedia.HolderData>{
    private List<SelectModel> mList;
    private Context ctx;

    public adapterpenyedia (Context ctx, List<SelectModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardpenyedia1,parent,false);
        adapterpenyedia.HolderData holder = new adapterpenyedia.HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        SelectModel dm = mList.get(position);
        holder.Nama_tempat.setText(dm.getNama_tempat());
        holder.Deskripsi.setText(dm.getDeskripsi());
        holder.Waktu.setText(dm.getWaktu());
        Glide.with(ctx).load(dm.getGambar()).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.Gambar);
        holder.dm = dm;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView Nama_tempat,Waktu,Deskripsi;
        ImageView Gambar;

        SelectModel dm;
        public HolderData (View v){
            super(v);
            Gambar = (ImageView) v.findViewById(R.id.img_item_photo);
            Nama_tempat = (TextView) v.findViewById(R.id.card_name);
            Waktu = (TextView) v.findViewById(R.id.card_waktu);
            Deskripsi = (TextView) v.findViewById(R.id.card_deskripsi);
            Button detail = (Button) v.findViewById(R.id.detail);
            detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detaill = new Intent(ctx, penyedia_detail_wisata.class);
                    detaill.putExtra("keypradha2", dm.getKeypradha2());
                    detaill.putExtra("Nama_tempat", dm.getNama_tempat());
                    detaill.putExtra("Deskripsi", dm.getDeskripsi());
                    detaill.putExtra("Waktu", dm.getWaktu());
                    detaill.putExtra("Alamat_Lokasi", dm.getAlamat_Lokasi());
                    detaill.putExtra("Harga", dm.getHarga());
                    detaill.putExtra("Persyaratan", dm.getPersyaratan());
                    detaill.putExtra("Jenis_Tempat", dm.getJenis_Tempat());
                    detaill.putExtra("Gambar", dm.getGambar());
                    ctx.startActivity(detaill);
                }
            });
        }
    }
}
