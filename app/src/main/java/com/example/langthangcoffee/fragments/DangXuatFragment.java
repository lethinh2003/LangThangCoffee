package com.example.langthangcoffee.fragments;

import static com.example.langthangcoffee.R.layout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.langthangcoffee.activities.MainActivity;

@SuppressWarnings("deprecation")
public class DangXuatFragment extends Fragment {
    MainActivity mainActivity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(layout.fragment_thay_doi_mat_khau, container, false);
        mainActivity = (MainActivity) getActivity();
        mainActivity.setTaiKhoan(null);
        mainActivity.clearStorageInformationLogin();
        mainActivity.drawerNavigation();
        DashBoardFragment dashBoardFragment = new DashBoardFragment();
        mainActivity.loadFragment(dashBoardFragment);
        return v;
    }



}
