package com.example.final_shopthucung.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.final_shopthucung.R;
import com.example.final_shopthucung.adapter.GioHangAdapter;
import com.example.final_shopthucung.model.GioHang;
import com.example.final_shopthucung.model.event.EventSumTotal;
import com.example.final_shopthucung.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangActivity extends AppCompatActivity {
    TextView rong,tongtien;
    Toolbar toolbarCart;
    RecyclerView rcvCart;
    Button btnBuy;
    GioHangAdapter cartAdapter;

    int total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        initView();
        initControl();
        sumTotal();

    }

    private void sumTotal() {
        total = 0;
        for (int i = 0; i < Utils.gioHangs.size(); i++) {
            total += (Utils.gioHangs.get(i).getGiaBan()*Utils.gioHangs.get(i).getSoluong());
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien.setText(decimalFormat.format(total));
    }

    private void initControl() {
        toolbarCart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rcvCart.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcvCart.setLayoutManager(layoutManager);
        if(Utils.gioHangs.size() == 0)
        {
            rong.setVisibility(View.VISIBLE);
        }else {
            cartAdapter = new GioHangAdapter(getApplicationContext(),Utils.gioHangs);
            rcvCart.setAdapter(cartAdapter);
        }

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PayActivity.class);
                intent.putExtra("tongtien", total);
                startActivity(intent);
            }
        });

    }

    private void initView() {
        rong = findViewById(R.id.tv_cart);
        toolbarCart = findViewById(R.id.toolbar_cart);
        rcvCart = findViewById(R.id.rcv_cart);
        tongtien = findViewById(R.id.tv_cart_total);
        btnBuy = findViewById(R.id.btnBuy);
        rcvCart.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcvCart.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void eventSumTotal(EventSumTotal event)
    {
        if (event != null){
            sumTotal();
        }
    }
}