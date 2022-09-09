package com.example.final_shopthucung.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_shopthucung.R;
import com.example.final_shopthucung.dao.UserDao;
import com.example.final_shopthucung.model.USER;

public class DangKiActivity extends AppCompatActivity {
    EditText edt_username1, edt_password1;
    Button btn_dangky;
    CheckBox ck_luu;
    TextView textViewDK;
    UserDao userDao;
    public static USER User = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);

        userDao = new UserDao(DangKiActivity.this);

        //Ánh xạ
        edt_username1 = findViewById(R.id.txt_username4);
        edt_password1 = findViewById(R.id.txt_password4);
        btn_dangky = findViewById(R.id.bt_dangky3);

        btn_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String US = edt_username1.getText().toString();
                String PW = edt_password1.getText().toString();
                USER user = new USER(US,PW);
                if(userDao.create(user))
                {
                    Toast.makeText(DangKiActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent i = new Intent(DangKiActivity.this, DangNhapActivity.class)  ;
                    startActivity(i);

                }
                else
                {
                    Toast.makeText(DangKiActivity.this, "Thêm không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}