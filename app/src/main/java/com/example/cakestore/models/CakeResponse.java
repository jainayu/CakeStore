package com.example.cakestore.models;

import java.util.List;

public class CakeResponse {
    private List<CakeDataModel> data;
    private String status;
    private String error_msg;

    public List<CakeDataModel> getData() {
        return data;
    }

    public void setData(List<CakeDataModel> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}
