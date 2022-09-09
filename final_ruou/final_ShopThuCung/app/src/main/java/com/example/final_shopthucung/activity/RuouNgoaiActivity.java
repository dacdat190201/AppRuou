package com.example.final_shopthucung.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.final_shopthucung.R;
import com.example.final_shopthucung.adapter.RuouNgoaiAdapter;
import com.example.final_shopthucung.model.Ruou;
import com.example.final_shopthucung.model.RuouModel;
import com.example.final_shopthucung.retrofit.Api;
import com.example.final_shopthucung.retrofit.RetrofitClient;
import com.example.final_shopthucung.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RuouNgoaiActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    Api api;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int page = 1;
    int loai;
    RuouNgoaiAdapter adapterRN;
    List<Ruou> ruouList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruou_ngoai);
        api = RetrofitClient.getInstance(Utils.BASE_URL).create(Api.class);
        loai = getIntent().getIntExtra("loai", 1);
        AnhXa();
        getData();
    }

    private void getData() {
        compositeDisposable.add(api.getRuouNgoai(page, loai)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                ruouModel -> {
                    if(ruouModel.isSuccess()){
                        ruouList = ruouModel.getResult();
                        adapterRN = new RuouNgoaiAdapter(getApplicationContext(), ruouList);
                        recyclerView.setAdapter(adapterRN);
                    }
                },
                throwable -> {
                    Toast.makeText(getApplicationContext(), "Khong ket noi duoc server", Toast.LENGTH_LONG).show();
                }
        ));

    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbar_ruoun);
        recyclerView = findViewById(R.id.rcvRuouNgoai);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        ruouList = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}