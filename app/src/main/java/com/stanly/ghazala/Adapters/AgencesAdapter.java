package com.stanly.ghazala.Adapters;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.stanly.ghazala.Beans.Agence ;
 import com.stanly.ghazala.MyApplication;
import com.stanly.ghazala.R;
import com.stanly.ghazala.Activities.MainActivity;
import com.stanly.ghazala.Widgets.AutoResizeTextView;

import java.util.List;

/**
 * @author stanly
 *
 */
public class AgencesAdapter extends BaseAdapter {


    private MainActivity mainActivity;
    private boolean searchEnabled;

    private List<Agence> addresse;


    public AgencesAdapter(MainActivity mainActivity, boolean searchEnabled, List<Agence> addresse) {
        this.mainActivity = mainActivity;
        this.searchEnabled = searchEnabled;
        this.addresse = addresse;
    }

    @Override
    public int getCount() {
        if (searchEnabled)
            return addresse.size();
        else
            return addresse.size();
    }

    @Override
    public Object getItem(int i) {
        return addresse.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((MainActivity) mainActivity).getLayoutInflater();
            row = inflater.inflate(R.layout.agence, viewGroup, false);
            holder = new ViewHolder();
            holder.agence_name = (AutoResizeTextView) row.findViewById(R.id.agence_name);
            holder.agence_addresse = (AutoResizeTextView) row.findViewById(R.id.agence_address);
            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }


        final Agence agency = addresse.get(i);

        if (agency != null && agency.getName() != null && !agency.getName().isEmpty()) {

            holder.agence_name.setText("" + agency.getName());

            holder.agence_addresse.setText("" + agency.getAdresse());


            row.findViewById(R.id.map_address).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCall(agency.getPhone());
                }
            });

        }
        return row;
    }

    static class ViewHolder {

        AutoResizeTextView agence_name;
        AutoResizeTextView agence_addresse;
    }
    public void onCall( String numberPhone) {
        Intent callIntent = new Intent(Intent.ACTION_CALL); //use ACTION_CALL class
        callIntent.setData(Uri.parse("tel:"+numberPhone));    //this is the phone number calling
        //check permission
        //If the device is running Android 6.0 (API level 23) and the app's targetSdkVersion is 23 or higher,
        //the system asks the user to grant approval.
        if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //request permission from user if the app hasn't got the required permission
            ActivityCompat.requestPermissions(mainActivity,
                    new String[]{Manifest.permission.CALL_PHONE},   //request specific permission from user
                    10);
            return;
        }else {     //have got permission
            try{
                mainActivity.startActivity(callIntent);  //call activity and make phone call
            }
            catch (android.content.ActivityNotFoundException ex){
                Toast.makeText(mainActivity,"Error !",Toast.LENGTH_SHORT).show();
            }
        }
    }
}