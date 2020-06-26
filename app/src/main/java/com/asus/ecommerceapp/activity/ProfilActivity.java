package com.asus.ecommerceapp.activity;

import android.app.ProgressDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.asus.ecommerceapp.Networking.APIClient;
import com.asus.ecommerceapp.Networking.APIInterface;
import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.SessionManager.UserSession;
import com.asus.ecommerceapp.model.Login.RequestResponse;
import com.asus.ecommerceapp.model.Pelanggan.Pelanggan;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    @BindView(R.id.btn_edit)
    Button btnEdit;
    private ProgressDialog mRegProgres;
    UserSession session;
    Call<RequestResponse> call;
    private APIInterface apiInterface;
    private APIClient apiClient = new APIClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        ButterKnife.bind(this);
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
                    mRegProgres.setTitle("Getting Data");
                    mRegProgres.setMessage("Please Wait...");
                    mRegProgres.setCanceledOnTouchOutside(false);
                    mRegProgres.show();
                    String id = String.valueOf(session.getUserID());
                    String nama = edtNama.getText().toString().trim();
                    String alamat = edtAlamat.getText().toString().trim();
                    String no_hp = edtHp.getText().toString().trim();
                    String email = edtEmail.getText().toString().trim();
                    String password = edtPass.getText().toString().trim();
                    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                    call = apiInterface.editUser(new Pelanggan(id,nama,email,alamat,no_hp,password));
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
        if(TextUtils.isEmpty(edtEmail.getText())){
            edtEmail.setError("Field Harus diisis");
            check = false;
        }
        if(TextUtils.isEmpty(edtAlamat.getText())){
            edtAlamat.setError("Field Harus diisis");
            check = false;
        }
        if(TextUtils.isEmpty(edtHp.getText())){
            edtHp.setError("Field Harus diisis");
            check = false;
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
                mRegProgres.dismiss();
            }

            @Override
            public void onFailure(Call<Pelanggan> call, Throwable t) {

            }
        });

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
