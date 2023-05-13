package com.example.langthangcoffee;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.langthangcoffee.fragment_menu_tool_bar.MainActivity;
import com.example.langthangcoffee.fragment_menu_tool_bar.SigninFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

@SuppressWarnings("deprecation")
public class QuanLySanPhamAdapter extends RecyclerView.Adapter<QuanLySanPhamAdapter.FoodOrderViewHolder> {
    private List<FoodOrder> mListFoodOrder;
    private int mQuantity = 0;
    private MainActivity mainActivity;

    public QuanLySanPhamAdapter(List<FoodOrder> mListFoodOrder) {
        this.mListFoodOrder = mListFoodOrder;
    }

    @NonNull
    @Override
    public FoodOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_san_pham, parent, false);
        mainActivity = (MainActivity) parent.getContext();
        return new FoodOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FoodOrderViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final FoodOrder foodOrder = mListFoodOrder.get(position);
        if (foodOrder == null) {
            return;
        }

        holder.tvTenSanPham.setText("Tên sản phẩm: " + foodOrder.getName());
        holder.tvGiaTien.setText("Giá bán: " + String.valueOf(foodOrder.getPrice()) + " đ");
        holder.tvTenDanhMuc.setText("Loại: " + foodOrder.getTenDanhMuc());
        holder.lnItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuanLySanPhamChiTietFragment quanLySanPhamChiTietFragment = new QuanLySanPhamChiTietFragment(foodOrder);
                mainActivity.loadFragment(quanLySanPhamChiTietFragment);
            }
        });

    }



    @Override
    public int getItemCount() {
        if (mListFoodOrder != null) {
            return mListFoodOrder.size();
        }
        return 0;
    }

    public static class FoodOrderViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTenSanPham, tvGiaTien, tvTenDanhMuc;
        private final LinearLayout lnItem;



        public FoodOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSanPham = itemView.findViewById(R.id.tv_ten_san_pham);
            tvGiaTien = itemView.findViewById(R.id.tv_gia_ban);
            tvTenDanhMuc = itemView.findViewById(R.id.tv_ten_danh_muc);
            lnItem = itemView.findViewById(R.id.ln_item_san_pham);


        }
    }
}

