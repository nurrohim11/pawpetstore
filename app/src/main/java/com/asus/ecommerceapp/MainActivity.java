package com.asus.ecommerceapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.asus.ecommerceapp.fragment.KonfirmasiGroomingFragment;
import com.asus.ecommerceapp.fragment.KonfirmasiPenitipanFragment;
import com.asus.ecommerceapp.fragment.KonfirmasiProdukFragment;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;
import com.asus.ecommerceapp.Networking.APIClient;
import com.asus.ecommerceapp.Networking.APIInterface;
import com.asus.ecommerceapp.SessionManager.UserSession;
import com.asus.ecommerceapp.activity.CartActivity;
import com.asus.ecommerceapp.activity.GroomingActivity;
import com.asus.ecommerceapp.activity.HistoryActivity;
import com.asus.ecommerceapp.activity.LoginActivity;
import com.asus.ecommerceapp.activity.PenitipanActivity;
import com.asus.ecommerceapp.activity.ProfilActivity;
import com.asus.ecommerceapp.feature.SchedulerTask;
import com.asus.ecommerceapp.fragment.AboutFragment;
import com.asus.ecommerceapp.fragment.HomeFragment;
import com.asus.ecommerceapp.fragment.KonfirmasiFragment;
import com.asus.ecommerceapp.fragment.ProdukFragment;
import com.asus.ecommerceapp.model.Pelanggan.Pelanggan;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    private UserSession session;
    public  static final String TAG="Response Session";
    private SchedulerTask schedulerTask;
    private showPesanTasl pesanTask;
    private TextView tvNamaUser;
    private TextView tvEmailUser;
    public String deviceToken;
    int type=10;
    Bundle bundle;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        schedulerTask = new SchedulerTask(this);
        schedulerTask.createPeriodetask();

        pesanTask = new showPesanTasl(this);
        pesanTask.createPeriodetask();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);
        tvNamaUser= (TextView)view.findViewById(R.id.txt_namauser);
        tvEmailUser=(TextView)view.findViewById(R.id.txt_emailuser);

        hideMenu();
        showHome();
        session = new UserSession(this);
        if(session != null && session.isUserLoggedIn()){
            getUser();
            updateFcm();
        }
        FirebaseMessaging.getInstance().subscribeToTopic("produk");
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(fragment instanceof  HomeFragment)
                super.onBackPressed();
            else
                showHome();
        }
    }

    private void getUser(){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Pelanggan> call = apiInterface.getUserData(session.getUserID());
        call.enqueue(new Callback<Pelanggan>() {
            @Override
            public void onResponse(Call<Pelanggan> call, Response<Pelanggan> response) {
                tvNamaUser.setText(response.body().getNama());
                tvEmailUser.setText(response.body().getEmail());
            }
            @Override
            public void onFailure(Call<Pelanggan> call, Throwable t) {

            }
        });
    }

    private void updateFcm(){
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                deviceToken = instanceIdResult.getToken();
                session.setFcm(deviceToken);
            }
        });
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        JsonObject json = new JsonObject();
        json.addProperty("id_user",session.getUserID());
        json.addProperty("fcm_id",session.getFcm());
        Call<Pelanggan> call = apiInterface.updateFCM(json);
        call.enqueue(new Callback<Pelanggan>() {
            @Override
            public void onResponse(Call<Pelanggan> call, Response<Pelanggan> response) {
            }
            @Override
            public void onFailure(Call<Pelanggan> call, Throwable t) {
//                Toast.makeText(MainActivity.this,t.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, CartActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    Fragment fragment = null;

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id ==R.id.nav_home){
            fragment = new HomeFragment();
        }else if (id == R.id.nav_profil) {
            startActivity(new Intent(this, ProfilActivity.class));
        } else if (id == R.id.nav_produk) {
            fragment = new ProdukFragment();
        } else if (id == R.id.nav_penitipan) {
            startActivity(new Intent(this, PenitipanActivity.class));
        } else if (id == R.id.nav_grooming) {
            startActivity(new Intent(this, GroomingActivity.class));
        } else if (id == R.id.nav_tf) {
            fragment = new KonfirmasiFragment();
        } else if (id == R.id.nav_history) {
            startActivity(new Intent(this, HistoryActivity.class));
        } else if (id == R.id.nav_about) {
            fragment = new AboutFragment();
        } else if (id == R.id.nav_login) {
            startActivity(new Intent(this, LoginActivity.class));
        } else if (id == R.id.nav_logout) {
            UserSession session = new UserSession(this);
            session.logoutUser();
            finish();
        }
        if(fragment != null){
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_place, fragment, fragment.getTag()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showHome(){

        fragment = new HomeFragment();
        if(fragment != null){
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_place, fragment, fragment.getTag()).commit();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(">>>>>",String.valueOf(requestCode));
        if(requestCode==PenitipanActivity.PICK_PENITIPAN) {
            showConfirmation(2);
        }else if(requestCode == KonfirmasiProdukFragment.PICK_CONFIRMATION_PRODUK){
            if(resultCode == RESULT_OK)
                showHome();
        }else if(requestCode == KonfirmasiGroomingFragment.PICK_CONFIRMATION_GROMING){
            if(resultCode == RESULT_OK)
                showHome();
        }else if(requestCode == KonfirmasiPenitipanFragment.PICK_CONFIRMATION_PENITIPAN){
            if(resultCode == RESULT_OK)
                showHome();
        }else{
            showHome();
        }
    }

    private void showConfirmation(int type) {
        fragment = new KonfirmasiFragment();
        if (fragment != null) {
            Bundle arguments = new Bundle();
            if(arguments != null){
                arguments.putInt("type", type);
                fragment.setArguments(arguments);
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_place, fragment, fragment.getTag()).commit();
            }
        }
    }

    public void hideMenu(){
        session = new UserSession(this);
        Log.d(TAG, "Session: "+session.getEmail());
        Menu nav_Menu = navigationView.getMenu();
        if (session != null && session.getUserID() != null){
            nav_Menu.findItem(R.id.nav_login).setVisible(false);
        }else {
            nav_Menu.findItem(R.id.nav_profil).setVisible(false);
            nav_Menu.findItem(R.id.nav_penitipan).setVisible(false);
            nav_Menu.findItem(R.id.nav_grooming).setVisible(false);
            nav_Menu.findItem(R.id.nav_history).setVisible(false);
            nav_Menu.findItem(R.id.nav_tf).setVisible(false);
            nav_Menu.findItem(R.id.nav_logout).setVisible(false);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        type=-1;
    }

}
