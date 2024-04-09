package com.example.application.controller.card;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.application.model.API.APISQL;
import com.example.application.model.manager.Retroserver;
import com.example.application.R;
import com.example.application.model.phpmodel.ResponsModel;
import com.example.application.controller.penyedia.penyedia2;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class update_rekomendasi extends AppCompatActivity {
    EditText NamaWisata,DeskripsiTempat,Waktu,Lokasi,Harga,Aturan;
    TextView NamaWisata1,jenistempat;
    ImageView Gambar,imageView;
    TextView Key;
    String part_image;
    Button Update;
    RadioGroup RadioGroup;
    RadioButton RadioButton;
    ProgressDialog pd;

    private static final int STORAGE_PERMISSION_CODE=4655;
    private int PICK_IMAGE_RESULT = 1;
    private Uri filepath;
    private Bitmap bitmap;
    private SwipeRefreshLayout SwipeRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_rekomendasi);

        Key = (TextView) findViewById(R.id.key);
        NamaWisata = (EditText) findViewById(R.id.nama_tempat);
        NamaWisata1 = (TextView) findViewById(R.id.nama_tempat1);
        DeskripsiTempat = (EditText) findViewById(R.id.deskripsitempat);
        Waktu = (EditText) findViewById(R.id.waktu);
        Lokasi = (EditText) findViewById(R.id.lokasi);
        Harga = (EditText) findViewById(R.id.harga);
        Aturan = (EditText) findViewById(R.id.aturan);
        RadioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        jenistempat = (TextView) findViewById(R.id.jenistempat);
        Update = (Button) findViewById(R.id.update);
        Gambar = (ImageView) findViewById(R.id.gambar);
        imageView = (ImageView) findViewById(R.id.imageView);
        pd = new ProgressDialog(this);

        Intent data = getIntent();
        Key.setText(data.getStringExtra("keypradha2"));
        NamaWisata.setText(data.getStringExtra("Nama_tempat"));
        NamaWisata1.setText(data.getStringExtra("Nama_tempat"));
        DeskripsiTempat.setText(data.getStringExtra("Deskripsi"));
        Waktu.setText(data.getStringExtra("Waktu"));
        Lokasi.setText(data.getStringExtra("Alamat_Lokasi"));
        Harga.setText(data.getStringExtra("Harga"));
        jenistempat.setText(data.getStringExtra("Jenis_Tempat"));
        Aturan.setText(data.getStringExtra("Persyaratan"));
        Glide.with(this).load(data.getStringExtra("Gambar")).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(Gambar);

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("send data ...");
                pd.setCancelable(false);
                pd.show();

                RequestBody namatempat = RequestBody.create(MediaType.parse("text/plain"), NamaWisata.getText().toString().trim());
                RequestBody namatempat1 = RequestBody.create(MediaType.parse("text/plain"), NamaWisata1.getText().toString().trim());
                RequestBody deskripsi = RequestBody.create(MediaType.parse("text/plain"), DeskripsiTempat.getText().toString().trim());
                RequestBody waktu = RequestBody.create(MediaType.parse("text/plain"), Waktu.getText().toString().trim());
                RequestBody alamat = RequestBody.create(MediaType.parse("text/plain"), Lokasi.getText().toString().trim());
                RequestBody harga = RequestBody.create(MediaType.parse("text/plain"), Harga.getText().toString().trim());
                RequestBody persyaratan = RequestBody.create(MediaType.parse("text/plain"), Aturan.getText().toString().trim());
                RequestBody key = RequestBody.create(MediaType.parse("text/plain"), Key.getText().toString().trim());
                int radiobutton = RadioGroup.getCheckedRadioButtonId();
                RadioButton = (RadioButton) findViewById(radiobutton);
                RequestBody jenis_tempat = RequestBody.create(MediaType.parse("text/plain"), RadioButton.getText().toString().trim());
                APISQL api = Retroserver.getClient().create(APISQL.class);
                Call<ResponsModel> update = api.sendUpdateTempat(namatempat,namatempat1,deskripsi,waktu,alamat,harga,persyaratan,jenis_tempat,key);
                update.enqueue(new Callback<ResponsModel>() {
                    @Override
                    public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                        pd.hide();
                        Log.d("RETRO", "response : " + response.body().toString());
                        String kode = response.body().getKode();
                        if (kode.equals("1")) {
                            Toast.makeText(update_rekomendasi.this, "Sukses Update..", Toast.LENGTH_SHORT).show();
                            Intent back = new Intent(update_rekomendasi.this, penyedia2.class);
                            startActivity(back);
                        }
                        else{
                            Toast.makeText(update_rekomendasi.this, "Data gagal Update..", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponsModel> call, Throwable t) {
                        pd.hide();
                        Log.d("RETRO", "Falure : " + "Riquest Tidak Falid");
                    }
                });
            }
        });
    }
    public void selectImage2(View view){
        showFileChooser();
    }
    private void showFileChooser(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"),PICK_IMAGE_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_RESULT && data != null && data.getData() != null){
            filepath= data.getData();
            try{
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                imageView.setImageBitmap(bitmap);
            }
            catch (Exception ex){

            }
        }
    }
    private String getPath(Uri uri){
        Cursor cursor=getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id=cursor.getString(0);
        document_id=document_id.substring(document_id.lastIndexOf(":")+1);
        cursor=getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,MediaStore.Images.Media._ID+"=?",new String[]{document_id},null);
        cursor.moveToFirst();
        String path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }

    public void saveimage(View view){
        SwipeRefresh = findViewById(R.id.swipe_refresh);
        RequestBody key = RequestBody.create(MediaType.parse("text/plain"), Key.getText().toString().trim());
        part_image = getPath(filepath);
        File imagefile = new File(part_image);
        APISQL api = Retroserver.getClient().create(APISQL.class);
        RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"),imagefile);
        MultipartBody.Part partImage = MultipartBody.Part.createFormData("imageupload", imagefile.getName(), reqBody);
        Call<ResponsModel> update1 = api.sendUpdateTempat1(key,partImage);
        update1.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                pd.hide();
                Log.d("RETRO", "response : " + response.body().toString());
                String kode = response.body().getKode();
                if (kode.equals("1")) {
                    Toast.makeText(update_rekomendasi.this, "Sukses Update Gambar..", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(update_rekomendasi.this, "Data gagal Update..", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                pd.hide();
                Log.d("RETRO", "Falure : " + "Riquest Tidak Falid");
            }
        });
    }

    public void backupdate (View v){
        Intent back = new Intent(update_rekomendasi.this, penyedia2.class);
        startActivity(back);
    }
}