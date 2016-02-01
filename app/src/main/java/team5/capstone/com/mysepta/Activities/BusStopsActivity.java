package team5.capstone.com.mysepta.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import team5.capstone.com.mysepta.Adapters.BusStopsAdapter;
import team5.capstone.com.mysepta.CallbackProxies.BusStopsProxy;
import team5.capstone.com.mysepta.CallbackProxies.NearestLocationProxy;
import team5.capstone.com.mysepta.Models.BusStopModel;
import team5.capstone.com.mysepta.Models.NearestLocationModel;
import team5.capstone.com.mysepta.R;

/**
 * Created by Andrew on 1/27/2016.
 */
public class BusStopsActivity extends AppCompatActivity {
    private static final String TAG = "BusStopsActivity";

    private Toolbar toolbar;
    private String busLine;
    private ArrayList<BusStopModel> bustStopsList;
    private BusStopsAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stops);

        Intent intent = getIntent();
        busLine = intent.getStringExtra("busLine");

        toolbar = (Toolbar) findViewById(R.id.busStopToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //ENABLE BACK BUTTON
        getSupportActionBar().setTitle("Bus Stop " + busLine);
        
        retrieveStops();
    }

    private void retrieveStops() {
        Callback callback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                bustStopsList = (ArrayList<BusStopModel>) o;
                Log.d(TAG, bustStopsList.toString());
                setUpRecyclerView();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Debug", String.valueOf(error.getResponse()));
            }
        };

        BusStopsProxy stopsProxy = new BusStopsProxy();
        stopsProxy.getStopsForLine(busLine, callback);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    private void setUpRecyclerView(){
        adapter = new BusStopsAdapter(this, bustStopsList);

        recyclerView = (RecyclerView) findViewById(R.id.busStopsRecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}
