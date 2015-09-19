package team5.capstone.com.mysepta.CallbackProxies;

import team5.capstone.com.mysepta.Interfaces.RailLocationInterface;
import team5.capstone.com.mysepta.Models.RailModel;
import team5.capstone.com.mysepta.Services.RailLocationService;

import java.util.ArrayList;

import retrofit.Callback;

/**
 * Created by Andrew on 9/7/2015.
 */
public class RailLocationProxy {
    public void getRailView(Callback<ArrayList<RailModel>> callBack) {
        RailLocationInterface railLocationService = RailLocationService.getRailLocationInterface();

        railLocationService.locations(callBack);
    }
}
