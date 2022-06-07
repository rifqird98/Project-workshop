package com.example.rpmtravel;

public class ListDoa {
    String kode_doa, latin_doa, nama_doa,terjemah_doa,text_doa;

   public ListDoa(){

    }

    public ListDoa(String kode_doa, String latin_doa, String nama_doa, String terjemah_doa, String text_doa) {
        this.kode_doa = kode_doa;
        this.latin_doa = latin_doa;
        this.nama_doa = nama_doa;
        this.terjemah_doa = terjemah_doa;
        this.text_doa = text_doa;
    }

    public String getKode_doa() {
        return kode_doa;
    }

    public void setKode_doa(String kode_doa) {
        this.kode_doa = kode_doa;
    }

    public String getLatin_doa() {
        return latin_doa;
    }

    public void setLatin_doa(String latin_doa) {
        this.latin_doa = latin_doa;
    }

    public String getNama_doa() {
        return nama_doa;
    }

    public void setNama_doa(String nama_doa) {
        this.nama_doa = nama_doa;
    }

    public String getTerjemah_doa() {
        return terjemah_doa;
    }

    public void setTerjemah_doa(String terjemah_doa) {
        this.terjemah_doa = terjemah_doa;
    }

    public String getText_doa() {
        return text_doa;
    }

    public void setText_doa(String text_doa) {
        this.text_doa = text_doa;
    }
}
