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

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
/**
 * {@link EarthquakeActivity} shows a list of ThreeColumns that occurred over
 * different locations, at different magnitude, and on different dates.
 */

public class EarthquakeActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private static final String LOG_TAG = EarthquakeActivity.class.getName();

    public static final String USGS_WEBSITE = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";

    public static EarthquakeAdapter earthquakeAdapter;
    public static ListView quakeListView;
    public static TextView showEmpty;
    private static final int EARTHQUAKE_LOADER_ID = 1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_settings){
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_lists);

        showEmpty = (TextView) findViewById(R.id.empty_text);
        quakeListView = (ListView) findViewById(R.id.list);
        quakeListView.setFastScrollEnabled(false);

        earthquakeAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>()); //initialize custom ArrayAdapter
        quakeListView.setAdapter(earthquakeAdapter);//display the content, look at onPostExecute() method

        quakeListView.setEmptyView(showEmpty);
        //open particular browser on click
        quakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //find the current earthquake that was clicked on
                Earthquake currentEarthquake = earthquakeAdapter.getItem(position);
                //convert the string url into a URI object (to pass into the Intent constructor)
                String earthquakeUrl = currentEarthquake.getmCode();
                Uri earthquakeUri = Uri.parse(currentEarthquake.getmCode());
                //Create a new intent to view tha earthquake URI

//                Intent startPage = new Intent(EarthquakeActivity.this, ShowUsTheWebPage.class);
//                startPage.putExtra(EXTRA_MESSAGE, earthquakeUrl);
//                startActivity(startPage);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                // Send the intent to launch a new activity
                startActivity(websiteIntent);

            }
        });

        //EarthquakeSyncTask earthquakeSyncTask = new EarthquakeSyncTask();
        //Get a reference to the LoaderManager, in order for interaction with other loaders.
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork !=null && activeNetwork.isConnectedOrConnecting();

        if(isConnected==true) {
            Log.i(LOG_TAG, "calling LoaderManager...");
            LoaderManager loaderManager = getLoaderManager();
            //initialize the loader, fill the parameter, for eg. intitLoader(id, null, context)
            //you put, initLoader(EARTHQUAKE_LOADER, null, this),
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        }else{
            quakeListView.setVisibility(View.GONE);
            showEmpty.setText("No internet connection, perhaps, airplane mode!");

        }
        //EarthquakeLoader earthquakeLoader = new EarthquakeLoader(EarthquakeActivity.this, USGS_WEBSITE);

    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        //Create new loader for the given URL
        Log.i(LOG_TAG, "onCreateLoader() is called");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String minMagnitude = sharedPreferences.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));

        String orderBy = sharedPreferences.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));

        Uri baseUri = Uri.parse(USGS_WEBSITE);
        Uri.Builder uriBuilder = baseUri.buildUpon(); //customizing or amending URL to particular url the we want to parse data

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "10");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);

        return new EarthquakeLoader(EarthquakeActivity.this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        //clear the adapter of previous earthquake data
        Log.i(LOG_TAG,"onLoadFinished()is called");
        earthquakeAdapter.clear();
        //If there's a valid list of Earthquake object, this will trigger the ListView to update.
        if(earthquakes != null && !earthquakes.isEmpty()){
            earthquakeAdapter.addAll(earthquakes);
        }


    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        //Loader reset, so we can clear out our existing data.
        Log.i(LOG_TAG,"onLoaderReset()is called");
        earthquakeAdapter.clear();
    }

}





































