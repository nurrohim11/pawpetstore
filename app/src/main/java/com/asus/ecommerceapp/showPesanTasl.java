package com.asus.ecommerceapp;

import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;

public class showPesanTasl {
    private GcmNetworkManager gcmNetworkManager;
    public showPesanTasl(Context context){
        gcmNetworkManager=  GcmNetworkManager.getInstance(context);
    }
    public void createPeriodetask(){
        Task task= new PeriodicTask.Builder()
                .setService(showPesanService.class)
                .setPeriod(3*50*1000)
                .setFlex(10)
                .setTag(showPesanService.TAG_SERVICE)
                .setPersisted(true)
                .build();
        gcmNetworkManager.schedule(task);
    }
    public void cancelPeriodeTask(){
        if (gcmNetworkManager != null){
            gcmNetworkManager.cancelTask(showPesanService.TAG_SERVICE, showPesanService.class);
        }
    }
}
