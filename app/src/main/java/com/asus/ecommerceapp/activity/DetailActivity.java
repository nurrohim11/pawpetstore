package com.asus.ecommerceapp.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.asus.ecommerceapp.BuildConfig;
import com.asus.ecommerceapp.Networking.APIClient;
import com.asus.ecommerceapp.Networking.APIInterface;
import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.SessionManager.UserSession;
import com.asus.ecommerceapp.model.Login.RequestResponse;
import com.asus.ecommerceapp.model.keranjang.Cart;
import com.asus.ecommerceapp.model.produk.ProdukItem;
import com.asus.ecommerceapp.model.produkdetail.Details;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    private ProgressDialog mRegProgres;
    public static final String PRODUK_ITEM = "produk_item";
    public static final String TAG = "Response";

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsing_toolbar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.img_backdrop)
    ImageView img_backdrop;

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.harga)
    TextView tvHarga;
    @BindView(R.id.deskripsi)
    TextView tvDeskripsi;
    @BindView(R.id.kategori)
    TextView tvKategori;
    @BindView(R.id.btn_cart)
    Button btnCart;
    private Call<Details> apiCall;
    private APIClient apiClient = new APIClient();
    private Gson gson = new Gson();
    private ProdukItem item;

    private Call<RequestResponse> call;
    UserSession session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsing_toolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        mRegProgres = new ProgressDialog(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onStart(){
        super.onStart();

        String produk_item = getIntent().getStringExtra(PRODUK_ITEM);
        loadData(produk_item);
    }
    @Override
    protected void onResume(){
        super.onResume();

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
    private void loadData(String produk_item) {
        item = gson.fromJson(produk_item, ProdukItem.class);
        mRegProgres = new ProgressDialog(this);

        getSupportActionBar().setTitle(item.getNama());
        tv_title.setText(item.getNama());

        session = new UserSession(this);
        Glide.with(DetailActivity.this)
                .load(BuildConfig.BASE_URL_IMG + item.getGambar())
                .into(img_backdrop);

        tvDeskripsi.setText(item.getDeskripsi());
        tvKategori.setText(item.getKategori());
        tvHarga.setText(toRupiah(item.getHarga()));
        if (session != null && session.getUserID()!= null) {
            btnCart.setOnClickListener(new View.OnClickListener() {
                String id_produk = item.getId();
                String jml = "1";
                String id_pelanggan = session.getUserID();
                String rego = item.getHarga();
                @Override
                public void onClick(View v) {

                    mRegProgres.setTitle("Getting Data");
                    mRegProgres.setMessage("Please Wait...");
                    mRegProgres.setCanceledOnTouchOutside(false);
                    mRegProgres.show();
                    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                    call = apiInterface.addToCart(new Cart(id_produk, id_pelanggan, jml, rego));
                    call.enqueue(new Callback<RequestResponse>() {
                        @Override
                        public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
                            if (response.body().getSuccess() == true) {
                                mRegProgres.dismiss();
                                Toast.makeText(DetailActivity.this, response.body().getInfo(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(DetailActivity.this, response.body().getInfo(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<RequestResponse> call, Throwable t) {
                            Toast.makeText(DetailActivity.this, "Error menghubugnakn ke API", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            });
        }else{
            btnCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(DetailActivity.this,"Untuk memasukkan barang ke keranjang anda harus login dulu", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

}
