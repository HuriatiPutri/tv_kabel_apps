package com.huriati.project.tvkabel.model;

import java.util.List;

public class HistoryResponse {
    String status;
    List<History> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<History> getData() {
        return data;
    }

    public void setData(List<History> data) {
        this.data = data;
    }
}
