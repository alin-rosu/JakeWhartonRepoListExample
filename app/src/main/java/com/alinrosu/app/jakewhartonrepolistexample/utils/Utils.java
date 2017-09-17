package com.alinrosu.app.jakewhartonrepolistexample.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by alin on 9/17/2017.
 */

public class Utils {

    public static boolean networkIsAvailable(Context parentActivity) {
        if(isAirplaneModeOn(parentActivity)){
            return  false;
        }else {
            if(parentActivity != null) {
                ConnectivityManager connectivityManager = (ConnectivityManager) parentActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnected();
            }else {
                return false;
            }
        }
    }

    private static boolean isAirplaneModeOn(Context context) {
        try{
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return Settings.System.getInt(context.getContentResolver(),
                        Settings.System.AIRPLANE_MODE_ON, 0) != 0;
            } else {
                return Settings.Global.getInt(context.getContentResolver(),
                        Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
            }
        }catch (Exception e){
            Log.e("","ERROR is airplane mode:" + e.getMessage());
            return false;
        }
    }
}
