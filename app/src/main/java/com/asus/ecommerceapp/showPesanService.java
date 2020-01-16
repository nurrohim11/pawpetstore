package com.asus.ecommerceapp;

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
import com.asus.ecommerceapp.Networking.APIClient;
import com.asus.ecommerceapp.Networking.APIInterface;
import com.asus.ecommerceapp.SessionManager.UserSession;
import com.asus.ecommerceapp.model.localnotification.NotifItem;
import com.asus.ecommerceapp.model.localnotification.ResponseNotif;
import com.asus.ecommerceapp.model.produk.ProdukItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class showPesanService extends GcmTaskService {

    private APIClient apiClient = new APIClient();
    private ArrayList<ProdukItem> list = new ArrayList<>();
    private Call<ResponseNotif> apiCall;
    private APIInterface apiInterface;
    public static String TAG_SERVICE="pesan_lokal";
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
        UserSession session = new UserSession(getApplicationContext());
        apiInterface = apiClient.getClient().create(APIInterface.class);
        apiCall = apiInterface.getLstPesan(session.getUserID());
        apiCall.enqueue(new Callback<ResponseNotif>() {
            @Override
            public void onResponse(Call<ResponseNotif> call, Response<ResponseNotif> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "Response" +response.body().getNotif());
                    List<NotifItem> items= response.body().getNotif();
                    int index = new Random().nextInt(items.size());

                    NotifItem produkItem= items.get(index);
                    String title = items.get(index).getNoOrder();
                    String message = items.get(index).getPesan();
                    int notifId = 200;
                    showNotif(getApplicationContext(), title, message, notifId, produkItem);
                }else {
                    loadFailed();
                }
            }
            @Override
            public void onFailure(Call<ResponseNotif> call, Throwable t) {
                //Toast.makeText(getActivity(),"Getting Workshop Failed", Toast.LENGTH_SHORT);
            }
        });
    }
    private void showNotif(Context context, String title, String message, int notifId, NotifItem notifItem){
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(context, notifId, i, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.logo_top)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000,1000,1000,1000,1000})
                .setSound(uri)
                .addAction(android.R.drawable.ic_popup_reminder,"NAVIGATE",pendingIntent);
        notificationManager.notify(notifId, builder.build());
    }
    private void loadFailed() {
        Toast.makeText(this, "Error Bro :(",Toast.LENGTH_SHORT).show();
    }
}
