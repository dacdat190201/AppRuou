package com.example.final_shopthucung.model;

import java.util.List;

public class RuouModel {
    boolean success;
    String message;
    List<Ruou> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Ruou> getResult() {
        return result;
    }

    public void setResult(List<Ruou> result) {
        this.result = result;
    }
}
