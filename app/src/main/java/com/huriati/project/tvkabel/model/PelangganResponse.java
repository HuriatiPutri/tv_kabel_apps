package com.huriati.project.tvkabel.model;

import java.util.List;

public class PelangganResponse {
    private String status, totalItems;
    private List<Pelanggan> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public List<Pelanggan> getData() {
        return data;
    }

    public void setData(List<Pelanggan> data) {
        this.data = data;
    }
}
