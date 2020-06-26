package com.asus.ecommerceapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.asus.ecommerceapp.Networking.APIClient;
import com.asus.ecommerceapp.Networking.APIInterface;
import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.model.Login.RequestResponse;
import com.asus.ecommerceapp.model.Pelanggan.Pelanggan;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private final String TAG ="Sign Up Activity";

    @BindView(R.id.txt_nama)
    EditText txtNama;

    @BindView(R.id.txt_email)
    EditText txtEmail;

    @BindView(R.id.txt_alamat)
    EditText txtAlamat;

    @BindView(R.id.txt_password)
    EditText txtPassword;

    @BindView(R.id.txt_hp)
    EditText txtHp;

    private ProgressDialog mRegProgres;
    @BindView(R.id.btn_signup)
    Button btnSignUp;

    private APIClient apiClient = new APIClient();
    private Call<RequestResponse> apiCall;
    private  APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        mRegProgres = new ProgressDialog(this);
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
//        getActionBar().hide();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fieldCheck() == true ){
                    mRegProgres.setTitle("Getting Data");
                    mRegProgres.setMessage("Please Wait...");
                    mRegProgres.setCanceledOnTouchOutside(false);
                    mRegProgres.show();
                    String nama = txtNama.getText().toString().trim();
                    String alamat = txtAlamat.getText().toString().trim();
                    String no_hp = txtHp.getText().toString().trim();
                    String email = txtEmail.getText().toString().trim();
                    String password = txtPassword.getText().toString().trim();
                    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                    apiCall = apiInterface.registerUser(new Pelanggan(nama,email,alamat,no_hp,password));
                    apiCall.enqueue(new Callback<RequestResponse>() {
                        @Override
                        public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
                            if(response.body().getSuccess()== true){
                                Toast.makeText(SignUpActivity.this, response.body().getInfo(), Toast.LENGTH_SHORT).show();
                                mRegProgres.dismiss();
                                finish();
                            }else{
                                mRegProgres.dismiss();
                                Toast.makeText(SignUpActivity.this, response.body().getInfo(), Toast.LENGTH_SHORT).show();
                                Log.d(TAG,"Response login"+URL.class);
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
        TextView tvSignUp = (TextView)findViewById(R.id.login);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }
    private Boolean fieldCheck(){
        Boolean fieldCheck = true;

        if(TextUtils.isEmpty(txtNama.getText())){
            txtNama.setError("Insert this Field!");
            fieldCheck = false;
        }
        if(TextUtils.isEmpty(txtAlamat.getText())){
            txtAlamat.setError("Insert this Field!");
            fieldCheck = false;
        }
        if(TextUtils.isEmpty(txtHp.getText())){
            txtHp.setError("Insert this Field!");
            fieldCheck = false;
        }
        if(TextUtils.isEmpty(txtEmail.getText())){
            txtEmail.setError("Insert this Field!");
            fieldCheck = false;
        }
        if(TextUtils.isEmpty(txtPassword.getText())){
            txtPassword.setError("Insert this Field!");
            fieldCheck = false;
        }

        return fieldCheck;
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
