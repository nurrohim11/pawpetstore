package com.asus.ecommerceapp.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.asus.ecommerceapp.Networking.APIClient;
import com.asus.ecommerceapp.Networking.APIInterface;
import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.SessionManager.UserSession;
import com.asus.ecommerceapp.adapter.CartAdapter;
import com.asus.ecommerceapp.model.Login.RequestResponse;
import com.asus.ecommerceapp.model.keranjang.EditCart;
import com.asus.ecommerceapp.model.keranjang.KeranjangItem;
import com.asus.ecommerceapp.model.keranjang.KeranjangResponse;
import com.asus.ecommerceapp.model.keranjang.TotalKeranjang;
import com.asus.ecommerceapp.utils.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.LinearLayout.VERTICAL;

public class CartActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, CartAdapter.CartAdapterCallback {
    private Util util;
    private Context context;
    @BindView(R.id.rv_cart)
    RecyclerView rvCart;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    public CartAdapter adapter;
    @BindView(R.id.placeOrder)
    Button btnCheckout;
    private APIClient apiClient = new APIClient();
    private List<KeranjangItem> list = new ArrayList<>();
    private ArrayList<TotalKeranjang> totalKeranjangs = new ArrayList<>();
    UserSession session;
    Call<KeranjangResponse> call;
    Call<TotalKeranjang> callTotal;
    private APIInterface apiInterface;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    Unbinder unbinder;

    private Gson gson = new Gson();
    private KeranjangItem item;
    private Call<RequestResponse> request_call;

    Button decrease;
    Button increase;
    private ProgressDialog mRegProgres;
    EditText integerNumber;
    int value = 0;

    public static final int PICK_CART = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        swipeLayout.setOnRefreshListener(CartActivity.this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        util = new Util();
        unbinder = ButterKnife.bind(this);
        mRegProgres = new ProgressDialog(this);

    }
    @Override
    protected void  onStart(){
        super.onStart();
    }
    @Override
    protected void  onResume(){
        super.onResume();
        setuplist();
        loadData();
    }
    private void setuplist() {
        adapter = new CartAdapter(list,CartActivity.this, this);
        rvCart.setLayoutManager(new LinearLayoutManager(context));
        rvCart.addItemDecoration(new DividerItemDecoration(this, VERTICAL));
        rvCart.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void loadData(){
        session = new UserSession(this);
        apiInterface = apiClient.getClient().create(APIInterface.class);
        call = apiInterface.getKeranjang(session.getUserID());
        call.enqueue(new Callback<KeranjangResponse>() {
            @Override
            public void onResponse(Call<KeranjangResponse> call, final Response<KeranjangResponse> response) {
                if (response.isSuccessful()){
                    list = response.body().getKeranjang();
                    if (list == null) Log.i("notepads", "NULL");
                    adapter = new CartAdapter(list,CartActivity.this, CartActivity.this);
                    rvCart.setAdapter(adapter);

                    if(response.body().getTotal().equalsIgnoreCase("")){
                        tvTotal.setText(0);
                    }else{
                        tvTotal.setText(util.toRupiah(response.body().getTotal()));
                    }

                    if(response.body().getResult().equalsIgnoreCase("0")){
                        btnCheckout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                @SuppressLint("WrongConstant") Snackbar snackbar = Snackbar.make(coordinatorLayout,"Cart anda kosong",Toast.LENGTH_SHORT);
                                snackbar.setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(getApplicationContext(),"Silahkan masukkan ke cart dulu",Toast.LENGTH_SHORT).show();
                                    }
                                });

                                snackbar.setActionTextColor(Color.BLUE);

                                View snackbarView = snackbar.getView();
                                TextView snackbarText = (TextView) snackbarView.findViewById(R.id.snackbar_text);
                                snackbarText.setTextColor(Color.YELLOW);
                                snackbar.show();
                            }
                        });
                    }else{
                        btnCheckout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String total = response.body().getTotal();
                                Intent i = new Intent(CartActivity.this, CheckoutActivity.class);
                                Bundle b = new Bundle();
                                b.putString("total", total);
                                i.putExtras(b);
                                startActivityForResult(i,PICK_CART);
                            }
                        });
                    }
                }else {
                    Toast.makeText(CartActivity.this ,"Getting Workshop Failed", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<KeranjangResponse> call, Throwable t) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_CART){
            if(resultCode == RESULT_OK){
                finish();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (call != null) call.cancel();
    }

    @Override
    public void onEditDialog(String id_keranjang, String jumlah) {
       editKeranjang(id_keranjang, jumlah);
    }

    public void editKeranjang(final String id_keranjang, final  String jumlah){
        adapter = new CartAdapter(list,CartActivity.this, this);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        View view;
        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.dialog_edit_qty, null);
        increase = (Button)view.findViewById(R.id.increase);
        decrease = (Button)view.findViewById(R.id.decrease);
        integerNumber =(EditText)view.findViewById(R.id.tv_qty);
        integerNumber.setText(jumlah);
        value=Integer.parseInt(jumlah);

        decrease.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(value!=0){
                    value = value-1;
                }
                integerNumber.setText(""+value);

            }
        });
        increase.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                value = value+1;
                integerNumber.setText(""+value);
            }
        });
        alert.setView(view);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Thanks you", Toast.LENGTH_SHORT).show();
            }
        });
        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String jml = integerNumber.getText().toString().trim();
                APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
                request_call = apiInterface.updateJumlah(new EditCart(jml ,id_keranjang ));
                request_call.enqueue(new Callback<RequestResponse>() {
                    @Override
                    public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
                        if(response.body().getSuccess() == true) {
                            Toast.makeText(getApplicationContext(), response.body().getInfo(), Toast.LENGTH_SHORT).show();
                            loadData();
                        }else {
                            Toast.makeText(getApplicationContext(), response.body().getInfo(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RequestResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                setuplist();
                loadData();
                swipeLayout.setRefreshing(false);
            }
        }, 5000);
    }

}
