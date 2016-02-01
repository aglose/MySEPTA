package team5.capstone.com.mysepta.CallbackProxies;

import java.util.ArrayList;

import retrofit.Callback;
import team5.capstone.com.mysepta.Interfaces.BusStopsInterface;
import team5.capstone.com.mysepta.Interfaces.NearestLocationInterface;
import team5.capstone.com.mysepta.Models.BusStopModel;
import team5.capstone.com.mysepta.Models.NearestLocationModel;
import team5.capstone.com.mysepta.Services.BusStopsService;
import team5.capstone.com.mysepta.Services.NearestLocationService;

/**
 * Created by Andrew on 1/26/2016.
 */
public class BusStopsProxy {

    public void getStopsForLine(String stopNumber, Callback<ArrayList<BusStopModel>> callBack) {

        BusStopsInterface busStopsInterface = BusStopsService.getBusStopsInterface();

        busStopsInterface.stops(stopNumber, callBack);
    }
}
