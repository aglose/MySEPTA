package team5.capstone.com.mysepta.Fragment;


import android.os.Bundle;
import android.app.Fragment;
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
import team5.capstone.com.mysepta.Adapters.RailScheduleAdapter;
import team5.capstone.com.mysepta.Adapters.RailToFromViewAdapter;
import team5.capstone.com.mysepta.CallbackProxies.NextToArriveRailProxy;
import team5.capstone.com.mysepta.CallbackProxies.RailLocationProxy;
import team5.capstone.com.mysepta.Models.NextToArriveRailModel;
import team5.capstone.com.mysepta.Models.RailLocationData;
import team5.capstone.com.mysepta.Models.RailModel;
import team5.capstone.com.mysepta.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class RailScheduleFragment extends Fragment {

    private RecyclerView railSchedule;
    private ArrayList<RailLocationData> rails;
    private View view;

    public RailScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_rail_schedule, container, false);


        railSchedule = (RecyclerView) view.findViewById(R.id.railSchedule);
        railSchedule.setLayoutManager(new LinearLayoutManager(view.getContext()));


        getNextTrainData("Philmont","Ardmore","5");

        // Inflate the layout for this fragment
        return view;
    }

    private void getNextTrainData(final String startStation, final String endStation,String numResults) {
        Callback callback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                ArrayList<NextToArriveRailModel> trainLocationList = (ArrayList<NextToArriveRailModel>) o;
                setupRailAdapter(trainLocationList,endStation,startStation);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Debug", "fail");
            }
        };

        NextToArriveRailProxy railViews = new NextToArriveRailProxy();
        railViews.getRailView(startStation,endStation,numResults,callback);
    }

    private void setupRailAdapter(ArrayList<NextToArriveRailModel> trainList,String finalStation,String startStation){
        if(trainList.isEmpty()){
            Log.d("Debug", "No train data");
        }

        rails = new ArrayList<>();

        for(NextToArriveRailModel train : trainList){
            if(train.getIsDirect().equalsIgnoreCase("true")){
                rails.add(new RailLocationData("ACR",
                        train.getOrigTrainName(),
                        finalStation,
                        train.getOrigTrainNumber(),
                        train.getOrigDepartureTime(),
                        false));
            }
            else{
                rails.add(new RailLocationData("ACR",
                        train.getOrigTrainName(),
                        train.getConStation(),
                        train.getOrigTrainNumber(),
                        train.getOrigDepartureTime(),
                        false));

                rails.add(new RailLocationData("ACR",
                        train.getConTrainName(),
                        finalStation,
                        train.getConTrainNumber(),
                        train.getConDepartureTime(),
                        true));
            }

        }

        railSchedule.setAdapter(new RailScheduleAdapter(rails, view.getContext(),startStation,finalStation));
    }

}
