package com.asus.ecommerceapp.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.asus.ecommerceapp.Networking.APIClient;
import com.asus.ecommerceapp.Networking.APIInterface;
import com.asus.ecommerceapp.Networking.volley.ApiVolley;
import com.asus.ecommerceapp.Networking.volley.AppRequestCallback;
import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.SessionManager.UserSession;
import com.asus.ecommerceapp.model.Login.RequestResponse;
import com.asus.ecommerceapp.model.Pelanggan.Pelanggan;
import com.asus.ecommerceapp.utils.DialogFactory;
import com.asus.ecommerceapp.utils.JSONBuilder;
import com.asus.ecommerceapp.utils.SearchableSpinnerDialog.SearchableSpinnerDialogAdapter;
import com.asus.ecommerceapp.utils.SearchableSpinnerDialog.SimpleObjectModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.asus.ecommerceapp.utils.Util.isHasLength;
import static com.asus.ecommerceapp.utils.Util.isHasLowerCase;
import static com.asus.ecommerceapp.utils.Util.isHasSymbol;
import static com.asus.ecommerceapp.utils.Util.isHasUpperCase;
import static com.asus.ecommerceapp.utils.Util.isValidEmail;

public class ProfilActivity extends AppCompatActivity {
    @BindView(R.id.edt_nama)
    EditText edtNama;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_hp)
    EditText edtHp;
    @BindView(R.id.edt_alamat)
    EditText edtAlamat;
    @BindView(R.id.edt_pass)
    EditText edtPass;
    @BindView(R.id.edt_kodepos)
    EditText edtKodePos;
    @BindView(R.id.tv_provinsi)
    TextView tvProvinsi;
    @BindView(R.id.tv_kota)
    TextView tvKota;
    @BindView(R.id.tv_kecamatan)
    TextView tvKecamatan;
    @BindView(R.id.btn_edit)
    Button btnEdit;
    private ProgressDialog mRegProgres;
    UserSession session;
    Call<RequestResponse> call;
    private APIInterface apiInterface;
    private APIClient apiClient = new APIClient();

    private List<SimpleObjectModel> listProvinsi = new ArrayList<>(),listKota = new ArrayList<>(),listKecamatan = new ArrayList<>();
    private List<SimpleObjectModel> listChooser = new ArrayList<>();
    private String selectedKota = "", selectedKecamatan = "", selectedProvinsi = "";
    private SearchableSpinnerDialogAdapter dialogAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        ButterKnife.bind(this);
        edtEmail.setEnabled(false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Your Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRegProgres = new ProgressDialog(this);
        session = new UserSession(this);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check() ==true) {
                    mRegProgres.setTitle("Processing..");
                    mRegProgres.setMessage("Please Wait...");
                    mRegProgres.setCanceledOnTouchOutside(false);
                    mRegProgres.show();
                    String id = String.valueOf(session.getUserID());
                    String nama = edtNama.getText().toString().trim();
                    String alamat = edtAlamat.getText().toString().trim();
                    String no_hp = edtHp.getText().toString().trim();
                    String email = edtEmail.getText().toString().trim();
                    String password = edtPass.getText().toString().trim();
                    String kode_pos = edtKodePos.getText().toString().trim();
                    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                    call = apiInterface.editUser(new Pelanggan(id,nama,email,alamat,no_hp,password,selectedProvinsi,selectedKota,selectedKecamatan,kode_pos));
                    call.enqueue(new Callback<RequestResponse>() {
                        @Override
                        public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
                            if(response.body().getSuccess() == true){
                                Toast.makeText(ProfilActivity.this, response.body().getInfo(), Toast.LENGTH_SHORT).show();
                                mRegProgres.dismiss();
                                finish();
                            }else{
                                mRegProgres.dismiss();
                                Toast.makeText(ProfilActivity.this, response.body().getInfo(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RequestResponse> call, Throwable t) {
                            mRegProgres.dismiss();
                        }
                    });
                }
            }
        });
        getInitData();
        initData();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (call != null) call.cancel();
    }

    private Boolean check(){
        Boolean check =true;
        if (TextUtils.isEmpty(edtNama.getText())){
            edtNama.setError("Field Harus Diisi");
            check =false;
        }
        if(TextUtils.isEmpty(edtEmail.getText()) || !isValidEmail(edtEmail.getText().toString())){
            if(TextUtils.isEmpty(edtEmail.getText())){
                edtEmail.setError("Insert this Field!");
                check = false;
            }else if(!isValidEmail(edtEmail.getText().toString())){
                edtEmail.setError("Invalid email format");
                check = false;
            }
            edtEmail.setFocusable(true);
        }

        if(TextUtils.isEmpty(edtAlamat.getText())){
            edtAlamat.setError("Field Harus diisis");
            check = false;
        }
        if(TextUtils.isEmpty(edtHp.getText())){
            edtHp.setError("Field Harus diisis");
            check = false;
        }

        if(selectedProvinsi.equals("")){
            tvProvinsi.setError("You have not selected a province");
            check = false;
            tvProvinsi.setFocusable(true);
        }

        if(selectedKota.equals("")){
            tvKota.setError("You have not selected a city");
            check = false;
            tvKota.setFocusable(true);
        }

        if(selectedKecamatan.equals("")){
            tvKecamatan.setError("You have not selected a district");
            check = false;
            tvKecamatan.setFocusable(true);
        }

        if(TextUtils.isEmpty(edtKodePos.getText())){
            edtKodePos.setError("Insert this field");
            check = false;
        }

        if(edtPass.length() > 0){
            if(!isHasLength(edtPass.getText().toString())
                    || !isHasSymbol(edtPass.getText().toString())
                    || !isHasUpperCase(edtPass.getText().toString())
                    || !isHasLowerCase(edtPass.getText().toString())){

                if(!isHasLength(edtPass.getText().toString())){
                    edtPass.setError("The password must be more than 6 letters long!");
                    check = false;
                }

                if(!isHasSymbol(edtPass.getText().toString())){
                    edtPass.setError("The password must consist of one symbol");
                    check = false;
                }

                if(!isHasUpperCase(edtPass.getText().toString())){
                    edtPass.setError("The password must consist of one capital letter");
                    check = false;
                }

                if(!isHasLowerCase(edtPass.getText().toString())){
                    edtPass.setError("The password must consist of one lowercase letter");
                    check = false;
                }
                edtPass.setFocusable(true);
            }
        }
        return  check;
    }

    private void getInitData(){
        mRegProgres.setTitle("Getting Data");
        mRegProgres.setMessage("Please Wait...");
        mRegProgres.setCanceledOnTouchOutside(false);
        mRegProgres.show();

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Pelanggan> call = apiInterface.getUserData(session.getUserID());
        call.enqueue(new Callback<Pelanggan>() {
            @Override
            public void onResponse(Call<Pelanggan> call, Response<Pelanggan> response) {
                edtNama.setText(response.body().getNama());
                edtAlamat.setText(response.body().getAlamat());
                edtHp.setText(response.body().getNo_hp());
                edtEmail.setText(response.body().getEmail());

                selectedProvinsi = response.body().getId_provinsi();
                selectedKota = response.body().getId_kota();
                selectedKecamatan = response.body().getId_kecamatan();

                tvProvinsi.setText(response.body().getProvinsi());
                tvKota.setText(response.body().getKota());
                tvKecamatan.setText(response.body().getKecamatan());
                edtKodePos.setText(response.body().getKode_pos());
                mRegProgres.dismiss();
            }

            @Override
            public void onFailure(Call<Pelanggan> call, Throwable t) {

            }
        });

    }

    private void initData(){
        tvProvinsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogProvinsi();
            }
        });

        tvKota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogKota();
            }
        });

        tvKecamatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogKecamatan();
            }
        });
        loadProvinsi();
    }

    private void showDialogProvinsi(){
        final Dialog dialogChooser = DialogFactory.getInstance().createDialog(ProfilActivity.this,
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
        rv_items.setLayoutManager(new LinearLayoutManager(ProfilActivity.this));
        dialogAdapter = new SearchableSpinnerDialogAdapter(ProfilActivity.this,
                listChooser, new SearchableSpinnerDialogAdapter.ChooserListener() {
            @Override
            public void onSelected(String id, String value) {
                tvProvinsi.setText(value);
                selectedProvinsi = id;

                selectedKota = "";
                tvKota.setText("");
                listKota.clear();
                selectedKecamatan = "";
                tvKecamatan.setText("");
                listKecamatan.clear();

                loadKota(id);
                dialogChooser.dismiss();
            }
        });

        rv_items.setAdapter(dialogAdapter);
        dialogChooser.show();
    }


    private void showDialogKota(){
        final Dialog dialogChooser = DialogFactory.getInstance().createDialog(ProfilActivity.this,
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
        rv_items.setLayoutManager(new LinearLayoutManager(ProfilActivity.this));
        dialogAdapter = new SearchableSpinnerDialogAdapter(ProfilActivity.this,
                listChooser, new SearchableSpinnerDialogAdapter.ChooserListener() {
            @Override
            public void onSelected(String id, String value) {
                tvKota.setText(value);
                selectedKota = id;

                selectedKecamatan = "";
                tvKecamatan.setText("");
                listKecamatan.clear();

                loadKecamatan(id);
                dialogChooser.dismiss();
            }
        });

        rv_items.setAdapter(dialogAdapter);
        dialogChooser.show();
    }

    private void showDialogKecamatan(){
        final Dialog dialogChooser = DialogFactory.getInstance().createDialog(ProfilActivity.this,
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
        rv_items.setLayoutManager(new LinearLayoutManager(ProfilActivity.this));
        dialogAdapter = new SearchableSpinnerDialogAdapter(ProfilActivity.this,
                listChooser, new SearchableSpinnerDialogAdapter.ChooserListener() {
            @Override
            public void onSelected(String id, String value) {
                tvKecamatan.setText(value);
                selectedKecamatan = id;

                dialogChooser.dismiss();
            }
        });

        rv_items.setAdapter(dialogAdapter);
        dialogChooser.show();
    }

    private void loadProvinsi(){

        new ApiVolley(ProfilActivity.this, new JSONObject(), "GET",
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


//                if(listProvinsi.size() > 0){
//                    tvProvinsi.setText(listProvinsi.get(0).getValue());
//                    selectedProvinsi = listProvinsi.get(0).getId();
//                    loadKota(listProvinsi.get(0).getId());
//                }
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

        new ApiVolley(ProfilActivity.this, body.create(), "POST",
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
                    tvKota.setText(listKota.get(0).getValue());
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

        new ApiVolley(ProfilActivity.this, body.create(), "POST",
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
                    tvKecamatan.setText(listKecamatan.get(0).getValue());
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
