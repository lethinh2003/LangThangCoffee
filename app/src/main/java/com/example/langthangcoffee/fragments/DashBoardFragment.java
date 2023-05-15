package com.example.langthangcoffee.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.langthangcoffee.adapters.DashBoardAdapter;
import com.example.langthangcoffee.R;
import com.example.langthangcoffee.viewmodels.VerticalViewPager;

import java.util.ArrayList;
import java.util.List;

public class DashBoardFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.dashboard_fragment, container, false);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        List<Fragment> list = new ArrayList<>();
        list.add(new FirstPageDashBoardFragment());
        list.add(new SecondPageDashBoardFragment());
        list.add(new ThirdPageDashBoardFragment());
        list.add(new FourthPageDashBoardFragment());
        list.add(new FifthPageDashBoardFragment());
        list.add(new SixthPageDashBoardFragment());

        DashBoardAdapter vPagerAdapter = new DashBoardAdapter(fragmentManager, list);
        VerticalViewPager viewPager = v.findViewById(R.id.vp);
        viewPager.setAdapter(vPagerAdapter);



        return v;
    }
}
