package com.stanly.ghazala;

import android.app.Application;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.androidnetworking.AndroidNetworking;
import com.stanly.ghazala.Activities.MainActivity;

import java.util.concurrent.TimeUnit;


import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                //.addNetworkInterceptor(new CallServerInterceptor(true))
                .build();
        AndroidNetworking.initialize(getApplicationContext(), okHttpClient);

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public void setFragment(FragmentActivity fragmentActivity, Fragment fm, int layout_id){
        ((MainActivity) fragmentActivity).bodyFragment  = fm.getClass().getSimpleName();

        // Replace fragmentCotainer with your container id

        fragmentActivity.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                .replace(layout_id, fm, ((MainActivity) fragmentActivity).bodyFragment).addToBackStack(((MainActivity) fragmentActivity).bodyFragment).commitAllowingStateLoss();

    }
    public void setDownFragment(FragmentActivity fragmentActivity, Fragment fm, int layout_id){
        ((MainActivity) fragmentActivity).bodyFragment  = fm.getClass().getSimpleName();
        // Replace fragmentCotainer with your container id

        fragmentActivity.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_top, R.anim.slide_out_bottom)
                .replace(layout_id, fm, ((MainActivity) fragmentActivity).bodyFragment).addToBackStack(((MainActivity) fragmentActivity).bodyFragment).commitAllowingStateLoss();
    }
    public void setUpFragment(FragmentActivity fragmentActivity,Fragment fm, int layout_id){
        ((MainActivity) fragmentActivity).bodyFragment  = fm.getClass().getSimpleName();

        fragmentActivity.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_top, R.anim.slide_out_bottom)
                .replace(layout_id, fm, ((MainActivity) fragmentActivity).bodyFragment).addToBackStack(((MainActivity) fragmentActivity).bodyFragment).commitAllowingStateLoss();
    }
}
