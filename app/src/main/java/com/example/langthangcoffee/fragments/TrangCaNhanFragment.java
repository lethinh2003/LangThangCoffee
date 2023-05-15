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

public class TrangCaNhanFragment extends Fragment {

    ImageView imgBack;
    LinearLayout lnLichSuDonHang, lnThongTinCaNhan, lnThayDoiMatKhau, lnDangXuat, lnLienHe, lnUuDai, lnDanhSachYeuThich;
    MainActivity mainActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_trang_ca_nhan, container, false);
        imgBack =  v.findViewById(R.id.img_back);
        lnLichSuDonHang = v.findViewById(R.id.ln_lich_su_don_hang);
        lnThongTinCaNhan = v.findViewById(R.id.ln_thongtincanhan);
        lnThayDoiMatKhau = v.findViewById(R.id.ln_thaydoimatkhau);
        lnUuDai = v.findViewById(R.id.ln_danhsachuudai);
        lnDangXuat = v.findViewById(R.id.ln_dangxuat);
        lnDanhSachYeuThich = v.findViewById(R.id.ln_danhsachyeuthich);
        lnLienHe = v.findViewById(R.id.ln_lienhe);
        mainActivity = (MainActivity)getActivity();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
        lnLichSuDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LichSuBillCaNhanFragment lichSuBillCaNhanFragment = new LichSuBillCaNhanFragment();
                mainActivity.loadFragment(lichSuBillCaNhanFragment);
            }
        });
        lnDanhSachYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DanhSachYeuThichFragment danhSachYeuThichFragment = new DanhSachYeuThichFragment();
                mainActivity.loadFragment(danhSachYeuThichFragment);
            }
        });
        lnUuDai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrangVoucherFragment trangVoucherFragment = new TrangVoucherFragment();
                mainActivity.loadFragment(trangVoucherFragment);
            }
        });
        lnThongTinCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThongTinCaNhanFragment thongTinCaNhanFragment = new ThongTinCaNhanFragment();
                mainActivity.loadFragment(thongTinCaNhanFragment);
            }
        });
        lnThayDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThayDoiMatKhauFragment thayDoiMatKhauFragment = new ThayDoiMatKhauFragment();
                mainActivity.loadFragment(thayDoiMatKhauFragment);
            }
        });
        lnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DangXuatFragment dangXuatFragment = new DangXuatFragment();
                mainActivity.loadFragment(dangXuatFragment);
            }
        });
        lnLienHe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LienHeFragment lienHeFragment = new LienHeFragment();
                mainActivity.loadFragment(lienHeFragment);
            }
        });
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


}
