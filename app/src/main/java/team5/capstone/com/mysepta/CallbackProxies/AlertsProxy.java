package team5.capstone.com.mysepta.CallbackProxies;

import java.util.ArrayList;

import retrofit.Callback;
import team5.capstone.com.mysepta.Interfaces.AlertsInterface;
import team5.capstone.com.mysepta.Models.AlertsModel;
import team5.capstone.com.mysepta.Services.AlertsService;

/**
 *
 */
public class AlertsProxy {

    /**
     *
     * @param callBack
     */
    public void getAlertsView(Callback<ArrayList<AlertsModel>> callBack) {
       AlertsInterface alertsService = AlertsService.getAlertsInterface();

        alertsService.alerts(callBack);
    }
}
