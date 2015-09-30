package team5.capstone.com.mysepta.CallbackProxies;

import java.util.ArrayList;

import retrofit.Callback;
import team5.capstone.com.mysepta.Interfaces.RailLocationInterface;
import team5.capstone.com.mysepta.Interfaces.SubwayLocationInterface;
import team5.capstone.com.mysepta.Models.RailModel;
import team5.capstone.com.mysepta.Models.SubwayNextModel;
import team5.capstone.com.mysepta.Services.RailLocationService;
import team5.capstone.com.mysepta.Services.SubwayLocationService;

/**
 * Created by Andrew on 9/20/2015.
 */
public class SubwayLocationProxy {
    public void getNextSubwayView(Callback<ArrayList<String>> callBack, String location) {
        SubwayLocationInterface subwayLocationService = SubwayLocationService.getSubwayLocationInterface();
        subwayLocationService.getNextSubwayData(location, callBack);
    }
}
