package com.example.langthangcoffee;

import static com.example.langthangcoffee.R.color;
import static com.example.langthangcoffee.R.drawable;
import static com.example.langthangcoffee.R.drawable.custom_btn_deep_orange_radius_10dp;
import static com.example.langthangcoffee.R.drawable.custom_btn_pale_cream_radius_10dp;
import static com.example.langthangcoffee.R.id;
import static com.example.langthangcoffee.R.layout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.langthangcoffee.fragment_menu_tool_bar.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class DatHangFragment extends Fragment implements View.OnClickListener {
    private final int POS_TYPE_CAFE = 0;
    private int POS_TYPE_TRASUA = 0;
    private int POS_TYPE_SODA = 0;
    private int POS_TYPE_SWEETCAKE = 0;
    private String url = "http://10.0.2.2/server_langthangcoffee/sanpham";
    private RecyclerView recyclerView;
    private FoodOrderAdapter foodOrderAdapter;
    private EditText searchFoodOrder;
    private List<FoodOrder> filterList = new ArrayList<>();
    private List<FoodOrder> list = new ArrayList<>();
    private ColorStateList defText;
    private Button item1, item2, item3, item4, searchButton, priceButton;
    private TextView tvQuantity, tvQuantityOrder;
    GridLayoutManager gridLayoutManager;

    public MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();

        list.clear();
        ViewGroup v = (ViewGroup) inflater.inflate(layout.fragment_trang_order, container, false);

        recyclerView = v.findViewById(id.rcv_order);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        foodOrderAdapter = new FoodOrderAdapter(list);
        recyclerView.setAdapter(foodOrderAdapter);
        getListFoodOrder();
        item1 = v.findViewById(id.btn_caffe_selected);
        item2 = v.findViewById(id.btn_milktea_selected);
        item3 = v.findViewById(id.btn_soda_selected);
        item4 = v.findViewById(id.btn_sweetcake_selected);
        searchButton = v.findViewById(id.btn_search_food_order);
        priceButton = v.findViewById(id.btn_price);

        tvQuantityOrder = v.findViewById(id.tv_quantity_order);

        if (mainActivity.getTaiKhoan() != null) {
            DonHang getDonHang = mainActivity.getDonHang();
            if (getDonHang != null) {
                priceButton.setText(String.valueOf(getDonHang.getThanhTien()) + " đ");
                tvQuantityOrder.setText(String.valueOf(getDonHang.getLichSuOrderList().size()));

            } else {
                priceButton.setText("0 đ");
                tvQuantityOrder.setText("0");
            }
            Log.i("DatHang", "Reload");
        }

        item1.setOnClickListener(this);
        item2.setOnClickListener(this);
        item3.setOnClickListener(this);
        item4.setOnClickListener(this);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @SuppressLint({"ResourceAsColor", "UseCompatLoadingForDrawables"})
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                int positionOfCafe = 0;
                int positionOfTrasua = POS_TYPE_TRASUA;
                int positionOfSoda = POS_TYPE_SODA;
                int positionOfSweetcake = POS_TYPE_SWEETCAKE;

                int firstVisiblePosition = gridLayoutManager.findFirstVisibleItemPosition();
                int lastVisiblePosition = gridLayoutManager.findLastVisibleItemPosition();

                if (positionOfCafe >= firstVisiblePosition && positionOfCafe <= lastVisiblePosition) {
                    item1.setTextColor(getResources().getColor(color.white));
                    item2.setTextColor(defText);
                    item3.setTextColor(defText);
                    item4.setTextColor(defText);

                    item1.setBackgroundDrawable(getResources().getDrawable(custom_btn_deep_orange_radius_10dp));
                    item2.setBackgroundDrawable(getResources().getDrawable(custom_btn_pale_cream_radius_10dp));
                    item3.setBackgroundDrawable(getResources().getDrawable(custom_btn_pale_cream_radius_10dp));
                    item4.setBackgroundDrawable(getResources().getDrawable(custom_btn_pale_cream_radius_10dp));
                } else if (positionOfTrasua >= firstVisiblePosition && positionOfTrasua <= lastVisiblePosition) {
                    item1.setTextColor(defText);
                    item2.setTextColor(getResources().getColor(color.white));
                    item3.setTextColor(defText);
                    item4.setTextColor(defText);

                    item1.setBackgroundDrawable(getResources().getDrawable(custom_btn_pale_cream_radius_10dp));
                    item2.setBackgroundDrawable(getResources().getDrawable(custom_btn_deep_orange_radius_10dp));
                    item3.setBackgroundDrawable(getResources().getDrawable(custom_btn_pale_cream_radius_10dp));
                    item4.setBackgroundDrawable(getResources().getDrawable(custom_btn_pale_cream_radius_10dp));
                } else if (positionOfSoda >= firstVisiblePosition && positionOfSoda <= lastVisiblePosition) {
                    item1.setTextColor(defText);
                    item2.setTextColor(defText);
                    item3.setTextColor(getResources().getColor(color.white));
                    item4.setTextColor(defText);

                    item1.setBackgroundDrawable(getResources().getDrawable(custom_btn_pale_cream_radius_10dp));
                    item2.setBackgroundDrawable(getResources().getDrawable(custom_btn_pale_cream_radius_10dp));
                    item3.setBackgroundDrawable(getResources().getDrawable(custom_btn_deep_orange_radius_10dp));
                    item4.setBackgroundDrawable(getResources().getDrawable(custom_btn_pale_cream_radius_10dp));
                } else if (positionOfSweetcake >= firstVisiblePosition && positionOfSweetcake <= lastVisiblePosition) {
                    item1.setTextColor(defText);
                    item2.setTextColor(defText);
                    item3.setTextColor(defText);
                    item4.setTextColor(getResources().getColor(color.white));

                    item1.setBackgroundDrawable(getResources().getDrawable(custom_btn_pale_cream_radius_10dp));
                    item2.setBackgroundDrawable(getResources().getDrawable(custom_btn_pale_cream_radius_10dp));
                    item3.setBackgroundDrawable(getResources().getDrawable(custom_btn_pale_cream_radius_10dp));
                    item4.setBackgroundDrawable(getResources().getDrawable(custom_btn_deep_orange_radius_10dp));
                }
            }

        });


        defText = ColorStateList.valueOf(getResources().getColor(R.color.black));
        priceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XacNhanDatHangFragment xacNhanDatHangFragment = new XacNhanDatHangFragment();
                mainActivity.loadFragment(xacNhanDatHangFragment);
            }
        });

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
                    recyclerView.setAdapter(foodOrderAdapter);
                    foodOrderAdapter.notifyDataSetChanged();
                } else {
                    Filter(s.toString());
                }
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

        recyclerView.setAdapter(new FoodOrderAdapter(filterList));
        foodOrderAdapter.notifyDataSetChanged();
    }


    private void getListFoodOrder() {
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
                                foodOrder.setDesc(jsonObject.getString("MoTa"));
                                foodOrder.setName(jsonObject.getString("TenSanPham"));
                                foodOrder.setID(jsonObject.getInt("MaSanPham"));
                                foodOrder.setType(jsonObject.getInt("MaDanhMuc"));
                                foodOrder.setPrice(jsonObject.getInt("GiaTien"));
                                list.add(foodOrder);

                            }
                            foodOrderAdapter.notifyDataSetChanged();
                            updatePosition(list);
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
                        //displaying the error in toast if occur
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        //adding the string request to request queue
        requestQueue.add(stringRequest);


    }

    private void updatePosition(List<FoodOrder> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getType() == 2) {
                POS_TYPE_SODA = i;
                break;
            }
        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getType() == 3) {
                POS_TYPE_TRASUA = i;
                break;
            }
        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getType() == 4) {
                POS_TYPE_SWEETCAKE = i;
                break;
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        if (v.getId() == id.btn_caffe_selected) {
            item1.setTextColor(getResources().getColor(color.white));
            item2.setTextColor(defText);
            item3.setTextColor(defText);
            item4.setTextColor(defText);

            item1.setBackgroundDrawable(getResources().getDrawable(custom_btn_deep_orange_radius_10dp));
            item2.setBackgroundDrawable(getResources().getDrawable(custom_btn_pale_cream_radius_10dp));
            item3.setBackgroundDrawable(getResources().getDrawable(custom_btn_pale_cream_radius_10dp));
            item4.setBackgroundDrawable(getResources().getDrawable(custom_btn_pale_cream_radius_10dp));

            scrollToItem(POS_TYPE_CAFE);
        } else if (v.getId() == id.btn_milktea_selected) {
            item1.setTextColor(defText);
            item2.setTextColor(getResources().getColor(color.white));
            item3.setTextColor(defText);
            item4.setTextColor(defText);

            item1.setBackgroundDrawable(getResources().getDrawable(custom_btn_pale_cream_radius_10dp));
            item2.setBackgroundDrawable(getResources().getDrawable(custom_btn_deep_orange_radius_10dp));
            item3.setBackgroundDrawable(getResources().getDrawable(custom_btn_pale_cream_radius_10dp));
            item4.setBackgroundDrawable(getResources().getDrawable(custom_btn_pale_cream_radius_10dp));

            scrollToItem(POS_TYPE_TRASUA);
        } else if (v.getId() == id.btn_soda_selected) {
            item1.setTextColor(defText);
            item2.setTextColor(defText);
            item3.setTextColor(getResources().getColor(color.white));
            item4.setTextColor(defText);

            item1.setBackgroundDrawable(getResources().getDrawable(custom_btn_pale_cream_radius_10dp));
            item2.setBackgroundDrawable(getResources().getDrawable(custom_btn_pale_cream_radius_10dp));
            item3.setBackgroundDrawable(getResources().getDrawable(custom_btn_deep_orange_radius_10dp));
            item4.setBackgroundDrawable(getResources().getDrawable(custom_btn_pale_cream_radius_10dp));

            scrollToItem(POS_TYPE_SODA);
        } else if (v.getId() == id.btn_sweetcake_selected) {
            item1.setTextColor(defText);
            item2.setTextColor(defText);
            item3.setTextColor(defText);
            item4.setTextColor(getResources().getColor(color.white));

            item1.setBackgroundDrawable(getResources().getDrawable(custom_btn_pale_cream_radius_10dp));
            item2.setBackgroundDrawable(getResources().getDrawable(custom_btn_pale_cream_radius_10dp));
            item3.setBackgroundDrawable(getResources().getDrawable(custom_btn_pale_cream_radius_10dp));
            item4.setBackgroundDrawable(getResources().getDrawable(custom_btn_deep_orange_radius_10dp));

            scrollToItem(POS_TYPE_SWEETCAKE);
        }
    }

    public void scrollToItem(int position) {
        if (gridLayoutManager == null)
            return;
        gridLayoutManager.scrollToPositionWithOffset(position, 0);
    }
}
