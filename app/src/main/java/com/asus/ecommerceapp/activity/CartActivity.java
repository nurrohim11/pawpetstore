package com.asus.ecommerceapp.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import static android.support.constraint.Constraints.TAG;
import static android.widget.LinearLayout.VERTICAL;

public class CartActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, CartAdapter.CartAdapterCallback {
    private Util util;
    private Context context;
    @BindView(R.id.rv_cart)
    RecyclerView rvCart;
    @BindView(R.id.tv_total)
    TextView tvTotal;
//    public KeranjangAdapter adapter;
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

//    @BindView(R.id.decrease)
    Button decrease;
//    @BindView(R.id.increase)
    Button increase;
    private ProgressDialog mRegProgres;
//    @BindView(R.id.tv_qty)
    EditText integerNumber;
    int value = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Keranjang Belanja");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        swipeLayout.setOnRefreshListener(CartActivity.this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        util = new Util();
        unbinder = ButterKnife.bind(this);
        mRegProgres = new ProgressDialog(this);
//        value = Integer.parseInt(item.getJumlah());

//        View inflatedView = getLayoutInflater().inflate(R.layout.dialog_edit_qty, null);

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
//        loadTotal(session.getUserID());
        apiInterface = apiClient.getClient().create(APIInterface.class);
        call = apiInterface.getKeranjang(session.getUserID());
        call.enqueue(new Callback<KeranjangResponse>() {
            @Override
            public void onResponse(Call<KeranjangResponse> call, final Response<KeranjangResponse> response) {
                if (response.isSuccessful()){
//                    adapter.updateData(response.body().getKeranjang());
                    Log.d(TAG, "Response keranjang" +response.body().getKeranjang());
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
                                Snackbar snackbar = Snackbar.make(coordinatorLayout,"Keranjang belanja anda kosong",Toast.LENGTH_SHORT);
                                snackbar.setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(getApplicationContext(),"Silahkan masukkan ke keranjang dulu",Toast.LENGTH_SHORT).show();
                                    }
                                });

                                snackbar.setActionTextColor(Color.BLUE);

                                View snackbarView = snackbar.getView();
                                TextView snackbarText = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
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
                                startActivity(i);
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
    public void onDestroy() {
        super.onDestroy();
        if (call != null) call.cancel();
    }

//    show dialog edit quantity
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
//                        if (response.body().getSuccess() == true){
                        if(response.body().getSuccess() == true) {
                            Toast.makeText(getApplicationContext(), response.body().getInfo(), Toast.LENGTH_SHORT).show();
                            loadData();
//                            adapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(getApplicationContext(), response.body().getInfo(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RequestResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
//                Toast.makeText(getBaseContext(), "Thanks you "+id_keranjang+" "+integerNumber.getText().toString(), Toast.LENGTH_SHORT).show();
//                adapter.notifyDataSetChanged();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

//    private  void loadTotal(String id){
//        session = new UserSession(this);
//        apiInterface = apiClient.getClient().create(APIInterface.class);
//        callTotal = apiInterface.getTotal(id);
//        callTotal.enqueue(new Callback<TotalKeranjang>() {
//            @Override
//            public void onResponse(Call<TotalKeranjang> call, Response<TotalKeranjang> response) {
//                final TotalKeranjang item = response.body();
//                if (response.isSuccessful()) {
//                    tvTotal.setText(toRupiah(item.getTotal()));
//
//                    btnCheckout.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            String total = item.getTotal();
//                            Intent i = new Intent(CartActivity.this, CheckoutActivity.class);
//                            Bundle b = new Bundle();
//                            b.putString("total", total);
//                            i.putExtras(b);
//                            startActivity(i);
//                        }
//                    });
//                }else if (item.getTotal().equalsIgnoreCase("")){
//                    tvTotal.setText(0);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TotalKeranjang> call, Throwable t) {
//
//            }
//        });
//
//    }

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
