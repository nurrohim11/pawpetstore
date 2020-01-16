package com.asus.ecommerceapp.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.asus.ecommerceapp.Networking.APIClient;
import com.asus.ecommerceapp.Networking.APIInterface;
import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.SessionManager.UserSession;
import com.asus.ecommerceapp.model.penitipan.OrderPenitipan;
import com.asus.ecommerceapp.model.penitipan.ResponseOrderPenitipan;

import java.net.URL;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PenitipanActivity extends AppCompatActivity {
    public  static final String TAG="Response Session";
    UserSession session;
    @BindView(R.id.edt_dari)
    EditText edtDari;

    @BindView(R.id.edt_sampai)
    EditText edtSampai;

    private ProgressDialog mRegProgres;

    DatePickerDialog datePickerDialog;

    @BindView(R.id.btn_nitip)
    Button btnNitip;

    private APIClient apiClient = new APIClient();
    private Call<ResponseOrderPenitipan> apiCall;
    private  APIInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penitipan);
        session = new UserSession(this);
        Log.d(TAG, "Session: "+session.getEmail());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Order Penitirpan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        edtDari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(PenitipanActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        edtDari.setText(dayOfMonth+ "-"+ (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        edtSampai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(PenitipanActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        edtSampai.setText(dayOfMonth+ "-"+ (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        mRegProgres = new ProgressDialog(this);
//        if (session != null && session.getUserID()!= null && session.getEmail() != null){
//            TextView tvTest = (TextView)findViewById(R.id.tv_test);
//            tvTest.setText(session.getEmail());
//        }
        btnNitip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(session != null && session.getUserID() != null){
                    if (check() ==true){
                        mRegProgres.setTitle("Getting Data");
                        mRegProgres.setMessage("Please Wait...");
                        mRegProgres.setCanceledOnTouchOutside(false);
                        mRegProgres.show();
                        String id_pelanggan = session.getUserID();
                        String dari = edtDari.getText().toString().trim();
                        String sampai = edtSampai.getText().toString().trim();
                        apiInterface = APIClient.getClient().create(APIInterface.class);
                        apiCall = apiInterface.orderPenitipan(new OrderPenitipan(id_pelanggan, dari, sampai));
                        apiCall.enqueue(new Callback<ResponseOrderPenitipan>() {
                            @Override
                            public void onResponse(Call<ResponseOrderPenitipan> call, Response<ResponseOrderPenitipan> response) {
                                if (response.body().getSuccess() == true){
                                    Toast.makeText(PenitipanActivity.this, response.body().getInfo(), Toast.LENGTH_SHORT).show();
                                    mRegProgres.dismiss();
                                    finish();
                                }else{
                                    mRegProgres.dismiss();
                                    Toast.makeText(PenitipanActivity.this, response.body().getInfo(), Toast.LENGTH_SHORT).show();
                                    Log.d(TAG,"Response login"+URL.class);
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseOrderPenitipan> call, Throwable t) {

                            }
                        });
                    }
                }else {
                    Toast.makeText(PenitipanActivity.this, "Anda Harus Login Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
    private Boolean check(){
        Boolean check =true;
        if (TextUtils.isEmpty(edtDari.getText())){
            edtDari.setError("Field Harus Diisi");
            check =false;
        }
        if(TextUtils.isEmpty(edtSampai.getText())){
            edtSampai.setError("Field Harus diisis");
            check = false;
        }
        return  check;
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
