package com.huriati.project.tvkabel.model;

public class History {
    private String id,
            pelanggan_id,
            bulan_id,
            jumlah_tagihan,
            status_pembayaran,
            updated_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPelanggan_id() {
        return pelanggan_id;
    }

    public void setPelanggan_id(String pelanggan_id) {
        this.pelanggan_id = pelanggan_id;
    }

    public String getBulan_id() {
        return bulan_id;
    }

    public void setBulan_id(String bulan_id) {
        this.bulan_id = bulan_id;
    }

    public String getJumlah_tagihan() {
        return jumlah_tagihan;
    }

    public void setJumlah_tagihan(String jumlah_tagihan) {
        this.jumlah_tagihan = jumlah_tagihan;
    }

    public String getStatus_pembayaran() {
        return status_pembayaran;
    }

    public void setStatus_pembayaran(String status_pembayaran) {
        this.status_pembayaran = status_pembayaran;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
