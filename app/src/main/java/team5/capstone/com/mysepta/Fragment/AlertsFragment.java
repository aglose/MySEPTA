package team5.capstone.com.mysepta.Fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import team5.capstone.com.mysepta.Adapters.AlertsAdapter;
import team5.capstone.com.mysepta.CallbackProxies.AlertsProxy;
import team5.capstone.com.mysepta.Models.AlertsModel;
import team5.capstone.com.mysepta.R;


/**
 * Organizes alert data
 */
public class AlertsFragment extends Fragment {

    private RecyclerView alertsRecyclerView;
    private View view;
    public static ArrayList<AlertsModel> aList;
    public static ArrayList<AlertsModel> generalList;
    public static ArrayList<AlertsModel> rrList;
    public static ArrayList<AlertsModel> subList;

    private AlertsAdapter alertsAdapter;

    /**
     * Empty Constructor
     */
    public AlertsFragment() {}

    /**
     * Creates view for Alerts
     * @param inflater inflates the view and formats the data
     * @param container holds the view
     * @param savedInstanceState saved state on close
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_alerts, container, false);

        //getAlertsData();

        alertsRecyclerView = (RecyclerView) view.findViewById(R.id.alertsRecyclerView);
        alertsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        aList = new ArrayList<>();
        alertsAdapter = new AlertsAdapter(aList, view.getContext());
        alertsRecyclerView.setAdapter(alertsAdapter);

        return view;
    }

 }



