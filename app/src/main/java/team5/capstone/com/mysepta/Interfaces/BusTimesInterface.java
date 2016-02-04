package team5.capstone.com.mysepta.Interfaces;

import java.util.ArrayList;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import team5.capstone.com.mysepta.Models.BusStopModel;
import team5.capstone.com.mysepta.Models.BusTimeModel;

/**
 * Created by Andrew on 2/3/2016.
 */
public interface BusTimesInterface {
    //ex. /hackathon/BusSchedules/?req1=17842&req2=17&req3=i&req6=7&callback=?
    @GET("/hackathon/BusSchedules/")
    void times (
            @QueryMap Map<String, String> stringMap,
            Callback<ArrayList<BusTimeModel>> callback
    );
}
