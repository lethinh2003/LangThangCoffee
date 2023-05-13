package com.example.langthangcoffee.fragment_menu_tool_bar;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.langthangcoffee.DashBoardAdapter;
import com.example.langthangcoffee.FifthPageDashBoard;
import com.example.langthangcoffee.FirstPageDashBoard;
import com.example.langthangcoffee.FourthPageDashBoard;
import com.example.langthangcoffee.InformationItem;
import com.example.langthangcoffee.R;
import com.example.langthangcoffee.SecondPageDashBoard;
import com.example.langthangcoffee.SixthPageDashBoard;
import com.example.langthangcoffee.ThirdPageDashBoard;
import com.example.langthangcoffee.VerticalViewPager;

import java.util.ArrayList;
import java.util.List;

public class DashBoardFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.dashboard_fragment, container, false);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        List<Fragment> list = new ArrayList<>();
        list.add(new FirstPageDashBoard());
        list.add(new SecondPageDashBoard());
        list.add(new ThirdPageDashBoard());
        list.add(new FourthPageDashBoard());
        list.add(new FifthPageDashBoard());
        list.add(new SixthPageDashBoard());

        DashBoardAdapter vPagerAdapter = new DashBoardAdapter(fragmentManager, list);
        VerticalViewPager viewPager = v.findViewById(R.id.vp);
        viewPager.setAdapter(vPagerAdapter);



        return v;
    }
}
