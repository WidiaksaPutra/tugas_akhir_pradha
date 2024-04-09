package com.example.application.model.phpmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponsModel {
    @SerializedName("kode")
    String kode;
    @SerializedName("pesan")
    String pesan;
    List<SelectModel> result;


    public List<SelectModel> getResult() {
        return result;
    }

    public void setResult(List<SelectModel> result) {
        this.result = result;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }
}
