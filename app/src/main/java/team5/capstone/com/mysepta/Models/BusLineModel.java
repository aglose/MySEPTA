package team5.capstone.com.mysepta.Models;

/**
 * Created by Andrew on 1/27/2016.
 */
public class BusLineModel {
    private String routeId;
    private String routeShortName;
    private String routeLongName;

    public String getRouteLongName() {
        return routeLongName;
    }

    public void setRouteLongName(String routeLongName) {
        this.routeLongName = routeLongName;
    }

    public String getRouteShortName() {
        return routeShortName;
    }

    public void setRouteShortName(String routeShortName) {
        this.routeShortName = routeShortName;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    @Override
    public String toString() {
        return "BusLineModel{" +
                "routeId=" + routeId +
                ", routeShortName=" + routeShortName +
                ", routeLongName='" + routeLongName + '\'' +
                '}';
    }
}
