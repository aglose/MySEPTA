package team5.capstone.com.mysepta.CallbackProxies;

import java.util.ArrayList;

import retrofit.Callback;
import team5.capstone.com.mysepta.Interfaces.RailScheduleInterface;
import team5.capstone.com.mysepta.Models.TrainScheduleModel;
import team5.capstone.com.mysepta.Services.RailScheduleService;

/**
 * Proxy to call functions to retrieve static rail data.
 * Created by Kevin on 10/21/15.
 */
public class RailScheduleProxy {
    /**
     * Retrieve train schedule.
     * @param trainNumber train number
     * @param callBack callback to hold train data
     */
    public void getRailView(String trainNumber, Callback<ArrayList<TrainScheduleModel>> callBack) {
        RailScheduleInterface railLocationService = RailScheduleService.getRailScheduleInterface();

        railLocationService.locations(trainNumber,callBack);
    }
}
