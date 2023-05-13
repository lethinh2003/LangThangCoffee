package com.example.langthangcoffee;

import static com.example.langthangcoffee.R.drawable;
import static com.example.langthangcoffee.R.id;
import static com.example.langthangcoffee.R.layout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.ColorStateList;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.langthangcoffee.fragment_menu_tool_bar.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class QuanLySanPhamFragment extends Fragment {

    private RecyclerView recyclerView;
    private QuanLySanPhamAdapter quanLySanPhamAdapter;
    private EditText searchFoodOrder;
    private List<FoodOrder> filterList = new ArrayList<>();
    private List<FoodOrder> list = new ArrayList<>();
    private ColorStateList defText;
    private Button item1, item2, item3, item4, searchButton, priceButton, btnThemSanPham;
    private TextView tvQuantity, tvQuantityOrder;
    GridLayoutManager gridLayoutManager;
    ImageView imgBack;

    public MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        list.clear();
        ViewGroup v = (ViewGroup) inflater.inflate(layout.fragment_trang_quan_ly_san_pham, container, false);
        recyclerView = v.findViewById(id.rcv_sanpham);
        gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);


        quanLySanPhamAdapter = new QuanLySanPhamAdapter(list);
        recyclerView.setAdapter(quanLySanPhamAdapter);
        getListSanPhamAdmin();
        btnThemSanPham = v.findViewById(id.btn_them_moi);
        imgBack = v.findViewById(id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });



        searchButton = v.findViewById(id.btn_search_food_order);
        // Search Food
        searchFoodOrder = v.findViewById(id.edt_search_item);
        searchFoodOrder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    searchButton.setEnabled(false);
                    searchButton.setBackgroundResource(drawable.ic_search);
                } else {
                    searchButton.setEnabled(true);
                    searchButton.setBackgroundResource(drawable.ic_close);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                filterList.clear();
                if (s.toString().isEmpty()) {
                    recyclerView.setAdapter(quanLySanPhamAdapter);
                    quanLySanPhamAdapter.notifyDataSetChanged();
                } else {
                    Filter(s.toString());
                }
            }
        });
        btnThemSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemSanPhamFragment themSanPhamFragment = new ThemSanPhamFragment();
                mainActivity.loadFragment(themSanPhamFragment);
            }
        });
        searchFoodOrder.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Ẩn bàn phím sau khi người dùng nhập xong
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchFoodOrder.getWindowToken(), 0);
                    searchFoodOrder.clearFocus();
                    return true;
                }
                return false;
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xoá nội dung của EditText
                searchFoodOrder.setText("");
            }
        });
        return v;
    }

    private void Filter(String text) {
        for (FoodOrder item : list) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filterList.add(item);
            }
        }

        recyclerView.setAdapter(new QuanLySanPhamAdapter(filterList));
        quanLySanPhamAdapter.notifyDataSetChanged();
    }

    private void getListSanPhamAdmin() {
        String url = getString(R.string.endpoint_server) + "/admin/sanpham/get-danh-sach";
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion

                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named data  inside the object
                            //so here we are getting that json array
                            JSONArray datasArray = obj.getJSONArray("data");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < datasArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject jsonObject = datasArray.getJSONObject(i);
                                FoodOrder foodOrder = new FoodOrder();
                                int imageID = getActivity().getResources().getIdentifier(jsonObject.getString("HinhAnh"), "drawable", getActivity().getPackageName());
                                foodOrder.setImage(imageID);
                                foodOrder.setTenDanhMuc(jsonObject.getString("TenDanhMuc"));
                                foodOrder.setHinhAnh(jsonObject.getString("HinhAnh"));
                                foodOrder.setDesc(jsonObject.getString("MoTa"));
                                foodOrder.setName(jsonObject.getString("TenSanPham"));
                                foodOrder.setID(jsonObject.getInt("MaSanPham"));
                                foodOrder.setType(jsonObject.getInt("MaDanhMuc"));
                                foodOrder.setMaDanhMuc(jsonObject.getInt("MaDanhMuc"));
                                foodOrder.setPrice(jsonObject.getInt("GiaTien"));
                                List<KichThuocSanPham> kichThuocSanPhamList = new ArrayList<>();
                                JSONArray kichThuocArray = jsonObject.getJSONArray("kichThuoc");
                                for (int j = 0; j < kichThuocArray.length(); j++) {
                                    JSONObject kichThuocObject = kichThuocArray.getJSONObject(j);
                                    KichThuocSanPham kichThuocSanPham = new KichThuocSanPham();
                                    kichThuocSanPham.setMaSanPham(kichThuocObject.getInt("MaSanPham"));
                                    kichThuocSanPham.setGiaTien(kichThuocObject.getInt("GiaTien"));
                                    kichThuocSanPham.setTenKichThuoc(kichThuocObject.getString("TenKichThuoc"));
                                    kichThuocSanPhamList.add(kichThuocSanPham);
                                }
                                List<ToppingSanPham> toppingSanPhamList = new ArrayList<>();
                                JSONArray toppingArray = jsonObject.getJSONArray("topping");
                                for (int j = 0; j < toppingArray.length(); j++) {
                                    JSONObject toppingObject = toppingArray.getJSONObject(j);
                                    ToppingSanPham toppingSanPham = new ToppingSanPham();
                                    toppingSanPham.setMaSanPham(toppingObject.getInt("MaSanPham"));
                                    toppingSanPham.setGiaTien(toppingObject.getInt("GiaTien"));
                                    toppingSanPham.setTenTopping(toppingObject.getString("TenTopping"));
                                    toppingSanPhamList.add(toppingSanPham);
                                }
                                foodOrder.setKichThuocSanPhamList(kichThuocSanPhamList);
                                foodOrder.setToppingSanPhams(toppingSanPhamList);
                                list.add(foodOrder);

                            }
                            quanLySanPhamAdapter.notifyDataSetChanged();

                            progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                JSONObject obj = new JSONObject(res);
                                Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            } catch (UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                            }
                        }


                        progressDialog.dismiss();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        //adding the string request to request queue
        requestQueue.add(stringRequest);


    }
}



