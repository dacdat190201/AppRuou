package com.example.final_shopthucung.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.final_shopthucung.model.USER;
import com.example.final_shopthucung.retrofit.MyDataBase;

public class UserDao {
    MyDataBase mydata;
    public UserDao(Context context) {
        mydata = new MyDataBase(context);
    }
    public boolean create(USER user){
        SQLiteDatabase db = mydata.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put("UserName", user.getUsername());
        values.put("password", user.getPassword());

        long row = db.insert("user", null,values);
        return row>0;
    }
}
