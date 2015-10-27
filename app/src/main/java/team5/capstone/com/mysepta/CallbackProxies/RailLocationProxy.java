package team5.capstone.com.mysepta.CallbackProxies;

import team5.capstone.com.mysepta.Interfaces.RailLocationInterface;
import team5.capstone.com.mysepta.Models.RailModel;
import team5.capstone.com.mysepta.Services.RailLocationService;

import java.util.ArrayList;

import retrofit.Callback;

/**
 * Proxy to call functions to retrieve rail location data.
 * Created by Andrew on 9/7/2015.
 */
public class RailLocationProxy {
    /**
     * Retrieve rail location data.
     * @param callBack callback to hold train data
     */
    public void getRailView(Callback<ArrayList<RailModel>> callBack) {
        RailLocationInterface railLocationService = RailLocationService.getRailLocationInterface();

        railLocationService.locations(callBack);
    }
}
