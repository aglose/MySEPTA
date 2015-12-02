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

public class SubAlertsFragment extends Fragment {

    private RecyclerView alertsRecyclerView;
    private View view;
    public ArrayList<AlertsModel> subList;

    private AlertsAdapter alertsAdapter;

    public SubAlertsFragment(ArrayList<AlertsModel> subList) {
        this.subList = subList;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_alerts, container, false);

        alertsRecyclerView = (RecyclerView) view.findViewById(R.id.alertsRecyclerView);
        alertsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        alertsAdapter = new AlertsAdapter(subList, view.getContext());
        alertsRecyclerView.setAdapter(alertsAdapter);

        return view;
    }
}
