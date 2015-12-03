package team5.capstone.com.mysepta.CallbackProxies;

import java.util.ArrayList;

import retrofit.Callback;
import team5.capstone.com.mysepta.Interfaces.AlertsDescriptionInterface;
import team5.capstone.com.mysepta.Models.AlertsDescriptionModel;
import team5.capstone.com.mysepta.Services.AlertsDescriptionService;

/**
 * Created by Andrew on 12/2/2015.
 */
public class AlertsDescriptionProxy {
    /**
     *
     * @param callBack
     */
    public void getAlertsDescriptionView(String routeID, Callback<ArrayList<AlertsDescriptionModel>> callBack) {
        AlertsDescriptionInterface alertsService = AlertsDescriptionService.getAlertsDescriptionInterface();

        alertsService.alerts(routeID, callBack);
    }

}
