package com.example.langthangcoffee;

import static com.example.langthangcoffee.R.layout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.InputType;
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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.langthangcoffee.fragment_menu_tool_bar.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

@SuppressWarnings("deprecation")
public class ThongTinCaNhanFragment extends Fragment {
    ImageView imgBack;
    MainActivity mainActivity;
    TaiKhoan taiKhoan;
    EditText edtHo, edtTen, edtSDT, edtDiaChi;
    TextView tvTenQuyenHan;
    Button btnCapNhat;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(layout.fragment_thong_tin_ca_nhan, container, false);
        mainActivity = (MainActivity) getActivity();
        taiKhoan = mainActivity.getTaiKhoan();
        imgBack = v.findViewById(R.id.img_back);
        btnCapNhat = v.findViewById(R.id.btn_cap_nhat);
        edtHo = v.findViewById(R.id.edt_hokh);
        edtTen = v.findViewById(R.id.edt_tenkh);
        edtSDT = v.findViewById(R.id.edt_sdtkh);
        tvTenQuyenHan = v.findViewById(R.id.tv_role_user);
        edtDiaChi = v.findViewById(R.id.edt_diachi_giaohang);
        edtSDT.setInputType(InputType.TYPE_NULL);
        getThongTinTaiKhoan();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateThongTinTaiKhoan();
            }
        });


        return v;
    }

    public void getThongTinTaiKhoan() {
        try {
            String url = "http://10.0.2.2/server_langthangcoffee/taikhoan/";
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("SDTTaiKhoan", taiKhoan.getSdtTaiKhoan());
            final String requestBody = jsonBody.toString();
            //creating a string request to send request to the url
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //hiding the progressbar after completion

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONObject jsonObjectData = jsonObject.getJSONObject("data");
                                taiKhoan.setSdtTaiKhoan(jsonObjectData.getString("SDTTaiKhoan"));
                                taiKhoan.setHo(jsonObjectData.getString("Ho"));
                                taiKhoan.setTen(jsonObjectData.getString("Ten"));
                                taiKhoan.setDiaChiGiaoHang(jsonObjectData.getString("DiaChiGiaoHang"));
                                taiKhoan.setMaQuyenHan(jsonObjectData.getInt("MaQuyenHan"));
                                taiKhoan.setTenQuyenHan(jsonObjectData.getString("TenQuyenHan"));
                                taiKhoan.setDiaChiGiaoHang(jsonObjectData.getString("DiaChiGiaoHang"));
                                hienThiThongTin();
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
    public void updateThongTinTaiKhoan() {
        try {
            String url = "http://10.0.2.2/server_langthangcoffee/taikhoan/update";
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Ho", edtHo.getText().toString());
            jsonBody.put("SDTTaiKhoan", taiKhoan.getSdtTaiKhoan());
            jsonBody.put("Ten", edtTen.getText().toString());
            jsonBody.put("DiaChiGiaoHang", edtDiaChi.getText().toString());
            final String requestBody = jsonBody.toString();
            //creating a string request to send request to the url
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //hiding the progressbar after completion

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONObject jsonObjectData = jsonObject.getJSONObject("data");
                                taiKhoan.setSdtTaiKhoan(jsonObjectData.getString("SDTTaiKhoan"));
                                taiKhoan.setHo(jsonObjectData.getString("Ho"));
                                taiKhoan.setTen(jsonObjectData.getString("Ten"));
                                taiKhoan.setDiaChiGiaoHang(jsonObjectData.getString("DiaChiGiaoHang"));
                                taiKhoan.setMaQuyenHan(jsonObjectData.getInt("MaQuyenHan"));
                                taiKhoan.setTenQuyenHan(jsonObjectData.getString("TenQuyenHan"));
                                taiKhoan.setDiaChiGiaoHang(jsonObjectData.getString("DiaChiGiaoHang"));

                                Toast.makeText(getActivity(),jsonObject.getString("message") , Toast.LENGTH_SHORT).show();

                                hienThiThongTin();
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

    public void hienThiThongTin() {
        edtHo.setText(taiKhoan.getHo());
        edtTen.setText(taiKhoan.getTen());
        edtSDT.setText(taiKhoan.getSdtTaiKhoan());
        tvTenQuyenHan.setText(taiKhoan.getTenQuyenHan());
        edtDiaChi.setText(taiKhoan.getDiaChiGiaoHang());

    }

}
