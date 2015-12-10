package team5.capstone.com.mysepta.Models;

import com.google.gson.annotations.SerializedName;

/**
 * data model for alert desc
 * Created by Andrew on 12/2/2015.
 */
public class AlertsDescriptionModel {
    @SerializedName("route_id")
    private String routeId;
    @SerializedName("current_message")
    private String currentMessage;
    @SerializedName("advisory_message")
    private String advisoryMessage;
    @SerializedName("detour_message")
    private String detourMessage;

    public String getRouteId() {
        return routeId;
    }

    @Override
    public String toString() {
        return "AlertsDescriptionModel{" +
                "routeId='" + routeId + '\'' +
                ", currentMessage='" + currentMessage + '\'' +
                ", advisoryMessage='" + advisoryMessage + '\'' +
                ", detourMessage='" + detourMessage + '\'' +
                '}';
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getCurrentMessage() {
        return currentMessage;
    }

    public void setCurrentMessage(String currentMessage) {
        this.currentMessage = currentMessage;
    }

    public String getAdvisoryMessage() {
        return advisoryMessage;
    }

    public void setAdvisoryMessage(String advisoryMessage) {
        this.advisoryMessage = advisoryMessage;
    }

    public String getDetourMessage() {
        return detourMessage;
    }

    public void setDetourMessage(String detourMessage) {
        this.detourMessage = detourMessage;
    }


}
