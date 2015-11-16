package team5.capstone.com.mysepta.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tiestodoe on 11/9/15.
 */
public class NearestLocationModel {
    @SerializedName("location_name") private String locationName;

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
