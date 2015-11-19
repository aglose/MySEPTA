package team5.capstone.com.mysepta.Fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import team5.capstone.com.mysepta.Adapters.AlertsAdapter;
import team5.capstone.com.mysepta.CallbackProxies.AlertsProxy;
import team5.capstone.com.mysepta.Models.AlertsModel;
import team5.capstone.com.mysepta.R;


/**
 *
 */
public class AlertsFragment extends Fragment {

    private RecyclerView alertsRecyclerView;
    private View view;
    private ArrayList<AlertsModel> aList;
    private AlertsAdapter alertsAdapter;

    public AlertsFragment() {}

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

        getAlertsData();

        alertsRecyclerView = (RecyclerView) view.findViewById(R.id.alertsRecyclerView);
        alertsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        aList = new ArrayList<>();
        alertsAdapter = new AlertsAdapter(aList, view.getContext());
        alertsRecyclerView.setAdapter(alertsAdapter);

        return view;
    }


    /**
     *
     */
    public void getAlertsData(){
        Callback callback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                ArrayList<AlertsModel> aList = (ArrayList<AlertsModel>) o;
                setUpAlertsAdapter(aList);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Debug", "fail");
            }
        };

        AlertsProxy alertsViews = new AlertsProxy();
        alertsViews.getAlertsView(callback);
    }

    /**
     *
     * @param alertsList
     */
    public void setUpAlertsAdapter(ArrayList<AlertsModel> alertsList){
        if(alertsList.isEmpty()){
            Log.d("Debug", "No alert data");
        }

        for(AlertsModel alert : alertsList) {
            alertsAdapter.addItem(alert);
        }
    }

}



