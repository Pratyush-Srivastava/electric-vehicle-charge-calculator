package com.example.android.evcharge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DuringChargeActivity extends AppCompatActivity {
    private Button btStopCharging;
    private Button btNearBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_during_charge);
        btNearBy=(Button)findViewById(R.id.bt_nearby);
        btStopCharging=(Button)findViewById(R.id.bt_stop_charging);
        btStopCharging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DuringChargeActivity.this,SelectModeActivity.class);
                startActivity(intent);
            }
        });
        btNearBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to do
            }
        });

    }
}
