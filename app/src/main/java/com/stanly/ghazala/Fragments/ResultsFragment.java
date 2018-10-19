package com.stanly.ghazala.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.stanly.ghazala.Activities.MainActivity;
import com.stanly.ghazala.Adapters.TimesAdapter;
import com.stanly.ghazala.Beans.Data;
import com.stanly.ghazala.Beans.HoraireData;
import com.stanly.ghazala.R;
import com.stanly.ghazala.Widgets.AutoResizeTextView;

import java.util.List;

import io.realm.Realm;

public class ResultsFragment extends Fragment {

    private View v;
    private Realm r;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       r = Realm.getDefaultInstance();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(((MainActivity)getActivity()).findViewById(R.id.logo_bar) != null) {
            ((ImageView) ((MainActivity) getActivity()).findViewById(R.id.logo_bar)).setImageResource(R.drawable.info_icon);
            ((ImageView) ((MainActivity) getActivity()).findViewById(R.id.logo_bar)).setColorFilter(ContextCompat.getColor(getActivity(), R.color.white));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.table_result_timing, container, false);

        Bundle b = getArguments();
        if(b != null)
        {
          String depart = b.getString("depart");
            String arrive =   b.getString("arrive");
            String date =   b.getString("date");

            ((AutoResizeTextView)v.findViewById(R.id.date)).setText(date);
            ((AutoResizeTextView)v.findViewById(R.id.from)).setText(depart);
            ((AutoResizeTextView)v.findViewById(R.id.to)).setText(arrive);

            ListView listView = (ListView) v.findViewById(R.id.list_times);

            List<HoraireData> horaireData = r.where(HoraireData.class).findAll();
            listView.setAdapter(new TimesAdapter(horaireData,(MainActivity)getActivity(),depart,arrive,date));
        }



        return v;
    }
    @Override
    public void onResume() {
        super.onResume();
        if(((MainActivity)getActivity()).findViewById(R.id.logo_bar) != null) {
            ((ImageView) ((MainActivity) getActivity()).findViewById(R.id.logo_bar)).setImageResource(R.drawable.info_icon);
            ((ImageView) ((MainActivity) getActivity()).findViewById(R.id.logo_bar)).setColorFilter(ContextCompat.getColor(getActivity(), R.color.white));
        }
    }
}
