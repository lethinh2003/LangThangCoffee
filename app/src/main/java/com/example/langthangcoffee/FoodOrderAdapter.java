package com.example.langthangcoffee;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.langthangcoffee.fragment_menu_tool_bar.MainActivity;
import com.example.langthangcoffee.fragment_menu_tool_bar.SigninFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

@SuppressWarnings("deprecation")
public class FoodOrderAdapter extends RecyclerView.Adapter<FoodOrderAdapter.FoodOrderViewHolder> {
    private List<FoodOrder> mListFoodOrder;
    private int mQuantity = 0;
    private MainActivity mainActivity;

    public FoodOrderAdapter(List<FoodOrder> mListFoodOrder) {
        this.mListFoodOrder = mListFoodOrder;
    }

    @NonNull
    @Override
    public FoodOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_order, parent, false);
        mainActivity = (MainActivity) parent.getContext();
        return new FoodOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FoodOrderViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final FoodOrder foodOrder = mListFoodOrder.get(position);
        if (foodOrder == null) {
            return;
        }
        Picasso.get()
                .load(foodOrder.getHinhAnh())
                .fit()
                .centerInside()
                .into(holder.imageView);

        holder.tvName.setText(foodOrder.getName());
        holder.tvPrice.setText(String.valueOf(foodOrder.getPrice()) + " đ");
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity myActivity = (MainActivity) v.getContext();
                DatHangChiTietFragment datHangChiTietFragment = new DatHangChiTietFragment();
                // Truyền ID sản phẩm đến trang chi tiết
                Bundle bundle = new Bundle();
                bundle.putInt("IDSanPham", foodOrder.getID());
                datHangChiTietFragment.setArguments(bundle);
                // Mở trang chi tiết
                myActivity.loadFragment(datHangChiTietFragment);
            }
        });
        holder.btnFavorite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Check login
                if (mainActivity.getTaiKhoan() == null) {
                    SigninFragment signinFragment = new SigninFragment();
                    mainActivity.loadFragment(signinFragment);
                    return;
                }

                // Toggle the isSelected property of the item
                foodOrder.setFavorite(!foodOrder.getFavorite());
                if (foodOrder.getFavorite())
                    mQuantity++;
                else
                    mQuantity--;
                // Change the background of the button based on the isSelected property
                int drawableId = foodOrder.getFavorite() ? R.drawable.heart_symbol_checked : R.drawable.heart_symbol_uncheck;
                holder.btnFavorite.setBackgroundResource(drawableId);

                // Notify the adapter that the item has changed
                notifyItemChanged(position);
            }

        });

        if (foodOrder.getFavorite()) {
            holder.btnFavorite.setBackgroundResource(R.drawable.heart_symbol_checked);
        } else {
            holder.btnFavorite.setBackgroundResource(R.drawable.heart_symbol_uncheck);
        }

    }

    public int getQuantity() {
        return mQuantity;
    }

    @Override
    public int getItemCount() {
        if (mListFoodOrder != null) {
            return mListFoodOrder.size();
        }
        return 0;
    }

    public static class FoodOrderViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView tvName;
        private final TextView tvPrice;
        private final Button btnFavorite;


        public FoodOrderViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_food_order);
            tvName = itemView.findViewById(R.id.tv_food_name_order);
            tvPrice = itemView.findViewById(R.id.tv_food_price_order);
            btnFavorite = itemView.findViewById(R.id.btn_heart_symbol_order);


        }
    }
}

