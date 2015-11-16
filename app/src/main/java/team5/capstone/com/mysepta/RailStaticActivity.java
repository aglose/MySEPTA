package team5.capstone.com.mysepta;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import team5.capstone.com.mysepta.Adapters.RailExpandableAdapter;
import team5.capstone.com.mysepta.CallbackProxies.RailScheduleProxy;
import team5.capstone.com.mysepta.Fragment.RailStaticFragment;
import team5.capstone.com.mysepta.Models.StaticRailModel;
import team5.capstone.com.mysepta.Models.TrainScheduleModel;

/**
 * Created by Kevin on 11/9/15.
 */
public class RailStaticActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private RailExpandableAdapter railExpandableAdapter;

    private HashMap<String,ArrayList<TrainScheduleModel>> fullSchedule;
    private HashMap<String,ArrayList<TrainScheduleModel>> fullReverseSchedule;

    private Toolbar toolbar;
    private Fragment scheduleFrag;

    private String startStation;
    private String endStation;

    private int day;
    private int direction;

    private String railLine;
    private String railLineAcro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_rail);

        fullSchedule = new HashMap<>();
        day=0;
        direction=0;
        startStation="";
        endStation="";

        toolbar = (Toolbar) findViewById(R.id.rail_static_tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Daily Schedule");

        expandableListView = (ExpandableListView) findViewById(R.id.railstaticlistview);

        List<String> railNames = Arrays.asList(getResources().getStringArray(R.array.rail_names));
        List<String> railAcro = Arrays.asList(getResources().getStringArray(R.array.rail_acro));

        Intent args = getIntent();


        if(!args.hasExtra(this.getString(R.string.name_tag)))
            finish();

        railLine = args.getStringExtra(this.getString(R.string.name_tag)).trim();
        railLineAcro = railAcro.get(railNames.indexOf(railLine));

        getSupportActionBar().setTitle("Daily Schedule for " + railLine);

        Thread thread = new Thread(){
            @Override
            public void run(){
                getData();
            }
        };

        thread.start();
    }

    private void getData(){
        fullSchedule = new HashMap<>();
        fullReverseSchedule = new HashMap<>();

        //Build train resource string
        String res = railLineAcro+"_static";
        switch(day){
            case 1://Saturday
                res = res+"_sat";
                break;
            case 2://Sunday
                res = res+"_sun";
                break;
        }
        //if from center city
        if(direction == 1){
            res = res+"_rev";
        }

        //Get train resource
        Integer id = getResources().getIdentifier(res, "array", getPackageName());
        Integer idRev = getResources().getIdentifier(res+"_rev", "array", getPackageName());
        String [] numbers = getResources().getStringArray(id);
        String [] numbersRev = getResources().getStringArray(idRev);

        //populate schedule
        for(String number:numbers){
            getTrainData(number,false,false);
        }

        for(String number:numbersRev){
            getTrainData(number,number.equalsIgnoreCase(numbersRev[numbersRev.length-1]),true);
        }

    }

    /**
     * Retrieve specific trains schedule
     * @param railNumber number of train to retrieve
     */
    private void getTrainData(final String railNumber,final boolean isFinal, final boolean isReverse) {
        Callback callback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                ArrayList<TrainScheduleModel> trainLocationList = (ArrayList<TrainScheduleModel>) o;
                setupRailAdapter(trainLocationList,railNumber,isReverse);
                if(isFinal){
                    handler.sendMessage(handler.obtainMessage());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Retrofit", railNumber + ": fail");
                if(isFinal){
                    handler.sendMessage(handler.obtainMessage());
                }
            }
        };

        RailScheduleProxy railViews = new RailScheduleProxy();
        railViews.getRailView(railNumber, callback);
    }


    private void setupRailAdapter(ArrayList<TrainScheduleModel> train,String railNumber,boolean isReverse){
        if(!isReverse)
            fullSchedule.put(railNumber, train);
        else
            fullReverseSchedule.put(railNumber, train);
    }

    // Handler that will received and process messages in the UI thread
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {

            setupRailAdapter();

            return false;
        }
    });

    private void setupRailAdapter(){
        getRails();
        expandableListView.setAdapter(railExpandableAdapter);
        expandableListView.setFastScrollEnabled(true);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                final String stationName = (String) railExpandableAdapter.getChild(groupPosition, childPosition);
                boolean changeFrag = false;

                if (parent.isGroupExpanded(groupPosition))
                    parent.collapseGroup(groupPosition);

                if (groupPosition == 0) {
                    if (!startStation.equalsIgnoreCase(stationName)) {
                        startStation = stationName;
                        changeFrag = true;
                    }
                } else {
                    if (!endStation.equalsIgnoreCase(stationName)) {
                        endStation = stationName;
                        changeFrag = true;
                    }
                }

                railExpandableAdapter.updateParent(groupPosition, stationName);
                railExpandableAdapter.notifyDataSetChanged();

                if (!startStation.isEmpty() && !endStation.isEmpty() && changeFrag) {
                    scheduleFrag = new RailStaticFragment();
                    loadFragment(R.id.staticschedulefrag, scheduleFrag, false);
                }

                return true;
            }
        });
    }

    private void getRails(){
        String start = this.getResources().getString(R.string.from_rail_text);
        String end = this.getResources().getString(R.string.to_rail_text);
        ArrayList<String> stationNames = new ArrayList<>();

        List<String> stationNamesList = Arrays.asList(getResources().getStringArray(R.array.station_names));
        List<String> apiStationNames = Arrays.asList(getResources().getStringArray(R.array.station_names_api));

        //Get all stations for lines (this is wrong)
        String keyTemp = "";
        int size = 0;
        for(String key : fullSchedule.keySet()){
            ArrayList<TrainScheduleModel> tsmArray = fullSchedule.get(key);

            for(TrainScheduleModel tsm:tsmArray){
                try {
                    String temp = stationNamesList.get(apiStationNames.indexOf(tsm.getStationName().trim()));
                    if(!stationNames.contains(temp)){
                        stationNames.add(temp);
                    }
                }catch (Exception e){
                    Log.d("Static Rail","No such station:"+tsm.getStationName());
                }
            }
        }

        for(String key : fullReverseSchedule.keySet()){
            ArrayList<TrainScheduleModel> tsmArray = fullReverseSchedule.get(key);

            for(TrainScheduleModel tsm:tsmArray){
                try {
                    String temp = stationNamesList.get(apiStationNames.indexOf(tsm.getStationName().trim()));
                    if(!stationNames.contains(temp)){
                        stationNames.add(temp);
                    }
                }catch (Exception e){
                    Log.d("Static Rail","No such station:"+tsm.getStationName());
                }
            }
        }


        Collections.sort(stationNames, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });

        ArrayList<String> groupList = new ArrayList<String>();
        groupList.add(start);
        groupList.add(end);


        Map<String,ArrayList<String>> railList = new LinkedHashMap<String,ArrayList<String>>();
        railList.put(start, stationNames);
        railList.put(end, stationNames);

        railExpandableAdapter = new RailExpandableAdapter(this,groupList,railList);
    }

    public void loadFragment(int paneId,Fragment fragment,boolean placeOnBackStack){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        List<String> stationNamesList = Arrays.asList(getResources().getStringArray(R.array.station_names));
        List<String> apiStationNames = Arrays.asList(getResources().getStringArray(R.array.station_names_api));

        ArrayList<StaticRailModel> trains = new ArrayList<>();

        String startTime="",endTime="";
        Bundle args = new Bundle();
        for(String key:fullSchedule.keySet()){
            ArrayList<TrainScheduleModel> ts = fullSchedule.get(key);
            String start="";
            String end="";
            for(TrainScheduleModel tsm:ts){
                if(stationNamesList.get(apiStationNames.indexOf(tsm.getStationName().trim())).equalsIgnoreCase(startStation)){
                    start = tsm.getScheduledTime();
                    startTime = tsm.getScheduledTime();
                }
                else if(stationNamesList.get(apiStationNames.indexOf(tsm.getStationName().trim())).equalsIgnoreCase(endStation)){
                    end = tsm.getScheduledTime();
                    endTime = tsm.getScheduledTime();
                }
            }

            if(!start.isEmpty() && !end.isEmpty()){
                if(compareTimes(startTime,endTime) > 0)
                    break;
                trains.add(new StaticRailModel(key,start,end));
            }
        }

        if(trains.isEmpty()){
            for(String key:fullReverseSchedule.keySet()){
                ArrayList<TrainScheduleModel> ts = fullReverseSchedule.get(key);
                String start="";
                String end="";
                for(TrainScheduleModel tsm:ts){
                    if(stationNamesList.get(apiStationNames.indexOf(tsm.getStationName().trim())).equalsIgnoreCase(startStation)){
                        start = tsm.getScheduledTime();
                        startTime = tsm.getScheduledTime();
                    }
                    else if(stationNamesList.get(apiStationNames.indexOf(tsm.getStationName().trim())).equalsIgnoreCase(endStation)){
                        end = tsm.getScheduledTime();
                        endTime = tsm.getScheduledTime();
                    }
                }

                if(!start.isEmpty() && !end.isEmpty()){
                    if(compareTimes(startTime,endTime) > 0)
                        break;
                    trains.add(new StaticRailModel(key,start,end));
                }
            }
        }

        args.putParcelableArrayList("trains", trains);

        fragment.setArguments(args);

        fragmentTransaction.replace(paneId, fragment);

        if(placeOnBackStack){
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }


    /**
     * Compare two strings of times.
     * @param start must be time in form HH:mm AM
     * @param end must be time in form HH:mm AM
     * @return 0 if equal, 1 if start < end, else -1
     */
    private int compareTimes(String start,String end){
        int hrLHS,hrRHS,mLHS,mRHS;

        String[] lhsSplit = start.split(":|\\s");
        String[] rhsSplit = end.split(":|\\s");

        hrLHS = Integer.parseInt(lhsSplit[0]);
        hrRHS = Integer.parseInt(rhsSplit[0]);

        //Convert hours into 24 hrs and check for end of day AM trains
        if(lhsSplit[2].equalsIgnoreCase("PM")){
            if(hrLHS != 12)
                hrLHS = hrLHS + 12;
        }
        else if(hrLHS == 12){
            hrLHS = hrLHS + 12;
        }
        else if(hrLHS < 4){
            hrLHS = hrLHS + 24;
        }
        if(rhsSplit[2].equalsIgnoreCase("PM")){
            if(hrRHS != 12)
                hrRHS = hrRHS + 12;
        }
        else if(hrRHS == 12){
            hrRHS = hrRHS + 12;
        }
        else if(hrRHS < 4){
            hrRHS = hrRHS + 24;
        }

        //if hours are same compare minutes
        if(hrLHS == hrRHS){
            mLHS = Integer.parseInt(lhsSplit[1]);
            mRHS = Integer.parseInt(rhsSplit[1]);

            if(mLHS < mRHS){
                return -1;
            }
            else if(mLHS > mRHS){
                return 1;
            }
            else{
                return 0;
            }
        }
        else if(hrLHS < hrRHS){
            return -1;
        }
        else{
            return 1;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case android.R.id.home:
                finish();
                return true;
           /* case R.id.favoriteIcon:
                if(favorite){
                    removeLineFromFavorites();
                }
                else{
                    addLineToFavorites();
                }
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
