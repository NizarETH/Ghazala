package com.stanly.ghazala.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.stanly.ghazala.Beans.Agence;
import com.stanly.ghazala.Beans.Data;
import com.stanly.ghazala.Beans.Depart;
import com.stanly.ghazala.Beans.ElementSwipe;
import com.stanly.ghazala.R;
import com.stanly.ghazala.Utils.ConnectionDetector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.ref.WeakReference;

import io.realm.Realm;

public class SplashActivity extends FragmentActivity {

    private ProgressBar progressBar;
    private ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = (ProgressBar) findViewById(R.id.progress);



        cd = new ConnectionDetector(getApplicationContext());

        if (cd.isConnectingToInternet()) {

            new MyTask(this).execute();


        }else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.connect), Toast.LENGTH_LONG).show();
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 800);
        }




    }

    private  class MyTask extends AsyncTask<Void, Void, Element> {
        // only retain a weak reference to the activity
        private WeakReference<SplashActivity> activityReference;

        /***
         * private WeakReference<Application> appReference;
         *
         * MyTask(Application context) {
         *     appReference = new WeakReference<>(context);
         * }***/
        MyTask(SplashActivity context) {
            activityReference = new WeakReference<>(context);
         }
        @Override
        protected Element doInBackground(Void... params) {
            String title = "";
            Document doc;
            int id = 0;
            int idDepart = 0;
              Realm r = Realm.getDefaultInstance();
            Element element = null;
            try {

                r.beginTransaction();
                r.where(ElementSwipe.class).findAll().deleteAllFromRealm();
                r.where(Depart.class).findAll().deleteAllFromRealm();
                r.where(Agence.class).findAll().deleteAllFromRealm();

                doc = Jsoup.connect("http://www.transghazala.ma/").get();

              Elements departcity =  doc.getElementsByClass("form-control form-control-lg departcity");

                for (int i = 0; i < ((Elements) departcity).size(); i++) {
                    Element departCity = departcity.get(i);
                    if(departCity != null)
                    {
                        for (int j = 0; j < departCity.getAllElements().size(); j++) {
                         Element elementDepart = departCity.getAllElements().get(j);
                         if(elementDepart != null)
                         {
                             if(elementDepart.select("option")
                                     .first().attr("value") != null )
                             {
                                 String Value = elementDepart.select("option")
                                         .first().attr("value") ;
                                 if( !Value.equalsIgnoreCase("-1"))
                                 {

                                     Depart  depart =   r.createObject(Depart.class, idDepart++) ;
                                     depart.setName(Value);
                                 }
                             }
                         }
                        }
                    }

                }




                element = doc.getElementById("home-carousel");

                for (int i = 0; i <  doc.getElementById("home-carousel").getElementsByClass("carousel-inner").size(); i++) {
                    Element el = doc.getElementById("home-carousel").getElementsByClass("carousel-inner").get(i);

                    if(el != null)
                    {
                        for (int j = 0; j < el.getElementsByClass("item").size(); j++) {
                            Element el1 = el.getElementsByClass("item").get(j);
                            if(el1 != null)
                            {

                                ElementSwipe elementSwipe  = r.createObject(ElementSwipe.class, id++);
                                elementSwipe.setImage(el1.select("img").first()
                                        .absUrl("src"));
                            }
                        }

                    }

                }
                Elements elements = doc.getElementsByClass("table table-reflow");

                Elements rows = elements.get(0).select("tr");
                for (int i = 1; i <rows.size() ; i++) {
                    Agence agence = r.createObject(Agence.class, i);
                    Element row = rows.get(i);
                    if(row != null && row.select("td").size() == 3){

                        String name = row.select("td").get(0).text();
                        agence.setName(name);
                        String adresse = row.select("td").get(1).text();
                        agence.setAdresse(adresse);
                        String tel = row.select("td").get(2).text();
                        agence.setPhone(tel);

                    }

                }

                r.commitTransaction();



            } catch (IOException e) {
                e.printStackTrace();
            }
            return element;
        }


        @Override
        protected void onPostExecute(Element result) {
            //if you had a ui element, you could display the title
            // get a reference to the activity if it is still there
            SplashActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;


            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
