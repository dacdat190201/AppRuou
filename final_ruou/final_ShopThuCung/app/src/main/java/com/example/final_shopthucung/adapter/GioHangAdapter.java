package com.example.final_shopthucung.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.final_shopthucung.ImageClickListener;
import com.example.final_shopthucung.R;
import com.example.final_shopthucung.model.GioHang;
import com.example.final_shopthucung.model.event.EventSumTotal;
import com.example.final_shopthucung.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {
    Context context;
    List<GioHang> cartList;

    public GioHangAdapter(Context context, List<GioHang> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GioHang cart = cartList.get(position);
        holder.name.setText(cart.getTenRuou());
        Glide.with(context).load(cart.getHinhanh()).into(holder.img);
        holder.quantity.setText(cart.getSoluong()+ "");
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.price.setText(decimalFormat.format(cart.getGiaBan()));
        int total = cart.getGiaBan() * cart.getSoluong();
        holder.total.setText(decimalFormat.format(total));
        holder.setImageClickListener(new ImageClickListener() {
            @Override
            public void onImageClick(View view, int pos, int value) {
                if (value == 1){
                    if (cartList.get(pos).getSoluong()>1){
                        int slnew = cartList.get(pos).getSoluong()-1;
                        cartList.get(pos).setSoluong(slnew);

                        holder.quantity.setText(cartList.get(pos).getSoluong()+ "");
                        int total = cartList.get(pos).getGiaBan() * cartList.get(pos).getSoluong();
                        holder.total.setText(decimalFormat.format(total));
                        EventBus.getDefault().postSticky(new EventSumTotal());
                    }
                    else if( cartList.get(pos).getSoluong() == 1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Bạn có muốn xóa sản phẩm khỏi giỏ hàng?");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Utils.gioHangs.remove(pos);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new EventSumTotal());
                            }
                        });
                        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
                    }
                }
                else if (value ==2){
                    if (cartList.get(pos).getSoluong() < 11){
                        int slnew = cartList.get(pos).getSoluong()+1;
                        cartList.get(pos).setSoluong(slnew);
                    }
                    holder.quantity.setText(cartList.get(pos).getSoluong()+ "");
                    int total = cartList.get(pos).getGiaBan() * cartList.get(pos).getSoluong();
                    holder.total.setText(decimalFormat.format(total));
                    EventBus.getDefault().postSticky(new EventSumTotal());
                }

            }

        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img,imgSub,imgAdd;
        TextView name,price,quantity,total;
        ImageClickListener iImageClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_cart);
            name = itemView.findViewById(R.id.cart_name);
            price = itemView.findViewById(R.id.cart_price);
            quantity = itemView.findViewById(R.id.cart_quantity);
            total  = itemView.findViewById(R.id.cart_total);
            imgSub = itemView.findViewById(R.id.cart_sub);
            imgAdd = itemView.findViewById(R.id.cart_add);

            imgAdd.setOnClickListener(this);
            imgSub.setOnClickListener(this);
        }

        public void setImageClickListener(ImageClickListener iImageClickListener) {
            this.iImageClickListener = iImageClickListener;
        }

        @Override
        public void onClick(View view) {
            if (view  == imgSub){
                iImageClickListener.onImageClick(view,getAdapterPosition(),1);
            }else if(view == imgAdd){
                iImageClickListener.onImageClick(view,getAdapterPosition(),2);
            }
        }
    }
}
