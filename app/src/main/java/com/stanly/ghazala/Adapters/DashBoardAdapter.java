package com.stanly.ghazala.Adapters;


import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stanly.ghazala.Activities.MainActivity;
import com.stanly.ghazala.Beans.ElementSwipe;
import com.stanly.ghazala.R;


import java.util.List;


/**
 * Created by nizar on 14/12/16.
 */
public class DashBoardAdapter extends PagerAdapter {

    private MainActivity mainActivity;
    private List<ElementSwipe> promos;


    public DashBoardAdapter(MainActivity mainActivity, List<ElementSwipe> promos) {
        this.mainActivity = mainActivity;
        this.promos = promos;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater inflater = LayoutInflater.from(mainActivity);

        final ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.consigne_item, container, false);


        Glide.with(mainActivity).load(promos.get(position).getImage()).into( ((ImageView)layout.findViewById(R.id.image)));

        ((ViewPager) container).addView(layout);

        layout.setTag(position);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }
    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}