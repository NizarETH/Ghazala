package com.stanly.ghazala.Utils;

import android.app.*;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.stanly.ghazala.Activities.MainActivity;
import com.stanly.ghazala.Beans.Data;
import com.stanly.ghazala.Beans.HoraireData;
import com.stanly.ghazala.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Realm;

/**
 * @author
 *
 */
public class Utils {
    private static final int timeout = 20000;
    public static String LANG = "LANG";
    public static String fontPath = "fonts/montserrat/Montserrat-Light.otf";//"fonts/montserrat/Montserrat-Hairline.otf";//



    public static void changeLocale(String string, Context context) {
        Configuration config = new Configuration();
        Locale locale = new Locale(string);
        Locale.setDefault(locale);

        config.locale = locale;
        context.getResources()
                .updateConfiguration(
                        config, null);

    }
    public static void setupUI(View view , final MainActivity mainActivity) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(mainActivity);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView, mainActivity);
            }
        }
    }
    public static void hideSoftKeyboard(MainActivity activity) {
        if(activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        }

    }

    public static android.app.ProgressDialog processSnackbar(Context context, String s){
        android.app.ProgressDialog pSnackbar = null;
        try {

            pSnackbar = new android.app.ProgressDialog(context, R.style.CustomDialog) {
                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);

                    ProgressBar progress = (ProgressBar) findViewById(android.R.id.progress);
                    LinearLayout bodyLayout = (LinearLayout) progress.getParent();
                    TextView messageView = (TextView) bodyLayout.getChildAt(1);

                    messageView.setPadding(20,0,0,0);
                    LinearLayout.LayoutParams llp =
                            (LinearLayout.LayoutParams) messageView.getLayoutParams();
                    llp.width = 0;
                    llp.weight = 1;


                    bodyLayout.removeAllViews();
                    bodyLayout.addView(messageView, llp);
                    bodyLayout.addView(progress);
                }
            };
            pSnackbar.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
            pSnackbar.setMessage(s);
            pSnackbar.getWindow().setGravity(Gravity.BOTTOM);
            pSnackbar.setCancelable(false);

            return pSnackbar;
        }catch (Exception e)
        {
            Log.e("Error ",e.getMessage());
        }
        finally {
            return pSnackbar;
        }
    }

    public static String retreiveJsonFromAssetsFile(String fileName, Context context) {
        StringBuilder returnString = new StringBuilder();


        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;


        try {
            fIn = context.getResources().getAssets()
                    .open(fileName, Context.MODE_WORLD_READABLE);
            isr = new InputStreamReader(fIn);//,"ISO-8859-1");
            input = new BufferedReader(isr);
            String line = "";

            while ((line = input.readLine()) != null) {
                returnString.append(line);
                //Log.e(" File content :  "," ::  "+returnString);


            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fIn != null)
                    fIn.close();
                if (input != null)
                    input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
    }


    public static String remove2Dots(String value){
        if(value != null && !value.isEmpty()) {
            value = value.replaceAll(":", "h");
            return value;
        }
        else
            return "";//Removes all items in brackets

    }
    public static boolean isUpperCase(String s)
    {
        for (int i=0; i<s.length(); i++)
        {
            if (Character.isLowerCase(s.charAt(i)) )	{
                return false;
            }
        }
        return true;
    }

    public static int handleTime (int t2, int t1)
    {
        if(t2>t1)
        {
            return t2-t1;
        }
        else
        {
            int a = 24*60 - t1 ;
            return a +t2;

        }


    }
    public static int toMins(String s) {
        if(s.contains("Lendemain"))
        {
            s = s.replaceAll("Lendemain","");
        }
        s = s.replaceAll(" ","");
        String[] hourMin = s.split("h");
        int hour = Integer.parseInt(hourMin[0]);
        int mins = Integer.parseInt(hourMin[1]);
        int hoursInMins = hour * 60;
        return hoursInMins + mins;
    }



    public static boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    public static void SnackBar(final String mssg, final View v, final MainActivity activity)
    {

        /*************** Snack Bar ***********/
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(v != null && activity != null)
                {
                    try {
                        Snackbar snackbar = Snackbar
                                .make(v, ""+mssg, Snackbar.LENGTH_LONG)
                                .setAction("Successful", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                    }
                                });
                        View snackBarView = snackbar.getView();
                        snackBarView.setBackgroundColor(activity.getResources().getColor(R.color.Yellow));

                        // Changing message text color
                        snackbar.setActionTextColor(Color.LTGRAY);

                        // Changing action button text color
                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.WHITE);

                        snackbar.show();
                    } catch (Exception e)
                    {
                        Log.e(" Utils ERROR ===>"+e.getMessage()," view is null");
                        return;
                    }

                }
                else
                {
                    Log.e("===>"," view is null");
                }
                /*************** End ***********/
            }
        }, 400);

    }





}
