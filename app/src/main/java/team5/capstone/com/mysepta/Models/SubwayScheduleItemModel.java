package team5.capstone.com.mysepta.Models;

import java.sql.Time;
import java.util.Date;

/**
 * Created by Andrew on 9/20/2015.
 */
public class SubwayScheduleItemModel implements Comparable<SubwayScheduleItemModel>{
    private String formattedTimeStr;
    private String direction;
    private String location;
    private String line;
    private String stopID;
    private Date timeObject;

    public SubwayScheduleItemModel(){

    }

    public Date getTimeObject() {
        return timeObject;
    }

    public void setTimeObject(Date timeObject) {
        this.timeObject = timeObject;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getStopID() {
        return stopID;
    }

    public void setStopID(String stopID) {
        this.stopID = stopID;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public SubwayScheduleItemModel(String formattedTimeStr){
        this.formattedTimeStr = formattedTimeStr;
    }

    public String getFormattedTimeStr() {
        return formattedTimeStr;
    }

    public void setFormattedTimeStr(String formattedTimeStr) {
        this.formattedTimeStr = formattedTimeStr;
    }

    @Override
    public int compareTo(SubwayScheduleItemModel o) {
        if (getTimeObject() == null || o.getTimeObject() == null)
            return 0;
        return getTimeObject().compareTo(o.getTimeObject());
    }

}
