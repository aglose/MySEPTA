package team5.capstone.com.mysepta.Models;

import java.util.HashMap;

/**
 * Created by Kevin on 9/30/15.
 */
public class RailLocationData {

    private String railName;
    private String railAcr;

    private int railNumber;

    private String station;
    private String time;

    public RailLocationData(String railAcr,String railName,String station,int railNumber,String time){
        this.railAcr = railAcr;
        this.railName = railName;
        this.railNumber = railNumber;
        this.station = station;
        this.time = time;
    }

    public void setRailAcr(String railAcr) {
        this.railAcr = railAcr;
    }

    public void setRailName(String railName) {
        this.railName = railName;
    }

    public void setRailNumber(int railNumber) {
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

    public int getRailNumber() {
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
}
