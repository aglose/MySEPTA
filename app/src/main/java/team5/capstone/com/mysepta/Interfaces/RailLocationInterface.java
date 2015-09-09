package team5.capstone.com.mysepta.Interfaces;

import team5.capstone.com.mysepta.Models.RailModel;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Andrew on 9/7/2015.
 */
public interface RailLocationInterface {
    @GET("/hackathon/TrainView/")
    void locations (
            Callback<ArrayList<RailModel>> callback
    );
}
