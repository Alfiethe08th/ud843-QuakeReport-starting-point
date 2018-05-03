package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by TenTsering on 3/25/18.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    private static final String LOG_TAG = EarthquakeLoader.class.getName();
    private String mUrl;

    /**
     * New {@link EarthquakeLoader}
     * @param context of the activity
     * @param url to load data from
     */
    public EarthquakeLoader(Context context,String url){
        super(context);
        this.mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "onStartLoading() method is called");
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground(){
        Log.i(LOG_TAG, "loadInBackground() method is called");
        if(mUrl == null){
            return null;
        }
        List<Earthquake> earthquakes = QueryUtils.extractQuakeData(mUrl);
        return earthquakes;

    }

}































