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
        list.add(new InformationItem());

        DashBoardAdapter vPagerAdapter = new DashBoardAdapter(fragmentManager, list);
        VerticalViewPager viewPager = v.findViewById(R.id.vp);
        viewPager.setAdapter(vPagerAdapter);

//        EditText editTextPhoneNumber = v.findViewById(R.id.et_phone_number);
//        TextView textViewPlus84 = v.findViewById(R.id.tv_plus_84);
//        TextView textViewVerticalLine = v.findViewById(R.id.tv_vertical_line);
//        LinearLayout linearLayoutPhoneNumber = v.findViewById(R.id.ln_phone_number);
//        Button button = v.findViewById(R.id.btn_sign_in);
//
//
//        editTextPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener()
//        {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus)
//            {
//                if(hasFocus)
//                {
//                    editTextPhoneNumber.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.light_blue)));
//                    textViewPlus84.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.light_blue)));
//                    textViewVerticalLine.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.light_blue)));
//                    linearLayoutPhoneNumber.setBackground(getResources().getDrawable(R.drawable.layout_phone_number_bg_checked));
//                }
//                else
//                {
//                    editTextPhoneNumber.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.light_silver_gray)));
//                    textViewPlus84.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.light_silver_gray)));
//                    textViewVerticalLine.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.light_silver_gray)));
//                    linearLayoutPhoneNumber.setBackground(getResources().getDrawable(R.drawable.layout_phone_number_bg_uncheck));
//                }
//            }
//
//        });
//
//        editTextPhoneNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
//                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    // Ẩn bàn phím sau khi người dùng nhập xong
//                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(editTextPhoneNumber.getWindowToken(), 0);
//                    editTextPhoneNumber.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.light_silver_gray)));
//                    textViewPlus84.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.light_silver_gray)));
//                    textViewVerticalLine.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.light_silver_gray)));
//                    linearLayoutPhoneNumber.setBackground(getResources().getDrawable(R.drawable.layout_phone_number_bg_uncheck));
//                    editTextPhoneNumber.clearFocus();
//                    return true;
//                }
//                return false;
//            }
//        });
//
//
//
//
//    // Xác định màu ban đầu của button dựa trên nội dung của EditText ban đầu
//        String text = editTextPhoneNumber.getText().toString();
//        if (text.startsWith("0") && text.length() > 9) {
//            button.setBackground(getResources().getDrawable(R.drawable.custom_btn_deep_orange_radius_30dp)); // Cam đậm
//        } else {
//            button.setBackground(getResources().getDrawable(R.drawable.custom_btn_light_gray_radius_30dp)); //
//        }
//
//    // Thêm TextWatcher để lắng nghe sự kiện thay đổi nội dung của EditText
//        editTextPhoneNumber.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                // Kiểm tra nội dung của EditText
//                String text = editTextPhoneNumber.getText().toString();
//                if (text.startsWith("0") && text.length() > 9) {
//                    // Nếu số đầu tiên là 0 và đã nhập đủ số điện thoại, thì đổi màu button thành cam
//                    button.setBackground(getResources().getDrawable(R.drawable.custom_btn_deep_orange_radius_30dp));
//                } else {
//                    // Nếu không thỏa mãn điều kiện trên, đổi màu button thành xám
//                    button.setBackground(getResources().getDrawable(R.drawable.custom_btn_light_gray_radius_30dp)); // Xám
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        });
//



        return v;
    }
}
