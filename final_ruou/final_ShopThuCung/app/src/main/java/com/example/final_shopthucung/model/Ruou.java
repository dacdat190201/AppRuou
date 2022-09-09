package com.example.final_shopthucung.model;

import java.io.Serializable;

public class Ruou implements Serializable {
    int idRuou;
    String tenRuou;
    String hinhanh;
    String xuatXu;
    String moTa;
    int giaBan;
    int soluong;
    int idLoai;

    public Ruou(int idRuou, String tenRuou, String hinhanh, String xuatXu, String moTa, int giaBan,int soluong, int idLoai) {
        this.idRuou = idRuou;
        this.tenRuou = tenRuou;
        this.hinhanh = hinhanh;
        this.xuatXu = xuatXu;
        this.moTa = moTa;
        this.giaBan = giaBan;
        this.soluong = soluong;
        this.idLoai = idLoai;
    }

    public int getIdRuou() {
        return idRuou;
    }

    public void setIdRuou(int idRuou) {
        this.idRuou = idRuou;
    }

    public String getTenRuou() {
        return tenRuou;
    }

    public void setTenRuou(String tenRuou) {
        this.tenRuou = tenRuou;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }

    public int getIdLoai() {
        return idLoai;
    }

    public void setIdLoai(int idLoai) {
        this.idLoai = idLoai;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
