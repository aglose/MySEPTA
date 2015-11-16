package team5.capstone.com.mysepta.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import team5.capstone.com.mysepta.Adapters.RailStaticAdapter;
import team5.capstone.com.mysepta.Models.StaticRailModel;
import team5.capstone.com.mysepta.R;


public class RailStaticFragment extends Fragment {

    public static RailStaticFragment newInstance() {
        RailStaticFragment fragment = new RailStaticFragment();
        return fragment;
    }

    public RailStaticFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rail_static, container, false);

        RecyclerView scheduleView = (RecyclerView) view.findViewById(R.id.railStaticSchedule);
        scheduleView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        Bundle args = getArguments();

        ArrayList<StaticRailModel> trains = args.getParcelableArrayList("trains");

        scheduleView.setAdapter(new RailStaticAdapter(trains));
        // Inflate the layout for this fragment
        return view;
    }


}
