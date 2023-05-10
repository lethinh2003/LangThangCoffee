package com.example.langthangcoffee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.langthangcoffee.fragment_menu_tool_bar.MainActivity;

public class TrangCaNhanFragment extends Fragment {

    ImageView imgBack;
    LinearLayout lnLichSuDonHang;
    MainActivity mainActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_trang_ca_nhan, container, false);
        imgBack =  v.findViewById(R.id.img_back);
        lnLichSuDonHang = v.findViewById(R.id.ln_lich_su_don_hang);
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

        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


}
