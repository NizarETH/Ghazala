package com.stanly.ghazala.Fragments;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.stanly.ghazala.Activities.MainActivity;
import com.stanly.ghazala.R;

/**
 * Created by next on 22/03/17.
 */
public class ContactFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(((MainActivity)getActivity()).findViewById(R.id.logo_bar) != null) {
            ((ImageView) ((MainActivity) getActivity()).findViewById(R.id.logo_bar)).setImageResource(R.drawable.tel);
            ((ImageView) ((MainActivity) getActivity()).findViewById(R.id.logo_bar)).setColorFilter(ContextCompat.getColor(getActivity(), R.color.white));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(((MainActivity)getActivity()).findViewById(R.id.logo_bar) != null) {
            ((ImageView) ((MainActivity) getActivity()).findViewById(R.id.logo_bar)).setImageResource(R.drawable.tel);
            ((ImageView) ((MainActivity) getActivity()).findViewById(R.id.logo_bar)).setColorFilter(ContextCompat.getColor(getActivity(), R.color.white));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contact_fragment, container, false);
        LinearLayout call_ctm = (LinearLayout) v.findViewById(R.id.call_ctm);

        LinearLayout open_gmail = (LinearLayout) v.findViewById(R.id.open_gmail);
        LinearLayout open_gmail_ghazala = (LinearLayout) v.findViewById(R.id.open_gmail_ghazala);

        open_gmail_ghazala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","contact@transghazala.ma", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });



        call_ctm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);

                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            getActivity(),
                            new String[]{Manifest.permission.CALL_PHONE},
                            123);
                } else {
                    startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:0522605070")));
                }
            }
        });

        open_gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","stanly.inc.dev@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact ghazala");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });

        return  v;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 123:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:0522605070"));
                    startActivity(callIntent);
                } else {
                    Log.d("TAG", "Call Permission Not Granted");
                }
                break;

            default:
                break;
        }
    }
    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getActivity().getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }
}