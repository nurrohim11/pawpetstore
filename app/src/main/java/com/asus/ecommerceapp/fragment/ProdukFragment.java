package com.asus.ecommerceapp.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.asus.ecommerceapp.Networking.APIClient;
import com.asus.ecommerceapp.Networking.APIInterface;
import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.adapter.ProdukAdapter;
import com.asus.ecommerceapp.model.produk.ProdukItem;
import com.asus.ecommerceapp.model.produk.ResponseProduk;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProdukFragment extends Fragment {
    private Context context;
    @BindView(R.id.recyclerview2)
    RecyclerView rv_produk;
    private ProdukAdapter adapter;

    private APIClient apiClient = new APIClient();
    private ArrayList<ProdukItem> list = new ArrayList<>();
    private Call<ResponseProduk> apiCall;
    private  APIInterface apiInterface;
    Activity activity;
    public ProdukFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_produk,container,false);
        context = activity;
        ButterKnife.bind(this, view);
        setuplist();
        loadData();
       // if (savedInstanceState == null){
      //      loadData();
            // Log.d(TAG,"data" +resultsItems);
        //}
        return view;
    }

    private void setuplist() {
        adapter = new ProdukAdapter();
//        rv_produk.setLayoutManager(new LinearLayoutManager(context));
//        rv_produk.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
        rv_produk.setLayoutManager(mLayoutManager);
        rv_produk.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        rv_produk.setItemAnimator(new DefaultItemAnimator());
        rv_produk.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void loadData(){
        apiInterface = apiClient.getClient().create(APIInterface.class);
        apiCall = apiInterface.getBarang();
        apiCall.enqueue(new Callback<ResponseProduk>() {
            @Override
            public void onResponse(Call<ResponseProduk> call, Response<ResponseProduk> response) {
                if (response.isSuccessful()){
                    adapter.updateData(response.body().getProduk());
                    Log.d(">>>>", "Response" +response.body().getProduk());
                    //mSwipeRefreshLayout.setRefreshing(false);
//                    Toast.makeText(getActivity(),"response" +response.body().getProduk(), Toast.LENGTH_SHORT);
                }else {
                    Toast.makeText(getActivity(),"Getting Product Failed", Toast.LENGTH_SHORT);
                }
            }
            @Override
            public void onFailure(Call<ResponseProduk> call, Throwable t) {
                //Toast.makeText(getActivity(),"Getting Workshop Failed", Toast.LENGTH_SHORT);
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (apiCall != null) apiCall.cancel();
    }
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
