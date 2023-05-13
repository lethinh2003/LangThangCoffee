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
import com.example.langthangcoffee.DangXuatFragment;
import com.example.langthangcoffee.DatHangFragment;
import com.example.langthangcoffee.DonHang;
import com.example.langthangcoffee.DrawerAdapter;
import com.example.langthangcoffee.DrawerItem;
import com.example.langthangcoffee.FoodOrderTopping;
import com.example.langthangcoffee.LichSuOrder;
import com.example.langthangcoffee.NavigationItem;
import com.example.langthangcoffee.R;
import com.example.langthangcoffee.SimpleItem;
import com.example.langthangcoffee.TaiKhoan;
import com.example.langthangcoffee.TrangCaNhanFragment;
import com.example.langthangcoffee.TrangQuanLyFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {
    private List<NavigationItem> navigationItemList = new ArrayList<NavigationItem>();
    private SlidingRootNav slidingRootNav;
    private TaiKhoan taiKhoan = null;
    private DonHang donHang = null;
    private RecyclerView mDrawerList;
    private DrawerAdapter drawerAdapter;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {

                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });

    private void askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {

            } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {

            } else {

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

    }
    // Vẽ thanh navigation
    public void drawerNavigation() {
        setNavigationItemList();
        List<DrawerItem> drawerItemList = new ArrayList<DrawerItem>();
        for (NavigationItem item : navigationItemList
        ) {
            DrawerItem drawerItem = createItemFor(item);
            drawerItemList.add(drawerItem);
        }
        drawerAdapter = new DrawerAdapter(drawerItemList);
        drawerAdapter.setListener(this);
        mDrawerList = findViewById(R.id.drawer_list);
        mDrawerList.setNestedScrollingEnabled(false);
        mDrawerList.setLayoutManager(new LinearLayoutManager(this));
        mDrawerList.setAdapter(drawerAdapter);
        // Mặc định hiển thị ở trang chủ
        drawerAdapter.setSelected(1);


    }

    private DrawerItem createItemFor(NavigationItem navigationItem) {
        return new SimpleItem(navigationItem.getDrawable(), navigationItem.getTitle()).withIconTint(color(R.color.deep_orange)).withTextTint(color(R.color.dark_sienna)).withSelectedIconTint(color(R.color.deep_orange)).withSelectedTextTint(color(R.color.deep_orange));
    }

    @Override
    public void onItemSelected(int position) {
        @SuppressLint("CommitTransaction") FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        NavigationItem navigationItem = navigationItemList.get(position);
        String key = navigationItem.getKey();
        switch (key) {
            case "close":
                onBackPressed();
                break;
            case "home":
                DashBoardFragment dashBoardFragment = new DashBoardFragment();
                loadFragment(dashBoardFragment);
                break;
            case "profile":
                TrangCaNhanFragment trangCaNhanFragment = new TrangCaNhanFragment();
                loadFragment(trangCaNhanFragment);
                break;
            case "cart":
                DatHangFragment datHangFragment = new DatHangFragment();
                loadFragment(datHangFragment);
                break;
            case "signin":
                SigninFragment signinFragment = new SigninFragment();
                loadFragment(signinFragment);
                break;
            case "logout":
                DangXuatFragment dangXuatFragment = new DangXuatFragment();
                loadFragment(dangXuatFragment);
                break;
            case "admin":
                TrangQuanLyFragment trangQuanLyFragment = new TrangQuanLyFragment();
                loadFragment(trangQuanLyFragment);
                break;
            default:

                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void setNavigationItemList() {
        navigationItemList.clear();
        String[] screenTitles = loadScreenTitles();
        String[] screenKeys = loadScreenKeys();
        Drawable[] screenIcons = loadScreenIcons();
        for (int i = 0; i < screenTitles.length; i++) {
            NavigationItem navigationItem = new NavigationItem();
            navigationItem.setDrawable(screenIcons[i]);
            navigationItem.setKey(screenKeys[i]);
            navigationItem.setTitle(screenTitles[i]);
            navigationItemList.add(navigationItem);
        }

    }


    private String[] loadScreenTitles() {
        if (taiKhoan == null) {
            return getResources().getStringArray(R.array.ld_activityScreenTitles);
        } else {
            if (taiKhoan.getMaQuyenHan() == 1) {
                return getResources().getStringArray(R.array.ld_activityScreenTitlesAuthenciated);
            } else {
                return getResources().getStringArray(R.array.ld_activityScreenTitlesAuthenciatedAdmin);
            }
        }
    }

    private String[] loadScreenKeys() {
        if (taiKhoan == null) {
            return getResources().getStringArray(R.array.ld_activityScreenKeys);
        } else {
            if (taiKhoan.getMaQuyenHan() == 1) {
                return getResources().getStringArray(R.array.ld_activityScreenKeysAuthenciated);
            } else {
                return getResources().getStringArray(R.array.ld_activityScreenKeysAuthenciatedAdmin);
            }
        }
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta;
        if (taiKhoan == null) {
            ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);

        } else {
            if (taiKhoan.getMaQuyenHan() == 1) {
                ta = getResources().obtainTypedArray(R.array.ld_activityScreenIconsAuthenciated);
            } else {
                ta = getResources().obtainTypedArray(R.array.ld_activityScreenIconsAuthenciatedAdmin);

            }
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

    // Chuyển fragment
    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
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
            String url = getString(R.string.endpoint_server) + "/donhang/moinhat";
            donHang = null;
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("SDTTaiKhoan", taiKhoan.getSdtTaiKhoan());
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
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
                            NetworkResponse response = error.networkResponse;
                            if (error instanceof ServerError && response != null) {
                                try {
                                    String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                    JSONObject obj = new JSONObject(res);
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
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
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } catch (JSONException err) {
            err.printStackTrace();
        }
    }

    // Lấy registration token FCM (nhận thông báo)
    void getTokenNotification() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("error", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        String token = task.getResult();
                        Log.i("token", token);
                        updateTokenNotificationDatabase(token);
                    }
                });
    }

    // Cập nhật registration token lên server
    void updateTokenNotificationDatabase(String token) {
        try {
            String url = getString(R.string.endpoint_server) + "/taikhoan/update-notification-token";
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("SDTTaiKhoan", taiKhoan.getSdtTaiKhoan());
            jsonBody.put("Token", token);
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                taiKhoan.setNotificationToken(token);
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
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
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
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } catch (JSONException err) {
            err.printStackTrace();
        }

    }

}
