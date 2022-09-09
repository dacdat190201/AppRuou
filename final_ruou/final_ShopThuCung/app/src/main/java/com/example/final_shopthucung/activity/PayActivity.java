package com.example.final_shopthucung.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.final_shopthucung.R;
import com.example.final_shopthucung.utils.Utils;
import com.google.gson.Gson;

import java.text.DecimalFormat;

public class PayActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txttongtien, txtsdt, txtemail, editdiachi;
    Button btnDat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        initView();
        initControl();
        
    }

    private void initControl() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        int total = getIntent().getIntExtra("tongtien", 0);
        txttongtien.setText(decimalFormat.format(total));
        btnDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ThongBaoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar_pay);
        txttongtien = findViewById(R.id.total_pay);
        txtsdt = findViewById(R.id.phone_pay);
        txtemail = findViewById(R.id.email_pay);
        editdiachi = findViewById(R.id.location_pay);
        btnDat = findViewById(R.id.btnPay);
    }
}