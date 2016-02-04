package team5.capstone.com.mysepta.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit.http.GET;
import team5.capstone.com.mysepta.Adapters.BusTimeAdapter;
import team5.capstone.com.mysepta.Models.BusModelHolder;
import team5.capstone.com.mysepta.Models.BusTimeModel;
import team5.capstone.com.mysepta.R;

public class BusTimesActivity extends AppCompatActivity {
    private static final String TAG = "BusTimesActivity";

    private Toolbar toolbar;
    private BusModelHolder busModelHolder;
    private String stopId;
    private String direction;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_times);

        Intent intent = getIntent();
        String busStop = intent.getStringExtra("busStop");
        stopId = intent.getStringExtra("stopId");
        direction = intent.getStringExtra("direction");

        if (direction == null) {
            direction = "i";
        }
        toolbar = (Toolbar) findViewById(R.id.busTimeToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //ENABLE BACK BUTTON
        getSupportActionBar().setTitle("Bus Schedules for " + busStop);

        retrieveSchedule();
    }

    private void retrieveSchedule() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www3.septa.org/hackathon/BusSchedules/?req1="+stopId+"&req3=i&req6=7";

        JSONObject jsonRequest = null;

        Response.Listener listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                createList(response);
            }
        };

        Response.ErrorListener li = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, jsonRequest, listener,
                li);

        queue.add(jsonObjectRequest);
    }

    private void createList(JSONObject response) {
        busModelHolder = new BusModelHolder();
        JSONArray busLineNames = response.names();
        for(int i = 0; i< busLineNames.length(); i++){
            try {
                String busLineName = busLineNames.get(i).toString();
                Log.d(TAG, "arrayName"+busLineName);
                JSONObject busLineObject = response.getJSONObject(busLineName);
                JSONArray busLineObjectOrder = busLineObject.names();
                for(int j=0; j<busLineObjectOrder.length(); j++) {
                    busLineObject.getJSONArray((String) busLineObjectOrder.get(j));
                    for (int k = 0; k < busLineObject.length(); k++) {

                        BusTimeModel busTimeModel = new BusTimeModel();
                        JSONObject jsonObject = jsonArray.getJSONObject(k);
                        Log.d(TAG, "jsonObject" + jsonObject.toString());
                        busTimeModel.setRoute(jsonObject.getString("Route"));
                        busTimeModel.setDate(jsonObject.getString("date"));
                        busTimeModel.setDirection(jsonObject.getString("direction"));

                        busModelHolder.addBusScheduleModel(busTimeModel);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        adapter = new BusTimeAdapter(this, busModelHolder.getBusScheduleList());

        recyclerView = (RecyclerView) findViewById(R.id.busTimeRecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

}
