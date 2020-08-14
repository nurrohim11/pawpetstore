package com.asus.ecommerceapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.asus.ecommerceapp.MainActivity;
import com.asus.ecommerceapp.R;

public class GroomingCheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grooming_checkout);
        Button btnSelesai = findViewById(R.id.btnSelesai);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Final Grooming");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GroomingCheckoutActivity.this,"Checkout telah berhasil. Silahkan lanjut ke transfer conformation",Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();
                    }
                },1000);

//                Toast.makeText(GroomingCheckoutActivity.this,"Checkout telah berhasil. Silahkan lanjut ke transfer conformation",Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(GroomingCheckoutActivity.this, MainActivity.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(i);
//                finish();
            }
        });
    }
}

