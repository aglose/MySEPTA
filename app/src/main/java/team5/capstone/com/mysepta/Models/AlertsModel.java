package team5.capstone.com.mysepta.Models;

import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * data model for alerts
 * Created by Matt
 */
public class AlertsModel {
    @SerializedName("route_id")
    private String routeId;
    @SerializedName("route_name")
    private String routeName;
    @SerializedName("mode")
    private String mode;
    @SerializedName("isadvisory")
    private String isAdvisory;
    @SerializedName("isdetour")
    private String isDetour;
    @SerializedName("isalert")
    private String isAlert;
    @SerializedName("issuspend")
    private String isSuspended;
    @SerializedName("isSnow")
    private String isSnow;
    @SerializedName("last_updated")
    private String lastUpdate;

    /*Description coming from AlertsDescriptionModel*/
    private Spanned description;

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getIsAdvisory() {
        if(isAdvisory==null){
            isAdvisory = "";
        }
        return isAdvisory;
    }

    public void setIsAdvisory(String isAdvisory) {
        this.isAdvisory = isAdvisory;
    }

    public String getIsDetour() {
        if(isDetour==null){
            isDetour = "";
        }
        return isDetour;
    }

    public void setIsDetour(String isDetour) {
        this.isDetour = isDetour;
    }

    public String getIsAlert() {
        if(isAlert==null){
            isAlert = "";
        }
        return isAlert;
    }

    public void setIsAlert(String isAlert) {
        this.isAlert = isAlert;
    }

    public String getIsSuspended() {
        if(isSuspended==null){
            isSuspended = "";
        }
        return isSuspended;
    }

    public void setIsSuspended(String isSuspended) {
        this.isSuspended = isSuspended;
    }

    public String getIsSnow() {
        if(isSnow==null){
            isSnow = "";
        }
        return isSnow;
    }

    public void setIsSnow(String isSnow) {
        this.isSnow = isSnow;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Spanned getDescription() {
        return description;
    }

    public void setDescription(Spanned description) {
        this.description = description;
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

    public boolean isBus() {
        if (mode.equals("Bus")) {
            return true;
        }

        return false;
    }
    
    public boolean isAlertDeleteable(){
        if (getIsAlert().equalsIgnoreCase("N") && getIsDetour().equalsIgnoreCase("N") && getIsAdvisory().equalsIgnoreCase("no")
                && getIsSnow().equalsIgnoreCase("n") && (getIsSuspended().equalsIgnoreCase("n") || getIsSuspended().equalsIgnoreCase("") || getIsSuspended().equalsIgnoreCase("null"))){
            return true;
        }
        
        return false;
    }

    @Override
    public String toString() {
        return "AlertsModel{" +
                "routeId='" + routeId + '\'' +
                ", routeName='" + routeName + '\'' +
                ", mode='" + mode + '\'' +
                ", isAdvisory='" + isAdvisory + '\'' +
                ", isDetour='" + isDetour + '\'' +
                ", isAlert='" + isAlert + '\'' +
                ", isSuspended='" + isSuspended + '\'' +
                ", isSnow='" + isSnow + '\'' +
                ", lastUpdate='" + lastUpdate + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public AlertsModel(String routeId, String routeName, String mode, String isAdvisory, String isDetour, String isAlert, String isSuspended, String isSnow,String lastUpdate){
        this.routeId = routeId;
        this.routeName = routeName;
        this.mode = mode;
        this.isAdvisory = isAdvisory;
        this.isDetour = isDetour;
        this.isAlert = isAlert;
        this.isSuspended = isSuspended;
        this.isSnow = isSnow;
        this.lastUpdate = lastUpdate;
    }
}
