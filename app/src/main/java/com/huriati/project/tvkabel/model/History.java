package com.huriati.project.tvkabel.model;

public class History {
    private String id,
            tagihan_kolektor_id,
            tagihan_pelanggan_id,
            pelanggan_id,
            tgl_tagihan,
            bulan_tagihan,
            jumlah_pembayaran;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTagihan_kolektor_id() {
        return tagihan_kolektor_id;
    }

    public void setTagihan_kolektor_id(String tagihan_kolektor_id) {
        this.tagihan_kolektor_id = tagihan_kolektor_id;
    }

    public String getTagihan_pelanggan_id() {
        return tagihan_pelanggan_id;
    }

    public void setTagihan_pelanggan_id(String tagihan_pelanggan_id) {
        this.tagihan_pelanggan_id = tagihan_pelanggan_id;
    }

    public String getPelanggan_id() {
        return pelanggan_id;
    }

    public void setPelanggan_id(String pelanggan_id) {
        this.pelanggan_id = pelanggan_id;
    }

    public String getTgl_tagihan() {
        return tgl_tagihan;
    }

    public void setTgl_tagihan(String tgl_tagihan) {
        this.tgl_tagihan = tgl_tagihan;
    }

    public String getBulan_tagihan() {
        return bulan_tagihan;
    }

    public void setBulan_tagihan(String bulan_tagihan) {
        this.bulan_tagihan = bulan_tagihan;
    }

    public String getJumlah_pembayaran() {
        return jumlah_pembayaran;
    }

    public void setJumlah_pembayaran(String jumlah_pembayaran) {
        this.jumlah_pembayaran = jumlah_pembayaran;
    }
}
