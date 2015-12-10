package team5.capstone.com.mysepta.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import team5.capstone.com.mysepta.Adapters.AlertsAdapter;
import team5.capstone.com.mysepta.Models.AlertsModel;
import team5.capstone.com.mysepta.R;

/**
 * Displays Regional Rail Alerts
 */
public class RRAlertsFragment extends Fragment {

    private RecyclerView alertsRecyclerView;
    private View view;
    public ArrayList<AlertsModel> rrList;

    private AlertsAdapter alertsAdapter;

    /**
     * Constructor
     * @param rrList arraylist of rr alerts
     */
    public RRAlertsFragment(ArrayList<AlertsModel> rrList) {
        this.rrList = rrList;
    }

    /**
     * Creates view for Regional Rail Alerts
     * @param inflater inflates the view and formats the data
     * @param container holds the view
     * @param savedInstanceState saved state on close
     * @return view
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_alerts, container, false);

        alertsRecyclerView = (RecyclerView) view.findViewById(R.id.alertsRecyclerView);
        alertsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        alertsAdapter = new AlertsAdapter(rrList, view.getContext());
        alertsRecyclerView.setAdapter(alertsAdapter);

        return view;
    }
}
