package com.example.langthangcoffee;

import static com.example.langthangcoffee.R.layout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.langthangcoffee.fragment_menu_tool_bar.DashBoardFragment;
import com.example.langthangcoffee.fragment_menu_tool_bar.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

@SuppressWarnings("deprecation")
public class DangXuatFragment extends Fragment {
    MainActivity mainActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(layout.fragment_thay_doi_mat_khau, container, false);
        mainActivity = (MainActivity) getActivity();
        mainActivity.setTaiKhoan(null);
        mainActivity.drawerNavigation();
        DashBoardFragment dashBoardFragment = new DashBoardFragment();
        mainActivity.loadFragment(dashBoardFragment);
        return v;
    }



}
