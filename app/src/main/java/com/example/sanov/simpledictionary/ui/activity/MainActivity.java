package com.example.sanov.simpledictionary.ui.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ProgressBar;

import com.example.sanov.simpledictionary.R;
import com.example.sanov.simpledictionary.db.helper.EngToIndHelper;
import com.example.sanov.simpledictionary.db.helper.IndToEngHelper;
import com.example.sanov.simpledictionary.model.EngToIndModel;
import com.example.sanov.simpledictionary.model.IndToEngModel;
import com.example.sanov.simpledictionary.prefs.AppPreference;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ProgressBar preLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preLoad = findViewById(R.id.pb_preload);

        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {

        final String TAG = LoadData.class.getSimpleName();
        EngToIndHelper engToIndHelper;
        double progress;
        double maxprogress = 100;
        AppPreference appPreference;

        @Override
        protected void onPreExecute() {
            engToIndHelper = new EngToIndHelper(MainActivity.this);
            appPreference = new AppPreference(MainActivity.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Boolean firstRun = appPreference.getFirstRun();

            if (firstRun) {
                ArrayList<EngToIndModel> engToIndModels = preLoadRawEng();

                engToIndHelper.open();

                progress = 30;
                publishProgress((int) progress);
                Double progressMaxInsert = 80.0;
                Double progressDiff = (progressMaxInsert - progress) / (engToIndModels.size());

                //insert english to indonesian preload data
                engToIndHelper.beginTransaction();

                try {
                    for (int i = 0; i < engToIndModels.size(); i++) {
                        EngToIndModel model = engToIndModels.get(i);
                        engToIndHelper.insertTransaction(model);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                    engToIndHelper.setTransactionSuccess();
                } catch (Exception e) {

                }
                engToIndHelper.endTransaction();
                engToIndHelper.close();

                publishProgress((int) maxprogress);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... v) {
            preLoad.setProgress(v[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            new LoadSecondData().execute();
        }
    }


    public ArrayList<EngToIndModel> preLoadRawEng() {
        ArrayList<EngToIndModel> engToIndModels = new ArrayList<>();
        String line = null;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_eng = res.openRawResource(R.raw.english_indonesia);

            reader = new BufferedReader(new InputStreamReader(raw_eng));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                EngToIndModel engToIndModel;
                engToIndModel = new EngToIndModel(splitstr[0], splitstr[1]);
                engToIndModels.add(engToIndModel);
                count++;
            } while (line != null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return engToIndModels;
    }

    private class LoadSecondData extends  AsyncTask<Void, Integer, Void> {

        final String TAG = LoadSecondData.class.getSimpleName();
        IndToEngHelper indToEngHelper;
        double progress;
        double maxprogress = 100;
        AppPreference appPreference;

        @Override
        protected void onPreExecute() {
            indToEngHelper = new IndToEngHelper(MainActivity.this);
            appPreference = new AppPreference(MainActivity.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Boolean firstRun = appPreference.getFirstRun();

            if (firstRun) {
                ArrayList<IndToEngModel> indToEngModels = preLoadRawInd();

                indToEngHelper.open();

                progress = 30;
                publishProgress((int) progress);
                Double progressMaxInsert = 80.0;
                Double progressDiff = (progressMaxInsert - progress) / indToEngModels.size();

                //insert indonesian to english
                indToEngHelper.beginTransaction();
                try {
                    for (int i = 0; i < indToEngModels.size(); i++) {
                        IndToEngModel model = indToEngModels.get(i);
                        indToEngHelper.insertTransaction(model);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                    indToEngHelper.setTransactionSuccess();
                } catch (Exception e) {

                }
                indToEngHelper.endTransaction();
                indToEngHelper.close();

                appPreference.setFirstRun(false);

                publishProgress((int) maxprogress);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... v) {
            preLoad.setProgress(v[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent i = new Intent(MainActivity.this, DictionaryActivity.class);
            startActivity(i);
            finish();
        }
    }

    public ArrayList<IndToEngModel> preLoadRawInd() {
        ArrayList<IndToEngModel> indToEngModels = new ArrayList<>();
        String line = null;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_ind = res.openRawResource(R.raw.indonesia_english);

            reader = new BufferedReader(new InputStreamReader(raw_ind));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                IndToEngModel indToEngModel;
                indToEngModel = new IndToEngModel(splitstr[0], splitstr[1]);
                indToEngModels.add(indToEngModel);
                count++;
            } while(line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return indToEngModels;
    }
}
