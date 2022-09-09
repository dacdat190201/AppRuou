package com.example.final_shopthucung.model;

import java.io.Serializable;

public class USER implements Serializable {
    String username;
    String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public USER(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
