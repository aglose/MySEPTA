package team5.capstone.com.mysepta.Fragment;


import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
 * Fragment for rail schedule.
 */
public class RailScheduleFragment extends Fragment {

    private RecyclerView railSchedule;
    private ArrayList<RailLocationData> rails;
    private View view;
    private String start;
    private String end;
    private String numResults;
    private List<String> railNames;
    private List<String> railAcro;
    private List<String> stationNames;
    private List<String> apiStationNames;

    /**
     * Empty constructor
     */
    public RailScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Create and initialize schedule view
     * @param inflater layout inflater
     * @param container parent view group
     * @param savedInstanceState saved instance on close
     * @return fragment view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_rail_schedule, container, false);

        railSchedule = (RecyclerView) view.findViewById(R.id.railSchedule);
        railSchedule.setLayoutManager(new LinearLayoutManager(view.getContext()));

        Resources res = getResources();

        railNames = Arrays.asList(res.getStringArray(R.array.rail_names));
        railAcro = Arrays.asList(res.getStringArray(R.array.rail_acro));
        stationNames = Arrays.asList(res.getStringArray(R.array.station_names));
        apiStationNames = Arrays.asList(res.getStringArray(R.array.station_names_api));

        Bundle args = getArguments();
        start = args.getString("start","Philmont");
        end = args.getString("end","Ardmore");
        numResults = args.getString("results","5");

        getNextTrainData(start,end,numResults);

        // Inflate the layout for this fragment
        return view;
    }

    /**
     * Retrieve NextToArrive trains from strating station to ending station.
     * @param startStation starting station for schedule
     * @param endStation ending station for schedule
     * @param numResults number of results
     */
    public void getNextTrainData(final String startStation, final String endStation,String numResults) {
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
        railViews.getRailView(apiStationNames.get(stationNames.indexOf(startStation)),
                apiStationNames.get(stationNames.indexOf(endStation)),numResults,callback);
    }

    /**
     * Iinitialize rail adapter and set it.
     * @param trainList list of trains to arrive
     * @param finalStation final station
     * @param startStation starting station
     */
    public void setupRailAdapter(ArrayList<NextToArriveRailModel> trainList,String finalStation,String startStation){
        if(trainList.isEmpty()){
            Log.d("Debug", "No train data");
        }

        rails = new ArrayList<>();

        for(NextToArriveRailModel train : trainList){
            Log.d("Train name:",train.getOrigTrainName());
            if(train.getIsDirect().equalsIgnoreCase("true")){
                rails.add(new RailLocationData(railAcro.get(railNames.indexOf(train.getOrigTrainName().trim())),
                        train.getOrigTrainName(),
                        finalStation,
                        train.getOrigTrainNumber(),
                        train.getOrigDepartureTime(),
                        false));
            }
            else{
                rails.add(new RailLocationData(railAcro.get(railNames.indexOf(train.getOrigTrainName().trim())),
                        train.getOrigTrainName(),
                        train.getConStation(),
                        train.getOrigTrainNumber(),
                        train.getOrigDepartureTime(),
                        false));

                rails.add(new RailLocationData(railAcro.get(railNames.indexOf(train.getConTrainName().trim())),
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
