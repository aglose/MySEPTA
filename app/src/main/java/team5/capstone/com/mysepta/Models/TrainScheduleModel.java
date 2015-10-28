package team5.capstone.com.mysepta.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Model for static train data.
 * Created by Kevin on 10/21/15.
 */
public class TrainScheduleModel {
    @SerializedName("station") private String stationName;
    @SerializedName("sched_tm") private String scheduledTime;
    @SerializedName("est_tm") private String estimatedTime;
    @SerializedName("act_tm") private String actualTime;

    public String getActualTime() {
        return actualTime;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    public String getStationName() {
        return stationName;
    }

    public void setActualTime(String actualTime) {
        this.actualTime = actualTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}
