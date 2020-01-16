package com.asus.ecommerceapp.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.asus.ecommerceapp.BuildConfig;
import com.asus.ecommerceapp.Networking.APIInterface;
import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.activity.GroomingActivity;
import com.asus.ecommerceapp.activity.PenitipanActivity;
import com.asus.ecommerceapp.adapter.SliderAdapter;
import com.asus.ecommerceapp.model.banner.BannerItem;
import com.asus.ecommerceapp.model.banner.BannerResponse;
import com.asus.ecommerceapp.utils.SliderLayout;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    public  static final String AppConstant="homefragment";
    SliderLayout mDemoSlider;
    private Context context;
    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    private static int currentPage = 0;
    private static final Integer[] XMEN= {R.drawable.beast,R.drawable.charles,R.drawable.magneto,R.drawable.wolverine};
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();
    Activity activity;
    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        context =activity;
        ButterKnife.bind(this, view);
        init();
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ImageView imgGrooming = (ImageView) view.findViewById(R.id.img_grooming);
        imgGrooming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GroomingActivity.class));
            }
        });
        ImageView imgNitip =(ImageView) view.findViewById(R.id.img_nitip);
        imgNitip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PenitipanActivity.class));
            }
        });
        ImageView imgProduk =(ImageView)view.findViewById(R.id.img_produk);
        imgProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProdukFragment newGamefragment = new ProdukFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_place, newGamefragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
    private void init() {
        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //Defining retrofit api service
        APIInterface service = retrofit.create(APIInterface.class);

        //creating an api call
        Call<BannerResponse> call = service.getBanner();
        Log.d("baner>>>>>>>>>>> ", String.valueOf(call));
        call.enqueue(new Callback<BannerResponse>() {
            @Override
            public void onResponse(Call<BannerResponse> call, Response<BannerResponse> response) {
                ArrayList<BannerItem> heros = response.body().getBanner();
                Log.d("baner>>>>>>>>>>> ",String.valueOf(response.body().getBanner()));
                viewPager.setAdapter(new SliderAdapter(getContext(), heros));
                indicator.setViewPager(viewPager);

                // Auto start of viewpager
                final Handler handler = new Handler();
                final Runnable Update = new Runnable() {
                    public void run() {
                        if (currentPage == XMEN.length) {
                            currentPage = 0;
                        }
                        viewPager.setCurrentItem(currentPage++, true);
                    }
                };
                Timer swipeTimer = new Timer();
                swipeTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(Update);
                    }
                }, 2500, 2500);
            }

            @Override
            public void onFailure(Call<BannerResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error "+t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
