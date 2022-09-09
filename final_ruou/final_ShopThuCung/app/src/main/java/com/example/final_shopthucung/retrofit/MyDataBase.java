package com.example.final_shopthucung.retrofit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDataBase extends SQLiteOpenHelper {
    public MyDataBase(@Nullable Context context) {super(context, "TaiKhoan", null, 1);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql= "CREATE TABLE USER(UserName Text primary key not null,password Text)";
        db.execSQL(sql);

        db.execSQL("INSERT INTO USER values('Cuong', '123')");
        db.execSQL("INSERT INTO USER values('Manh', '456')");
        db.execSQL("INSERT INTO USER values('Dat', '789')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS QUANTRI");
        db.execSQL("DROP TABLE IF EXISTS USER");
    }
}
