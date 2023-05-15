package com.example.langthangcoffee.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.langthangcoffee.activities.MainActivity;
import com.example.langthangcoffee.R;

public class TrangQuanLyFragment extends Fragment {

    ImageView imgBack;
    LinearLayout lnDanhSachDonHang, lnDanhSachKhachHang, lnDanhSachSanPham, lnDanhSachVoucher, lnDanhSachLoaiVoucher, lnDoanhThu;
    MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_trang_quan_ly, container, false);
        imgBack = v.findViewById(R.id.img_back);
        lnDanhSachDonHang = v.findViewById(R.id.ln_danhsachdonhang);
        lnDanhSachKhachHang = v.findViewById(R.id.ln_danhsachtaikhoan);
        lnDanhSachSanPham = v.findViewById(R.id.ln_danhsachsanpham);
        lnDanhSachVoucher = v.findViewById(R.id.ln_danhsachvoucher);
        lnDanhSachLoaiVoucher = v.findViewById(R.id.ln_danhsachloaivoucher);
        lnDoanhThu = v.findViewById(R.id.ln_doanhthu);
        mainActivity = (MainActivity) getActivity();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
        lnDanhSachDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DanhSachDonHangAdminFragment danhSachDonHangAdminFragment = new DanhSachDonHangAdminFragment();
                mainActivity.loadFragment(danhSachDonHangAdminFragment);
            }
        });
        lnDanhSachKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuanLyTaiKhoanFragment quanLyTaiKhoanFragment = new QuanLyTaiKhoanFragment();
                mainActivity.loadFragment(quanLyTaiKhoanFragment);
            }
        });
        lnDanhSachSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuanLySanPhamFragment quanLySanPhamFragment = new QuanLySanPhamFragment();
                mainActivity.loadFragment(quanLySanPhamFragment);
            }
        });
        lnDanhSachVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuanLyVoucherFragment quanLyVoucherFragment = new QuanLyVoucherFragment();
                mainActivity.loadFragment(quanLyVoucherFragment);
            }
        });
        lnDanhSachLoaiVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuanLyLoaiVoucherFragment quanLyLoaiVoucherFragment = new QuanLyLoaiVoucherFragment();
                mainActivity.loadFragment(quanLyLoaiVoucherFragment);
            }
        });
        lnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoanhThuFragment doanhThuFragment = new DoanhThuFragment();
                mainActivity.loadFragment(doanhThuFragment);
            }
        });
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


}
