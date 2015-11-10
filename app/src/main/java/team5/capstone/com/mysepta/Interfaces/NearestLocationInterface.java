package team5.capstone.com.mysepta.Interfaces;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;
import team5.capstone.com.mysepta.Models.NearestLocationModel;
import team5.capstone.com.mysepta.Models.RailModel;

/**
 * Created by tiestodoe on 11/9/15.
 */
public interface NearestLocationInterface {

    /**
     * Retrieve train location data.
     * @param callback callback to hold train data
     */
    @GET("/hackathon/locations/get_locations.php?lon=-75.33299748&lat=40.11043326&radius=3&type=rail_stations")
    void locations (
            Callback<ArrayList<NearestLocationModel>> callback
    );
}