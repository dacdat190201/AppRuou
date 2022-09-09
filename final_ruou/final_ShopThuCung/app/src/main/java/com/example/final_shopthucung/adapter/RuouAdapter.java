package com.example.final_shopthucung.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
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
import com.example.final_shopthucung.model.event.SuaXoaEvent;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class RuouAdapter extends RecyclerView.Adapter<RuouAdapter.MyViewHolder> {
    Context context;
    List<Ruou> array;

    public RuouAdapter(Context context, List<Ruou> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ruou, parent, false);

        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Ruou ruou = array.get(position);
        holder.txtten.setText(ruou.getTenRuou());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgia.setText("Giá: "+decimalFormat.format(ruou.getGiaBan())+ "Đ");
        Glide.with(context).load(ruou.getHinhanh()).into(holder.imghinhanh);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if(!isLongClick){
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("chitiet", ruou);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else{
                    EventBus.getDefault().postSticky(new SuaXoaEvent(ruou));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, View.OnLongClickListener {
        TextView txtgia, txtten;
        ImageView imghinhanh;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            txtgia = itemView.findViewById(R.id.price_product_main);
            txtten = itemView.findViewById(R.id.name_product_main);
            imghinhanh = itemView.findViewById(R.id.image_product_main);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }


        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(0, 0, getAdapterPosition(), "Sửa");
            contextMenu.add(0, 1, getAdapterPosition(), "Xóa");
        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), true);
            return false;
        }
    }
}
