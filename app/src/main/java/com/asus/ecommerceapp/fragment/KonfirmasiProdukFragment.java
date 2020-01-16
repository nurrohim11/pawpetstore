package com.asus.ecommerceapp.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import static android.support.constraint.Constraints.TAG;
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
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
//        rvGrooming.setLayoutManager(mLayoutManager);
//        rvGrooming.addItemDecoration(new ProdukFragment.GridSpacingItemDecoration(2, dpToPx(10), true));
//        rvGrooming.setItemAnima
//        mRegProgres = new ProgressDialog(getActivity());tor(new DefaultItemAnimator());
        rvPenitipan.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
    private void loadData(){
        mRegProgres = new ProgressDialog(getActivity());
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
                    Log.d(TAG, "Response" +response.body().getHistoryProduk());
                    //mSwipeRefreshLayout.setRefreshing(false);
                    mRegProgres.dismiss();
                }else {
                    Toast.makeText(getActivity(),"Getting Workshop Failed", Toast.LENGTH_SHORT);
                    mRegProgres.dismiss();

                }
            }

            @Override
            public void onFailure(Call<HistoryProdukResponse> call, Throwable t) {

            }
        });
    }
}
