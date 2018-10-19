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

import com.stanly.ghazala.Activities.MainActivity;
import com.stanly.ghazala.R;

public class ServicesFragment extends Fragment {

    private View v;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(((MainActivity)getActivity()).findViewById(R.id.logo_bar) != null) {
            ((ImageView) ((MainActivity) getActivity()).findViewById(R.id.logo_bar)).setImageResource(R.drawable.works);
            ((ImageView) ((MainActivity) getActivity()).findViewById(R.id.logo_bar)).setColorFilter(ContextCompat.getColor(getActivity(), R.color.white));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(((MainActivity)getActivity()).findViewById(R.id.logo_bar) != null) {
            ((ImageView) ((MainActivity) getActivity()).findViewById(R.id.logo_bar)).setImageResource(R.drawable.works);
            ((ImageView) ((MainActivity) getActivity()).findViewById(R.id.logo_bar)).setColorFilter(ContextCompat.getColor(getActivity(), R.color.white));
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.services, container, false);

        return v;
    }
}
