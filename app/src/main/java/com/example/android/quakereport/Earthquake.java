package com.example.android.quakereport;

import android.net.Uri;

/**
 * Created by TenSherab on 3/10/18.
 */

public class Earthquake {

    //magnitude of the Earthquake
    private double mMag;
    //location of the Earthquake
    private String mLocation;
    //time on which the Earthquake occurred
    private long mTime;
    //code for the Earthquake's url
    private String mUrl;


    //Drawable resource ID for the image, incase!
    private int mImageResourceId;

    /**
    * @param mMag is the magnitude of the Earthquake
    * @param mLocation is the location for the Earthquake
     * @param mTime is the time in milliseond for the Earthquake
     * @param mCode is the url for the Earthquake's
     *
    */
    public Earthquake(double mMag, String mLocation, long mTime, String mCode){
        this.mMag = mMag;
        this.mLocation = mLocation;
        this.mTime = mTime;
        this.mUrl = mCode;
    }

    /**
     * Get the magnitude
     * @return magnitude of earthquake
     */
    public double getmMag(){return mMag;}

    /**
     * set the magnitude
     * @param mMag magnitude
     */
    public void setmMag(double mMag){this.mMag = mMag;}

    /**
     * Get the locaiton
     * @return location of earthquake
     */
    public String getmLocation(){return mLocation;}
    /**
     * set the location
     * @param mLocation location
     */
    public void setmLocation(String mLocation){this.mLocation = mLocation;}

    /**
     * Get the time
     * @return date of earthquake
     */

    public long getmTime(){return mTime;}

    /**
     * Get the code
     * @return code to fit in that earthquake's url
     */

    public String getmCode(){return mUrl;}


}



































