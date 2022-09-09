package com.example.final_shopthucung.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.final_shopthucung.R;
import com.example.final_shopthucung.model.GioHang;
import com.example.final_shopthucung.model.Ruou;
import com.example.final_shopthucung.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {
    TextView name,price,description,xuatxu;
    Button btnAddtoCart;
    ImageView img;
    Spinner spinner;
    Toolbar toolbarDetail;
    Ruou ruou;
    NotificationBadge badge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        AnhXa();
        InitData();
        InitControl();
    }

    private void InitControl() {
        btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themGioHang();
            }


        });
    }

    private void themGioHang() {
        if (Utils.gioHangs.size() > 0){
            boolean flag = false;
            int sl = Integer.parseInt(spinner.getSelectedItem().toString());
            for (int i = 0; i < Utils.gioHangs.size(); i++) {
                if(Utils.gioHangs.get(i).getTenRuou() == ruou.getTenRuou()){
                    Utils.gioHangs.get(i).setSoluong(sl+Utils.gioHangs.get(i).getSoluong());
                    int price = ruou.getGiaBan() * Utils.gioHangs.get(i).getSoluong();
                    Utils.gioHangs.get(i).setGiaBan(price);
                    flag = true;
                }
            }
            if (flag==false){
                int price =ruou.getGiaBan() * sl;
                GioHang cart = new GioHang();
                cart.setTenRuou(ruou.getTenRuou());
                cart.setGiaBan(price);
                cart.setHinhanh(ruou.getHinhanh());
                cart.setSoluong(sl);
                Utils.gioHangs.add(cart);
            }
        }
        else {

                int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                int price =ruou.getGiaBan() * sl;
                GioHang cart = new GioHang();
                cart.setTenRuou(ruou.getTenRuou());
                cart.setGiaBan(price);
                cart.setHinhanh(ruou.getHinhanh());
                cart.setSoluong(sl);
                Utils.gioHangs.add(cart);


        }
        int totalitem = 0;
        for(int i = 0; i<Utils.gioHangs.size(); i++)
        {
            totalitem = totalitem + Utils.gioHangs.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totalitem));


    }

    private void InitData() {
        ruou =(Ruou) getIntent().getSerializableExtra("chitiet");
        name.setText(ruou.getTenRuou());
        description.setText(ruou.getMoTa());
        xuatxu.setText(ruou.getXuatXu());
        Glide.with(getApplicationContext()).load(ruou.getHinhanh()).into(img);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        price.setText(decimalFormat.format(ruou.getGiaBan()));
        Integer[] so = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapterspin = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,so);
        spinner.setAdapter(adapterspin);
    }

    private void AnhXa() {
        name = findViewById(R.id.name_detail);
        price = (TextView) findViewById(R.id.price_detail);
        description = findViewById(R.id.description_detail);
        xuatxu = findViewById(R.id.xuatxu_detail);
        btnAddtoCart = findViewById(R.id.btnAddtoCart);
        img = findViewById(R.id.img_detail);
        spinner =  findViewById(R.id.spinner);
        toolbarDetail = findViewById(R.id.toolbar_detail);
        badge = findViewById(R.id.badge_sl);
        FrameLayout frameLayout = findViewById(R.id.frame_cart);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cart = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(cart);
            }
        });
        if(Utils.gioHangs != null)
        {
            int totalitem = 0;
            for(int i = 0; i<Utils.gioHangs.size(); i++)
            {
                totalitem = totalitem + Utils.gioHangs.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalitem));
        }



    }


}