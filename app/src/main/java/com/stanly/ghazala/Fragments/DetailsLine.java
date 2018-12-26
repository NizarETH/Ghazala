package com.stanly.ghazala.Fragments;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stanly.ghazala.Activities.MainActivity;
import com.stanly.ghazala.Beans.Models.Weather;
import com.stanly.ghazala.Activities.MainActivity;

import com.stanly.ghazala.Beans.Models.Weather;
 import com.stanly.ghazala.MyApplication;
import com.stanly.ghazala.R;
import com.stanly.ghazala.Utils.JSONWeatherParser;
import com.stanly.ghazala.Utils.Utils;
import com.stanly.ghazala.Utils.WeatherHttpClient;
import com.stanly.ghazala.Widgets.SmallBang;
import com.stanly.ghazala.Widgets.SmallBangListener;


import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import io.realm.Realm;

/**
 * Created by next on 22/01/17.
 */
public class DetailsLine extends Fragment {


    private SmallBang mSmallBang;
    private ImageView mImage;
     JSONWeatherTask task = null;

    private TextView fromVille;
    private TextView toVille;
    private TextView Date;
    private TextView timeDepart;
    private TextView timeArrivee;

    private TextView info;
    private TextView info_data;
    private TextView date_boarding;
    private ImageView imgView;

    private String from;
    private String to;
    private String date;
    private String depart;
    private String arrivee;
    private String infos;
    private String prix;
    private int id_intern;
    private int id;

    private TextView time_depart1;

    private TextView priceBebe;
    private TextView priceAdulte;
    private TextView reducBebeTxt;
    private TextView reducEnfantTxt;
    private TextView priceEnfant;

    private TextView prix1;
    private TextView ville_depart;
    private TextView time_arrivee;
    private TextView ville_arrivee;
    private TextView duree;
    private TextView tempValue;
    private TextView environ;
    private TextView info_price;
    private ProgressBar progress;
    private LinearLayout band_weather;
    private LinearLayout bande_price;
    private LinearLayout boarding_date;
    private LinearLayout info_bar;
    private ImageView save_travel;
    private LinearLayout weatherLayout;
    private LinearLayout progressbarWeather;
    private Realm realm;
    private static String IMG_URL = "http://openweathermap.org/img/w/";

    private ImageView map_travel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
         realm  = Realm.getDefaultInstance();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSmallBang = SmallBang.attach2Window(getActivity());

        Bundle b = getArguments();

        if( b != null)
        {

            from = b.getString("from");
            to = b.getString("to");

            date = b.getString("date");

            prix = b.getString("prix");
            id = b.getInt("id");
        }


        if(((MainActivity)getActivity()).findViewById(R.id.logo_bar) != null) {
            ((ImageView) ((MainActivity) getActivity()).findViewById(R.id.logo_bar)).setImageResource(R.drawable.info_icon);
            ((ImageView) ((MainActivity) getActivity()).findViewById(R.id.logo_bar)).setColorFilter(ContextCompat.getColor(getActivity(), R.color.bleuDarkCTM));
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if(((MainActivity)getActivity()).findViewById(R.id.logo_bar) != null) {
            ((ImageView) ((MainActivity) getActivity()).findViewById(R.id.logo_bar)).setImageResource(R.drawable.info_icon);
            ((ImageView) ((MainActivity) getActivity()).findViewById(R.id.logo_bar)).setColorFilter(ContextCompat.getColor(getActivity(), R.color.bleuDarkCTM));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_general, container, false);



        progress = (ProgressBar) view.findViewById(R.id.progressWeather);
        fromVille = (TextView) view.findViewById(R.id.from);
        tempValue = (TextView) view.findViewById(R.id.degree_temp);
        toVille = (TextView) view.findViewById(R.id.to);
        Date = (TextView) view.findViewById(R.id.date);
        timeDepart = (TextView) view.findViewById(R.id.time_depart);
        timeArrivee = (TextView) view.findViewById(R.id.time_arrivee);
        prix1 = (TextView) view.findViewById(R.id.prix);
        band_weather = (LinearLayout) view.findViewById(R.id.band_weather);
        save_travel = (ImageView) view.findViewById(R.id.save_travel);

        map_travel = (ImageView) view.findViewById(R.id.map_travel);


        time_depart1 = (TextView) view.findViewById(R.id.time_depart1);
        environ = (TextView) view.findViewById(R.id.environ);
        ville_depart = (TextView) view.findViewById(R.id.ville_depart);
        ville_arrivee = (TextView) view.findViewById(R.id.ville_arrivee);
        info = (TextView) view.findViewById(R.id.info);
        duree = (TextView) view.findViewById(R.id.duree);



        prix1.setText(prix + " DH");

        fromVille.setText(from);
        ville_depart.setText(from);


        toVille.setText(to);
        ville_arrivee.setText(to);

        imgView = (ImageView) view.findViewById(R.id.condIcon);


        String city = Utils.toCity(to) + ",MA&APPID=fb874a1d06e1d5190417ac3694a0562c";
        task = new JSONWeatherTask();
        task.execute(new String[]{city});


        return view;
    }



    private class JSONWeatherTask extends AsyncTask<String, Integer, Weather> {
        String path = "";

        @Override
        protected Weather doInBackground(String... params) {
            Weather weather = new Weather();
            String data = ((new WeatherHttpClient()).getWeatherData(params[0]));

            if (data != null && !data.isEmpty()) {
                try {
                    weather = JSONWeatherParser.getWeather(data);
                    if (weather != null) {
                        String code = weather.currentCondition.getIcon();

                        try {
                            path = Utils.Download_icon(code, (MainActivity) getActivity());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (path != null) {
                            if (path.isEmpty())
                                path = IMG_URL.concat(code.concat(".png"));

                        }
                    }

                    // Let's retrieve the icon
                    //weather.iconData = ((new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return weather;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progress.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);


            if (weather != null && weather.location != null) {

                progress.setVisibility(View.GONE);
                band_weather.setVisibility(View.VISIBLE);
                imgView.setVisibility(View.VISIBLE);

                tempValue.setText("" + Math.round((weather.temperature.getTemp() - 273.15)) + "°C");
                Glide.with((MainActivity) getActivity()).load(path).into(imgView);
            }

        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (task != null)
            task.cancel(true);
    }


    public void like(View view) {
        save_travel.setColorFilter(ContextCompat.getColor(getActivity(), R.color.soft_grey));

        mSmallBang.bang(view);
        mSmallBang.setmListener(new SmallBangListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationEnd() {
                // Toast.makeText(getActivity(), " Saved !", Toast.LENGTH_SHORT).show();
                save_travel.setEnabled(false);
            }
        });
    }

}