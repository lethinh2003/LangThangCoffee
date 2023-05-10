package com.example.langthangcoffee;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class LichSuBillCaNhanAdapter extends FragmentStatePagerAdapter {
    private static int NUM_ITEMS = 4;

    public LichSuBillCaNhanAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);

    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new BillDangChoFragment();
            case 1:
                return new BillDangGiaoFragment();
            case 2:
                return new BillHoanThanhFragment();
            case 3:
                return new BillHuyFragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

//    @Override
//    public int getItemPosition(@NonNull Object object) {
//        return POSITION_NONE;
//    }
}


