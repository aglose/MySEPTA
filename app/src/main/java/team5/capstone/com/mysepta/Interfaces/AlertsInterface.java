package team5.capstone.com.mysepta.Interfaces;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import team5.capstone.com.mysepta.Models.AlertsModel;

/**
 *
 */
public interface AlertsInterface {

    /**
     *
     * @param callback
     */
    @GET("/hackathon/Alerts/")
    void alerts (

            Callback<ArrayList<AlertsModel>> callback
    );
}
