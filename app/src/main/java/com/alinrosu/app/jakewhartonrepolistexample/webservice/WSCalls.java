package com.alinrosu.app.jakewhartonrepolistexample.webservice;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.alinrosu.app.jakewhartonrepolistexample.interfaces.StringCallback;
import com.alinrosu.app.jakewhartonrepolistexample.utils.Utils;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alin on 9/17/2017.
 */

public class WSCalls {

    private final static String TAG = "WSCalls";
    //A standardized method to make a call that returns a json
    public static void fetchData(final Integer type , final String url, final Context context, final  View loader, final StringCallback listener){
        String URL = url;
        Log.i("","sendData url is:" + url);
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        StringRequest jr = new StringRequest(type, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "sendData Response: " + response.toString());
                try{
                    if(response != null){
                        if(loader!= null)
                            loader.setVisibility(View.GONE);
                        if(listener != null)
                            listener.onResponse(200, response);
                    }else{
                        if(listener != null)
                            listener.onResponse(200, response);
                    }
                }catch (Exception e){
                    Log.e(TAG, "JsonException: " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String json = null;
                int code = 404;
                if(loader!= null)
                    loader.setVisibility(View.GONE);
                if(error != null && error.networkResponse != null) {
                    Log.e("","WSCALL send data done, ERROR stop STATUS CODE" + error.networkResponse.statusCode);
                    code = error.networkResponse.statusCode;
                    json = new String(error.networkResponse.data);
                    json = trimMessage(json, "message");
                    if (json != null) {
                        Log.e("", "Error is: " + json);
                    }
                }
                if(listener != null)
                    listener.onResponse(code, null);
            }
        });
        if (Utils.networkIsAvailable( context)) {
            jr.setRetryPolicy(new DefaultRetryPolicy(60 * 1000, 0, 1));
            mRequestQueue.add(jr);
        }
        else {
            if(loader!= null)
                loader.setVisibility(View.GONE);
            Log.i(TAG, "sendData - no internet");
        }
    }

    public static String trimMessage(String json, String key){
        String trimmedString = null;

        try{
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch(JSONException e){
            Log.i("","string is is: " + json);
            Log.i("","trim message error is: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }

}