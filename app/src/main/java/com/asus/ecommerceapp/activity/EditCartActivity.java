package com.asus.ecommerceapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.asus.ecommerceapp.model.Login.RequestResponse;
import com.asus.ecommerceapp.model.keranjang.EditCart;
import com.asus.ecommerceapp.model.keranjang.KeranjangItem;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCartActivity extends AppCompatActivity {
    public static final String PRODUK_ITEM = "produk_item";
    public static final String TAG = "Response";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.harga)
    TextView tvHarga;
    @BindView(R.id.btnUpdate)
    Button btnUpdate;
    @BindView(R.id.decrease)
    Button decrease;
    @BindView(R.id.increase)
    Button increase;
    private Gson gson = new Gson();
    private KeranjangItem item;
    private Call<RequestResponse> call;

    private ProgressDialog mRegProgres;
    @BindView(R.id.tv_qty)
    EditText integerNumber;
    int value = 1;
    UserSession session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cart);
        ButterKnife.bind(this);
        session = new UserSession(this);

        mRegProgres = new ProgressDialog(this);
//        value = Integer.parseInt(item.getJumlah());
        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = value-1;
                integerNumber.setText(""+value);
            }
        });
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = value+1;
                integerNumber.setText(""+value);
            }
        });
        String produk_item = getIntent().getStringExtra(PRODUK_ITEM);
        loadData(produk_item);
        //Log.d(TAG,"Response"+item.getIdKeranjang());
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_keranjang = item.getIdKeranjang();
                String jumlah = integerNumber.getText().toString().trim();
                Log.d("C|EDJT CART","Response"+item.getIdKeranjang());
                APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
                call = apiInterface.updateJumlah(new EditCart(jumlah ,id_keranjang ));
                call.enqueue(new Callback<RequestResponse>() {
                    @Override
                    public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
//                        if (response.body().getSuccess() == true){
                        if(response.body().getSuccess() == true) {
                            Toast.makeText(EditCartActivity.this, response.body().getInfo(), Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(EditCartActivity.this, CartActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            finish();
                        }else {
                            Toast.makeText(EditCartActivity.this, "Data gagal masuk ke cart", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RequestResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

    }
    private String toRupiah(String nominal) {
        String hasil = "";
        DecimalFormat toRupiah = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatAngka = new DecimalFormatSymbols();
        formatAngka.setCurrencySymbol("Rp. ");
        formatAngka.setMonetaryDecimalSeparator(',');
        formatAngka.setMonetaryDecimalSeparator('.');
        toRupiah.setDecimalFormatSymbols(formatAngka);
        hasil = toRupiah.format(Double.valueOf(nominal));
        return hasil;
    }
    @Override
    protected void onStart(){
        super.onStart();
    }
//    @Override
//    public void onBackPressed(){
//        super.onBackPressed();
//        finishAffinity();
//    }
    private void loadData(String produk_item) {
        item = gson.fromJson(produk_item, KeranjangItem.class);
//        loadDataSQLite();
//        loadDataInServer(String.valueOf(item.getId()));
        integerNumber.setText(item.getJumlah());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(item.getNama());

        tvHarga.setText(toRupiah(item.getHarga()));

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
}
