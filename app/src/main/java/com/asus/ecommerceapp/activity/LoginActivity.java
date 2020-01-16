package com.asus.ecommerceapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.asus.ecommerceapp.MainActivity;
import com.asus.ecommerceapp.Networking.APIClient;
import com.asus.ecommerceapp.Networking.APIInterface;
import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.SessionManager.UserSession;
import com.asus.ecommerceapp.model.Login.LoginRequest;
import com.asus.ecommerceapp.model.Login.RequestResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.txt_email)
    TextView txtLoginEmail;
    @BindView(R.id.txt_password)
    TextView txtLoginPassword;
    @BindView(R.id.btn_signin)
    Button btnLogin;
    private ProgressDialog mRegProgres;
    private APIClient apiClient = new APIClient();
    private Call<RequestResponse> apiCall;
    private APIInterface apiInterface;
    public  static final String KEY_EMAIL="key_email";
    public  static final String KEY_ID="key_id";
    public  static final String KEY="key_security";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign In");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRegProgres = new ProgressDialog(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRegProgres.setTitle("Getting Data");
                mRegProgres.setMessage("Please Wait...");
                mRegProgres.setCanceledOnTouchOutside(false);
                mRegProgres.show();
                final String email = txtLoginEmail.getText().toString().trim();
                final String pass = txtLoginPassword.getText().toString().trim();
                APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                Call<RequestResponse> call = apiInterface.getLoginStatus(new LoginRequest(email,pass));
                call.enqueue(new Callback<RequestResponse>() {
                    @Override
                    public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
                        if(response.body().getSuccess() == true){
                            mRegProgres.dismiss();
                            UserSession userSession = new UserSession(LoginActivity.this);
                            userSession.setLoginSession(response.body().getId(), email, response.body().getAlamat(),pass);
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            String userId = response.body().getId();
                            i.putExtra(KEY_ID,userId);
                            i.putExtra(KEY_EMAIL,email);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            finish();
                        }else{
                            mRegProgres.dismiss();
                            Toast.makeText(LoginActivity.this, response.body().getInfo(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<RequestResponse> call, Throwable t) {
                        mRegProgres.dismiss();
                        Toast.makeText(LoginActivity.this, "Login Gagal : "+ t, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        TextView tvSignUp = (TextView)findViewById(R.id.signUP);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
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
