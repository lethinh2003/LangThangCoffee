package com.example.langthangcoffee;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.langthangcoffee.fragment_menu_tool_bar.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class BottomSheetDiaChiGiaoHangFragment extends BottomSheetDialogFragment {
    ImageView imgClose;
    BottomSheetDiaChiGiaoHangFragment bottomSheetDiaChiGiaoHangFragmentl;
    XacNhanDatHangFragment xacNhanDatHangFragment;
    EditText edtNguoiNhan, edtSDT, edtDiaChi;
    Button btnChangeInfoShip;
    CheckBox chkDefaultAddress;
    MainActivity mainActivity;
    DonHang donHang;



    public BottomSheetDiaChiGiaoHangFragment(XacNhanDatHangFragment xacNhanDatHangFragment) {
        // Required empty public constructor
        bottomSheetDiaChiGiaoHangFragmentl = this;
        this.xacNhanDatHangFragment = xacNhanDatHangFragment;

    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_bottom_sheet_dialog_dia_chi, container, false);
        edtNguoiNhan = v.findViewById(R.id.edt_nguoinhan_info_ship);
        edtDiaChi = v.findViewById(R.id.edt_diachi_info_ship);
        edtSDT = v.findViewById(R.id.edt_sdt_info_ship);
        btnChangeInfoShip = v.findViewById(R.id.btn_change_info_ship_order);
        chkDefaultAddress = v.findViewById(R.id.chk_default_address);
        mainActivity = (MainActivity) getActivity();
        donHang = mainActivity.getDonHang();
        btnChangeInfoShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (donHang != null) {
                    updateAddressDonHang();

                }
            }
        });



        return v;
    }
    private void updateAddressDonHang() {
        try {
            String url = "http://10.0.2.2/server_langthangcoffee/donhang/update-dia-chi";
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            JSONObject jsonBody = new JSONObject();
            String infoShip =  edtNguoiNhan.getText().toString() + ", " + edtSDT.getText().toString() + ", " + edtDiaChi.getText().toString();
            jsonBody.put("sdtTaiKhoan", mainActivity.getTaiKhoan().getSdtTaiKhoan());
            jsonBody.put("maDonHang", donHang.getMaDonHang());
            jsonBody.put("diaChiGiaoHang", infoShip);
            jsonBody.put("macDinh", chkDefaultAddress.isChecked());

            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                String message = obj.getString("message");
                                Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();

                                donHang.setDiaChiGiaoHang(infoShip);
                                xacNhanDatHangFragment.updateDonHangView();

                                progressDialog.dismiss();
                                bottomSheetDiaChiGiaoHangFragmentl.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
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
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        } catch (JSONException err) {
            err.printStackTrace();
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgClose = view.findViewById(R.id.img_close_bottom_dialog);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDiaChiGiaoHangFragmentl.dismiss();
            }


        });
    }
}
