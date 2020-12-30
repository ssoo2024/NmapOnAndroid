package com.example.mynmap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HostScan extends AppCompatActivity {
    TextView tvResult;
    Button gotomain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hostscan);

        tvResult = (TextView) findViewById(R.id.scan_result);
        gotomain = (Button) findViewById(R.id.gotomain_bt);

        Intent intent = getIntent();
        String data = intent.getExtras().getString("data");
        tvResult.setText("");
        tvResult.setText(data);

        gotomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
