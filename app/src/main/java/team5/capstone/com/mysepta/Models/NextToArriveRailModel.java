package team5.capstone.com.mysepta.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Model to hold next to arrive data.
 * Created by Kevin on 10/7/15.
 */
public class NextToArriveRailModel {

    @SerializedName("orig_train") private String origTrainNumber;
    @SerializedName("orig_line") private String origTrainName;
    @SerializedName("orig_departure_time") private String origDepartureTime;
    @SerializedName("orig_arrival_time") private String origArrivalTime;
    @SerializedName("orig_delay") private String origDelay;
    @SerializedName("term_train") private String conTrainNumber;
    @SerializedName("term_line") private String conTrainName;
    @SerializedName("term_depart_time") private String conDepartureTime;
    @SerializedName("term_arrival_time") private String conArrivalTime;
    @SerializedName("Connection") private String conStation;
    @SerializedName("term_delay") private String conDelay;
    @SerializedName("isdirect") private String isDirect;

    private String startingStation;
    private String finalStation;
    private String lineAcronym;

    public String getLineAcronym() {
        return lineAcronym;
    }

    public void setLineAcronym(String lineAcronym) {
        this.lineAcronym = lineAcronym;
    }

    public String getFinalStation() {
        return finalStation;
    }

    public void setFinalStation(String finalStation) {
        this.finalStation = finalStation;
    }

    public String getStartingStation() {
        return startingStation;
    }

    public void setStartingStation(String startingStation) {
        this.startingStation = startingStation;
    }

    public String getConArrivalTime() {
        return conArrivalTime;
    }

    public String getConDepartureTime() {
        return conDepartureTime;
    }

    public String getConTrainName() {
        return conTrainName;
    }

    public String getConTrainNumber() {
        return conTrainNumber;
    }

    public String getOrigArrivalTime() {
        return origArrivalTime;
    }

    public String getOrigDelay() {
        return origDelay;
    }

    public String getOrigDepartureTime() {
        return origDepartureTime;
    }

    public String getOrigTrainName() {
        return origTrainName;
    }

    public String getOrigTrainNumber() {
        return origTrainNumber;
    }

    public void setConArrivalTime(String conArrivalTime) {
        this.conArrivalTime = conArrivalTime;
    }

    public void setConDepartureTime(String conDepartureTime) {
        this.conDepartureTime = conDepartureTime;
    }

    public void setConTrainName(String conTrainName) {
        this.conTrainName = conTrainName;
    }

    public void setConTrainNumber(String conTrainNumber) {
        this.conTrainNumber = conTrainNumber;
    }

    public void setOrigArrivalTime(String origArrivalTime) {
        this.origArrivalTime = origArrivalTime;
    }

    public void setOrigDelay(String origDelay) {
        this.origDelay = origDelay;
    }

    public void setOrigDepartureTime(String origDepartureTime) {
        this.origDepartureTime = origDepartureTime;
    }

    public void setOrigTrainName(String origTrainName) {
        this.origTrainName = origTrainName;
    }

    public void setOrigTrainNumber(String origTrainNumber) {
        this.origTrainNumber = origTrainNumber;
    }

    public String getConStation(){return conStation;}

    public void setConStation(String conStation) {this.conStation = conStation;}

    public String getConDelay(){return conDelay;}

    public void setConDelay(String conDelay) {this.conDelay = conDelay;}

    public String getIsDirect() {
        String text = "";
        if(isDirect.equalsIgnoreCase("false")){
            text = "No";
        }else{
            text = "Yes";
        }
        return text;
    }

    public void setIsDirect(String isDirect) {this.isDirect = isDirect;}
}
