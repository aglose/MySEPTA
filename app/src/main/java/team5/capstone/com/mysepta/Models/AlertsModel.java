package team5.capstone.com.mysepta.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class AlertsModel {
    @SerializedName("route_id")
    private String routeId;
    @SerializedName("route_name")
    private String routeName;
    @SerializedName("mode")
    private String mode;
    @SerializedName("is_advisory")
    private String isAdvisory;
    @SerializedName("is_detour")
    private String isDetour;
    @SerializedName("is_alert")
    private String isAlert;
    @SerializedName("is_suspend")
    private String isSuspended;
    @SerializedName("is_snow")
    private String isSnow;
    @SerializedName("last_updated")
    private String lastUpdate;
    @SerializedName("description")
    private String description;

    public AlertsModel(String mode, String routeName, String lastUpdate, String description, String isAdvisory, String isDetour, String isAlert, String isSuspended) {
        this.mode = "Empty";
        this.routeName = "Empty";
        this.lastUpdate = "";
        this.description = "Empty";
        this.isAdvisory = "No";
        this.isDetour = "N";
        this.isAlert = "N";
        this.isSuspended = "N";

    }

    public String getRouteId() {
        return routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public String getMode() {
        return mode;
    }

    public String getLastUpdate() {
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

    public boolean hasDetourFlag() {
        if (isDetour.toUpperCase().equals("Y")) {
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

    public boolean hasSuspendedFlag() {
        if (isSuspended.toUpperCase().equals("Y")) {
            return true;
        }

        return false;
    }

    public int hasFlag() {
        int flagNum = 0;
        if (hasAdvisoryFlag()) {
            flagNum = 1;
        } else if (hasDetourFlag()) {
            flagNum = 2;
        } else if (hasAlertFlag()) {
            flagNum =3;
        } else if (hasSuspendedFlag()) {
            flagNum=4;
        }else {

        }
        return flagNum;

    }

    public boolean isGeneral() {
        if (mode.equals("generic")) {

            return true;
        }

        return false;
    }

    public boolean isSubway() {
        if (mode.equals("Broad Street Line") || mode.equals("Market/ Frankford")) {
            return true;
        }

        return false;
    }

    public boolean isRegionalRail() {
        if (mode.equals("Regional Rail")) {
            return true;
        }

        return false;
    }


}
