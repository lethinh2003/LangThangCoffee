package com.example.langthangcoffee.fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.langthangcoffee.activities.MainActivity;
import com.example.langthangcoffee.R;
import com.example.langthangcoffee.models.TaiKhoan;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class SigninFragment extends Fragment {
    public static final String MyPREFERENCES = "ACCOUNT_LOGIN";
    SharedPreferences sharedpreferences;
    MainActivity mainActivity;
    EditText edtPassKH, edtSDTKH;
    Button btnSignin;
    ImageView imgShowPassword;
    Boolean isShowPassword = false;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, getActivity().MODE_PRIVATE);
        String sdtTaiKhoanStorage = sharedpreferences.getString("SDTTaiKhoan", "");
        String matKhauStorage = sharedpreferences.getString("MatKhau", "");
        Log.i("Account", sdtTaiKhoanStorage + " " + matKhauStorage);
        btnSignin = view.findViewById(R.id.btn_sign_in);
        imgShowPassword = view.findViewById(R.id.img_showpassword);
        edtPassKH = view.findViewById(R.id.edt_password);
        edtSDTKH = view.findViewById(R.id.edt_phone_number);


        LinearLayout app_layer = view.findViewById(R.id.ln_sign_up);
        // Open Sign up fragment
        app_layer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment signupFragment = new SignupFragment();
                ((MainActivity) getActivity()).loadFragment(signupFragment);
            }
        });
        imgShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isShowPassword) {
                    edtPassKH.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    edtPassKH.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                isShowPassword = !isShowPassword;
                edtPassKH.setSelection(edtPassKH.length());
                updateImageShowHidePassword();
            }
        });
        // Login
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtSDTKH.getText().toString().trim().length() != 10) {
                    Toast.makeText(getActivity(), "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                } else if (edtPassKH.getText().toString().trim().length() < 6) {
                    Toast.makeText(getActivity(), "Mật khẩu phải từ 6 kí tự trở lên", Toast.LENGTH_SHORT).show();
                } else {
                    loginTaiKhoan();
                }
            }
        });
    }
    void updateImageShowHidePassword() {
        int drawableId = isShowPassword ? R.drawable.hidepassword : R.drawable.showpassword;
        imgShowPassword.setImageResource(drawableId);
    }

    public void loginTaiKhoan() {
        {
            String url = getString(R.string.endpoint_server) + "/taikhoan/sign-in";
            Log.i("url", url);
            try {
                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("SDTTaiKhoan", edtSDTKH.getText().toString());
                jsonBody.put("MatKhau", edtPassKH.getText().toString());
                final String requestBody = jsonBody.toString();
                //creating a string request to send request to the url
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion

                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            // Instance TaiKhoan
                            TaiKhoan taiKhoan = new TaiKhoan();
                            JSONObject jsonObjectData = obj.getJSONObject("data");
                            taiKhoan.setSdtTaiKhoan(jsonObjectData.getString("SDTTaiKhoan"));
                            taiKhoan.setHo(jsonObjectData.getString("Ho"));
                            taiKhoan.setTen(jsonObjectData.getString("Ten"));
                            taiKhoan.setDiaChiGiaoHang(jsonObjectData.getString("DiaChiGiaoHang"));
                            taiKhoan.setMaQuyenHan(jsonObjectData.getInt("MaQuyenHan"));
                            taiKhoan.setTenQuyenHan(jsonObjectData.getString("TenQuyenHan"));
                            taiKhoan.setDiaChiGiaoHang(jsonObjectData.getString("DiaChiGiaoHang"));


                            mainActivity.setTaiKhoan(taiKhoan);
                            mainActivity.drawerNavigation();
                            // Load Dashboard fragment
                            DashBoardFragment dashBoardFragment = new DashBoardFragment();
                            mainActivity.loadFragment(dashBoardFragment);

                            // Storage information account
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("SDTTaiKhoan", edtSDTKH.getText().toString());
                            editor.putString("MatKhau", edtPassKH.getText().toString());
                            editor.commit();
                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        return inflater.inflate(R.layout.fragment_signin, container, false);
    }
}
