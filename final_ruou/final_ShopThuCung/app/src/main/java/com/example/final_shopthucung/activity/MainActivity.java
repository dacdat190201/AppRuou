package com.example.final_shopthucung.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.final_shopthucung.R;
import com.example.final_shopthucung.adapter.ProductAdapter;
import com.example.final_shopthucung.adapter.RuouAdapter;
import com.example.final_shopthucung.model.Product;
import com.example.final_shopthucung.model.Ruou;
import com.example.final_shopthucung.model.RuouModel;
import com.example.final_shopthucung.retrofit.Api;
import com.example.final_shopthucung.retrofit.RetrofitClient;
import com.example.final_shopthucung.utils.Utils;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Notification;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView rcvMain;
    NavigationView navbarMain;
    ListView listNavMain;
    DrawerLayout drawerLayout;
    ProductAdapter productAdapter;
    List<Product> products;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Api api;
    List<Ruou> ruous;
    RuouAdapter ruouAdapter;
    NotificationBadge badge;
    FrameLayout frameLayout;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        api = RetrofitClient.getInstance(Utils.BASE_URL).create(Api.class);
        
        AnhXa();
        if(isConnected(this)){
            Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();
            ActionViewFlipper();
            getProduct();
            getRuou();
            getEventClick();
        }else{
            Toast.makeText(getApplicationContext(), "ko ok", Toast.LENGTH_LONG).show();
        }
    }

    private void getEventClick() {
        listNavMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent ruoungoai = new Intent(getApplicationContext(), RuouNgoaiActivity.class);
                        ruoungoai.putExtra("idLoai", 1);
                        startActivity(ruoungoai);
                        break;
                    case 1:
                        Intent ruouvang = new Intent(getApplicationContext(), RuouVangActivity.class);
                        ruouvang.putExtra("idLoai", 2);
                        startActivity(ruouvang);
                        break;
                    case 2:
                        Intent quanli = new Intent(getApplicationContext(), QuanLiActivity.class);
                        startActivity(quanli);
                        break;
                }
            }
        });
    }
    //Hàm Show Tất cả Rượu
    private void getRuou() {
        compositeDisposable.add(api.getRuou().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ruouModel -> {
                            if(ruouModel.isSuccess()){
                                ruous = ruouModel.getResult();
                                ruouAdapter = new RuouAdapter(getApplicationContext(),ruous);
                                rcvMain.setAdapter(ruouAdapter);
                            }
                        }
                ));
    }
    //Hàm show thanh Loại
    private void getProduct() {
        compositeDisposable.add(api.getProduct().subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                productModel -> {
                    if(productModel.isSuccess()){
                        products = productModel.getResult();
                        productAdapter = new ProductAdapter(getApplicationContext(),products);
                        listNavMain.setAdapter(productAdapter);
                    }
                }
        ));
    }
    //Bảng slide
    private void ActionViewFlipper(){
        List<String> flip = new ArrayList<>();
        flip.add("https://ruousg.com/wp-content/uploads/2020/10/banner-ru%CC%9Bo%CC%9B%CC%A3u-mani-1.jpg");
        flip.add("https://ruousg.com/wp-content/uploads/2020/10/banner-ru%CC%9Bo%CC%9B%CC%A3u-main-2.jpg");
        flip.add("https://ruousg.com/wp-content/uploads/2020/09/banner-ru%CC%9Bo%CC%9B%CC%A3u-3.jpg");
        for (int i = 0; i < flip.size(); i++) {
            ImageView img= new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(flip.get(i)).into(img);
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(img);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setInAnimation(slide_out);
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbarMain);
        viewFlipper = findViewById(R.id.viewFlipMain);
        rcvMain = findViewById(R.id.rcvMain);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        rcvMain.setLayoutManager(layoutManager);
        rcvMain.setHasFixedSize(true);
        navbarMain = findViewById(R.id.navMain);
        listNavMain = findViewById(R.id.listNavMain);
        drawerLayout = findViewById(R.id.drawerlayout);
        badge = findViewById(R.id.badge_sl);
        frameLayout = findViewById(R.id.frame_cart);
        products = new ArrayList<>();
        ruous = new ArrayList<>();
        if(Utils.gioHangs == null){
            Utils.gioHangs = new ArrayList<>();
        }else{
            int totalitem = 0;
            for(int i = 0; i<Utils.gioHangs.size(); i++)
            {
                totalitem = totalitem + Utils.gioHangs.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalitem));
        }
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(giohang);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        int totalitem = 0;
        for(int i = 0; i<Utils.gioHangs.size(); i++)
        {
            totalitem = totalitem + Utils.gioHangs.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totalitem));
    }

    private boolean isConnected (Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((wifi != null && wifi.isConnected()) ||(mobile != null && mobile.isConnected()) ){
            return true;
        }else{
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}