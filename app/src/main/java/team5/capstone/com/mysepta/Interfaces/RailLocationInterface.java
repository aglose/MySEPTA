package team5.capstone.com.mysepta.Interfaces;

import team5.capstone.com.mysepta.Models.RailModel;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Retrieve train location data.
 * Created by Andrew on 9/7/2015.
 */
public interface RailLocationInterface {

    /**
     * Retrieve train location data.
     * @param callback callback to hold train data
     */
    @GET("/hackathon/TrainView/")
    void locations (
            Callback<ArrayList<RailModel>> callback
    );
}
