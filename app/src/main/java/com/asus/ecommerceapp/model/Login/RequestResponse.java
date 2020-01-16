package com.asus.ecommerceapp.model.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestResponse {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("Success")
    @Expose
    private Boolean success;
    @SerializedName("Info")
    @Expose
    private String info;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
