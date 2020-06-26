package com.asus.ecommerceapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.asus.ecommerceapp.Networking.APIClient;
import com.asus.ecommerceapp.Networking.APIInterface;
import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.adapter.ItemOrderProdukAdapter;
import com.asus.ecommerceapp.model.produk.HistoryProdukItem;
import com.asus.ecommerceapp.model.produk.ItemItem;
import com.asus.ecommerceapp.model.produk.ItemOrderResponse;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.LinearLayout.VERTICAL;

public class DetailOrderActivity extends AppCompatActivity {

    public static final String DETAIL_ITEM = "detail";
    public static final String TAG = "Response";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Gson gson = new Gson();
    private HistoryProdukItem item;

    @BindView(R.id.no_order)
    TextView tvNoOrder;
    @BindView(R.id.tgl)
    TextView tvTgl;
    @BindView(R.id.alamat)
    TextView tvAlamat;
    @BindView(R.id.tv_ongkir)
    TextView tvOngkir;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.rv_item)
    RecyclerView rvItem;
    private ItemOrderProdukAdapter adapter;

    private APIClient apiClient = new APIClient();
    private ArrayList<ItemItem> list = new ArrayList<>();
    private Call<ItemOrderResponse> apiCall;
    private APIInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail Order Produk");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    protected void onStart(){
        super.onStart();
        setuplist();
        String detail = getIntent().getStringExtra(DETAIL_ITEM);
        loadData(detail);
    }

    private void setuplist() {
        adapter = new ItemOrderProdukAdapter();
        rvItem.setLayoutManager(new LinearLayoutManager(this));
        rvItem.setAdapter(adapter);
        rvItem.addItemDecoration(new DividerItemDecoration(this, VERTICAL));
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
//        rvGrooming.setLayoutManager(mLayoutManager);
//        rvGrooming.addItemDecoration(new ProdukFragment.GridSpacingItemDecoration(2, dpToPx(10), true));
//        rvGrooming.setItemAnima
//        mRegProgres = new ProgressDialog(getActivity());tor(new DefaultItemAnimator());
        rvItem.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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

    private void loadData(String detail){
        item = gson.fromJson(detail, HistoryProdukItem.class);
        tvNoOrder.setText(item.getNoOrder());
        tvAlamat.setText(item.getAlamatPengiriman());
        tvOngkir.setText(toRupiah(item.getOngkir()));
        String total_a =item.getTotal();
        tvTotal.setText(toRupiah(total_a));
        tvTgl.setText(item.getTgl());
        String no_order = item.getNoOrder();
        apiInterface =APIClient.getClient().create(APIInterface.class);
        apiCall = apiInterface.getItemOrder(no_order);
        apiCall.enqueue(new Callback<ItemOrderResponse>() {
            @Override
            public void onResponse(Call<ItemOrderResponse> call, Response<ItemOrderResponse> response) {
                if (response.isSuccessful()){
                    adapter.updateData(response.body().getItem());
                }else {
                    Toast.makeText(DetailOrderActivity.this,"Getting Workshop Failed", Toast.LENGTH_SHORT);

                }
            }

            @Override
            public void onFailure(Call<ItemOrderResponse> call, Throwable t) {

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
