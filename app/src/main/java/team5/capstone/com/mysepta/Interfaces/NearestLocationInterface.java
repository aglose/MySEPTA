package team5.capstone.com.mysepta.Interfaces;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
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
    @GET("/hackathon/locations/get_locations.php?radius=3&type=rail_stations")
    void locations (
            @Query("lat") String latitude,
            @Query("lon") String longitude,
            Callback<ArrayList<NearestLocationModel>> callback
    );
}