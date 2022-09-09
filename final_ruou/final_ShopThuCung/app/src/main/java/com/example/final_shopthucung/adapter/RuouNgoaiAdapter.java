package com.example.final_shopthucung.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.final_shopthucung.ItemClickListener;
import com.example.final_shopthucung.R;
import com.example.final_shopthucung.activity.DetailActivity;
import com.example.final_shopthucung.model.Ruou;

import java.text.DecimalFormat;
import java.util.List;

public class RuouNgoaiAdapter extends RecyclerView.Adapter<RuouNgoaiAdapter.MyViewHolder> {
    Context context;
    List<Ruou> array;

    public RuouNgoaiAdapter(Context context, List<Ruou> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ruou_ngoai, parent, false);
        return new MyViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Ruou ruou = array.get(position);
        holder.tensp.setText(ruou.getTenRuou());
        holder.xuatxu.setText(ruou.getXuatXu());
        holder.mota.setText(ruou.getMoTa());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.giasp.setText("Giá: "+decimalFormat.format(ruou.getGiaBan())+ "Đ");
        Glide.with(context).load(ruou.getHinhanh()).into(holder.hinhanh);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if(!isLongClick){
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("chitiet", ruou);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tensp, giasp, xuatxu, mota;
        ImageView hinhanh;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            tensp = itemView.findViewById(R.id.name_ruoun);
            giasp = itemView.findViewById(R.id.price_ruoun);
            xuatxu = itemView.findViewById(R.id.xuatxu_ruoun);
            mota = itemView.findViewById(R.id.description_ruoun);
            hinhanh = itemView.findViewById(R.id.image_ruoun);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }
    }
}
