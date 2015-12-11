package team5.capstone.com.mysepta.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Holds static rail data.
 * Created by Kevin on 11/15/15.
 */
public class StaticRailModel implements Parcelable {
    private String trainNumber;
    private String startTime;
    private String endTime;

    public StaticRailModel(String trainNumber,String startTime,String endTime){
        this.trainNumber = trainNumber;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    protected StaticRailModel(Parcel in) {
        trainNumber = in.readString();
        startTime = in.readString();
        endTime = in.readString();
    }

    public static final Creator<StaticRailModel> CREATOR = new Creator<StaticRailModel>() {
        @Override
        public StaticRailModel createFromParcel(Parcel in) {
            return new StaticRailModel(in);
        }

        @Override
        public StaticRailModel[] newArray(int size) {
            return new StaticRailModel[size];
        }
    };

    public String getEndTime() {
        return endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(trainNumber);
        dest.writeString(startTime);
        dest.writeString(endTime);
    }
}
