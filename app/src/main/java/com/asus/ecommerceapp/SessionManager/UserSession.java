package com.asus.ecommerceapp.SessionManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.asus.ecommerceapp.MainActivity;
import com.asus.ecommerceapp.activity.LoginActivity;


public class UserSession {
    SharedPreferences preferences;
    Editor editor;
    Context mContext;
    public static final String PREFER_NAME = "PrefBengkel";
    public static final String IS_USER_LOGIN = "LoginStatus";
    public static final String KEY_USER_ID = "id_user";
    public static final String KEY_ALAMAT = "alamat";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_FIREBASE = "firebase";


    public static final String SP_USER_ID = "id_user";
    public static final String SP_USERNAME = "username";
    public static final String SP_SUDAH_LOGIN = "spSudahLogin";
    private String KEY_EMAIL="email";

    public UserSession(Context mContext) {
        this.mContext = mContext;
        preferences = mContext.getSharedPreferences(PREFER_NAME,0);
        editor = preferences.edit();
    }
    public void setLoginSession(String userid, String email, String alamat, String password){
        editor.putBoolean(IS_USER_LOGIN,true);
        editor.putString(KEY_USER_ID, userid);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_ALAMAT, alamat);
        editor.putString(KEY_PASSWORD, password);
        editor.commit();
    }

    public void setFcm(String fcm){
        editor.putString(KEY_FIREBASE,fcm);
        editor.commit();
    }

    public void checkLogin(){
        if (!this.isUserLoggedIn()){
            Intent i = new Intent(mContext, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i);
        }else {
            Intent i = new Intent(mContext, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i);
        }
    }
    public void logoutUser(){
        editor.clear();
        editor.commit();
        mContext.startActivity(new Intent(mContext, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
    }
    public String getUserID(){
        return preferences.getString(KEY_USER_ID,null);
    }
    public String getEmail(){
        return preferences.getString(KEY_EMAIL,null);
    }
    public String getAlamat(){
        return preferences.getString(KEY_ALAMAT,null);
    }
    public String getPassword(){
        return preferences.getString(KEY_PASSWORD,null);
    }
    public String getFcm(){return  preferences.getString(KEY_FIREBASE, null);}
    // Check for login
    public boolean isUserLoggedIn(){
        return preferences.getBoolean(IS_USER_LOGIN, false);
    }

}

