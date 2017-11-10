package com.hashbook.www.jarvis;

import android.content.Context;
import android.text.format.DateFormat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Date;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

/**
 * Created by ray on 10/29/17.
 */

public class JarvisWork
{
    String ipaddress;
    RequestQueue requestQueue;
    public String result="";
    JarvisWork(String IPadddress, Context context)
    {
        ipaddress = IPadddress;
        requestQueue = Volley.newRequestQueue(context);
    }

    protected void JarvisSwitchChange(int number, boolean swithchon)
    {
        String d = (swithchon)?"on":"off";
        StringRequest request = new StringRequest(Request.Method.GET, "http://" + ipaddress + "/" + number + d,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                result = response;
            }}, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                result = "Cannot connect";
            }
        });
        requestQueue.add(request);
    }
    protected String getWorkDone(String actNo)
    {
        Date d = new Date();
        //String hrs = " " + DateFormat.format("yyyy-MM-dd hh:mm:ss", d.getTime());
        String hrs = " " + DateFormat.format("hh", d.getTime()),
                min = " " + DateFormat.format("mm", d.getTime()),
                sec = " " + DateFormat.format("ss", d.getTime()),
                date_of_month = " " + DateFormat.format("dd", d.getTime()),
                month = " " + DateFormat.format("MM", d.getTime());
        if (actNo.equals("7")) return " " + DateFormat.format("hh:mm:ss", d.getTime());
        return "";
    }


}
