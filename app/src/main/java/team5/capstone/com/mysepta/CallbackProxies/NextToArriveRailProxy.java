package team5.capstone.com.mysepta.CallbackProxies;

import java.util.ArrayList;

import retrofit.Callback;
import team5.capstone.com.mysepta.Interfaces.NextToArriveRailInterface;
import team5.capstone.com.mysepta.Interfaces.RailLocationInterface;
import team5.capstone.com.mysepta.Models.NextToArriveRailModel;
import team5.capstone.com.mysepta.Models.RailModel;
import team5.capstone.com.mysepta.Services.NextToArriveRailService;
import team5.capstone.com.mysepta.Services.RailLocationService;

/**
 * Proxy to call functions to retrieve nextToArrive data.
 * Created by Kevin on 10/12/15.
 */
public class NextToArriveRailProxy {
    /**
     * Retrieve NextToArriveData between start and end station.
     * @param startStation starting station
     * @param endStation ending station
     * @param numResults number of results to get
     * @param callBack callback to hold train data
     */
    public void getRailView(String startStation, String endStation, String numResults,Callback<ArrayList<NextToArriveRailModel>> callBack) {
        NextToArriveRailInterface railLocationService = NextToArriveRailService.getRailLocationInterface();

        railLocationService.locations(startStation,endStation,numResults,callBack);
    }
}
