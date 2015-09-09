package team5.capstone.com.mysepta.Models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Andrew on 9/7/2015.
 */
public class Coordinates {
    private Double latitude;
    private Double longitude;

    public Coordinates(Double latitude, Double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public LatLng getLatLng(){
        return new LatLng(latitude, longitude);
    }
}
