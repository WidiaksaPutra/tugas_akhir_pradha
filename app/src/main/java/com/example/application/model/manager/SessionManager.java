package com.example.application.model.manager;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    public static final String KEY = "keypradha1";

    public static final String UserLatitude = "latitudeUser";
    public static final String UserLongitude = "longitudeUser";

    public static final String NamaLengkap = "namalengkap";
    public static final String UserLogin = "userlogin";

    public static final String Umur = "umur";
    public static final String Gender = "gender";
    public static final String WisataAlam = "wisataalam";
    public static final String WisataBuatan = "wisatabuatan";
    public static final String WisataBudaya = "wisatabudaya";

    public static final String Asal = "asal";
    public static final String Hp = "hp";

    private final String SHARE_NAME = "loginsession";
    private final int MODE_PRIVATE = 0;
    private Context _context;

    public SessionManager (Context context){
        this._context = context;
        sp = _context.getSharedPreferences(SHARE_NAME,MODE_PRIVATE);
        editor = sp.edit();
    }

    public void storeLogin(String keypradha1) {
        editor.putString(KEY, keypradha1);
        editor.commit();
    }
    public void storeUserLocation(String latitudeUser , String longitudeUser) {
        editor.putString(UserLatitude, latitudeUser);
        this.editor.commit();

        this.editor.putString(UserLongitude, longitudeUser);
        this.editor.commit();
    }
    public void storeUserRegis1(String namalengkap, String userlogin) {
        editor.putString(NamaLengkap, namalengkap);
        this.editor.commit();

        editor.putString(UserLogin, userlogin);
        this.editor.commit();
    }
    public void storeUserRegis2(String umur, String gender, String wisataalam, String wisatabuatan, String wisatabudaya) {
        editor.putString(Umur, umur);
        this.editor.commit();

        editor.putString(Gender, gender);
        this.editor.commit();

        editor.putString(WisataAlam, wisataalam);
        this.editor.commit();

        editor.putString(WisataBuatan, wisatabuatan);
        this.editor.commit();

        editor.putString(WisataBudaya, wisatabudaya);
        this.editor.commit();
    }
    public void storeUserRegis3(String umur, String gender, String asal, String hp) {
        editor.putString(Umur, umur);
        this.editor.commit();

        editor.putString(Gender, gender);
        this.editor.commit();

        editor.putString(Asal, asal);
        this.editor.commit();

        editor.putString(Hp, hp);
        this.editor.commit();
    }

    public HashMap getDetailLogin(){
        HashMap<String, String> map = new HashMap<>();
        map.put(KEY, sp.getString(KEY,null));
        return map;
    }
    public HashMap getUserLocation(){
        HashMap<String, String> map1 = new HashMap<>();
        map1.put(UserLatitude, sp.getString(UserLatitude,null));
        map1.put(UserLongitude, sp.getString(UserLongitude,null));
        return map1;
    }
    public HashMap getUserRegis1(){
        HashMap<String, String> regis1 = new HashMap<>();
        regis1.put(NamaLengkap, sp.getString(NamaLengkap,null));
        regis1.put(UserLogin, sp.getString(UserLogin,null));
        return regis1;
    }
    public HashMap getUserRegis2(){
        HashMap<String, String> regis2 = new HashMap<>();
        regis2.put(Umur, sp.getString(Umur,null));
        regis2.put(Gender, sp.getString(Gender,null));
        regis2.put(WisataAlam, sp.getString(WisataAlam,null));
        regis2.put(WisataBuatan, sp.getString(WisataBuatan,null));
        regis2.put(WisataBudaya, sp.getString(WisataBudaya,null));
        return regis2;
    }
    public HashMap getUserRegis3(){
        HashMap<String, String> regis3 = new HashMap<>();
        regis3.put(Umur, sp.getString(Umur,null));
        regis3.put(Gender, sp.getString(Gender,null));
        regis3.put(Asal, sp.getString(Asal,null));
        regis3.put(Hp, sp.getString(Hp,null));
        return regis3;
    }
}


