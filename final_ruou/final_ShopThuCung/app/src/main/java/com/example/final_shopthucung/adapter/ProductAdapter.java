package com.example.final_shopthucung.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.final_shopthucung.R;
import com.example.final_shopthucung.model.Product;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    List<Product> array;
    Context context;

    public ProductAdapter(Context context, List<Product> array) {
        this.array = array;
        this.context = context;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder{
        TextView texttensp;
        ImageView imghinhanh;
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_sanpham,null);
            viewHolder.texttensp = view.findViewById(R.id.item_tensp);
            viewHolder.imghinhanh = view.findViewById(R.id.item_image);
            view.setTag(viewHolder);


        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.texttensp.setText(array.get(i).getTenLoai());
        Glide.with(context).load(array.get(i).getHinhanh()).into(viewHolder.imghinhanh);

        return view;
    }
}
