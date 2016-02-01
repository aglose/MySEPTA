package team5.capstone.com.mysepta.Interfaces;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import team5.capstone.com.mysepta.Models.BusStopModel;
import team5.capstone.com.mysepta.Models.NearestLocationModel;

/**
 * Created by Andrew on 1/26/2016.
 */
public interface BusStopsInterface {

    @GET("/hackathon/Stops/{stopNumber}")
    void stops (
            @Path("stopNumber") String stopNumber,
            Callback<ArrayList<BusStopModel>> callback
    );
}
