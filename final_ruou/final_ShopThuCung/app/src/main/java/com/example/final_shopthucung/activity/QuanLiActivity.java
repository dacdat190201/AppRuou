package com.example.final_shopthucung.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.final_shopthucung.R;
import com.example.final_shopthucung.adapter.RuouAdapter;
import com.example.final_shopthucung.model.Ruou;
import com.example.final_shopthucung.model.event.SuaXoaEvent;
import com.example.final_shopthucung.retrofit.Api;
import com.example.final_shopthucung.retrofit.RetrofitClient;
import com.example.final_shopthucung.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import soup.neumorphism.NeumorphCardView;

public class QuanLiActivity extends AppCompatActivity {

    ImageView img_them;
    RecyclerView recyclerView;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Api api;
    List<Ruou> ruous;
    RuouAdapter ruouAdapter;
    Ruou ruousuaxoa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_li);
        api = RetrofitClient.getInstance(Utils.BASE_URL).create(Api.class);
        initView();
        initControl();
        getRuou();
    }

    private void initControl() {
        img_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ThemSPActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getRuou() {
        compositeDisposable.add(api.getRuou().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ruouModel -> {
                            if(ruouModel.isSuccess()){
                                ruous = ruouModel.getResult();
                                ruouAdapter = new RuouAdapter(getApplicationContext(),ruous);
                                recyclerView.setAdapter(ruouAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối được với server"+throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }

                ));
    }

    private void initView() {
        recyclerView = findViewById(R.id.recycleviewql);
        img_them = findViewById(R.id.img_them);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals("Sửa")){
            suaSanPham();
        }else if(item.getTitle().equals("Xóa"))
        {
            xoaSanPham();
        }
        return super.onContextItemSelected(item);
    }

    private void xoaSanPham() {
        compositeDisposable.add(api.xoaSanPham(ruousuaxoa.getIdRuou())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                messageModel -> {
                    if(messageModel.isSuccess()){
                        Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                    }
                },
                throwable -> {
                    Log.d("log", throwable.getMessage());
                }
        ));
    }

    private void suaSanPham() {
        Intent intent = new Intent(getApplicationContext(), ThemSPActivity.class);
        intent.putExtra("sua", ruousuaxoa);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public  void eventSuaXoa(SuaXoaEvent event){
        if(event != null){
            ruousuaxoa = event.getRuou();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}