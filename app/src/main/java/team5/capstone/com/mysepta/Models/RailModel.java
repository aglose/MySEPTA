package team5.capstone.com.mysepta.Models;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

/**
 * Model to hold train view data.
 * Created by Andrew on 9/7/2015.
 */
public class RailModel {
    @SerializedName("lat") private double latitude;
    @SerializedName("lon") private double longitude;
    @SerializedName("trainno") private String trainNumber;
    @SerializedName("dest") private String destination;
    @SerializedName("nextstop") private String nextStop;
    @SerializedName("late") private int late;
    @SerializedName("SOURCE") private String source;
    @SerializedName("TRACK") private String track;
    @SerializedName("TRACK_CHANGE") private String trackChange;

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getNextStop() {
        return nextStop;
    }

    public void setNextStop(String nextStop) {
        this.nextStop = nextStop;
    }

    public int getLate() {
        return late;
    }

    public void setLate(int late) {
        this.late = late;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getTrackChange() {
        return trackChange;
    }

    public void setTrackChange(String trackChange) {
        this.trackChange = trackChange;
    }



    public RailModel(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }

    @Override
    public String toString(){
        return "Location has latitude: "+latitude+"\n            longitude: "+longitude;
    }
}
