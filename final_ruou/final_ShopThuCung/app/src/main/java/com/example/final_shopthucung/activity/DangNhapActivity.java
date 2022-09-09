package com.example.final_shopthucung.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_shopthucung.R;
import com.example.final_shopthucung.dao.QuanTriDao;
import com.example.final_shopthucung.model.USER;

public class DangNhapActivity extends AppCompatActivity {
    EditText edt_username1, edt_password1;
    Button btn_dn1, btn_bai2;
    CheckBox ck_luu;
    TextView textViewDK;
    public static USER User = null;
    QuanTriDao qtdao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        qtdao = new QuanTriDao(DangNhapActivity.this);

        edt_username1 = findViewById(R.id.txt_username);
        edt_password1 = findViewById(R.id.txt_password);
        ck_luu = findViewById(R.id.checkBox);
        btn_dn1 = findViewById(R.id.bt_dangky);
        textViewDK = findViewById(R.id.textView3);
        loadData();
        btn_dn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String US = edt_username1.getText().toString();
                String PW = edt_password1.getText().toString();

                boolean check = ck_luu.isChecked();

                USER user = new USER(US,PW);
                if(qtdao.KiemTra(user)){
                    LuuTT(US, PW, check);
                    User = user;
                    Toast.makeText(DangNhapActivity.this,"Dang Nhap Thanh Cong", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(DangNhapActivity.this, MainActivity.class)  ;
                    startActivity(i);
                }
                else {
                    Toast.makeText(DangNhapActivity.this,"Dang Nhap Khong Thanh Cong", Toast.LENGTH_LONG).show();
                }
            }
        });
        textViewDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DangNhapActivity.this, DangKiActivity.class)  ;
                startActivity(i);
            }
        });
    }
    private void LuuTT(String un, String pw,boolean check)
    {
        SharedPreferences pref = getSharedPreferences("thongtin.dat", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if(check){
            editor.putString("username", un);
            editor.putString("password", pw);
            editor.putBoolean("check", check);
        }
        else
        {
            editor.clear();
        }
        editor.commit();
    }
    private void loadData() {
        SharedPreferences pref = getSharedPreferences("thongtin.dat", MODE_PRIVATE);
        boolean check  = pref.getBoolean("check", false);
        if(check)
        {
            edt_username1.setText(pref.getString("username", ""));
            edt_password1.setText(pref.getString("password", ""));
            ck_luu.setChecked(check);
        }
    }
}