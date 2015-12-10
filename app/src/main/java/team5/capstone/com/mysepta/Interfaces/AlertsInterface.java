package team5.capstone.com.mysepta.Interfaces;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import team5.capstone.com.mysepta.Models.AlertsModel;

/**
 * Retrieve alert data
 * Created by Matt
 */
public interface AlertsInterface {

    /**
     * Retrieve Alert data
     * @param callback callback to hold alert data
     */
    @GET("/hackathon/Alerts/")
    void alerts (

            Callback<ArrayList<AlertsModel>> callback
    );
}
