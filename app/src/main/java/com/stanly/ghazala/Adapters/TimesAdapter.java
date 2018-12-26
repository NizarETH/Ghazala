package com.stanly.ghazala.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.stanly.ghazala.Activities.MainActivity;
import com.stanly.ghazala.Beans.Data;
import com.stanly.ghazala.Beans.HoraireData;
import com.stanly.ghazala.Fragments.DetailsLine;
import com.stanly.ghazala.MyApplication;
import com.stanly.ghazala.R;
import com.stanly.ghazala.Widgets.AutoResizeTextView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimesAdapter extends BaseAdapter {

    private List<HoraireData> rows ;
    private MainActivity mainActivity;
    private String from ;
    private String to ;
    private String date ;


    private PopupWindow pw;

    public TimesAdapter(List<HoraireData> rows, MainActivity mainActivity, String from, String to, String date) {
        this.rows = rows;
        this.mainActivity = mainActivity;
        this.from = from;
        this.to = to;
        this.date = date;
    }

    @Override
    public int getCount() {
        return rows.size();
    }

    @Override
    public Object getItem(int i) {
        return rows.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {



        ViewHolder holder = null;
        try {

            if (view == null) {
                LayoutInflater inflater = ((MainActivity) mainActivity).getLayoutInflater();
                view = inflater.inflate(R.layout.line_data, viewGroup, false);
                holder = new ViewHolder();
                holder.heure_depart = (AutoResizeTextView)view.findViewById(R.id.heure_depart);
                holder.heure_arrivee = (AutoResizeTextView)view.findViewById(R.id.heure_arrivee);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            if(i % 2 ==0 )
            {
                view.setBackgroundColor(mainActivity.getResources().getColor(R.color.light_gray));
            }
            else {
                view.setBackgroundColor(mainActivity.getResources().getColor(R.color.white));

            }
            final HoraireData data = rows.get(i);

            if(data != null)
            {


                holder.heure_depart.setText(data.getHd());
                holder.heure_arrivee.setText(data.getHa());
                holder.prix.setText(data.getPrix());

            }
            final ViewHolder finalHolder = holder;

               /* view.findViewById(R.id.item).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DetailsLine d = new DetailsLine();

                    Bundle b = new Bundle();
                    b.putString("from",from);
                    b.putString("prix", finalHolder.prix.getText().toString());
                    b.putString("to",to);

                    if(data != null)
                    b.putInt("id",data.getId());

                    b.putString("date",date);
                    d.setArguments(b);

                    ((MyApplication)mainActivity.getApplication()).setFragment(mainActivity,d,R.id.fragment_container);

                }
            });*/

        }catch (Exception e)
        {
            // FirebaseCrash.report(e);
        }

        return view;

    }
    static class ViewHolder {
        AutoResizeTextView heure_depart ;
        AutoResizeTextView heure_arrivee ;
        AutoResizeTextView prix ;

    }


}
