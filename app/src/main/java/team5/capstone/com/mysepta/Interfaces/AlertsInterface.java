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
     * @param route_Name
     * @param travel_Mode
     * @param callback
     */
    @GET("/hackathon/Alerts/")
    void alerts (
            @Path("routeName") String route_Name,
            @Path("mode") String travel_Mode,
            Callback<ArrayList<AlertsModel>> callback
    );
}
