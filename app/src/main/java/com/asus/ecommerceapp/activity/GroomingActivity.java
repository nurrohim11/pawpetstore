package com.asus.ecommerceapp.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.asus.ecommerceapp.Networking.APIClient;
import com.asus.ecommerceapp.Networking.APIInterface;
import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.SessionManager.UserSession;
import com.asus.ecommerceapp.model.grooming.OrderGrooming;
import com.asus.ecommerceapp.model.penitipan.ResponseOrderPenitipan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroomingActivity extends AppCompatActivity {
    public  static final String TAG="Response Session";
    UserSession session;
    @BindView(R.id.edt_tgl)
    EditText edtTgl;
    @BindView(R.id.sp_jenis)
    Spinner spJenis;
    @BindView(R.id.sp_jenis_hewan)
    Spinner spJenisHewan;
    @BindView(R.id.sp_ukuran)
    Spinner spUkuran;

    private ProgressDialog mRegProgres;

    private  String SelectUkuran;
    private  String SelectJenisHewan;
    private  String SelectJenis;
    DatePickerDialog datePickerDialog;
    @BindView(R.id.btn_order)
    Button btnOrder;
    private APIClient apiClient = new APIClient();
    private Call<ResponseOrderPenitipan> apiCall;
    private APIInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grooming);
        session = new UserSession(this);
        Log.d(TAG, "Session: "+session.getEmail());
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Grooming Booking");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String[] plants = new String[]{
                "Pilih Hewan",
                "Kucing",
                "Kelinci",
                "Anjing"
        };
        String[] JenisHewan = new String[]{
                "Pilih Jenis Hewan",
                "Bulu Panjang",
                "Bulu Pendek"
        };
        String[] UkuranHewan = new String[]{
                "Pilih Ukuran",
                "Kecil",
                "Sedang",
                "Besar"
        };

        final List<String> plantsList = new ArrayList<>(Arrays.asList(plants));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,plantsList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spJenis.setAdapter(spinnerArrayAdapter);

        //        Toast.makeText(this, spJenis.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
        spJenis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectJenis = (String) parent.getItemAtPosition(position);
                // Notify the selected item text
                //Aksi Spinner

                String pilih = "Pilih Hewan";
                String jenis = "Kucing";
                String jenisa = "Kelinci";
                String anjing = "Anjing";
                spUkuran.setVisibility(View.GONE);
                spJenisHewan.setVisibility(View.GONE);
                if (SelectJenis.equalsIgnoreCase(jenisa)){
                    spJenisHewan.setVisibility(View.VISIBLE);
                }else if (SelectJenis.equalsIgnoreCase(jenis)){
                    spJenisHewan.setVisibility(View.VISIBLE);
                }else if (SelectJenis.equalsIgnoreCase(anjing)){
                    spUkuran.setVisibility(View.VISIBLE);
                }else  if (SelectJenis.equalsIgnoreCase(pilih)){
                    spUkuran.setVisibility(View.GONE);
                    spJenisHewan.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String jenis_a = spJenis.getSelectedItem().toString();
        //Spinner Jenis Hewan
        final List<String> jenishewan = new ArrayList<>(Arrays.asList(JenisHewan));
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> jenisAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,jenishewan);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spJenisHewan.setAdapter(jenisAdapter);
        spJenisHewan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectJenisHewan = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Spinner Jenis Ukuran
        final List<String> ukuran = new ArrayList<>(Arrays.asList(UkuranHewan));
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> UkuranHewanAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,ukuran);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spUkuran.setAdapter(UkuranHewanAdapter);
        spUkuran.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectUkuran = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edtTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(GroomingActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        edtTgl.setText(dayOfMonth+ "-"+ (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        mRegProgres = new ProgressDialog(this);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(session != null && session.getUserID() != null){
                    if (check() == true){
                        mRegProgres.setTitle("Getting Data");
                        mRegProgres.setMessage("Please Wait...");
                        mRegProgres.setCanceledOnTouchOutside(false);
                        mRegProgres.show();
                        String id_pelanggan = session.getUserID();
                        String tgl_booking= edtTgl.getText().toString().trim();
                        String jenis =SelectJenis;
                        String jenishewan = SelectJenisHewan;
                        String ukuran = SelectUkuran;
                        apiInterface = APIClient.getClient().create(APIInterface.class);
                        apiCall = apiInterface.orderGrooming(new OrderGrooming(id_pelanggan, tgl_booking, jenis, jenishewan, ukuran));
                        apiCall.enqueue(new Callback<ResponseOrderPenitipan>() {
                            @Override
                            public void onResponse(Call<ResponseOrderPenitipan> call, Response<ResponseOrderPenitipan> response) {
                                if (response.body().getSuccess() == true){
                                    Toast.makeText(GroomingActivity.this, response.body().getInfo(), Toast.LENGTH_SHORT).show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            finish();
                                        }
                                    },1000);
                                    mRegProgres.dismiss();
                                }else {
                                    mRegProgres.dismiss();
                                    Toast.makeText(GroomingActivity.this, response.body().getInfo(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseOrderPenitipan> call, Throwable t) {
                                Toast.makeText(GroomingActivity.this, "Failed network", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }else{
                    Toast.makeText(GroomingActivity.this, "Anda Harus Sign In Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private  Boolean check(){
        Boolean check = true;
        if (TextUtils.isEmpty(edtTgl.getText())){
            edtTgl.setError("Field harus diisi");
            check = false;
        }
        return  check;
    }

    private void setSpinnerError(Spinner spinner, String error){
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(error); // actual error message
            spinner.performClick(); // to open the spinner list if error is found.

        }
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