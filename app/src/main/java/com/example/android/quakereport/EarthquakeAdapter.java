package com.example.android.quakereport;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by TenSherab on 3/10/18.
 * {@link EarthquakeAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
 * based on a date source, which is a list of {@link Earthquake} objects.
 *
 * In plain english, an ArrayAdapter is a built-in class that convert xml files to corresponding java object
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake>{

    private static int indexOfAnchorWord;
    private static final String ANCHOR_WORD = "of";
    private static final String LOG_TAG = EarthquakeAdapter.class.getSimpleName();

    /**
     * This is a custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want to populate
     *
     * @param context       The current context, used to inflate the layout file.
     * @param earthquakes  A list of ThreeColumn objects to display in a list
     */
    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes){

        //Initialize the ArrayAdapter's internal storage for the context and the list.
        //the second argument is for when the ArrayAdapter is populating a single TextView.
        //ArrayAdapter takes three argument, as follows:
        //ArrayAdapter<String> adapter =
        // new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, earthquakes); ....remember?
        //        (whateverActivity.this, android.R.l..default_layout_1, Earthquake-object); ....ok?
        super(context, 0, earthquakes);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc)
     *
     * @param position  the position in which the list of data that should be displayed in the list tem vjew
     * @param convertView   The recycled view to populate
     * @param parent    The parent ViewGroup that is used for inflation.
     * @return  The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //check if the existing view is being re-used, otherwise inflate the view

        View listItemView = convertView;

        if(listItemView==null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake, parent, false);
        }

        Earthquake currentPosition = getItem(position);

        //find the TextView in the earthquakel layout with the ID magnitude
        TextView quakeMagnitude = (TextView) listItemView.findViewById(R.id.magnitude);
        //get the magnitude from the current ThreeColumns object and write/set new text(i.e., the magnitude of the earthquake) on TextView quakeMagnitde.
        quakeMagnitude.setText(formatMag(currentPosition.getmMag()));

        GradientDrawable magCircle = (GradientDrawable) quakeMagnitude.getBackground();
        int magColor = magColorid(currentPosition.getmMag());
        magCircle.setColor(magColor);
        //magCircle.setColor(magColorid(columnPosition.getmMag()); results in same thing as above.

        TextView locationOffset = (TextView) listItemView.findViewById(R.id.locationOffset);
        locationOffset.setText(offsetQuakeLocation(currentPosition.getmLocation()));

        TextView primaryLocation = (TextView) listItemView.findViewById(R.id.primaryLocation);
        primaryLocation.setText(primaryQuakeLocation(currentPosition.getmLocation()));


        //get the date object that is a 'long' data type and store the value in dateObject
        Date dateObject = new Date(currentPosition.getmTime());
        //initialize a Textview, corresponding it to the layout view in xml
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        String formattedDate = formatDate(dateObject);//call the formatDate method which will pass the dateObject as its arguments
        dateView.setText(formattedDate);

        //find the TextView in the earthquake.xmlyout with the ID time
        TextView timeView= (TextView) listItemView.findViewById(R.id.time);
        String formattedTime = formatTime(dateObject);
        timeView.setText(formattedTime);
        return listItemView;
    }

    public String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy");
        return dateFormat.format(dateObject);
    }

    public String formatTime(Date dateObject){
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    public String offsetQuakeLocation(String location){
        String contentBeforeComma = "";

        //if the location string doesn't have the word "of", then
        if(location.contains(ANCHOR_WORD)==true) {
            indexOfAnchorWord= location.lastIndexOf(ANCHOR_WORD) + 2;
            contentBeforeComma = location.substring(0, indexOfAnchorWord);
        }
        return contentBeforeComma;

    }

    public String primaryQuakeLocation(String location) {

        String contentAfterComma;
        //if the location string doesn't have the word "of", then
        if (location.contains(ANCHOR_WORD) != true) {
            contentAfterComma = location;
        } else {
            contentAfterComma = location.substring(indexOfAnchorWord + 1);
        }
        return contentAfterComma;
    }

    public String formatMag(double magnitude){
        DecimalFormat desiredMag = new DecimalFormat("0.0");
        String finalMag = desiredMag.format(magnitude);
        return finalMag;
    }

    public int magColorid(double magnitude){
        int id;
        int magInDouble = (int) Math.floor(magnitude) ;
        switch (magInDouble){
            //less than 2
            case 0:
            case 1:
                id = ContextCompat.getColor(getContext(), R.color.magnitude1);
                break;
            case 2:
                id = ContextCompat.getColor(getContext(), R.color.magnitude2);
                break;
            // 3 <= mag < 4
            case 3:
                id = ContextCompat.getColor(getContext(), R.color.magnitude3);
                break;
            case 4:
                id = ContextCompat.getColor(getContext(), R.color.magnitude4);
                break;
            case 5:
                id = ContextCompat.getColor(getContext(), R.color.magnitude5);
                break;
            case 6:
                id = ContextCompat.getColor(getContext(), R.color.magnitude6);
                break;
            case 7:
                id = ContextCompat.getColor(getContext(), R.color.magnitude7);
                break;
            case 8:
                id = ContextCompat.getColor(getContext(), R.color.magnitude8);
                break;
            case 9:
            case 10:
                id = ContextCompat.getColor(getContext(), R.color.magnitude9);
                break;
            default:
                id = ContextCompat.getColor(getContext(), R.color.magnitude10plus);
        }
        return id;
    }
}













































































