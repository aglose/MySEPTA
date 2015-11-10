package team5.capstone.com.mysepta;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import team5.capstone.com.mysepta.Adapters.RailExpandableAdapter;
import team5.capstone.com.mysepta.DropDownView.RailChooser;
import team5.capstone.com.mysepta.DropDownView.RailChooserChild;
import team5.capstone.com.mysepta.Fragment.RailScheduleFragment;
import team5.capstone.com.mysepta.Fragment.ToFromFragment;
import team5.capstone.com.mysepta.Managers.FavoritesManager;

public class RailActivity extends AppCompatActivity implements ToFromFragment.OnFragmentInteractionListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Toolbar toolbar;
    private ExpandableListView expandableListView;
    private ArrayList<String> groupList;
    Map<String,ArrayList<String>> railList;
    private RailExpandableAdapter railExpandableAdapter;
    private String start,end;
    private Fragment scheduleFrag;
    private Menu mOptionsMenu;
    private boolean favorite;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rail);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Live View");

        expandableListView = (ExpandableListView) findViewById(R.id.tofromlistview);
        getRails();
        railExpandableAdapter = new RailExpandableAdapter(this,groupList,railList);
        expandableListView.setAdapter(railExpandableAdapter);
        expandableListView.setFastScrollEnabled(true);

        scheduleFrag = new RailScheduleFragment();

        start = "";
        end = "";
        favorite = false;

        Intent args = getIntent();
        String tempStart = args.getStringExtra(getResources().getString(R.string.name_tag));
        String tempEnd = args.getStringExtra(getResources().getString(R.string.loc_tag));

        if(tempStart != null) {
            start = tempStart;
            railExpandableAdapter.updateParent(0, start);
            railExpandableAdapter.notifyDataSetChanged();
        }else{
            buildGoogleApiClient();
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
                double latitude = mLastLocation.getLatitude();
                double longitude = mLastLocation.getLongitude();
            }
        }

        if(tempEnd != null) {
            end = tempEnd;
            railExpandableAdapter.updateParent(1, end);
            railExpandableAdapter.notifyDataSetChanged();
        }

        if(!start.isEmpty() && !end.isEmpty()){
            scheduleFrag = new RailScheduleFragment();
            loadFragment(R.id.schedulefrag, scheduleFrag, false);
        }

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                final String stationName = (String) railExpandableAdapter.getChild(groupPosition, childPosition);
                boolean changeFrag = false;

                if (parent.isGroupExpanded(groupPosition))
                    parent.collapseGroup(groupPosition);

                if(groupPosition == 0) {
                    if(!start.equalsIgnoreCase(stationName)) {
                        start = stationName;
                        changeFrag = true;
                    }
                }
                else {
                    if (!end.equalsIgnoreCase(stationName)) {
                        end = stationName;
                        changeFrag = true;
                    }
                }

                railExpandableAdapter.updateParent(groupPosition, stationName);
                railExpandableAdapter.notifyDataSetChanged();

                if(start.equalsIgnoreCase(end)){
                    mOptionsMenu.findItem(R.id.favoriteIcon).setVisible(false);
                    scheduleFrag = new RailScheduleFragment();
                    loadFragment(R.id.schedulefrag, scheduleFrag, false);
                }
                else if (!start.isEmpty() && !end.isEmpty() && changeFrag) {
                    if(!mOptionsMenu.findItem(R.id.favoriteIcon).isVisible()){
                        mOptionsMenu.findItem(R.id.favoriteIcon).setVisible(true);
                    }
                    checkFavorite();
                    scheduleFrag = new RailScheduleFragment();
                    loadFragment(R.id.schedulefrag, scheduleFrag, false);
                }

                return true;
            }
        });





    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle connectionHint) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.mOptionsMenu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rail, menu);

        if(start.isEmpty() || end.isEmpty()){
            mOptionsMenu.findItem(R.id.favoriteIcon).setVisible(false);
        }
        else
            checkFavorite();

        return true;
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
            case R.id.favoriteIcon:
                if(favorite){
                    removeLineFromFavorites();
                }
                else{
                    addLineToFavorites();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void getRails(){
        String start = this.getResources().getString(R.string.from_rail_text);
        String end = this.getResources().getString(R.string.to_rail_text);
        ArrayList<String> stationNames = new ArrayList<>(Arrays.asList(this.getResources().getStringArray(R.array.station_names)));

        groupList = new ArrayList<String>();

        groupList.add(start);
        groupList.add(end);

        railList = new LinkedHashMap<String,ArrayList<String>>();

        railList.put(start, stationNames);
        railList.put(end,stationNames);
    }

    public void loadFragment(int paneId,Fragment fragment,boolean placeOnBackStack){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle args = new Bundle();
        args.putString("start",start);
        args.putString("end",end);
        args.putString("results", "5");

        fragment.setArguments(args);

        fragmentTransaction.replace(paneId, fragment);

        if(placeOnBackStack){
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void addLineToFavorites() {
        FavoritesManager.addRailLineToFavorites(start, end);
        mOptionsMenu.findItem(R.id.favoriteIcon).setIcon(R.drawable.star_icon);
        favorite = true;
        Toast.makeText(RailActivity.this, "Added to Favorites", Toast.LENGTH_SHORT).show();
    }

    private void removeLineFromFavorites() {
        FavoritesManager.removeRailLineFromFavorites(start, end);
        mOptionsMenu.findItem(R.id.favoriteIcon).setIcon(android.R.drawable.star_big_off);
        favorite = false;
        Toast.makeText(RailActivity.this, "Removed from Favorites", Toast.LENGTH_SHORT).show();
    }

    private boolean checkFavorite(){
        if(FavoritesManager.checkForFavoriteRailLine(start,end)){
            mOptionsMenu.findItem(R.id.favoriteIcon).setIcon(R.drawable.star_icon);
            favorite=true;
            return true;
        }
        else{
            mOptionsMenu.findItem(R.id.favoriteIcon).setIcon(android.R.drawable.star_big_off);
            favorite=false;
            return false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        FavoritesManager favoritesManager = FavoritesManager.getInstance();
        favoritesManager.storeSharedPreferences();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
