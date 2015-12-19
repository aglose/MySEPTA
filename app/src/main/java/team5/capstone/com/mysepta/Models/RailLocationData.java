package team5.capstone.com.mysepta.Models;

import java.util.HashMap;

/**
 * Model to hold rail location data for rail live schedule.
 * Created by Kevin on 9/30/15.
 */
public class RailLocationData {

    private String railName;
    private String railAcr;
    private String railNumber;
    private String station;
    private String time;
    private String delay;
    private boolean isConnection;

    public RailLocationData(String railAcr,String railName, String delay, String station,String railNumber,String time,boolean isConnection){
        this.railAcr = railAcr;
        this.railName = railName;
        this.delay = delay;
        this.railNumber = railNumber;
        this.station = station;
        this.time = time;
        this.isConnection = isConnection;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public void setIsConnection(boolean isConnection) {
        this.isConnection = isConnection;
    }

    public void setRailAcr(String railAcr) {
        this.railAcr = railAcr;
    }

    public void setRailName(String railName) {
        this.railName = railName;
    }

    public void setRailNumber(String railNumber) {
        this.railNumber = railNumber;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRailName(){
        return railName;
    }

    public String getRailNumber() {
        return railNumber;
    }

    public String getRailAcr() {
        return railAcr;
    }

    public String getStation() {
        return station;
    }

    public String getTime() {
        return time;
    }

    public boolean isConnection() {
        return isConnection;
    }
}
