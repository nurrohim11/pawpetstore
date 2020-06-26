package com.asus.ecommerceapp.fragment;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.asus.ecommerceapp.Networking.APIClient;
import com.asus.ecommerceapp.Networking.APIInterface;
import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.model.about.ResponseAbout;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    @BindView(R.id.tv_judul)
    TextView tvJudul;
    @BindView(R.id.tv_deskripsi)
    TextView tvDeskripsi;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    private Context context;

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this,view);
        init();
        return  view;
    }

    private void init(){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ResponseAbout> call = apiInterface.about();
        call.enqueue(new Callback<ResponseAbout>() {
            @Override
            public void onResponse(Call<ResponseAbout> call, Response<ResponseAbout> response) {
                ResponseAbout about = response.body();
                tvJudul.setText(about != null ? about.getJudul() : null);
                assert about != null;
                tvVersion.setText(about.getVersion());
                tvDeskripsi.setText(about.getDeskripsi());
            }

            @Override
            public void onFailure(Call<ResponseAbout> call, Throwable t) {
                Toast.makeText(getActivity(),t.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
