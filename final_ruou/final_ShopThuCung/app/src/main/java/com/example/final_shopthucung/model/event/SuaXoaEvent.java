package com.example.final_shopthucung.model.event;

import com.example.final_shopthucung.model.Ruou;

public class SuaXoaEvent {
    Ruou ruou;

    public SuaXoaEvent(Ruou ruou) {
        this.ruou = ruou;
    }

    public Ruou getRuou() {
        return ruou;
    }

    public void setRuou(Ruou ruou) {
        this.ruou = ruou;
    }
}
