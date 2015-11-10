package team5.capstone.com.mysepta.CallbackProxies;

import android.location.LocationManager;

import java.util.ArrayList;

import retrofit.Callback;
import team5.capstone.com.mysepta.Interfaces.NearestLocationInterface;
import team5.capstone.com.mysepta.Interfaces.RailLocationInterface;
import team5.capstone.com.mysepta.Models.NearestLocationModel;
import team5.capstone.com.mysepta.Models.RailModel;
import team5.capstone.com.mysepta.Services.NearestLocationService;
import team5.capstone.com.mysepta.Services.RailLocationService;

/**
 * Created by tiestodoe on 11/9/15.
 */
public class NearestLocationProxy {
    /**
     * Retrieve nearest location data.
     * @param callBack callback to hold location data
     */
    public void getNearestView(double latitude, double longitude, Callback<ArrayList<NearestLocationModel>> callBack) {

        NearestLocationInterface nearestLocationService = NearestLocationService.getNearestLocationInterface();

        nearestLocationService.locations(String.valueOf(latitude), String.valueOf(longitude), callBack);
    }
}
