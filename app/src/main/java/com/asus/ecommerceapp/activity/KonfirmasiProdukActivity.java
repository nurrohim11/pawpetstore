package com.asus.ecommerceapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.asus.ecommerceapp.fragment.KonfirmasiProdukFragment;
import com.google.gson.Gson;
import com.asus.ecommerceapp.MainActivity;
import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.SessionManager.UserSession;
import com.asus.ecommerceapp.model.Login.RequestResponse;
import com.asus.ecommerceapp.model.penitipan.HistoryPenitipanItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class KonfirmasiProdukActivity extends AppCompatActivity {
    public final static String KONFIRMASI_ITEM = "konfirmasi_item";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.buttonChoose)
    Button btnChoose;
    @BindView(R.id.img_gambar)
    ImageView imageView;
    @BindView(R.id.edt_order)
    EditText editOrder;
    @BindView(R.id.edt_total)
    EditText editTotal;
    @BindView(R.id.edt_dariakun)
    EditText editDariAkun;
    @BindView(R.id.edt_namaakun)
    EditText edtNamaAkun;
    @BindView(R.id.btn_konfirmasi)
    Button btnKonfirmasi;
    Bitmap bitmap, decoded;
    int success;
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60; // range 1 - 100


    private Gson gson = new Gson();
    private HistoryPenitipanItem item;
    private static final String TAG = KonfirmasiProdukActivity.class.getSimpleName();

    private String UPLOAD_URL = "http://pawspetstore.xyz/API/KonfirmasiProduk";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private String KEY_IMAGE = "bukti_transfer";
    private String KEY_ORDER = "no_order";
    private String KEY_TOTAL = "jumlah";
    private String KEY_DARI = "dari_rekening";
    private String KEY_NAMAAKUN = "an_rekening";
    private String KEY_KE = "ke_rekening";
    private String KEY_ID = "id_pelanggan";

    String tag_json_obj = "json_obj_req";
    private ProgressDialog mRegProgres;
    private Call<RequestResponse> apiCall;
    UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_produk);
        ButterKnife.bind(this);

        session = new UserSession(this);
        mRegProgres = new ProgressDialog(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Payment Confirmation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String[] no_akun = new String[]{
                "Pilih",
                "0912308312930 a/n kenza",
                "218973691236 a/n rohim"
        };
        // Initializing an ArrayAdapter
        final List<String> plantsList = new ArrayList<>(Arrays.asList(no_akun));

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, plantsList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        String detail = getIntent().getStringExtra(KONFIRMASI_ITEM);
        loadData(detail);
        btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmProduk();
            }
        });
    }
    @Override
    protected void onStart() {

        super.onStart();
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void loadData(String detail_item) {
        item = gson.fromJson(detail_item, HistoryPenitipanItem.class);
        editOrder.setText(item.getNoOrder());
        editTotal.setText(item.getTotal());
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
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

    private void confirmProduk() {
        final ProgressDialog loading = ProgressDialog.show(this, "Processing...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Response: " + response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);
                            Log.d(TAG,"res "+String.valueOf(jObj));

                            if (success == 1) {
                                Log.e("v Add", jObj.toString());
                                Toast.makeText(KonfirmasiProdukActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                kosong();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent returnIntent = new Intent();
                                        setResult(Activity.RESULT_OK,returnIntent);
                                        finish();
                                    }
                                },1000);
                            } else {
                                Toast.makeText(KonfirmasiProdukActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        loading.dismiss();
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();

//                        Toast.makeText(KonfirmasiProdukActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        Log.e(TAG, error.getMessage().toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put(KEY_IMAGE, getStringImage(decoded));
                params.put(KEY_ORDER, editOrder.getText().toString().trim());
                params.put(KEY_TOTAL, editTotal.getText().toString().trim());
                params.put(KEY_DARI, editDariAkun.getText().toString().trim());
                params.put(KEY_NAMAAKUN, edtNamaAkun.getText().toString().trim());
                session = new UserSession(getApplicationContext());
                params.put(KEY_KE, "empty");
                params.put(KEY_ID, session.getUserID());
                Log.e(TAG, "params " + params);
                return params;
            }
        };

//        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(stringRequest);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                setToImageView(getResizedBitmap(bitmap, 512));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    private void setToImageView(Bitmap bmp) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

        imageView.setImageBitmap(decoded);
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    private void kosong() {
        imageView.setImageResource(0);
    }

    private Boolean fieldCheck(){
        Boolean fieldCheck = true;

        if(TextUtils.isEmpty(editOrder.getText())){
            editOrder.setError("Insert this Field!");
            fieldCheck = false;
        }
        if(TextUtils.isEmpty(editTotal.getText())){
            editTotal.setError("Insert this Field!");
            fieldCheck = false;
        }
        if(TextUtils.isEmpty(editDariAkun.getText())){
            editDariAkun.setError("Insert this Field!");
            fieldCheck = false;
        }
        if(TextUtils.isEmpty(edtNamaAkun.getText())){
            edtNamaAkun.setError("Insert this Field!");
            fieldCheck = false;
        }
        imageView.setImageResource(0);

        return fieldCheck;
    }
}