package com.example.langthangcoffee.fragment_menu_tool_bar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.langthangcoffee.DatHangFragment;
import com.example.langthangcoffee.DonHang;
import com.example.langthangcoffee.DrawerAdapter;
import com.example.langthangcoffee.DrawerItem;
import com.example.langthangcoffee.FoodOrderTopping;
import com.example.langthangcoffee.LichSuOrder;

import com.example.langthangcoffee.R;
import com.example.langthangcoffee.SimpleItem;
import com.example.langthangcoffee.TaiKhoan;
import com.example.langthangcoffee.TrangCaNhanFragment;
import com.example.langthangcoffee.TrangQuanLyFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {
    public static int POS_CLOSE;
    public static int POS_DASHBOARD;
    public static int POS_ACCOUNT;
    public static int POS_CART;
    public static int POS_ABOUT_US;
    public static int POS_SIGNIN;
    public static int POS_LOGOUT;
    public static int POS_ADMIN;
    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;

    private String[] getScreenTitles;

    private String[] getScreenIcons;
    private String lastTitle;
    private String lastIcon;

    private TaiKhoan taiKhoan = null;
    private DonHang donHang = null;

    private RecyclerView mDrawerList;
    private DrawerAdapter drawerAdapter;



    // Declare the launcher at the top of your Activity/Fragment:
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });
    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.tb_activity);
        setSupportActionBar(toolbar);
        //noinspection deprecation
        slidingRootNav = new SlidingRootNavBuilder(this).withDragDistance(180).withRootViewScale(0.75f).withRootViewElevation(25).withToolbarMenuToggle(toolbar).withMenuOpened(false).withContentClickableWhenMenuOpened(false).withSavedState(savedInstanceState).withMenuLayout(R.layout.drawer_menu).inject();
// Draw navigation
        drawerNavigation();

        drawerAdapter.setSelected(POS_DASHBOARD);



    }

    public void drawerNavigation() {
        if (taiKhoan == null) {
            POS_CLOSE = 0;
            POS_DASHBOARD = 1;
            POS_CART = 2;
            POS_SIGNIN = 3;
            POS_ACCOUNT = 99;

            screenIcons = loadScreenIcons();
            screenTitles = loadScreenTitles();

            drawerAdapter = new DrawerAdapter(Arrays.asList(createItemFor(POS_CLOSE), createItemFor(POS_DASHBOARD).setChecked(true), createItemFor(POS_CART), createItemFor(POS_SIGNIN)));

            drawerAdapter.setListener(this);


            mDrawerList = findViewById(R.id.drawer_list);
            mDrawerList.setNestedScrollingEnabled(false);
            mDrawerList.setLayoutManager(new LinearLayoutManager(this));
            mDrawerList.setAdapter(drawerAdapter);
        } else if (taiKhoan != null) {
            POS_CLOSE = 0;
            POS_DASHBOARD = 1;
            POS_ACCOUNT = 2;
            POS_CART = 3;
            POS_SIGNIN = 4;
            POS_ADMIN = 5;

            screenIcons = loadScreenIcons();
            screenTitles = loadScreenTitles();
            drawerAdapter = new DrawerAdapter(Arrays.asList(createItemFor(POS_CLOSE), createItemFor(POS_DASHBOARD).setChecked(true), createItemFor(POS_ACCOUNT), createItemFor(POS_CART), createItemFor(POS_SIGNIN), createItemFor(POS_ADMIN)));

            drawerAdapter.setListener(this);


            mDrawerList = findViewById(R.id.drawer_list);
            mDrawerList.setNestedScrollingEnabled(false);
            mDrawerList.setLayoutManager(new LinearLayoutManager(this));
            mDrawerList.setAdapter(drawerAdapter);

        }
    }

    private String[] getScreenIconFromArray() {
        return getResources().getStringArray(R.array.ld_activityScreenIcons);
    }

    private String[] getScreenTitlesFromArray() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position]).withIconTint(color(R.color.deep_orange)).withTextTint(color(R.color.dark_sienna)).withSelectedIconTint(color(R.color.deep_orange)).withSelectedTextTint(color(R.color.deep_orange));
    }

    @Override
    public void onItemSelected(int position) {
        @SuppressLint("CommitTransaction") FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (position == POS_CLOSE) {
            onBackPressed();
        } else if (position == POS_DASHBOARD) {
            DashBoardFragment dashBoardFragment = new DashBoardFragment();
            loadFragment(dashBoardFragment);
        } else if (position == POS_ACCOUNT) {
            TrangCaNhanFragment trangCaNhanFragment = new TrangCaNhanFragment();
            loadFragment(trangCaNhanFragment);
        } else if (position == POS_CART) {
            DatHangFragment datHangFragment = new DatHangFragment();
            loadFragment(datHangFragment);
        } else if (position == POS_SIGNIN) {
            SigninFragment signinFragment = new SigninFragment();
            loadFragment(signinFragment);
        } else if (position == POS_ADMIN) {
            TrangQuanLyFragment trangQuanLyFragment = new TrangQuanLyFragment();
            loadFragment(trangQuanLyFragment);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    private String[] loadScreenTitles() {
        if (taiKhoan == null) {
            return getResources().getStringArray(R.array.ld_activityScreenTitles);
        } else {
            return getResources().getStringArray(R.array.ld_activityScreenTitlesAuthenciated);

        }
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta;
        if (taiKhoan == null) {
            ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);

        } else {
            ta = getResources().obtainTypedArray(R.array.ld_activityScreenIconsAuthenciated);

        }
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    public void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(fragment, null);
        transaction.replace(R.id.container_activity, fragment);
        slidingRootNav.closeMenu();
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public DonHang getDonHang() {
        return donHang;
    }


    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
        if (taiKhoan != null) {
            askNotificationPermission();
            getDonHangMoiNhat();
            getTokenNotification();
        }
    }


    public void getDonHangMoiNhat() {
        try {
            String url = "http://10.0.2.2/server_langthangcoffee/donhang/moinhat";
            donHang = null;

            final ProgressDialog progressDialog = new ProgressDialog(this);
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
                                //getting the whole json object from the response
                                JSONObject jsonObject = new JSONObject(response);
                                //we have the array named data  inside the object
                                //so here we are getting that json array

                                // get don hang info
                                JSONObject jsonObjectData = jsonObject.getJSONObject("data");
                                donHang = new DonHang();
                                donHang.setMaDonHang(jsonObjectData.getInt("MaDonHang"));
                                donHang.setPhiGiaoHang(jsonObjectData.getInt("PhiGiaoHang"));
                                donHang.setSoTienThanhToan(jsonObjectData.getInt("SoTienThanhToan"));
                                donHang.setThanhTien(jsonObjectData.getInt("ThanhTien"));
                                donHang.setTinhTrang(jsonObjectData.getInt("TinhTrang"));
                                donHang.setDiaChiGiaoHang(jsonObjectData.getString("DiaChiGiaoHang"));

                                donHang.setSdtKhachHang(jsonObjectData.getString("SDTTaiKhoan"));
                                donHang.setMaVoucher(jsonObjectData.getString("MaVoucher"));

                                // get lich su order cua don hang
                                List<LichSuOrder> lichSuOrderList = new ArrayList<LichSuOrder>();
                                JSONArray jsonArray = jsonObject.getJSONArray("lichSuOrder");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsobj = jsonArray.getJSONObject(i);
                                    LichSuOrder lichSuOrder = new LichSuOrder();
                                    lichSuOrder.setMaLichSuOrder(jsobj.getInt("MaLichSuOrder"));
                                    lichSuOrder.setSoLuong(jsobj.getInt("SoLuong"));
                                    lichSuOrder.setThanhTien(jsobj.getInt("ThanhTien"));
                                    lichSuOrder.setGhiChu(jsobj.getString("GhiChu"));
                                    lichSuOrder.setMaSanPham(jsobj.getInt("MaSanPham"));
                                    lichSuOrder.setMaDonHang(jsobj.getInt("MaDonHang"));
                                    lichSuOrder.setTenSanPham(jsobj.getString("TenSanPham"));
                                    lichSuOrder.setKichThuoc(jsobj.getString("KichThuoc"));
                                    lichSuOrder.setGiaTienKichThuoc(jsobj.getInt("GiaTienKichThuoc"));
                                    List<FoodOrderTopping> foodOrderToppingList = new ArrayList<FoodOrderTopping>();
                                    JSONArray toppingArray = jsobj.getJSONArray("Topping");
                                    for (int j = 0; j < toppingArray.length(); j++) {
                                        JSONObject toppingObject = toppingArray.getJSONObject(j);
                                        FoodOrderTopping foodOrderTopping = new FoodOrderTopping();
                                        foodOrderTopping.setTenTopping(toppingObject.getString("TenTopping"));
                                        foodOrderTopping.setGiaTien(toppingObject.getInt("GiaTien"));
                                        foodOrderTopping.setMaSanPham(toppingObject.getInt("MaSanPham"));
                                        foodOrderToppingList.add(foodOrderTopping);
                                    }
                                    lichSuOrder.setFoodOrderToppingList(foodOrderToppingList);


                                    lichSuOrderList.add(lichSuOrder);
                                }
                                donHang.setLichSuOrderList(lichSuOrderList);


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
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            //adding the string request to request queue
            requestQueue.add(stringRequest);

        } catch (JSONException err) {
            err.printStackTrace();
        }
    }


    void getTokenNotification() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("error", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();


                        // Log and toast
                        Log.i("token", token);
                        updateTokenNotificationDatabase(token);
                    }
                });

    }

    void updateTokenNotificationDatabase(String token) {

        try {
            String url = "http://10.0.2.2/server_langthangcoffee/taikhoan/update-notification-token";

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("SDTTaiKhoan", taiKhoan.getSdtTaiKhoan());
            jsonBody.put("Token", token);
            final String requestBody = jsonBody.toString();
            //creating a string request to send request to the url
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //hiding the progressbar after completion

                            try {
                                //getting the whole json object from the response
                                JSONObject jsonObject = new JSONObject(response);
                                //we have the array named data  inside the object
                                //so here we are getting that json array

                                taiKhoan.setNotificationToken(token);
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();


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
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            //adding the string request to request queue
            requestQueue.add(stringRequest);

        } catch (JSONException err) {
            err.printStackTrace();
        }

    }

}
