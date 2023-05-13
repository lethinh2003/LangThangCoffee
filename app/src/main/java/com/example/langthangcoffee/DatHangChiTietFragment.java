package com.example.langthangcoffee;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.langthangcoffee.fragment_menu_tool_bar.MainActivity;
import com.example.langthangcoffee.fragment_menu_tool_bar.SigninFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class DatHangChiTietFragment extends Fragment implements FoodOrderToppingAdapter.EventListener {
    private List<FoodOrderSize> listFoodOrderSize = new ArrayList<>();
    private List<FoodOrderTopping> listFoodOrderTopping = new ArrayList<>();
    private int maSanPham = 0;
    private RecyclerView recyclerView, recyclerView2;
    private FoodOrderSizeAdapter foodOrderSizeAdapter;
    private FoodOrderToppingAdapter foodOrderToppingAdapter;
    GridLayoutManager gridLayoutManager, gridLayoutManager2;
    ItemClickListener itemClickListener;
    ImageView imgFoodDetail;
    TextView tvFoodDetailName;
    TextView tvFoodDetailPrice;
    TextView tvFoodDetailDesc;
    Button btnPriceDetail;
    EditText edtGhiChu;
    TextView tvQuantity;
    TextView tvQuantityOrder;
    Button btnPlusFood;
    Button btnSubtractFood;


    LichSuOrder lichSuOrder = null;
    MainActivity mainActivity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_trang_order_chitiet, container, false);
        mainActivity = (MainActivity) getActivity();
        imgFoodDetail = v.findViewById(R.id.img_info_detail);
        tvFoodDetailName = v.findViewById(R.id.tv_info_item_name);
        tvFoodDetailDesc = v.findViewById(R.id.tv_info_item_desc);
        tvFoodDetailPrice = v.findViewById(R.id.tv_info_item_price);
        btnPriceDetail = v.findViewById(R.id.btn_price_detail);
        tvQuantity = v.findViewById(R.id.tv_quantity);
        btnPlusFood = v.findViewById(R.id.btn_plus);
        btnSubtractFood = v.findViewById(R.id.btn_subtract);
        edtGhiChu = v.findViewById(R.id.edt_note);
        tvQuantityOrder = v.findViewById(R.id.tv_quantity_order);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            int idSanPham = bundle.getInt("IDSanPham", 0);
            maSanPham = idSanPham;
            // Fetch API order food detail
            getFoodOrderDetail();
            lichSuOrder = new LichSuOrder();

            // Initialize listener
            itemClickListener = new ItemClickListener() {
                @Override
                public void onClick(JSONObject jsonObject) throws JSONException {
                    // Notify adapter
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            foodOrderSizeAdapter.notifyDataSetChanged();
                        }
                    });
                    // Cập nhật size lịch sử order
                    String tenKichThuoc = jsonObject.getString("tenKichThuoc");
                    int giaTien = jsonObject.getInt("giaTien");


                    int thanhTien = (lichSuOrder.getThanhTien() - lichSuOrder.getSoLuong() * lichSuOrder.getGiaTienKichThuoc()) + (lichSuOrder.getSoLuong() * giaTien);
                    lichSuOrder.setKichThuoc(tenKichThuoc);
                    lichSuOrder.setGiaTienKichThuoc(giaTien);
                    lichSuOrder.setThanhTien(thanhTien);
                    btnPriceDetail.setText(String.valueOf(lichSuOrder.getThanhTien()) + " đ");


                }
            };
            recyclerView = v.findViewById(R.id.rcv_select_size);
            gridLayoutManager = new GridLayoutManager(getContext(), 1);
            recyclerView.setLayoutManager(gridLayoutManager);
            foodOrderSizeAdapter = new FoodOrderSizeAdapter(listFoodOrderSize, itemClickListener);
            recyclerView.setAdapter(foodOrderSizeAdapter);

            // display list food topping size
            recyclerView2 = v.findViewById(R.id.rcv_select_topping);
            gridLayoutManager2 = new GridLayoutManager(getContext(), 1);
            recyclerView2.setLayoutManager(gridLayoutManager2);
            foodOrderToppingAdapter = new FoodOrderToppingAdapter(listFoodOrderTopping, lichSuOrder, this);
            recyclerView2.setAdapter(foodOrderToppingAdapter);

            btnPlusFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lichSuOrder.setSoLuong(lichSuOrder.getSoLuong() + 1);
                    tvQuantity.setText(String.valueOf(lichSuOrder.getSoLuong()));
                    int thanhTien = lichSuOrder.getSoLuong() * (lichSuOrder.getGiaTienKichThuoc() + lichSuOrder.getGiaTienTopping());
                    lichSuOrder.setThanhTien(thanhTien);
                    btnPriceDetail.setText(String.valueOf(lichSuOrder.getThanhTien()) + " đ");
                }
            });
            btnSubtractFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lichSuOrder.getSoLuong() > 0) {
                        lichSuOrder.setSoLuong(lichSuOrder.getSoLuong() - 1);
                        tvQuantity.setText(String.valueOf(lichSuOrder.getSoLuong()));
                        int thanhTien = lichSuOrder.getSoLuong() * (lichSuOrder.getGiaTienKichThuoc() + lichSuOrder.getGiaTienTopping());
                        lichSuOrder.setThanhTien(thanhTien);
                        btnPriceDetail.setText(String.valueOf(lichSuOrder.getThanhTien()) + " đ");

                    }
                }
            });
            btnPriceDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check login
                    if (mainActivity.getTaiKhoan() == null) {
                        SigninFragment signinFragment = new SigninFragment();
                        mainActivity.loadFragment(signinFragment);
                        return;
                    }
                    // Update lich su order
                    String ghiChu = edtGhiChu.getText().toString();
                    lichSuOrder.setGhiChu(ghiChu);


                    // Insert Database

                    themLichSuOrderDatabase();


                }
            });


        }

        return v;
    }

    private void themLichSuOrderDatabase() {
        try {
            String url =  getString(R.string.endpoint_server) + "/donhang/tao-lich-su-order";
            DonHang getDonHang = mainActivity.getDonHang();
            getDonHang.setThanhTien(getDonHang.getThanhTien() + lichSuOrder.getThanhTien());


            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            JSONObject jsonBody = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            for (FoodOrderTopping item : lichSuOrder.getFoodOrderToppingList()
            ) {
                JSONObject jsObj = new JSONObject();
                jsObj.put("TenTopping", item.getTenTopping());
                jsObj.put("GiaTien", item.getGiaTien());
                jsonArray.put(jsObj);
            }
            jsonBody.put("maDonHang", getDonHang.getMaDonHang());
            jsonBody.put("maSanPham", lichSuOrder.getMaSanPham());
            jsonBody.put("kichThuoc", lichSuOrder.getKichThuoc());
            jsonBody.put("giaTienKichThuoc", lichSuOrder.getGiaTienKichThuoc());
            jsonBody.put("ghiChu", lichSuOrder.getGhiChu());
            jsonBody.put("soLuong", lichSuOrder.getSoLuong());
            jsonBody.put("thanhTien", lichSuOrder.getThanhTien());
            jsonBody.put("topping", jsonArray);


            final String requestBody = jsonBody.toString();
            //creating a string request to send request to the url
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //hiding the progressbar after completion
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Toast.makeText(getActivity(), "Order thành công", Toast.LENGTH_SHORT).show();
                                // Thêm lịch sử order vào đơn hàng
                                lichSuOrder.setMaLichSuOrder(jsonObject.getInt("data"));
                                List<LichSuOrder> lichSuOrderList = getDonHang.getLichSuOrderList();
                                lichSuOrderList.add(lichSuOrder);
                                getDonHang.setLichSuOrderList(lichSuOrderList);


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
                    }) {

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }


            };

            //creating a request queue
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            //adding the string request to request queue
            requestQueue.add(stringRequest);

        } catch (JSONException err) {
            err.printStackTrace();
        }
    }

    // Cập nhật giá tiền khi chọn topping
    public void onUpdateLichSuOrder() {
        int thanhTien = lichSuOrder.getSoLuong() * (lichSuOrder.getGiaTienKichThuoc() + lichSuOrder.getGiaTienTopping());
        lichSuOrder.setThanhTien(thanhTien);
        btnPriceDetail.setText(String.valueOf(lichSuOrder.getThanhTien()) + " đ");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imgBack = (ImageView) view.findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
    }

    private void getFoodOrderDetail() {
        try {
            String url = getString(R.string.endpoint_server) + "/sanpham/chitiet";

            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("maSanPham", maSanPham);
            final String requestBody = jsonBody.toString();
            //creating a string request to send request to the url
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //hiding the progressbar after completion

                            try {
                                //getting the whole json object from the response
                                JSONObject obj = new JSONObject(response);
                                //we have the array named data  inside the object
                                //so here we are getting that json array

                                // Kich Thuoc
                                JSONArray dataArray = obj.getJSONArray("kichThuoc");
                                for (int i = 0; i < dataArray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject jsonObject = dataArray.getJSONObject(i);
                                    FoodOrderSize foodOrderSize = new FoodOrderSize();
                                    foodOrderSize.setTenKichThuoc(jsonObject.getString("TenKichThuoc"));
                                    foodOrderSize.setGiaTien(jsonObject.getInt("GiaTien"));
                                    foodOrderSize.setMaSanPham(jsonObject.getInt("MaSanPham"));
                                    listFoodOrderSize.add(foodOrderSize);
                                }
                                lichSuOrder.setFoodOrderSizeList(listFoodOrderSize);
                                foodOrderSizeAdapter.notifyDataSetChanged();

                                // Item
                                dataArray = obj.getJSONArray("data");
                                for (int i = 0; i < dataArray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject jsonObject = dataArray.getJSONObject(i);
                                    FoodOrder foodOrder = new FoodOrder();
                                    int imageID = getActivity().getResources().getIdentifier(jsonObject.getString("HinhAnh"), "drawable", getActivity().getPackageName());
                                    foodOrder.setImage(imageID);
                                    foodOrder.setHinhAnh(jsonObject.getString("HinhAnh"));
                                    foodOrder.setDesc(jsonObject.getString("MoTa"));
                                    foodOrder.setName(jsonObject.getString("TenSanPham"));
                                    foodOrder.setID(jsonObject.getInt("MaSanPham"));
                                    foodOrder.setType(jsonObject.getInt("MaDanhMuc"));
                                    foodOrder.setPrice(jsonObject.getInt("GiaTien"));

                                    Picasso.get()
                                            .load(foodOrder.getHinhAnh())
                                            .fit()
                                            .centerInside()
                                            .into(imgFoodDetail);

                                    tvFoodDetailName.setText(foodOrder.getName());
                                    tvFoodDetailDesc.setText(foodOrder.getDesc());
                                    tvFoodDetailPrice.setText(String.valueOf(foodOrder.getPrice()) + " đ");
// Create instance Lich su order
                                    lichSuOrder.setMaSanPham(foodOrder.getID());
                                    lichSuOrder.setKichThuoc(jsonObject.getString("TenKichThuoc"));
                                    lichSuOrder.setGiaTienKichThuoc(foodOrder.getPrice());
                                    lichSuOrder.setSoLuong(1);
                                    int thanhTien = lichSuOrder.getSoLuong() * lichSuOrder.getGiaTienKichThuoc();
                                    lichSuOrder.setThanhTien(thanhTien);
                                    tvQuantity.setText(String.valueOf(lichSuOrder.getSoLuong()));
                                    btnPriceDetail.setText(String.valueOf(lichSuOrder.getThanhTien()) + " đ");


                                }

                                // Topping
                                dataArray = obj.getJSONArray("topping");
                                for (int i = 0; i < dataArray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject jsonObject = dataArray.getJSONObject(i);
                                    FoodOrderTopping foodOrderTopping = new FoodOrderTopping();
                                    foodOrderTopping.setTenTopping(jsonObject.getString("TenTopping"));
                                    foodOrderTopping.setGiaTien(jsonObject.getInt("GiaTien"));
                                    foodOrderTopping.setMaSanPham(jsonObject.getInt("MaSanPham"));
                                    listFoodOrderTopping.add(foodOrderTopping);
                                }
                                foodOrderToppingAdapter.notifyDataSetChanged();

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
                    }) {

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }


            };

            //creating a request queue
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            //adding the string request to request queue
            requestQueue.add(stringRequest);

        } catch (JSONException err) {
            err.printStackTrace();
        }
    }

}
