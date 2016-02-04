package team5.capstone.com.mysepta.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Andrew on 2/3/2016.
 */
public class BusTimeModel {
    private String shortName;
    private String stopName;
    private String route;
    private String date;
    private String day;
    private String direction;
    private String calendarDate;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    @Override
    public String toString() {
        return "BusTimeModel{" +
                "stopName='" + stopName + '\'' +
                ", route='" + route + '\'' +
                ", date='" + date + '\'' +
                ", day='" + day + '\'' +
                ", direction='" + direction + '\'' +
                ", calendarDate='" + calendarDate + '\'' +
                '}';
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getCalendarDate() {
        return calendarDate;
    }

    public void setCalendarDate(String calendarDate) {
        this.calendarDate = calendarDate;
    }


}

