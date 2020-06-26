package com.asus.ecommerceapp.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.asus.ecommerceapp.BuildConfig;
import com.asus.ecommerceapp.Networking.APIClient;
import com.asus.ecommerceapp.Networking.APIInterface;
import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.model.keranjang.KeranjangItem;
import com.asus.ecommerceapp.model.keranjang.ResponseHapusKeranjang;
import com.asus.ecommerceapp.utils.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    List<KeranjangItem> list;
    Context context;
    private Util util;
    Call<ResponseHapusKeranjang> itemCall;
    CartAdapterCallback CAdapter;

    public CartAdapter(List<KeranjangItem> list, Context context, CartAdapterCallback cartAdapterCallback) {
        this.list = list;
        this.context =context;
        this.CAdapter=cartAdapterCallback;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_keranjang,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return  viewHolder;
    }
    public void updateData(List<KeranjangItem> items){
        list.addAll(items);
        notifyDataSetChanged();
    }

    public void replaceAll(List<KeranjangItem> items){
        list.clear();
        list=items;
    }
    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, final int i) {
        util = new Util();
        final KeranjangItem item = list.get(i);

        final String id_keranjang = item.getIdKeranjang();
        final String jumlah = item.getJumlah();
        holder.tvTitle.setText(item.getNama());
        holder.tvHarga.setText(util.toRupiah(item.getSubtotal()));
        holder.harga.setText(util.toRupiah(item.getHarga()));
        holder.tvQty.setText(item.getJumlah());
        Glide.with(context)
                .load(BuildConfig.BASE_URL_IMG+ item.getGambar())
                .apply(new RequestOptions()
                        .centerCrop())
                .into(holder.imgGambar);
        holder.imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context,id_keranjang, Toast.LENGTH_SHORT).show();
                APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                itemCall = apiInterface.deleteKeranjang(id_keranjang);
                itemCall.enqueue(new Callback<ResponseHapusKeranjang>() {
                    @Override
                    public void onResponse(Call<ResponseHapusKeranjang> call, Response<ResponseHapusKeranjang> response) {
                        if(!response.body().isStatus()){
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            list.remove(item);
                            notifyDataSetChanged();

                        }else{
                            Toast.makeText(context,"Data Gagal di Hapus",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseHapusKeranjang> call, Throwable t) {

                    }
                });

            }
        });
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, id_keranjang, Toast.LENGTH_SHORT).show();
                CAdapter.onEditDialog(id_keranjang, jumlah);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.img_gambar)
        ImageView imgGambar;
        @BindView(R.id.tv_harga) TextView tvHarga;
        @BindView(R.id.harga) TextView harga;
        @BindView(R.id.quantity) TextView tvQty;
        @BindView(R.id.remove)
        ImageButton imgRemove;
        @BindView(R.id.img_edit) ImageButton imgEdit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public interface CartAdapterCallback {
        void onEditDialog(String id, String jumlah);
    }
}
