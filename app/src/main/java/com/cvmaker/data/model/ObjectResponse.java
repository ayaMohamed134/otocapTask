package com.cvmaker.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ObjectResponse<T> implements Serializable {

    @Expose
    @SerializedName("status")
    private int status;

    @Expose
    @SerializedName("data")
    private T data;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("error")
    private String error;

    @Expose
    @SerializedName("code")
    private int code;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData(){
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
