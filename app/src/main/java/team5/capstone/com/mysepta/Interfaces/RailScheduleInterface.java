package team5.capstone.com.mysepta.Interfaces;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import team5.capstone.com.mysepta.Models.TrainScheduleModel;

/**
 * Created by Kevin on 10/21/15.
 */
public interface RailScheduleInterface {
    @GET("/hackathon/RRSchedules/{train}")
    void locations (
            @Path("train") String trainNumber,
            Callback<ArrayList<TrainScheduleModel>> callback
    );
}
