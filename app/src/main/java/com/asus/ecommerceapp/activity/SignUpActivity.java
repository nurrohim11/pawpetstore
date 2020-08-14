package com.asus.ecommerceapp.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.asus.ecommerceapp.Networking.APIClient;
import com.asus.ecommerceapp.Networking.APIInterface;
import com.asus.ecommerceapp.Networking.volley.ApiVolley;
import com.asus.ecommerceapp.Networking.volley.AppRequestCallback;
import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.model.Login.RequestResponse;
import com.asus.ecommerceapp.model.Pelanggan.Pelanggan;
import com.asus.ecommerceapp.utils.DialogFactory;
import com.asus.ecommerceapp.utils.JSONBuilder;
import com.asus.ecommerceapp.utils.SearchableSpinnerDialog.SearchableSpinnerDialogAdapter;
import com.asus.ecommerceapp.utils.SearchableSpinnerDialog.SimpleObjectModel;
import com.asus.ecommerceapp.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.asus.ecommerceapp.utils.Util.isHasLength;
import static com.asus.ecommerceapp.utils.Util.isHasLowerCase;
import static com.asus.ecommerceapp.utils.Util.isHasSymbol;
import static com.asus.ecommerceapp.utils.Util.isHasUpperCase;
import static com.asus.ecommerceapp.utils.Util.isValidEmail;

public class SignUpActivity extends AppCompatActivity {
    private final String TAG ="Sign Up Activity";

    @BindView(R.id.txt_nama_depan)
    EditText txtNamaDepan;

    @BindView(R.id.txt_nama_belakang)
    EditText txtNamaBelakang;

    @BindView(R.id.txt_email)
    EditText txtEmail;

    @BindView(R.id.txt_alamat)
    EditText txtAlamat;

    @BindView(R.id.txt_password)
    EditText txtPassword;

    @BindView(R.id.txt_hp)
    EditText txtHp;

    @BindView(R.id.txt_provinsi)
    TextView txtProvinsi;

    @BindView(R.id.txt_kota)
    TextView txtKota;

    @BindView(R.id.txt_kecamatan)
    TextView txtKecamatan;

    @BindView(R.id.txt_kodepos)
    TextView txtKodePos;

    private ProgressDialog mRegProgres;
    @BindView(R.id.btn_signup)
    Button btnSignUp;
    private Unbinder unbinder;

    private List<SimpleObjectModel> listProvinsi = new ArrayList<>(),listKota = new ArrayList<>(),listKecamatan = new ArrayList<>();
    private List<SimpleObjectModel> listChooser = new ArrayList<>();
    private String selectedKota = "", selectedKecamatan = "", selectedProvinsi = "";
    private SearchableSpinnerDialogAdapter dialogAdapter;

    private APIClient apiClient = new APIClient();
    private Call<RequestResponse> apiCall;
    private  APIInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        unbinder = ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRegProgres = new ProgressDialog(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fieldCheck() == true ){
                    mRegProgres.setTitle("Processing....");
                    mRegProgres.setMessage("Please Wait...");
                    mRegProgres.setCanceledOnTouchOutside(false);
                    mRegProgres.show();
                    String nama = txtNamaDepan.getText().toString().trim()+" "+txtNamaBelakang.getText().toString().trim();
                    String alamat = txtAlamat.getText().toString().trim();
                    String no_hp = txtHp.getText().toString().trim();
                    String email = txtEmail.getText().toString().trim();
                    String password = txtPassword.getText().toString().trim();
                    String kodepos = txtKodePos.getText().toString().trim();

                    JSONBuilder body = new JSONBuilder();
                    body.add("nama", nama);
                    body.add("alamat", alamat);
                    body.add("email", email);
                    body.add("no_hp", no_hp);
                    body.add("id_provinsi", selectedProvinsi);
                    body.add("id_kota", selectedKota);
                    body.add("id_kecamatan", selectedKecamatan);
                    body.add("kode_pos", kodepos);
                    body.add("password", password);

                    new ApiVolley(SignUpActivity.this, body.create(), "POST",
                            APIInterface.registerUser, new AppRequestCallback(new AppRequestCallback.ResponseListener() {
                        @Override
                        public void onSuccess(String response, String message) {
                            mRegProgres.dismiss();
                            Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onEmpty(String message) {
                            mRegProgres.dismiss();
                            Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFail(String message) {
                            mRegProgres.dismiss();
                            Toast.makeText(SignUpActivity.this, "Terjadi kesalahan data", Toast.LENGTH_SHORT).show();
                        }
                    }));
                }
            }
        });
        TextView tvSignUp = (TextView)findViewById(R.id.login);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
        initData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Boolean fieldCheck(){
        Boolean fieldCheck = true;

        if(TextUtils.isEmpty(txtNamaDepan.getText())){
            txtNamaDepan.setError("Insert this Field!");
            txtNamaDepan.setFocusable(true);
            fieldCheck = false;
        }
        if(TextUtils.isEmpty(txtNamaBelakang.getText())){
            txtNamaBelakang.setError("Insert this Field!");
            txtNamaBelakang.setFocusable(true);
            fieldCheck = false;
        }
        if(TextUtils.isEmpty(txtAlamat.getText())){
            txtAlamat.setError("Insert this Field!");
            txtAlamat.setFocusable(true);
            fieldCheck = false;
        }
        if(TextUtils.isEmpty(txtHp.getText())){
            txtHp.setError("Insert this Field!");
            txtHp.setFocusable(true);
            fieldCheck = false;
        }
        if(TextUtils.isEmpty(txtEmail.getText()) || !isValidEmail(txtEmail.getText().toString())){
            if(TextUtils.isEmpty(txtEmail.getText())){
                txtEmail.setError("Insert this Field!");
                fieldCheck = false;
            }else if(!isValidEmail(txtEmail.getText().toString())){
                txtEmail.setError("Invalid email format");
                fieldCheck = false;
            }
            txtEmail.setFocusable(true);
        }

        if(selectedProvinsi.equals("")){
            txtProvinsi.setError("You have not selected a province");
            fieldCheck = false;
            txtProvinsi.setFocusable(true);
        }

        if(selectedKota.equals("")){
            txtKota.setError("You have not selected a city");
            fieldCheck = false;
            txtKota.setFocusable(true);
        }

        if(selectedKecamatan.equals("")){
            txtKecamatan.setError("You have not selected a district");
            fieldCheck = false;
            txtKecamatan.setFocusable(true);
        }

        if(TextUtils.isEmpty(txtKodePos.getText())){
            txtKodePos.setError("Insert this field");
            fieldCheck = false;
        }
        if(TextUtils.isEmpty(txtPassword.getText())
                || !isHasLength(txtPassword.getText().toString())
                || !isHasSymbol(txtPassword.getText().toString())
                || !isHasUpperCase(txtPassword.getText().toString())
                || !isHasLowerCase(txtPassword.getText().toString())){
            if(TextUtils.isEmpty(txtPassword.getText())){
                txtPassword.setError("Insert this Field!");
                fieldCheck = false;
            }

            if(!isHasLength(txtPassword.getText().toString())){
                txtPassword.setError("The password must be more than 6 letters long!");
                fieldCheck = false;
            }

            if(!isHasSymbol(txtPassword.getText().toString())){
                txtPassword.setError("The password must consist of one symbol");
                fieldCheck = false;
            }

            if(!isHasUpperCase(txtPassword.getText().toString())){
                txtPassword.setError("The password must consist of one capital letter");
                fieldCheck = false;
            }

            if(!isHasLowerCase(txtPassword.getText().toString())){
                txtPassword.setError("The password must consist of one lowercase letter");
                fieldCheck = false;
            }
            txtPassword.setFocusable(true);
        }


        return fieldCheck;
    }


    private void showDialogProvinsi(){
        final Dialog dialogChooser = DialogFactory.getInstance().createDialog(SignUpActivity.this,
                R.layout.dialog_searchable_spinner, 90, 70);

        listChooser = new ArrayList<>();
        listChooser.addAll(listProvinsi);

        EditText txt_search = dialogChooser.findViewById(R.id.txt_search);
        txt_search.setHint("Cari Provinsi");
        txt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String search = s.toString();
                listChooser.clear();
                for(SimpleObjectModel o : listProvinsi){
                    if(o.getValue().toLowerCase().contains(search.toLowerCase())){
                        listChooser.add(o);
                    }
                }

                dialogAdapter.notifyDataSetChanged();
            }
        });

        RecyclerView rv_items = dialogChooser.findViewById(R.id.rv_items);
        rv_items.setItemAnimator(new DefaultItemAnimator());
        rv_items.setLayoutManager(new LinearLayoutManager(SignUpActivity.this));
        dialogAdapter = new SearchableSpinnerDialogAdapter(SignUpActivity.this,
                listChooser, new SearchableSpinnerDialogAdapter.ChooserListener() {
            @Override
            public void onSelected(String id, String value) {
                txtProvinsi.setText(value);
                selectedProvinsi = id;

                selectedKota = "";
                txtKota.setText("");
                listKota.clear();
                selectedKecamatan = "";
                txtKecamatan.setText("");
                listKecamatan.clear();

                loadKota(id);
                dialogChooser.dismiss();
            }
        });

        rv_items.setAdapter(dialogAdapter);
        dialogChooser.show();
    }

    private void showDialogKota(){
        final Dialog dialogChooser = DialogFactory.getInstance().createDialog(SignUpActivity.this,
                R.layout.dialog_searchable_spinner, 90, 70);

        listChooser = new ArrayList<>();
        listChooser.addAll(listKota);

        EditText txt_search = dialogChooser.findViewById(R.id.txt_search);
        txt_search.setHint("Cari Kota");
        txt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String search = s.toString();
                listChooser.clear();
                for(SimpleObjectModel o : listKota){
                    if(o.getValue().toLowerCase().contains(search.toLowerCase())){
                        listChooser.add(o);
                    }
                }

                dialogAdapter.notifyDataSetChanged();
            }
        });

        RecyclerView rv_items = dialogChooser.findViewById(R.id.rv_items);
        rv_items.setItemAnimator(new DefaultItemAnimator());
        rv_items.setLayoutManager(new LinearLayoutManager(SignUpActivity.this));
        dialogAdapter = new SearchableSpinnerDialogAdapter(SignUpActivity.this,
                listChooser, new SearchableSpinnerDialogAdapter.ChooserListener() {
            @Override
            public void onSelected(String id, String value) {
                txtKota.setText(value);
                selectedKota = id;

                selectedKecamatan = "";
                txtKecamatan.setText("");
                listKecamatan.clear();

                loadKecamatan(id);
                dialogChooser.dismiss();
            }
        });

        rv_items.setAdapter(dialogAdapter);
        dialogChooser.show();
    }

    private void showDialogKecamatan(){
        final Dialog dialogChooser = DialogFactory.getInstance().createDialog(SignUpActivity.this,
                R.layout.dialog_searchable_spinner, 90, 70);

        listChooser = new ArrayList<>();
        listChooser.addAll(listKecamatan);

        EditText txt_search = dialogChooser.findViewById(R.id.txt_search);
        txt_search.setHint("Cari Kecamatan");
        txt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String search = s.toString();
                listChooser.clear();
                for(SimpleObjectModel o : listKecamatan){
                    if(o.getValue().toLowerCase().contains(search.toLowerCase())){
                        listChooser.add(o);
                    }
                }

                dialogAdapter.notifyDataSetChanged();
            }
        });

        RecyclerView rv_items = dialogChooser.findViewById(R.id.rv_items);
        rv_items.setItemAnimator(new DefaultItemAnimator());
        rv_items.setLayoutManager(new LinearLayoutManager(SignUpActivity.this));
        dialogAdapter = new SearchableSpinnerDialogAdapter(SignUpActivity.this,
                listChooser, new SearchableSpinnerDialogAdapter.ChooserListener() {
            @Override
            public void onSelected(String id, String value) {
                txtKecamatan.setText(value);
                selectedKecamatan = id;

                dialogChooser.dismiss();
            }
        });

        rv_items.setAdapter(dialogAdapter);
        dialogChooser.show();
    }

    private void initData() {
        txtProvinsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loadProvinsi();
                showDialogProvinsi();
            }
        });

        txtKota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogKota();
            }
        });

        txtKecamatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogKecamatan();
            }
        });

        loadProvinsi();
    }

    private void loadProvinsi(){

        new ApiVolley(SignUpActivity.this, new JSONObject(), "GET",
                APIInterface.getProvinsi, new AppRequestCallback(new AppRequestCallback.ResponseListener() {
            @Override
            public void onSuccess(String response, String message) {
                try {
                    JSONArray ja = new JSONArray(response);
                    for(int i = 0; i < ja.length(); i ++){
                        JSONObject jo = ja.getJSONObject(i);
                        listProvinsi.add(
                                new SimpleObjectModel(
                                        jo.getString("id")
                                        ,jo.getString("provinsi")
                                )
                        );
                    }
                } catch (JSONException e) {
                    Log.e("json_log", e.getMessage());
                }

                if(listProvinsi.size() > 0){
                    txtProvinsi.setText(listProvinsi.get(0).getValue());
                    selectedProvinsi = listProvinsi.get(0).getId();
                    loadKota(listProvinsi.get(0).getId());
                }
            }

            @Override
            public void onEmpty(String message) {

            }

            @Override
            public void onFail(String message) {

            }
        }));
    }

    private void loadKota(final String idProvinsi){

        JSONBuilder body = new JSONBuilder();
        body.add("id_provinsi", idProvinsi);

        new ApiVolley(SignUpActivity.this, body.create(), "POST",
                APIInterface.getKota, new AppRequestCallback(new AppRequestCallback.ResponseListener() {
            @Override
            public void onSuccess(String response, String message) {
                try {
                    JSONArray ja = new JSONArray(response);
                    for(int i = 0; i < ja.length(); i ++){
                        JSONObject jo = ja.getJSONObject(i);
                        listKota.add(
                                new SimpleObjectModel(
                                        jo.getString("id")
                                        ,jo.getString("kota")
                                )
                        );
                    }
                } catch (JSONException e) {
                    Log.e("json_log", e.getMessage());
                }

                if(listKota.size() > 0){
                    txtKota.setText(listKota.get(0).getValue());
                    selectedKota = listKota.get(0).getId();
                    loadKecamatan(listKota.get(0).getId());
                }
            }

            @Override
            public void onEmpty(String message) {

            }

            @Override
            public void onFail(String message) {
            }
        }));
    }

    private void loadKecamatan(final String idKota){

        JSONBuilder body = new JSONBuilder();
        body.add("id_kota", idKota);

        new ApiVolley(SignUpActivity.this, body.create(), "POST",
                APIInterface.getKecamatan, new AppRequestCallback(new AppRequestCallback.ResponseListener() {
            @Override
            public void onSuccess(String response, String message) {
                try {
                    JSONArray ja = new JSONArray(response);
                    for(int i = 0; i < ja.length(); i ++){
                        JSONObject jo = ja.getJSONObject(i);
                        listKecamatan.add(
                                new SimpleObjectModel(
                                        jo.getString("id")
                                        ,jo.getString("kecamatan")
                                )
                        );
                    }
                } catch (JSONException e) {
                    Log.e("json_log", e.getMessage());
                }

                if(listKecamatan.size() > 0){
                    txtKecamatan.setText(listKecamatan.get(0).getValue());
                    selectedKecamatan = listKecamatan.get(0).getId();
                }
            }

            @Override
            public void onEmpty(String message) {

            }

            @Override
            public void onFail(String message) {
            }
        }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
