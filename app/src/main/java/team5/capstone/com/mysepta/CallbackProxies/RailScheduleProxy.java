package team5.capstone.com.mysepta.CallbackProxies;

import java.util.ArrayList;

import retrofit.Callback;
import team5.capstone.com.mysepta.Interfaces.RailScheduleInterface;
import team5.capstone.com.mysepta.Models.TrainScheduleModel;
import team5.capstone.com.mysepta.Services.RailScheduleService;

/**
 * Created by Kevin on 10/21/15.
 */
public class RailScheduleProxy {
    public void getRailView(String trainNumber, Callback<ArrayList<TrainScheduleModel>> callBack) {
        RailScheduleInterface railLocationService = RailScheduleService.getRailScheduleInterface();

        railLocationService.locations(trainNumber,callBack);
    }
}
