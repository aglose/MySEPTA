package team5.capstone.com.mysepta.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kevin on 10/7/15.
 */
public class NextToArriveRailModel {

    @SerializedName("orig_train") private double latitude;
    @SerializedName("orig_line") private double longitude;
    @SerializedName("orig_departure_time") private String trainNumber;
    @SerializedName("orig_arrival_time") private String destination;
    @SerializedName("orig_delay") private String nextStop;
    @SerializedName("term_train") private int late;
    @SerializedName("term_line") private String source;
    @SerializedName("term_depart_time") private String track;
    @SerializedName("term_arrival_time") private String trackChange;
    @SerializedName("Connection") private String conStation;
    @SerializedName("term_delay") private String conDelay;
    @SerializedName("isdirect") private String isDirect;

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

    public String getConStation(){return conStation;}

    public void setConStation(String conStation) {this.conStation = conStation;}

    public String getConDelay(){return conDelay;}

    public void setConDelay(String conDelay) {this.conDelay = conDelay;}

    public String getIsDirect() {return isDirect;}

    public void setIsDirect(String isDirect) {this.isDirect = isDirect;}
}
