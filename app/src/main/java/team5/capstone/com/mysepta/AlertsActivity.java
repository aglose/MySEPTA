package team5.capstone.com.mysepta;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import team5.capstone.com.mysepta.Adapters.AlertsAdapter;
import team5.capstone.com.mysepta.CallbackProxies.AlertsProxy;
import team5.capstone.com.mysepta.Fragment.AlertsFragment;
import team5.capstone.com.mysepta.Models.AlertsModel;

/**
 *
 */
public class AlertsActivity extends AppCompatActivity {
    private static final String TAG = "AlertsActivity";


    private ArrayList<AlertsModel> aList;
    public MaterialViewPager mViewPager;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);

        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

        toolbar = mViewPager.getToolbar();

        getAlertsData();

        RecyclerView alertView = (RecyclerView) findViewById(R.id.alerts);

        AlertsAdapter adapter = new AlertsAdapter(aList, this);
    // Attach the adapter to the recyclerview to populate items
        alertView.setAdapter(adapter);
    // Set layout manager to position the items
        alertView.setLayoutManager(new LinearLayoutManager(this));
    // That's all!

    }

    /**
     *
     */
    public void getAlertsData(){
        Callback callback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                ArrayList<AlertsModel> aList = (ArrayList<AlertsModel>) o;
                setUpAlertsAdapter(aList);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Debug", "fail");
            }
        };

        AlertsProxy alertsViews = new AlertsProxy();
        alertsViews.getAlertsView(callback);
    }

    /**
     *
     * @param alertsList
     */
    public void setUpAlertsAdapter(ArrayList<AlertsModel> alertsList){
        if(alertsList.isEmpty()){
            Log.d("Debug", "No alert data");
        }

        aList = new ArrayList<>();

        for(AlertsModel alert : alertsList) {

            aList.add(new AlertsModel(
                    alert.getMode(),
                    alert.getRouteName(),
                    alert.getLastUpdate(),
                    alert.getDescription()));
        }
        //if list is fully updated condition
    }
}