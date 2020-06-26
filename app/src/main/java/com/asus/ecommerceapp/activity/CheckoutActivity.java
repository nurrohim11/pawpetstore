package com.asus.ecommerceapp.activity;

import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.asus.ecommerceapp.MainActivity;
import com.asus.ecommerceapp.Networking.APIClient;
import com.asus.ecommerceapp.Networking.APIInterface;
import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.SessionManager.UserSession;
import com.asus.ecommerceapp.model.Login.RequestResponse;
import com.asus.ecommerceapp.model.Pelanggan.Pelanggan;
import com.asus.ecommerceapp.model.produk.Checkout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {
    UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Final Products");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle b = getIntent().getExtras();
        TextView tvTotal = (TextView)findViewById(R.id.tv_ctotal);
        tvTotal.setText(b.getString("total"));
        session = new UserSession(this);
        getInitData();

        Button btnSelesai = (Button) findViewById(R.id.btnSelesai);
        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = session.getUserID();
                APIInterface apiInterface1 = APIClient.getClient().create(APIInterface.class);
                Call<RequestResponse> call1 =apiInterface1.checkout(new Checkout(id));
                call1.enqueue(new Callback<RequestResponse>() {
                    @Override
                    public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
                        if (response.body().getSuccess() == true){
                            Toast.makeText(CheckoutActivity.this,"Checkout telah berhasil. Silahkan lanjut ke transfer conformation",Toast.LENGTH_SHORT).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent returnIntent = new Intent();
                                    setResult(Activity.RESULT_OK,returnIntent);
                                    finish();
                                }
                            },1000);
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
    private void getInitData(){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Pelanggan> call = apiInterface.getUserData(session.getUserID());
        call.enqueue(new Callback<Pelanggan>() {
            @Override
            public void onResponse(Call<Pelanggan> call, Response<Pelanggan> response) {
                TextView tvAlamat= (TextView) findViewById(R.id.tv_alamat);
                tvAlamat.setText(response.body().getAlamat());
            }

            @Override
            public void onFailure(Call<Pelanggan> call, Throwable t) {

            }
        });

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
