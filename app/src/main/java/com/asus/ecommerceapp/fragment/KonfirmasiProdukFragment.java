package com.asus.ecommerceapp.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.asus.ecommerceapp.Networking.APIClient;
import com.asus.ecommerceapp.Networking.APIInterface;
import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.SessionManager.UserSession;
import com.asus.ecommerceapp.adapter.KonfirmasiProdukAdapter;
import com.asus.ecommerceapp.model.produk.HistoryProdukResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.LinearLayout.VERTICAL;

/**
 * A simple {@link Fragment} subclass.
 */
public class KonfirmasiProdukFragment extends Fragment {
    private Context context;
    @BindView(R.id.rv_korder)
    RecyclerView rvPenitipan;
    private KonfirmasiProdukAdapter adapter;
    private ProgressDialog mRegProgres;
    UserSession session;
    Call<HistoryProdukResponse> call;
    private APIInterface apiInterface;
    private APIClient apiClient = new APIClient();
    Activity activity;
    public static final int PICK_CONFIRMATION_PRODUK =1001;

    public KonfirmasiProdukFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_konfirmasi_produk, container, false);
        context = activity;
        ButterKnife.bind(this, view);
        setuplist();
        loadData();
        return  view;
    }
    private void setuplist() {
        adapter = new KonfirmasiProdukAdapter();
        rvPenitipan.setLayoutManager(new LinearLayoutManager(context));
        rvPenitipan.setAdapter(adapter);
        rvPenitipan.addItemDecoration(new DividerItemDecoration(getContext(), VERTICAL));
        rvPenitipan.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
    private void loadData(){
        mRegProgres = new ProgressDialog(getContext());
        session = new UserSession(getContext());
        mRegProgres.setTitle("Getting Data");
        mRegProgres.setMessage("Please Wait...");
        mRegProgres.setCanceledOnTouchOutside(false);
        apiInterface =APIClient.getClient().create(APIInterface.class);
        call = apiInterface.getHistoryProdukPending(session.getUserID(),"PENDING");
        call.enqueue(new Callback<HistoryProdukResponse>() {
            @Override
            public void onResponse(Call<HistoryProdukResponse> call, Response<HistoryProdukResponse> response) {
                if (response.isSuccessful()){
                    adapter.updateData(response.body().getHistoryProduk());
                    Log.d(">>>>", "Response" +response.body().getHistoryProduk());
                    //mSwipeRefreshLayout.setRefreshing(false);
                    mRegProgres.dismiss();
                }else {
                    Toast.makeText(getContext(),"Getting Workshop Failed", Toast.LENGTH_SHORT);
                    mRegProgres.dismiss();

                }
            }

            @Override
            public void onFailure(Call<HistoryProdukResponse> call, Throwable t) {

            }
        });
    }
}
