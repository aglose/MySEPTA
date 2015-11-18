package team5.capstone.com.mysepta.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class AlertsModel {
    @SerializedName("route_id") private String routeId;
    @SerializedName("route_name") private String routeName;
    @SerializedName("mode") private String mode;
    @SerializedName("is_advisory") private String isAdvisory;
    @SerializedName("is_detour") private String isDetour;
    @SerializedName("is_alert") private String isAlert;
    @SerializedName("is_suspend") private String isSuspended;
    @SerializedName("is_snow") private String isSnow;
    @SerializedName("last_updated") private Date lastUpdate;
    @SerializedName("description") private String description;

    public AlertsModel(String mode, String routeName, Date lastUpdate, String description){
        this.mode = "Empty";
        this.routeName = "Empty";
        this.lastUpdate = new Date();
        this.description = "Empty";
    }

    public String getRouteId() {
        return routeId;
    }

    public String getRouteName(){
        return routeName;
    }

    public String getMode() {
        return mode;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public String getDescription() {
        return description;
    }

    public boolean hasSnowFlag() {
        if (isSnow.toUpperCase().equals("Y")) {
            return true;
        }

        return false;
    }

    public boolean hasAdvisoryFlag() {
        if (isAdvisory.toUpperCase().equals("YES")) {
            return true;
        }

        return false;
    }

    public boolean hasAlertFlag() {
        if (isAlert.toUpperCase().equals("Y")) {
            return true;
        }

        return false;
    }

    public boolean hasDetourFlag() {
        if (isDetour.toUpperCase().equals("Y")) {
            return true;
        }

        return false;
    }

    public boolean hasSuspendedFlag() {
        if (isSuspended.toUpperCase().equals("Y")) {
            return true;
        }

        return false;
    }

    public boolean isSuspended() {

        return hasSuspendedFlag();
    }

    public boolean hasFlag() {
        if (hasSuspendedFlag() || hasAlertFlag() || hasAdvisoryFlag() || hasDetourFlag()) {
            return true;
        }

        return false;
    }



}
