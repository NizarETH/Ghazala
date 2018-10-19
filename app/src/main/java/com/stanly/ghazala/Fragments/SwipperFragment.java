package com.stanly.ghazala.Fragments;

import android.support.v4.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.stanly.ghazala.Activities.MainActivity;
import com.stanly.ghazala.Adapters.DashBoardAdapter;
import com.stanly.ghazala.Beans.ElementSwipe;
import com.stanly.ghazala.MyApplication;
import com.stanly.ghazala.R;
import com.stanly.ghazala.Utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;


public class SwipperFragment extends Fragment {

    private View view;

    private ViewPager mViewPager;

    private TextView[] dots;
    private LinearLayout dotsLayout;
    private int[] layouts = {0,0,0,0};

    private boolean isTablet;
    private List<ElementSwipe> elementSwipes;
     public Realm realm;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


    }


    @Override
    public void onAttach(Context activity) {

         realm = Realm.getDefaultInstance();

        ((MainActivity)getActivity()).bodyFragment = "SwipperFragment";
        if(((MainActivity)getActivity()).extras == null)
            ((MainActivity)getActivity()).extras = new Bundle();
        int section_id = -1;
        if (getArguments() != null && getArguments().getInt("Section_id")!=0) {
            section_id = getArguments().getInt("Section_id");
            ((MainActivity)getActivity()).extras.putInt("Section_id", section_id);
            //id = section_id;
        }
        else if (getArguments() != null && getArguments().getInt("Category_id") != 0)
        {
            int category_id = getArguments().getInt("Category_id");
            ((MainActivity)getActivity()).extras.putInt("Category_id", category_id);
            //id = category_id;
        }
        isTablet = Utils.isTablet(activity);

        super.onAttach(activity);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);

        elementSwipes = new ArrayList<ElementSwipe>();
        elementSwipes = realm.where(ElementSwipe.class).findAll();

        if(((ImageView)((MainActivity)getActivity()).findViewById(R.id.logo_bar)) != null) {
            ((ImageView) ((MainActivity) getActivity()).findViewById(R.id.logo_bar)).setImageResource(R.drawable.home);
            ((ImageView) ((MainActivity) getActivity()).findViewById(R.id.logo_bar)).setColorFilter(ContextCompat.getColor(getActivity(), R.color.white));

        }
        super.onCreate(savedInstanceState);


    }

    //
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (elementSwipes.size() >1) {
            pageSwitcher(5, elementSwipes.size());
        }


        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = new View(getActivity());
        try {

            view = inflater.inflate(R.layout.swipper_center_top_layout_smart, container, false);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        dotsLayout = (LinearLayout) view.findViewById(R.id.layoutDots);

        addBottomDots(0);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);

        final DashBoardAdapter dashBoardAdapter = new DashBoardAdapter((MainActivity)getActivity(), elementSwipes);
        mViewPager.setAdapter(dashBoardAdapter);
        mViewPager.addOnPageChangeListener(viewPagerPageChangeListener);


        view.findViewById(R.id.open_time).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                TimingFragment t = new TimingFragment();
                ((MyApplication)getActivity().getApplication()).setFragment(getActivity(),t, R.id.fragment_container );

            }

        });
        view.findViewById(R.id.open_contact).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactFragment t = new ContactFragment();
                ((MyApplication)getActivity().getApplication()).setFragment(getActivity(),t, R.id.fragment_container );

            }
        });
        view.findViewById(R.id.open_agences).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AgencesFragment t = new AgencesFragment();
                ((MyApplication)getActivity().getApplication()).setFragment(getActivity(),t, R.id.fragment_container );

            }
        });


        view.findViewById(R.id.open_services).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ServicesFragment t = new ServicesFragment();
                ((MyApplication)getActivity().getApplication()).setFragment(getActivity(),t, R.id.fragment_container );

            }
        });


        /********************Animation**************************/


        /**************************************************/


        return view;
    }


    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];


        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {

            dots[i] = new TextView(getActivity());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.LTGRAY);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.DKGRAY);
    }
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {


        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };




    public Timer timer;
    int page = 0;




    @Override
    public void onPause() {
        if (timer!=null) {
            timer.cancel();
            //timer.purge();
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        if (timer!=null) {
            Runtime.getRuntime().gc();
            timer.cancel();
            //timer.purge();
        }
        super.onStart();
    }

    @Override
    public void onDestroy(){
        //		imageLoader.destroy();
        Runtime.getRuntime().gc();
        if (timer!=null) {
            //timer.purge();
            timer.cancel();
        }
        //		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
        //		.permitAll().build();
        //		StrictMode.setThreadPolicy(policy);
        //		sendRegistrationIdToBackend();


        super.onDestroy();
    }

    @Override
    public void onResume() {

        super.onResume();
        ((ImageView)((MainActivity)getActivity()).findViewById(R.id.logo_bar)).setImageResource(R.drawable.home);
        ((ImageView)((MainActivity)getActivity()).findViewById(R.id.logo_bar)).setColorFilter(ContextCompat.getColor(getActivity(), R.color.white));

    }


    public void pageSwitcher(int seconds, int max_page) {
        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(max_page), 0, seconds * 1000); // delay
        // in
        // milliseconds
    }
    // this is an inner class...
    class RemindTask extends TimerTask {

        int maxPage;


        @Override
        public void run() {

            // As the TimerTask run on a separate thread from UI thread we have
            // to call runOnUiThread to do work on UI thread.
            if(((MainActivity)getActivity()) == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else
                ((MainActivity)getActivity()).runOnUiThread(new Runnable() {
                    public void run() {
                        //					if(mViewPager.getChildAt(page) == null) {
                        //						List<Fragment> fragments2 = getFragments(elementSwipes);
                        //						//		Collections.reverse(fragments2);
                        //						pageAdapter = new MyPageAdapter(getFragmentManager()/*getChildFragmentManager()*/, fragments2);
                        ////						maxPage = pageAdapter.getCount();
                        //						mViewPager.setAdapter(pageAdapter);
                        ////						Toast.makeText(getActivity(), " adapter Item  : "+pageAdapter.getItem(page).getTag()+" Time of swipping to page : "+page+"/"+maxPage+"  of Child : "+mViewPager.getChildAt(page), 500).show();
                        //
                        //					}
                        if (page >= maxPage) { // In my case the number of pages are 5
                            page = 0;
                            mViewPager.setCurrentItem(page++);
                            // Showing a toast for just testing purpose

                        } else {
                            mViewPager.setCurrentItem(page++);
                        }

                        //					Toast.makeText(getActivity(), " adapter Item  : "+pageAdapter.getItem(page - 1).getTag()+" Time of swipping to page : "+page+"/"+maxPage+"  of Child : "+mViewPager.getChildAt(page), 500).show();
                    }
                    //				}
                });

        }


        public RemindTask(int maxPage) {
            super();
            this.maxPage = maxPage;
        }
    }

}
