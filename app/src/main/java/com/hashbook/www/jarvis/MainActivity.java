package com.hashbook.www.jarvis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonElement;

import java.util.Map;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.Result;

public class MainActivity extends AppCompatActivity{

    TextView tv;
    Button bt, srs, srst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = (Button)findViewById(R.id.main_bt1);
        srs = (Button)findViewById(R.id.main_ser_bt1);
        srst = (Button)findViewById(R.id.main_ser_bt2);
        tv = (TextView)findViewById(R.id.main_tv1);





        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aiService.startListening();
            }
        });

        /*Handling pocket sphinx work from here*/

        srs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(getBaseContext(), JarvisServis.class));
            }
        });

        srst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(getBaseContext(), JarvisServis.class));
            }
        });
    }




}
