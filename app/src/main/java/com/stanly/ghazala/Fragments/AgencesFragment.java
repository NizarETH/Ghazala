package com.stanly.ghazala.Fragments;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.stanly.ghazala.Activities.MainActivity;
import com.stanly.ghazala.Adapters.AgencesAdapter;
import com.stanly.ghazala.Beans.Agence;
import com.stanly.ghazala.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;


public class AgencesFragment extends Fragment {



    private Realm realm;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        realm = Realm.getDefaultInstance();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(((MainActivity)getActivity()).findViewById(R.id.logo_bar) != null) {
            ((ImageView) ((MainActivity) getActivity()).findViewById(R.id.logo_bar)).setImageResource(R.drawable.map);
            ((ImageView) ((MainActivity) getActivity()).findViewById(R.id.logo_bar)).setColorFilter(ContextCompat.getColor(getActivity(), R.color.white));
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if(((MainActivity)getActivity()).findViewById(R.id.logo_bar) != null) {
            ((ImageView) ((MainActivity) getActivity()).findViewById(R.id.logo_bar)).setImageResource(R.drawable.map);
            ((ImageView) ((MainActivity) getActivity()).findViewById(R.id.logo_bar)).setColorFilter(ContextCompat.getColor(getActivity(), R.color.white));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.agence_fragment, container, false);


        final ListView listView = (ListView) v.findViewById(R.id.list_travels);

        final List<Agence> cities =  realm.where(Agence.class).findAll();

        listView.setAdapter(  new AgencesAdapter((MainActivity) getActivity(), false, cities));


        try {

            ((EditText)v.findViewById(R.id.search_agence)).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    /***** Insensitive : pour ignore case maj = min ************/


                    List<Agence> filtredNames = new ArrayList<>();


                    for (int i = 0; i <cities.size() ; i++) {


                        if((cities.get(i).getName().toLowerCase().contains(s.toString().toLowerCase())))
                        {

                            filtredNames.add(cities.get(i));
                        }
                    }


                    listView.setAdapter( new AgencesAdapter((MainActivity) getActivity(),true, filtredNames));
                    if(s != null && s.toString().isEmpty())
                        listView.setAdapter(  new AgencesAdapter((MainActivity) getActivity(), false, cities));

                    ((BaseAdapter)listView.getAdapter()).notifyDataSetInvalidated();

                    ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }catch (Exception e)
        {
            Log.e("",""+e.getMessage());
        }
        return v;
    }

}