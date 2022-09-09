package com.example.final_shopthucung.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.final_shopthucung.model.USER;
import com.example.final_shopthucung.retrofit.MyDataBase;

import java.util.ArrayList;

public class QuanTriDao {
    MyDataBase mydata;

    public QuanTriDao(Context context) {
        mydata = new MyDataBase(context);
    }

    public boolean KiemTra(USER user)
    {
        SQLiteDatabase db = mydata.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from user where username=? and password=?", new String[]{
                user.getUsername(),user.getPassword()
        });
        if(cs.getCount() <= 0)
        {
            return false;
        }
        return true;
    }
    //update pass
    public  boolean updatepw(USER user)
    {
        SQLiteDatabase db = mydata.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", user.getPassword());
        int row = db.update("QUANTRI",values,"username=?", new String[]{user.getUsername()});

        return row > 0;
    }
}
