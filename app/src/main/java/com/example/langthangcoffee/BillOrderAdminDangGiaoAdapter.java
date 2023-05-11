package com.example.langthangcoffee;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.langthangcoffee.fragment_menu_tool_bar.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

@SuppressWarnings("deprecation")
public class BillOrderAdminDangGiaoAdapter extends RecyclerView.Adapter<BillOrderAdminDangGiaoAdapter.FoodOrderViewHolder> {
    private List<DonHang> mBillOrder;
    MainActivity mainActivity;
    BillOrderAdminDangGiaoAdapter billOrderAdminDangGiaoAdapter;


    public BillOrderAdminDangGiaoAdapter(List<DonHang> mBillOrder, MainActivity mainActivity) {
        this.mBillOrder = mBillOrder;
        this.mainActivity = mainActivity;
        billOrderAdminDangGiaoAdapter = this;
    }

    @NonNull
    @Override
    public FoodOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lich_su_don_hang_dang_giao_admin, parent, false);
        return new FoodOrderViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final FoodOrderViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final DonHang donHang = mBillOrder.get(position);
        if (donHang == null) {
            return;
        }
        holder.tvTaiKhoan.setText("Số điện thoại: " + (donHang.getSdtKhachHang()));
        holder.tvMaDonHang.setText("Mã đơn hàng: " + String.valueOf(donHang.getMaDonHang()));
        holder.tvTongTien.setText("Tổng tiền: " + String.valueOf(donHang.getSoTienThanhToan()) + " đ");
        holder.tvThoiGian.setText("Thời gian: " + donHang.getThoiGianDatHang());
        List<LichSuOrder> lichSuOrderList = donHang.getLichSuOrderList();
        String chiTietDonHang = "";
        for (int i = 0; i < lichSuOrderList.size(); i++) {
            String orderDetail = "";
            orderDetail = "x" + lichSuOrderList.get(i).getSoLuong() + " " + lichSuOrderList.get(i).getTenSanPham();
            if (i != lichSuOrderList.size() - 1) {
                orderDetail += ", ";
            }
            chiTietDonHang += orderDetail;
        }
        holder.tvDetail.setText("Chi tiết: " + chiTietDonHang);

        holder.lnItemDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChiTietDatHangAdminDangGiaoFragment chiTietDatHangAdminDangChoFragment = new ChiTietDatHangAdminDangGiaoFragment(billOrderAdminDangGiaoAdapter);
                Bundle bundle = new Bundle();
                bundle.putSerializable("donHang", donHang); // Put anything what you want
                chiTietDatHangAdminDangChoFragment.setArguments(bundle);
                mainActivity.loadFragment(chiTietDatHangAdminDangChoFragment);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mBillOrder != null) {
            return mBillOrder.size();
        }
        return 0;
    }

    public static class FoodOrderViewHolder extends RecyclerView.ViewHolder {
        ;
        private final TextView tvMaDonHang, tvDetail, tvTongTien, tvThoiGian, tvTaiKhoan;
        private  final LinearLayout lnItemDonHang;


        public FoodOrderViewHolder(@NonNull View itemView) {
            super(itemView);


            tvMaDonHang = itemView.findViewById(R.id.tv_ma_don_hang);
            tvDetail = itemView.findViewById(R.id.tv_detail);
            tvTongTien = itemView.findViewById(R.id.tv_tong_tien);
            tvThoiGian = itemView.findViewById(R.id.tv_thoi_gian);

            lnItemDonHang = itemView.findViewById(R.id.ln_item_don_hang);
            tvTaiKhoan = itemView.findViewById(R.id.tv_sdt_don_hang);


        }
    }
}

