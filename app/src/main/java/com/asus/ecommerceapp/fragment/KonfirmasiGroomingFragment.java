package com.asus.ecommerceapp.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
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
import com.asus.ecommerceapp.adapter.KonfirmasiGroomingHistory;
import com.asus.ecommerceapp.model.grooming.HistoryGroomingResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class KonfirmasiGroomingFragment extends Fragment {


    private Context context;
    @BindView(R.id.rv_kfgrooming)
    RecyclerView rvGrooming;
    private KonfirmasiGroomingHistory adapter;
    private ProgressDialog mRegProgres;
    UserSession session;
    Call<HistoryGroomingResponse> call;
    private APIInterface apiInterface;
    private APIClient apiClient = new APIClient();
    Activity activity;
    public static final int PICK_CONFIRMATION_GROMING =2001;


    public KonfirmasiGroomingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_konfirmasi_grooming, container, false);
        context = activity;
        ButterKnife.bind(this, view);
        setuplist();
        loadData();
        return  view;
    }

    private void setuplist() {
        adapter = new KonfirmasiGroomingHistory();
        rvGrooming.setLayoutManager(new LinearLayoutManager(context));
        rvGrooming.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData(){
        mRegProgres = new ProgressDialog(getContext());
        session = new UserSession(getContext());
        mRegProgres.setTitle("Getting Data");
        mRegProgres.setMessage("Please Wait...");
        mRegProgres.setCanceledOnTouchOutside(false);
        apiInterface =APIClient.getClient().create(APIInterface.class);
        call = apiInterface.getHistoryGroomingPending(session.getUserID(),"PENDING");
        call.enqueue(new Callback<HistoryGroomingResponse>() {
            @Override
            public void onResponse(Call<HistoryGroomingResponse> call, Response<HistoryGroomingResponse> response) {
                if (response.isSuccessful()){
                    adapter.updateData(response.body().getHistoryGrooming());
                    Log.d(">>>>", "Response" +response.body().getHistoryGrooming());
                    mRegProgres.dismiss();

                }else {
                    Toast.makeText(getContext(),"Getting Workshop Failed", Toast.LENGTH_SHORT);
                    mRegProgres.dismiss();

                }
            }

            @Override
            public void onFailure(Call<HistoryGroomingResponse> call, Throwable t) {

            }
        });
    }
}
