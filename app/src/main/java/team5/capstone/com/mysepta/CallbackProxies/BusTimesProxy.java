package team5.capstone.com.mysepta.CallbackProxies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import team5.capstone.com.mysepta.Interfaces.BusStopsInterface;
import team5.capstone.com.mysepta.Interfaces.BusTimesInterface;
import team5.capstone.com.mysepta.Models.BusStopModel;
import team5.capstone.com.mysepta.Models.BusTimeModel;
import team5.capstone.com.mysepta.Services.BusStopsService;
import team5.capstone.com.mysepta.Services.BusTimesService;

/**
 * Created by Andrew on 2/3/2016.
 */
public class BusTimesProxy {
    private static final String NUMBER_OF_RESULTS = "7";

    public void getTimesForStop(String stopId, String direction, Callback<ArrayList<BusTimeModel>> callBack) {
        BusTimesInterface busTimesInterface = BusTimesService.getBusTimesInterface();

        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("req1", String.valueOf(stopId));
        if (direction != null) {
            stringMap.put("req3", direction);
        }
        stringMap.put("req6", NUMBER_OF_RESULTS);

        busTimesInterface.times(stringMap, callBack);
    }
}
