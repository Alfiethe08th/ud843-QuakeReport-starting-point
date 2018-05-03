package com.example.android.quakereport;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by TenTsering on 3/11/18.
 */
public final class QueryUtils {
    public static final String LOG_TAG = QueryUtils.class.getName();

    /**
     * @return a list of {@link Earthquake} objects that has been built up from parsing a JSON response.
     */
    public static List<Earthquake> extractQuakeData(String requestData) {

        URL site = createUrl(requestData);

        String jsonResponse = null;
        try{
            jsonResponse = makehttpRequest(site);
        }catch (IOException e){
            Log.e(LOG_TAG, "Error! making http request", e);
        }

        List<Earthquake> earthquakes = extractFeatureFromJson(jsonResponse);
        return earthquakes;
    }

    private static List<Earthquake> extractFeatureFromJson(String earthjsonResponse) {


        //if the json content is empty return early
        if(TextUtils.isEmpty(earthjsonResponse)){
            return null;
        }
        List<Earthquake> earthquakes = new ArrayList<>();
        try {
            JSONObject quakeObject = new JSONObject(earthjsonResponse);
            JSONArray quakeFeatures = quakeObject.getJSONArray("features");
            //this will be in the loop
            for (int i = 0; i < quakeFeatures.length(); i++) {
                JSONObject quakeindices = quakeFeatures.getJSONObject(i);
                JSONObject quakeProperties = quakeindices.getJSONObject("properties");

                // Double magnitude = quakeProperties.getDouble("mag");
                double magnitude = quakeProperties.getDouble("mag");
                String location = quakeProperties.getString("place");
                long time = quakeProperties.getLong("time");
                String url = quakeProperties.getString("url");
                Earthquake value = new Earthquake(magnitude, location, time, url);
                earthquakes.add(value);
            }
            //Parse the response given by the SAMPLE_JSON_RESPONSE string and build up a list of ThreeColumn objects with the corresponding data.

        } catch (JSONException e) {
            //if an error is thrown when executing any of the above statements in the "try" block, catch the exception here, so the app doesn't crash. Print a log message
            //with the message from the exception.
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return earthquakes;
    }

    private static String makehttpRequest(URL site) throws IOException {
        String jsonResponse = "";

        if (site == null) {
            return jsonResponse;
        }
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) site.openConnection();
            httpURLConnection.setReadTimeout(1000);
            httpURLConnection.setConnectTimeout(1500);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error! The response code is : " + httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error! Problem retrieving JSON DATA", e);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder buildString = new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));//read binary to human readle character
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);//from character to reading word by word
            String eachline = bufferedReader.readLine();
            while(eachline!=null){
                buildString.append(eachline);
                eachline = bufferedReader.readLine();
            }
        }
        return buildString.toString();

    }

    private static URL createUrl(String stringUrl) {
        URL urls = null;
        try{
            urls = new URL(stringUrl);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG, "Error! URL creation failed" ,e);
        }
        return urls;
    }


}























































