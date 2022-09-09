package com.example.final_shopthucung.model;

public class Product {
    int idLoai;
    String tenLoai;
    String hinhanh;

    public Product(int idLoai, String tenLoai, String hinhanh) {
        this.idLoai = idLoai;
        this.tenLoai = tenLoai;
        this.hinhanh = hinhanh;
    }

    public int getIdLoai() {
        return idLoai;
    }

    public void setIdLoai(int idLoai) {
        this.idLoai = idLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
