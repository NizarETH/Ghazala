package com.stanly.ghazala.Fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stanly.ghazala.Activities.MainActivity;
import com.stanly.ghazala.Beans.Data;
import com.stanly.ghazala.Beans.Depart;
import com.stanly.ghazala.Beans.HoraireData;
import com.stanly.ghazala.Beans.SuperData;
import com.stanly.ghazala.MyApplication;
import com.stanly.ghazala.R;
import com.stanly.ghazala.Utils.Constants;
import com.stanly.ghazala.Utils.Utils;

import org.json.JSONObject;
import org.jsoup.nodes.Element;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmList;


public class TimingFragment extends Fragment {

    private String url = "http://www.transghazala.ma/get-horaire";
     private Realm r;
    private Realm realm;
    private Element el;
    private Element el1;
    private Element el2;
    private ProgressDialog dialog;
    private String dateDedepart;
    private String KeyArrive = "";
    private String KeyDepart = "";
    private int mYear, mMonth, mDay;
    private String dateDedepartIntern;
    private int Year, Month, Day;
    public String cityFrom;
    public String cityTo;
    private CheckedTextView international;
    private final HashMap map = new HashMap();
    private final HashMap mapPost = new HashMap();
    private CheckedTextView national;
    private AutoCompleteTextView from ;
    private AutoCompleteTextView to ;
    private  String authenticity_token;
    private CheckedTextView tomorrow ;

    private CheckedTextView today ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = Realm.getDefaultInstance();

        if(((MainActivity)getActivity()).findViewById(R.id.logo_bar) != null) {
            ((ImageView) ((MainActivity) getActivity()).findViewById(R.id.logo_bar)).setImageResource(R.drawable.clock);
            ((ImageView) ((MainActivity) getActivity()).findViewById(R.id.logo_bar)).setColorFilter(ContextCompat.getColor(getActivity(), R.color.white));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(((MainActivity)getActivity()).findViewById(R.id.logo_bar) != null) {
            ((ImageView) ((MainActivity) getActivity()).findViewById(R.id.logo_bar)).setImageResource(R.drawable.clock);
            ((ImageView) ((MainActivity) getActivity()).findViewById(R.id.logo_bar)).setColorFilter(ContextCompat.getColor(getActivity(), R.color.white));
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.timing, container, false);
        from = (AutoCompleteTextView) view.findViewById(R.id.from);

        to = (AutoCompleteTextView) view.findViewById(R.id.to);
        final EditText agendaEdit = (EditText) view.findViewById(R.id.agendaEdit);
        final List<Depart> cities = realm.where(Depart.class).findAll();
        from.setText("");
        to.setText("");

        String citiesNames[] = new String[cities.size()];
        for (int i = 0; i < cities.size(); i++) {
            if(cities.get(i) != null) {
                citiesNames[i] = cities.get(i).getName();
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, citiesNames);
        from.setAdapter(adapter);
        to.setAdapter(adapter);



        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String to_Day = formatter.format(date);

        agendaEdit.setText(to_Day);

        agendaEdit.setInputType(InputType.TYPE_NULL);

        tomorrow = (CheckedTextView) view.findViewById(R.id.tomorrow);

        today = (CheckedTextView) view.findViewById(R.id.today);
        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tomorrow.isChecked())
                {
                    tomorrow.toggle();
                }
                if(today.isChecked()) {
                    today.toggle();
                }
                else {
                    today.setChecked(true);
                    Date date = Calendar.getInstance().getTime();

                    DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    String today = formatter.format(date);

                    agendaEdit.setText(today);
                }
            }
        });
        tomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(today.isChecked())
                {
                    today.toggle();
                }
                if(tomorrow.isChecked())
                {
                    tomorrow.toggle();
                }
                else {
                    tomorrow.setChecked(true);
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                    Date tomorrow = calendar.getTime();
                    DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    String DateTomorrow = formatter.format(tomorrow);
                    agendaEdit.setText(DateTomorrow);
                }


            }
        });

        agendaEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker v, int year, int monthOfYear, int dayOfMonth) {
                                String  month = "";
                                if(monthOfYear + 1 <= 9)
                                    month = "0"+(monthOfYear + 1);
                                else
                                    month = ""+(monthOfYear + 1);
                                agendaEdit.setText(dayOfMonth + "-" + month + "-" + year);
                                Year = year;
                                Month = (monthOfYear + 1);
                                Day = dayOfMonth;
                                ((CheckedTextView) view.findViewById(R.id.today)).setChecked(false);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }

        });

        view.findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if((from != null && from.getText() != null && !from.getText().toString().isEmpty()) || ( to != null && to.getText() != null && !to.getText().toString().isEmpty())) {
                    if (from.getText() != null && to.getText() != null && Utils.isUpperCase(from.getText().toString()) && Utils.isUpperCase(to.getText().toString())) {
                    dialog = new ProgressDialog(getActivity());
                    dialog.setMessage("Loading, please wait !");
                    dialog.show();

                    AndroidNetworking.post(url)
                            .addBodyParameter("aller", "0")

                            .addBodyParameter("depart", from.getText().toString())
                            .addBodyParameter("arrive", to.getText().toString() )
                            .addBodyParameter("ddepart", agendaEdit.getText().toString())
                            .setTag("Request")
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    realm.beginTransaction();
                                    realm.where(SuperData.class).findAll().deleteAllFromRealm();
                                    realm.where(Data.class).findAll().deleteAllFromRealm();
                                    realm.where(HoraireData.class).findAll().deleteAllFromRealm();
                                    realm.createObjectFromJson(SuperData.class, response);

                                    RealmList<HoraireData> all = new RealmList<>();
                                    HoraireData horaireData = null;
                                    Data data = realm.where(Data.class).findFirst();
                                    int id = 0;
                                    String DataSplit[] =  data.getHoraire().replaceAll("\\[\\{"," ")
                                            .replaceAll("\\}"," ")
                                            .replaceAll("\\{"," ")
                                            .replaceAll("\\}\\]"," ")
                                            .split(",");

                                    horaireData = realm.createObject(HoraireData.class, id++);

                                    for (int i = 0; i < DataSplit.length; i++) {

                                          if(DataSplit[i].contains("ha")) {
                                              String Ha = DataSplit[i].replaceAll("\"ha\":", "").replaceAll("\""," ");
                                              horaireData.setHa(Ha);
                                          }
                                          if(DataSplit[i].contains("hd")) {
                                              String Hd = DataSplit[i].replaceAll("\"hd\":", "").replaceAll("\""," ");

                                              horaireData.setHd(Hd);
                                          }
                                          if(DataSplit[i].contains("prix")) {
                                              String prix = DataSplit[i].replaceAll("\"prix\":", "").replaceAll("\""," ");

                                              horaireData.setPrix(prix);
                                              horaireData = realm.createObject(HoraireData.class, id++);

                                          }
                                        all.add(horaireData);

                                    }
                                    data.setHoraireData(all);
                                    realm.commitTransaction();
                                    ResultsFragment resultsFragment =  new ResultsFragment();
                                    Bundle b = new Bundle();

                                    b.putString("depart", from.getText().toString());
                                    b.putString("arrive", to.getText().toString());
                                    b.putString("date", agendaEdit.getText().toString());
                                    resultsFragment.setArguments(b);

                                    ((MyApplication)getActivity().getApplication()).setUpFragment(getActivity(), resultsFragment, R.id.fragment_container);

                                    if (dialog.isShowing()) {
                                        dialog.dismiss();
                                    }
                                 }

                                @Override
                                public void onError(ANError error) {
                                    if (dialog.isShowing()) {
                                        dialog.dismiss();
                                    }
                                }
                            });
                } else
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.alert) , Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.alert) , Toast.LENGTH_LONG).show();

            }
        });


        return view;
    }
}
