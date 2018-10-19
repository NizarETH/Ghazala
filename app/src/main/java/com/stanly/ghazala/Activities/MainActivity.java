package com.stanly.ghazala.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.stanly.ghazala.Beans.ElementSwipe;
import com.stanly.ghazala.Fragments.AgencesFragment;
import com.stanly.ghazala.Fragments.ContactFragment;
import com.stanly.ghazala.Fragments.JobsFragment;
import com.stanly.ghazala.Fragments.ServicesFragment;
import com.stanly.ghazala.Fragments.SwipperFragment;
import com.stanly.ghazala.Fragments.TimingFragment;
import com.stanly.ghazala.MyApplication;
import com.stanly.ghazala.R;
import com.stanly.ghazala.Widgets.slidingMenu.SlidingMenu;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import io.realm.Realm;

public class MainActivity extends FragmentActivity {


    public String bodyFragment;
    public Bundle extras;
    private SlidingMenu menu;
    private View shadow;
    private String lang;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        MobileAds.initialize(this,"ca-app-pub-4398691256924309~6340352039");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("6B15F49C17E30E253F6AD419AE6C6B0B")
                .build();

        mAdView.loadAd(adRequest);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4398691256924309/4120424946");
        mInterstitialAd.loadAd(new AdRequest.Builder()
                .addTestDevice("6B15F49C17E30E253F6AD419AE6C6B0B").build());

        lang = Locale.getDefault().getLanguage();

        menu = new SlidingMenu(MainActivity.this);
        menu.setMode(SlidingMenu.LEFT);


        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.attachToActivity(MainActivity.this, SlidingMenu.SLIDING_CONTENT);

        menu.setSlidingEnabled(false);
        menu.setFadeDegree(0.25f);
        menu.setMenu(R.layout.selection_favorites_view);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        if (metrics.widthPixels > metrics.heightPixels) {
            menu.setBehindOffset(metrics.widthPixels - (metrics.widthPixels / 2));
        } else {
            menu.setBehindOffset(metrics.widthPixels - (metrics.heightPixels / 2));
        }


            menu.setBehindWidthRes(R.dimen.menu_width_res_phone);

        Animation slide_in_left = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_left);


        menu.findViewById(R.id.home).setAnimation(slide_in_left);
        menu.findViewById(R.id.agenda).setAnimation(slide_in_left);
        menu.findViewById(R.id.horaire).setAnimation(slide_in_left);
        menu.findViewById(R.id.agences).setAnimation(slide_in_left);
        menu.findViewById(R.id.services).setAnimation(slide_in_left);
        menu.findViewById(R.id.emploi).setAnimation(slide_in_left);
        menu.findViewById(R.id.contact).setAnimation(slide_in_left);


        ImageView Homebutton = (ImageView)
                findViewById(R.id.menu_home_button);

        shadow = findViewById(R.id.shadowview);
        Homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenMenu();
            }
        });
        shadow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseMenu();
            }
        });

        findViewById(R.id.logo_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwipperFragment swipperFragment = new SwipperFragment();
                ((MyApplication) getApplication()).setFragment(MainActivity.this, swipperFragment, R.id.fragment_container);

            }
        });


        findViewById(R.id.open_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseMenu();
                SwipperFragment swipperFragment = new SwipperFragment();
                ((MyApplication) getApplication()).setFragment(MainActivity.this, swipperFragment, R.id.fragment_container);

            }
        });

        findViewById(R.id.open_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseMenu();

                TimingFragment ag = new TimingFragment();
                ((MyApplication) getApplication()).setFragment(MainActivity.this, ag, R.id.fragment_container);
            }
        });
        findViewById(R.id.open_services).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseMenu();
                ServicesFragment servicesFragment = new ServicesFragment();
                ((MyApplication) getApplication()).setFragment(MainActivity.this, servicesFragment, R.id.fragment_container);

            }
        });

        findViewById(R.id.open_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseMenu();
                ContactFragment contactFragment = new ContactFragment();
                ((MyApplication) getApplication()).setFragment(MainActivity.this, contactFragment, R.id.fragment_container);


            }
        });

        findViewById(R.id.open_career).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseMenu();
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }

                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        // Load the next interstitial.
                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    }

                });

                JobsFragment jobsFragment = new JobsFragment();
                ((MyApplication) getApplication()).setFragment(MainActivity.this, jobsFragment, R.id.fragment_container);

            }
        });

        findViewById(R.id.open_agences).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseMenu();
                AgencesFragment agencesFragment = new AgencesFragment();
                ((MyApplication) getApplication()).setFragment(MainActivity.this, agencesFragment, R.id.fragment_container);

            }
        });


        Map<String, Object> map = (HashMap<String, Object>) getLastCustomNonConfigurationInstance();
        String type = null;
        if (map == null || map.isEmpty()) {

            SwipperFragment swipperFragment = new SwipperFragment();
            bodyFragment = "SwipperFragment";
            swipperFragment.setArguments(extras);
            ((MyApplication) getApplication()).setFragment(MainActivity.this, swipperFragment, R.id.fragment_container);

        } else {

        }
     }

    public void CloseMenu() {
        shadow.setVisibility(View.GONE);

        menu.toggle(true);
    }

    public void OpenMenu() {

        menu.showMenu(true);
        shadow.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            final AlertDialog.Builder b = new AlertDialog.Builder(this, R.style.MyDialogTheme);
            b.setTitle(getString(R.string.app_name));

            b.setMessage(getResources().getString(R.string.close));
            b.setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            b.show();
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }
}
