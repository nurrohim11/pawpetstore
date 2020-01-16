package com.asus.ecommerceapp.feature;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.google.gson.Gson;
import com.asus.ecommerceapp.Networking.APIClient;
import com.asus.ecommerceapp.Networking.APIInterface;
import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.activity.DetailActivity;
import com.asus.ecommerceapp.model.produk.ProdukItem;
import com.asus.ecommerceapp.model.produk.ResponseProduk;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class SchedulerService extends GcmTaskService {
    public static String TAG_SERVICE="yang_baru";
    private APIClient apiClient = new APIClient();
    private ArrayList<ProdukItem> list = new ArrayList<>();
    private Call<ResponseProduk> apiCall;
    private  APIInterface apiInterface;
    @Override
    public int onRunTask(TaskParams taskParams) {
        int result =0;
        if (taskParams.getTag().equals(TAG_SERVICE)){
            loadData();
            result= GcmNetworkManager.RESULT_SUCCESS;
        }
        return result;
    }
    private void loadData(){
        apiInterface = apiClient.getClient().create(APIInterface.class);
        apiCall = apiInterface.getBarang();
        apiCall.enqueue(new Callback<ResponseProduk>() {
            @Override
            public void onResponse(Call<ResponseProduk> call, Response<ResponseProduk> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "Response" +response.body().getProduk());
                    List<ProdukItem> items= response.body().getProduk();
                    int index = new Random().nextInt(items.size());

                    ProdukItem produkItem= items.get(index);
                    String title = items.get(index).getNama();
                    String message = items.get(index).getDeskripsi();
                    String harga = items.get(index).getHarga();
                    int notifId = 200;
                    showNotif(getApplicationContext(), title, message,harga, notifId, produkItem);
                }else {
                    loadFailed();
                }
            }
            @Override
            public void onFailure(Call<ResponseProduk> call, Throwable t) {
                //Toast.makeText(getActivity(),"Getting Workshop Failed", Toast.LENGTH_SHORT);
            }
        });
    }
    private void showNotif(Context context, String  title, String message,String harga, int notifId, ProdukItem item){
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent= new Intent(context, DetailActivity.class);
        intent.putExtra(DetailActivity.PRODUK_ITEM, new Gson().toJson(item));
        PendingIntent pendingIntent= PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setSubText(harga)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000,1000,1000,1000,1000})
                .setSound(uri);
        notificationManager.notify(notifId, builder.build());
    }

    private void loadFailed() {
        Toast.makeText(this, "Error Bro :(",Toast.LENGTH_SHORT).show();
    }
}
