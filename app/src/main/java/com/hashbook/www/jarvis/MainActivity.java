package com.hashbook.www.jarvis;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.hashbook.www.jarvis.jarvisextra.JarvisWork;

import java.util.Map;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.Result;

public class MainActivity extends Activity {


    Button  srs, srst;
    ImageButton dlight, obulb, fan;
    boolean dl, ol, fn;
    JarvisWork jw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        srs = (Button)findViewById(R.id.main_ser_bt1);
        srst = (Button)findViewById(R.id.main_ser_bt2);


        dlight = (ImageButton)findViewById(R.id.dlight);

        obulb = (ImageButton)findViewById(R.id.olight);
        fan = (ImageButton)findViewById(R.id.fan);
        dl= false;
        ol = false;
        fn = false;


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


        jw = new JarvisWork("192.168.43.139", MainActivity.this){
            @Override
            public void onSwitch2On() {
                //desk light
                dlight.setBackgroundResource(R.drawable.lb1on);
                dl = true;
            }

            @Override
            public void onSwitch2Off() {
                dlight.setBackgroundResource(R.drawable.lb1off);
                dl = false;
            }

            @Override
            public void onSwitch3On() {

                obulb.setBackgroundResource(R.drawable.lb2on);
                ol = true;
            }
            @Override
            public void onSwitch3Off() {
                obulb.setBackgroundResource(R.drawable.lb2off);
                ol = false;
            }

            @Override
            public void onSwitch6On() {
                fan.setBackgroundResource(R.drawable.fanon);
                fn = true;
            }


            @Override
            public void onSwitch6Off() {
                fan.setBackgroundResource(R.drawable.fanoff);
                fn = false;
            }
        };

        dlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jw.JarvisSwitchChange(2, !dl);
            }
        });

        obulb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jw.JarvisSwitchChange(3, !ol);
            }
        });

        fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jw.JarvisSwitchChange(6, !fn);
            }
        });

        jw.JarvisSwitchChange(8,true);
        dlight.setMinimumHeight(dlight.getWidth());
    }



}
