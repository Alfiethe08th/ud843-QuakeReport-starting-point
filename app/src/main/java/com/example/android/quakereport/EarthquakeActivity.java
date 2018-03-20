/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link EarthquakeActivity} shows a list of ThreeColumns that occurred over
 * different locations, at different magnitude, and on different dates.
 */

public class EarthquakeActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static final String USGS_WEBSITE = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";

    public static EarthquakeAdapter earthquakeAdapter;
    public static ListView quakeListView;

    private String TAG = EarthquakeActivity.class.getSimpleName();
    private String root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        EarthquakeSyncTask earthquakeSyncTask = new EarthquakeSyncTask();
        earthquakeSyncTask.execute(USGS_WEBSITE);

        quakeListView = (ListView) findViewById(R.id.list);

        earthquakeAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>()); //initialize custom ArrayAdapter
        quakeListView.setAdapter(earthquakeAdapter);//display the content, look at onPostExecute() method

        //open particular browser on click
        quakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //find the current earthquake that was clicked on
                Earthquake currentEarthquke = earthquakeAdapter.getItem(position);
                //convert the string url into a URI object (to pass into the Intent constructor)
                String earthquakeUrl = currentEarthquke.getmCode();

                //Create a new intent to view tha earthquake URI
                Intent startPage = new Intent(EarthquakeActivity.this, ShowUsTheWebPage.class);
                startPage.putExtra(EXTRA_MESSAGE, earthquakeUrl);
                startActivity(startPage);
            }});

    }

    private class EarthquakeSyncTask extends AsyncTask<String, Void, List<Earthquake>> {
        @Override
        protected List<Earthquake> doInBackground(String... urls) {
            //Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls == null) {
                return null;
            }
            List<Earthquake> result = QueryUtils.extractQuakeData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Earthquake> postResult){
            //clear previous adapter's data
            earthquakeAdapter.clear();
            if(postResult!=null&&!postResult.isEmpty()){
                earthquakeAdapter.addAll(postResult);
            }
    }
    }

}





































