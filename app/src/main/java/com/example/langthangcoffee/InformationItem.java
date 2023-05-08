package com.example.langthangcoffee;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class InformationItem extends Fragment {
    AppCompatRadioButton rbLeftItem,rbCenterItem,rbRightItem;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.item_info,container,false);
        rbLeftItem = v.findViewById(R.id.rb_left_item_size);
        rbCenterItem = v.findViewById(R.id.rb_center_item_size);
        rbRightItem = v.findViewById(R.id.rb_right_item_size);

        rbLeftItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRadioButtonClick(view);
            }
        });
        rbCenterItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRadioButtonClick(view);
            }
        });
        rbRightItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRadioButtonClick(view);
            }
        });

        return v;
    }


    public void onRadioButtonClick(View view)
    {
        boolean isSelected = ((AppCompatRadioButton)view).isChecked();
        switch (view.getId())
        {
            case R.id.rb_left_item_size:
                if(isSelected)
                {
                    rbLeftItem.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                    rbCenterItem.setTextColor(ContextCompat.getColor(getContext(), R.color.deep_orange));
                    rbRightItem.setTextColor(ContextCompat.getColor(getContext(), R.color.deep_orange));
                }
                break;
            case R.id.rb_center_item_size:
                if(isSelected)
                {
                    rbLeftItem.setTextColor(ContextCompat.getColor(getContext(), R.color.deep_orange));
                    rbCenterItem.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                    rbRightItem.setTextColor(ContextCompat.getColor(getContext(), R.color.deep_orange));
                }
                break;
            case R.id.rb_right_item_size:
                if(isSelected)
                {
                    rbLeftItem.setTextColor(ContextCompat.getColor(getContext(), R.color.deep_orange));
                    rbCenterItem.setTextColor(ContextCompat.getColor(getContext(), R.color.deep_orange));
                    rbRightItem.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                }
                break;
        }
    }
}
