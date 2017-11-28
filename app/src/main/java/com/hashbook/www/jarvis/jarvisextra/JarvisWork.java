package com.hashbook.www.jarvis.jarvisextra;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonElement;
import com.hashbook.www.jarvis.MainActivity;
import com.hashbook.www.jarvis.jarvisextra.androidExtra.HotSpotManager;

import java.util.Date;
import java.util.Map;

import ai.api.model.Result;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;
import static java.lang.Thread.sleep;

/**
 * Created by ray on 10/29/17.
 */

public class JarvisWork
{
    String ipaddress;
    RequestQueue requestQueue;
    public String result="";
    Context context;


    public JarvisWork(String IPadddress, Context context)
    {
        ipaddress = IPadddress;
        requestQueue = Volley.newRequestQueue(context);
        this.context = context;
    }

    public int JarvisSwitchChange(final int number, boolean swithchon)
    {
        // checking if the hotspot is on or not
        final String d = (swithchon)?"on":"off";

        if(!HotSpotManager.isApOn(this.context))
        {
            HotSpotManager.configApState(context);
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    //wait for 4 seconds
                    try{
                        sleep(4000);

                    }
                    catch (Exception e){ e.printStackTrace();}

                    StringRequest request = new StringRequest(Request.Method.GET, "http://" + ipaddress + "/" + number + d,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    result = response;

                                    HotSpotManager.configApState(context);
                                }}, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            result = "Cannot connect";
                        }
                    });
                    requestQueue.add(request);

                }
            });
            t.start();
            return -1;
        }

        StringRequest request = new StringRequest(Request.Method.GET, "http://" + ipaddress + "/" + number + d,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        result = response;
                        updateState(result);
                    }}, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                result = "Cannot connect";
            }
        });
        requestQueue.add(request);
        return 1;
    }
    protected String getTime()
    {
        Date d = new Date();
        //String hrs = " " + DateFormat.format("yyyy-MM-dd hh:mm:ss", d.getTime());
        String hrs = " " + DateFormat.format("hh", d.getTime()),
                min = " " + DateFormat.format("mm", d.getTime()),
                sec = " " + DateFormat.format("ss", d.getTime()),
                date_of_month = " " + DateFormat.format("dd", d.getTime()),
                month = " " + DateFormat.format("MM", d.getTime());
        return " " + DateFormat.format("hh:mm:ss", d.getTime());
    }


    public String process(ai.api.model.AIResponse result){
        Result result1= result.getResult();

        String paramString = "";
        if (result1.getParameters() != null && !result1.getParameters().isEmpty()) {
            for (final Map.Entry<String, JsonElement> entry : result1.getParameters().entrySet()) {
                paramString += "(" + entry.getKey() + ", " + entry.getValue() + ") ";
            }
        }
        String extra="";
        int hotspotOn=0;
        if(result1.getAction().equals("1")){ hotspotOn = JarvisSwitchChange(2, true);}
        else if(result1.getAction().equals("2")){ hotspotOn = JarvisSwitchChange(2, false);}
        else if(result1.getAction().equals("3")){ hotspotOn = JarvisSwitchChange(6, true);}
        else if(result1.getAction().equals("4")){ hotspotOn= JarvisSwitchChange(6, false);}
        else if(result1.getAction().equals("5")){ hotspotOn = JarvisSwitchChange(3, true);}
        else if(result1.getAction().equals("6")){ hotspotOn = JarvisSwitchChange(3, false);}
        else if(result1.getAction().equals("7")){ extra+=getTime();}

        if(hotspotOn != 0)
        {
            if(hotspotOn==-1)extra+="although it may take some time";
        }

        return result1.getFulfillment().getSpeech()+" "+extra;

    }
    void updateState(String aResponse)
    {
        if(aResponse.charAt(0)=='1') onSwitch0On();
                        else onSwitch0Off();

        if(aResponse.charAt(1)=='1') onSwitch1On();
        else onSwitch1Off();

        if(aResponse.charAt(2)=='1') onSwitch2On();
        else onSwitch2Off();

        if(aResponse.charAt(3)=='1') onSwitch3On();
        else onSwitch3Off();

        if(aResponse.charAt(4)=='1') onSwitch4On();
        else onSwitch4Off();

        if(aResponse.charAt(5)=='1') onSwitch5On();
        else onSwitch5Off();

        if(aResponse.charAt(6)=='1') onSwitch6On();
        else onSwitch6Off();

        if(aResponse.charAt(7)=='1') onSwitch7On();
        else onSwitch7Off();

        if(aResponse.charAt(8)=='1') onSwitch8On();
        else onSwitch8Off();
    }
    public void onSwitch0On(){}
    public void onSwitch1On(){}
    public void onSwitch2On(){}
    public void onSwitch3On(){}
    public void onSwitch4On(){}
    public void onSwitch5On(){}
    public void onSwitch6On(){}
    public void onSwitch7On(){}
    public void onSwitch8On(){}

    public void onSwitch0Off(){}
    public void onSwitch1Off(){}
    public void onSwitch2Off(){}
    public void onSwitch3Off(){}
    public void onSwitch4Off(){}
    public void onSwitch5Off(){}
    public void onSwitch6Off(){}
    public void onSwitch7Off(){}
    public void onSwitch8Off(){}
}
/*public class setButtons
{
    Context context;
    setButtons(final Context context)
    {
        this.context = context;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                threadMsg(JarvisWork.homeStatus(context));
            }

            private void threadMsg(String msg) {

                if (!msg.equals(null) && !msg.equals("")) {
                    Message msgObj = handler.obtainMessage();
                    Bundle b = new Bundle();
                    b.putString("message", msg);
                    msgObj.setData(b);
                    handler.sendMessage(msgObj);
                }
            }

            private final Handler handler = new Handler()
            {
                @Override
                public void handleMessage(Message msg) {
                    String aResponse = msg.getData().getString("message");

                    if ((null != aResponse)) {
                        if(aResponse.charAt(0)=='1') onSwitch0On();
                        else onSwitch0Off();

                        if(aResponse.charAt(1)=='1') onSwitch1On();
                        else onSwitch1Off();

                        if(aResponse.charAt(2)=='1') onSwitch2On();
                        else onSwitch2Off();

                        if(aResponse.charAt(3)=='1') onSwitch3On();
                        else onSwitch3Off();

                        if(aResponse.charAt(4)=='1') onSwitch4On();
                        else onSwitch4Off();

                        if(aResponse.charAt(5)=='1') onSwitch5On();
                        else onSwitch5Off();

                        if(aResponse.charAt(6)=='1') onSwitch6On();
                        else onSwitch6Off();

                        if(aResponse.charAt(7)=='1') onSwitch7On();
                        else onSwitch7Off();

                        if(aResponse.charAt(8)=='1') onSwitch8On();
                        else onSwitch8Off();
                    }
                    else Toast.makeText(context, "Not Got Response From Server.", Toast.LENGTH_SHORT).show();


                }
            };


        }



        );
    }



}*/