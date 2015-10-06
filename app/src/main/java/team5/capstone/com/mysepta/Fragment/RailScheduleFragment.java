package team5.capstone.com.mysepta.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import team5.capstone.com.mysepta.Adapters.RailScheduleAdapter;
import team5.capstone.com.mysepta.Adapters.RailToFromViewAdapter;
import team5.capstone.com.mysepta.Models.RailLocationData;
import team5.capstone.com.mysepta.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class RailScheduleFragment extends Fragment {

    private RecyclerView railSchedule;
    private ArrayList<RailLocationData> rails;

    public RailScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rail_schedule, container, false);


        railSchedule = (RecyclerView) view.findViewById(R.id.railSchedule);
        railSchedule.setLayoutManager(new LinearLayoutManager(view.getContext()));

        rails = new ArrayList<>();
        rails.add(new RailLocationData("WTR","West Trenton","Philmont",776,"12:00 PM"));
        rails.add(new RailLocationData("WTR","West Trenton","Philmont",777,"12:05 PM"));
        rails.add(new RailLocationData("WTR","West Trenton","Philmont",778,"12:10 PM"));
        rails.add(new RailLocationData("WTR", "West Trenton", "Philmont", 779, "12:15 PM"));

        railSchedule.setAdapter(new RailScheduleAdapter(rails, view.getContext(),"Philmont","Temple University"));

        // Inflate the layout for this fragment
        return view;
    }


}
