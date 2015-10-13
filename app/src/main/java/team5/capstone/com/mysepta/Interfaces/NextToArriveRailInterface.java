package team5.capstone.com.mysepta.Interfaces;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import team5.capstone.com.mysepta.Models.NextToArriveRailModel;

/**
 * Created by Kevin on 10/7/15.
 */
public interface NextToArriveRailInterface {

    @GET("/hackathon/NextToArrive/{start}/{end}/{results}")
    void locations (
            @Path("start") String startStation,
            @Path("end")String endStation,
            @Path("results") String numResults,
            Callback<ArrayList<NextToArriveRailModel>> callback
    );

}
