package team5.capstone.com.mysepta.Fragment;


import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import team5.capstone.com.mysepta.Adapters.AlertsAdapter;
import team5.capstone.com.mysepta.CallbackProxies.AlertsProxy;
import team5.capstone.com.mysepta.CallbackProxies.NextToArriveRailProxy;
import team5.capstone.com.mysepta.Models.AlertsModel;
import team5.capstone.com.mysepta.Models.NextToArriveRailModel;
import team5.capstone.com.mysepta.Models.RailLocationData;
import team5.capstone.com.mysepta.R;


/**
 *
 */
public class AlertsFragment extends Fragment {

    private RecyclerView alertsView;
    private View view;
    private ArrayList<AlertsModel> alertList;


    private String mode;
    private String routeName;

    public AlertsFragment() {
        // Required empty public constructor
    }

    public static AlertsFragment newInstance() {
        return new AlertsFragment();
    }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_alerts, container, false);
        alertsView = (RecyclerView) view.findViewById(R.id.alerts);
        alertsView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        Resources res = getResources();

        Bundle args = getArguments();
        mode = args.getString("mode", "Regional Rail");
        routeName = args.getString("routeName", "Trenton");

        getAlertsData(routeName, mode);

        return view;
    }


    /**
     *
     * @param route_Name
     * @param travel_Mode
     */
    private void getAlertsData(final String route_Name, final String travel_Mode){
        Callback callback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                ArrayList<AlertsModel> aList = (ArrayList<AlertsModel>) o;
                setUpAlertsAdapter(aList, route_Name, travel_Mode);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Debug", "fail");
            }
        };

        AlertsProxy alertsViews = new AlertsProxy();
        alertsViews.getAlertsView(route_Name, travel_Mode, callback);
    }

    /**
     *
     * @param alertsList
     * @param route_Name
     * @param travel_Mode
     */
    private void setUpAlertsAdapter(ArrayList<AlertsModel> alertsList,String route_Name, String travel_Mode){
        if(alertsList.isEmpty()){
            Log.d("Debug", "No alert data");
        }

        alertList = new ArrayList<>();

        for(AlertsModel alert : alertsList) {
            if(alert.getMode().equalsIgnoreCase("generic")){
                alertList.add(new AlertsModel(
                        alert.getMode(),
                        alert.getRouteName(),
                        alert.getLastUpdate(),
                        alert.getDescription()));
            }else if(alert.getMode().equalsIgnoreCase("Regional Rail")){
                alertList.add(new AlertsModel(
                        alert.getMode(),
                        alert.getRouteName(),
                        alert.getLastUpdate(),
                        alert.getDescription()));
            }else if (alert.getMode().equalsIgnoreCase("Broad Street Line")){
                alertList.add(new AlertsModel(
                        alert.getMode(),
                        alert.getRouteName(),
                        alert.getLastUpdate(),
                        alert.getDescription()));
            }else if (alert.getMode().equalsIgnoreCase("Market Frankford Line")){
                alertList.add(new AlertsModel(
                        alert.getMode(),
                        alert.getRouteName(),
                        alert.getLastUpdate(),
                        alert.getDescription()));
            }
        //if list is fully updated condition
        alertsView.setAdapter(new AlertsAdapter(alertList, view.getContext(), route_Name, travel_Mode));
    }

}
}


