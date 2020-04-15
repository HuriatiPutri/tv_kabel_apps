package com.huriati.project.tvkabel.model;

import java.util.List;

public class TagihanResponse {
    private String status;
    private List<Tagihan> tagihan;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Tagihan> getTagihan() {
        return tagihan;
    }

    public void setTagihan(List<Tagihan> tagihan) {
        this.tagihan = tagihan;
    }
}
