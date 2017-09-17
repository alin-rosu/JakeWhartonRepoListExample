package com.alinrosu.app.jakewhartonrepolistexample;

import android.app.Application;
import android.content.res.Configuration;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by alin on 9/17/2017.
 */

public class ApplicationClass extends Application { //this is the application class, this is not needed for where in the code I've reached. Here I create object that need to be global,
    // and need to stay alive more than the lifecycle of an activity
    private static final String TAG = "MyApp";
    private static ApplicationClass singleton;
    public RequestQueue mRequestQueue;
    public static RealmConfiguration Config;
    public static Realm realm;

    public Realm getRealm() {
        if (realm == null)
            realm = Realm.getInstance(Config);
        return realm;
    }

    public static ApplicationClass getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        mRequestQueue = Volley.newRequestQueue(ApplicationClass.this);
        Realm.init(ApplicationClass.this);
        Config = new RealmConfiguration.Builder().name("myrealm.realm").schemaVersion(1).deleteRealmIfMigrationNeeded().build();
        realm = Realm.getInstance(Config);
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
