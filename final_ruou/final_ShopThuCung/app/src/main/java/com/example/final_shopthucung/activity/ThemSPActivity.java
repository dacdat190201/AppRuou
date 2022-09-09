package com.example.final_shopthucung.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.final_shopthucung.R;
import com.example.final_shopthucung.databinding.ActivityThemSpactivityBinding;
import com.example.final_shopthucung.model.Ruou;
import com.example.final_shopthucung.retrofit.Api;
import com.example.final_shopthucung.retrofit.RetrofitClient;
import com.example.final_shopthucung.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThemSPActivity extends AppCompatActivity {
    EditText tenruou,xuatxu,giaban, hinh, mota;
    Spinner spinner;
    int loai = 0;
    ActivityThemSpactivityBinding binding;
    Api api;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Ruou ruusua;
    Button btThem;
    boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThemSpactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        initData();
        api = RetrofitClient.getInstance(Utils.BASE_URL).create(Api.class);
        Intent intent = getIntent();
        ruusua = (Ruou) intent.getSerializableExtra("sua");
        if(ruusua == null)
        {
            flag = false;
        }else{
            flag = true;
            binding.btnThem.setText("Sửa Sản Phẩm");

            tenruou.setText(ruusua.getTenRuou());
            xuatxu.setText(ruusua.getXuatXu());
            giaban.setText(ruusua.getGiaBan()+"");
            hinh.setText(ruusua.getHinhanh());
            mota.setText(ruusua.getMoTa());
            binding.spinnerLoai.setSelection(ruusua.getIdLoai());
        }


    }

    private void initData() {
        List<String> stringList = new ArrayList<>();
        stringList.add("Vui Lòng Chọn Loại");
        stringList.add("Loại 1");
        stringList.add("Loại 2");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,stringList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loai = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag == false){
                    themspmoi();
                }else
                {
                    suaSanPham();
                }

            }


        });

    }
    private void suaSanPham() {
        String str_ten = tenruou.getText().toString();
        String str_hinhanh = hinh.getText().toString();
        String str_xuatxu = xuatxu.getText().toString();
        String str_mota = mota.getText().toString();
        int str_gia = Integer.parseInt(giaban.getText().toString().trim());
        if(TextUtils.isEmpty(str_ten) || TextUtils.isEmpty(str_hinhanh) || TextUtils.isEmpty(str_mota) || TextUtils.isEmpty(str_xuatxu) || loai == 0)
        {
            Toast.makeText(getApplicationContext(), "Vui Lòng Nhập Đầy Đủ Thông Tin", Toast.LENGTH_LONG).show();

        }else{
            compositeDisposable.add(api.updatesp(str_ten, str_hinhanh, str_xuatxu, str_mota,str_gia, loai, ruusua.getIdRuou())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            messageModel -> {
                                if(messageModel.isSuccess())
                                {
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            },
                            throwable -> {
                                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                            }
                    ));
        }
    }
    private void themspmoi() {
        String str_ten = tenruou.getText().toString();
        String str_hinhanh = hinh.getText().toString();
        String str_xuatxu = xuatxu.getText().toString();
        String str_mota = mota.getText().toString();
        int str_gia = Integer.parseInt(giaban.getText().toString().trim());
        if(TextUtils.isEmpty(str_ten) || TextUtils.isEmpty(str_hinhanh) || TextUtils.isEmpty(str_mota) || TextUtils.isEmpty(str_xuatxu) || loai == 0)
        {
            Toast.makeText(getApplicationContext(), "Vui Lòng Nhập Đầy Đủ Thông Tin", Toast.LENGTH_LONG).show();

        }else{
            compositeDisposable.add(api.insertsp(str_ten, str_hinhanh, str_xuatxu, str_mota,str_gia, (loai))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    messageModel -> {
                        if(messageModel.isSuccess())
                        {
                            Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    },
                    throwable -> {
                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }
            ));
        }
    }

    private void initView() {
        spinner = findViewById(R.id.spinner_loai);
        tenruou = findViewById(R.id.txt_tenruou);
        hinh = findViewById(R.id.txt_hinhanh);
        mota = findViewById(R.id.txt_mota);
        xuatxu = findViewById(R.id.txt_xuatxu);
        giaban = findViewById(R.id.txt_giaban);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}