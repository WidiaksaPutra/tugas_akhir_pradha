package com.example.application.controller.penyedia;

        import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application.MapsActivity_Admin;
import com.example.application.R;
import com.example.application.model.API.APISQL;
import com.example.application.model.manager.Retroserver;
import com.example.application.model.manager.SessionManager;
import com.example.application.model.phpmodel.ResponsModel;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//        import com.example.application.MapsActivity_Admin;

public class rekomendasi_wisata extends AppCompatActivity {
    ImageView imageView;
    EditText NamaWisata,DeskripsiTempat,Waktu,Lokasi,Harga,Aturan;
    TextView keys,Latitude,Longitude;
    String part_image;
    RadioGroup RadioGroup;
    RadioButton RadioButton;
    ProgressDialog pd;
    SessionManager sm;
    Float Lat, Long;

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    public static final String session = "session";

    private static final int STORAGE_PERMISSION_CODE=4655;
    private int PICK_IMAGE_RESULT = 1;
    private Uri filepath;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rekomendasi_wisata);
        NamaWisata = (EditText) findViewById(R.id.namawisata);
        DeskripsiTempat = (EditText) findViewById(R.id.deskripsitempat);
        Waktu = (EditText) findViewById(R.id.waktu);
        Lokasi = (EditText) findViewById(R.id.lokasi);
        Harga = (EditText) findViewById(R.id.harga);
        Aturan = (EditText) findViewById(R.id.aturan);
        RadioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        keys = (TextView) findViewById(R.id.key);
        sm = new SessionManager(rekomendasi_wisata.this);
        HashMap<String, String> map = sm.getDetailLogin();
        keys.setText(map.get(sm.KEY));
        Latitude = (TextView) findViewById(R.id.latitude);
        Longitude = (TextView) findViewById(R.id.longitude);

        imageView = (ImageView) findViewById(R.id.imageView);
        pref = getSharedPreferences(session, MODE_PRIVATE);
        Lat = pref.getFloat("getlatitude", 0);
        Long = pref.getFloat("getlongitude", 0);
        Latitude.setText(String.valueOf(Lat));
        Longitude.setText(String.valueOf(Long));
        pd = new ProgressDialog(this);
        pd.setMessage("loading ...");
    }

    private boolean nama(){
        String namatempat = NamaWisata.getText().toString();
        if(namatempat.isEmpty()){
            NamaWisata.setError("nama tempat tidak boleh kosong");
            return false;
        }
        else{
            NamaWisata.setError(null);
            return true;
        }
    }

    private boolean deskripsi(){
        String deskripsi = DeskripsiTempat.getText().toString();
        if(deskripsi.isEmpty()){
            DeskripsiTempat.setError("deskripsi tidak boleh kosong");
            return false;
        }
        else{
            DeskripsiTempat.setError(null);
            return true;
        }
    }

    private boolean waktu(){
        String waktu = Waktu.getText().toString();
        if(waktu.isEmpty()){
            Waktu.setError("waktu tidak boleh kosong");
            return false;
        }
        else{
            Waktu.setError(null);
            return true;
        }
    }

    private boolean lokasi(){
        String alamat = Lokasi.getText().toString();
        if(alamat.isEmpty()){
            Lokasi.setError("alamat detail tidak boleh kosong");
            return false;
        }
        else{
            Lokasi.setError(null);
            return true;
        }
    }

    private boolean harga(){
        String harga = Harga.getText().toString();
        if(harga.isEmpty()){
            Harga.setError("harga tidak boleh kosong");
            return false;
        }
        else{
            Harga.setError(null);
            return true;
        }
    }

    private boolean aturan(){
        String persyaratan = Aturan.getText().toString();
        if(persyaratan.isEmpty()){
            Aturan.setError("aturan tidak boleh kosong");
            return false;
        }
        else{
            Aturan.setError(null);
            return true;
        }
    }

    public void submit(View v){
        if(!nama() | !deskripsi() | !waktu() | !lokasi() | !harga() | !aturan()){
            return;
        }
        pd.show();
        RequestBody namatempat = RequestBody.create(MediaType.parse("text/plain"), NamaWisata.getText().toString().trim());
        RequestBody deskripsi = RequestBody.create(MediaType.parse("text/plain"), DeskripsiTempat.getText().toString().trim());
        RequestBody waktu = RequestBody.create(MediaType.parse("text/plain"), Waktu.getText().toString().trim());
        RequestBody alamat = RequestBody.create(MediaType.parse("text/plain"), Lokasi.getText().toString().trim());
        RequestBody harga = RequestBody.create(MediaType.parse("text/plain"), Harga.getText().toString().trim());

        RequestBody latitude = RequestBody.create(MediaType.parse("text/plain"),Latitude.getText().toString().trim());
        RequestBody longitude = RequestBody.create(MediaType.parse("text/plain"), Longitude.getText().toString().trim());

        RequestBody persyaratan = RequestBody.create(MediaType.parse("text/plain"), Aturan.getText().toString().trim());
        int radiobutton = RadioGroup.getCheckedRadioButtonId();
        RadioButton = (RadioButton) findViewById(radiobutton);
        RequestBody jenistempat = RequestBody.create(MediaType.parse("text/plain"), RadioButton.getText().toString().trim());
        RequestBody key = RequestBody.create(MediaType.parse("text/plain"), keys.getText().toString().trim());
        part_image = getPath(filepath);
        File imagefile = new File(part_image);
        RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"),imagefile);
        MultipartBody.Part partImage = MultipartBody.Part.createFormData("imageupload", imagefile.getName(), reqBody);
        APISQL API = Retroserver.getClient().create(APISQL.class);
        Call<ResponsModel> sendbio1 = API.sendBiodata1(namatempat,deskripsi,waktu,alamat,harga,persyaratan,jenistempat,key,partImage,latitude,longitude);
        sendbio1.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                pd.hide();
                Log.d("RETRO", "response : " + response.body().toString());
                String kode = response.body().getKode();
                if (kode.equals("1")) {
                    Intent rekom = new Intent(rekomendasi_wisata.this, penyedia2.class);
                    startActivity(rekom);
                    Toast.makeText(rekomendasi_wisata.this, "Sukses Rekomendasi..", Toast.LENGTH_SHORT).show();
                }
                else if (kode.equals("3")) {
                    Toast.makeText(rekomendasi_wisata.this, "Nama Tempat sudah terdaftar..", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(rekomendasi_wisata.this, "Data gagal ditambahkan..", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                pd.hide();
                Log.d("RETRO", "Falure : " + "Riquest Tidak Falid");
            }
        });
    }

    public void selectImage(View view){
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

    public void backafterreg(View v){
        Intent back = new Intent(rekomendasi_wisata.this, penyedia2.class);
        startActivity(back);
    }

    public void map (View v){
        Intent mapwisata = new Intent(rekomendasi_wisata.this, MapsActivity_Admin.class);
        startActivity(mapwisata);
    }
}
