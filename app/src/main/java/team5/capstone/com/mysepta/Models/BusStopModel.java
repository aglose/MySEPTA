package team5.capstone.com.mysepta.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Andrew on 1/26/2016.
 */
public class BusStopModel {
    @SerializedName("lng")
    private String longitude;
    @SerializedName("lat")
    private String latitude;
    @SerializedName("stopid")
    private String stopID;
    @SerializedName("stopname")
    private String stopName;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getStopID() {
        return stopID;
    }

    public void setStopID(String stopID) {
        this.stopID = stopID;
    }

    public String getStopName() {
        String subString = stopName.replace("&amp;", "&");
        return subString;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    @Override
    public String toString() {
        return "BusStopModel{" +
                "longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", stopID='" + stopID + '\'' +
                ", stopName='" + stopName + '\'' +
                '}';
    }
}
