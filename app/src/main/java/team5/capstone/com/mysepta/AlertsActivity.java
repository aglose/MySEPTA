package team5.capstone.com.mysepta;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import team5.capstone.com.mysepta.Adapters.AlertsAdapter;
import team5.capstone.com.mysepta.Fragment.AlertsFragment;
import team5.capstone.com.mysepta.Models.AlertsModel;

/**
 *
 */
public class AlertsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private AlertsAdapter alertsAdapter;
    private ArrayList<AlertsModel> alerts;
    private Fragment alertsFragment;
    private ArrayList<String> travel_modes;


    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        setContentView(R.layout.activity_alerts);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Alerts");


        recyclerView = (RecyclerView) findViewById(R.id.recyc_view);
        //getAlerts();
        alertsAdapter = new AlertsAdapter(alerts, this, );
        recyclerView.setAdapter(alertsAdapter);


        alertsFragment = new AlertsFragment();
        */
    }}

