package com.asus.ecommerceapp.feature;

import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.gcm.PeriodicTask;

public class SchedulerTask {
    private GcmNetworkManager gcmNetworkManager;
    public  SchedulerTask(Context context){
        gcmNetworkManager=  GcmNetworkManager.getInstance(context);
    }
    public void createPeriodetask(){
        Task task= new PeriodicTask.Builder()
                .setService(SchedulerService.class)
                .setPeriod(3*50*1000)
                .setFlex(10)
                .setTag(SchedulerService.TAG_SERVICE)
                .setPersisted(true)
                .build();
        gcmNetworkManager.schedule(task);
    }
    public void cancelPeriodeTask(){
        if (gcmNetworkManager != null){
            gcmNetworkManager.cancelTask(SchedulerService.TAG_SERVICE, SchedulerService.class);
        }
    }
}
