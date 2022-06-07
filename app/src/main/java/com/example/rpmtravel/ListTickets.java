package com.example.rpmtravel;

import android.widget.ImageView;

public class ListTickets {
    String harga_tiket, waktu_keberangkatan, durasi, rute, url_thumbnail, kode_paket, nama_paket;
    public ListTickets() {
    }

    public ListTickets(String harga_tiket, String waktu_keberangkatan, String durasi, String rute, String url_thumbnail, String kode_paket, String nama_paket) {
        this.harga_tiket = harga_tiket;
        this.waktu_keberangkatan = waktu_keberangkatan;
        this.durasi = durasi;
        this.rute = rute;
        this.url_thumbnail = url_thumbnail;
        this.kode_paket = kode_paket;
        this.nama_paket = nama_paket;
    }

    public String getHarga_tiket() {
        return harga_tiket;
    }

    public void setHarga_tiket(String harga_tiket) {
        this.harga_tiket = harga_tiket;
    }

    public String getWaktu_keberangkatan() {
        return waktu_keberangkatan;
    }

    public void setWaktu_keberangkatan(String waktu_keberangkatan) {
        this.waktu_keberangkatan = waktu_keberangkatan;
    }

    public String getDurasi() {
        return durasi;
    }

    public void setDurasi(String durasi) {
        this.durasi = durasi;
    }

    public String getRute() {
        return rute;
    }

    public void setRute(String rute) {
        this.rute = rute;
    }

    public String getUrl_thumbnail() {
        return url_thumbnail;
    }

    public void setUrl_thumbnail(String url_thumbnail) {
        this.url_thumbnail = url_thumbnail;
    }

    public String getKode_paket() {
        return kode_paket;
    }

    public void setKode_paket(String kode_paket) {
        this.kode_paket = kode_paket;
    }

    public String getNama_paket() {
        return nama_paket;
    }

    public void setNama_paket(String nama_paket) {
        this.nama_paket = nama_paket;
    }


}
