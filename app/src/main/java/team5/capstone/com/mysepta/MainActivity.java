package team5.capstone.com.mysepta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Environment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;
import team5.capstone.com.mysepta.Adapters.DrawerAdapter;
import team5.capstone.com.mysepta.Fragment.RecyclerViewFragment;
import team5.capstone.com.mysepta.Helpers.SubwayScheduleCreatorDbHelper;
import team5.capstone.com.mysepta.Fragment.FavoritesFragment;
import team5.capstone.com.mysepta.Fragment.RailItineraryViewFragment;
import team5.capstone.com.mysepta.Fragment.DrawerFragment;
import team5.capstone.com.mysepta.Fragment.SubwayItineraryViewFragment;
import team5.capstone.com.mysepta.Managers.FavoritesManager;

public class MainActivity extends AppCompatActivity implements SubwayItineraryViewFragment.SubwayChangeFragmentListener{
    /*When you are debugging use this TAG as the first String (i.e. Log.d(TAG, String.valueOf(position));*/
    private static final String TAG = "MainActivity";

    /*Tab Id's*/
    private static final int HOME_TAB = 0;
    private static final int RAIL_TAB = 1;
    private static final int BUS_TAB = 2;
    private static final int SUBWAY_TAB = 3;

    String TITLES[] = {"Alerts","Settings","Help & Feedback"};
    int ICONS[] = {R.drawable.ic_alerts,R.drawable.ic_settings,R.drawable.ic_help};
    String NAME = "Username";
    String EMAIL = "username@gmail.com";
    int PROFILE = R.drawable.profile;

    /*Third party library for the Material looking view pager*/
    public MaterialViewPager mViewPager;
    private Toolbar toolbar;

    /*The FavoritesManager SINGLETON*/
    private FavoritesManager favoritesManager;

    /*Drawer layout*/
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private RecyclerView drawerListView;

    /*This is the Adapter that controls the Fragment views in the tabs*/
    public FragmentStatePagerAdapter fragmentPagerAdapter;

    /*Subway Title that is changed according to the user itinerary choice*/
    private String subwayTabTitle = "Subway";

    public static boolean PREVENT_CLOSE = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        final Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        Fabric.with(this, new Crashlytics());

        copyDatabase();

        /*Set to no title*/
        setTitle("");

        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

        toolbar = mViewPager.getToolbar();
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);       // Creating a layout Manager
        drawerListView = (RecyclerView) findViewById(R.id.left_drawer); // Assigning the RecyclerView Object to the xml View
        drawerListView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size

        DrawerAdapter mAdapter = new DrawerAdapter(TITLES,ICONS,NAME,EMAIL,PROFILE,this);

        drawerListView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView
        drawerListView.setLayoutManager(mLayoutManager);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }

        //The listener to press the menu which triggers the drawer to open
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        mDrawer.setDrawerListener(mDrawerToggle);

        /*Create the Tab Fragments*/
        fragmentPagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Log.d(TAG, String.valueOf(position));
                switch (position % 4) {
                    case HOME_TAB:
                        return FavoritesFragment.newInstance();
                    case RAIL_TAB:
                        return RailItineraryViewFragment.newInstance(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                    case BUS_TAB:
                        return RecyclerViewFragment.newInstance();
                    case SUBWAY_TAB:
                        return SubwayItineraryViewFragment.newInstance();
                    default:
                        return RecyclerViewFragment.newInstance();
                }
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 4) {
                    case HOME_TAB:
                        return "Home";
                    case RAIL_TAB:
                        return "Rail";
                    case BUS_TAB:
                        return "Bus";
                    case SUBWAY_TAB:
                        return subwayTabTitle;
                }
                return "";
            }
        };

        mViewPager.getViewPager().setAdapter(fragmentPagerAdapter);

        final Context c = this;
        //TODO: We need to change pictures because of copyright issues
        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case HOME_TAB:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.headerBlue,
                                ContextCompat.getDrawable(c, R.drawable.philly_header0));
                    case RAIL_TAB:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.headerBlue,
                                ContextCompat.getDrawable(c, R.drawable.philly_header1));
                    case BUS_TAB:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.headerBlue,
                                ContextCompat.getDrawable(c, R.drawable.philly_header2));
                    case SUBWAY_TAB:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.headerBlue,
                                ContextCompat.getDrawable(c, R.drawable.philly_header3));
                }
                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

//        View logo = findViewById(R.id.logo_background);
//        if (logo != null) {
//            logo.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    subwayTabTitle = "Subway";
//                    subwayViewFragment.changeAdapterToItineraryView();
//                    fragmentPagerAdapter.notifyDataSetChanged();
//                    mViewPager.notifyHeaderChanged();
//                }
//            });
//        }

        favoritesManager = FavoritesManager.getInstance();
        favoritesManager.setContext(this);
    }

    //Copy the subway database to local if necessary
    private void copyDatabase() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + SubwayScheduleCreatorDbHelper.DATABASE_NAME);
        if(!file.exists()){
            try {
                InputStream database = getAssets().open("SubwaySchedule.db");
                OutputStream out = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + SubwayScheduleCreatorDbHelper.DATABASE_NAME);

                byte[] buf = new byte[1024];
                int len;
                while ((len = database.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                database.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //TODO get this onResume, onStop, onPause, onCupid, onDonner, onBlitzen working
    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "ON RESUME CALLED");
    }

    @Override
     public void onStop(){
        super.onStop();
        favoritesManager.storeSharedPreferences();
        Log.d(TAG, "ON STOP CALLED");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "ON PAUSE CALLED");
    }

    @Override
    public void onDestroy(){

        super.onDestroy();
        Log.d(TAG, "ON DESTROY CALLED");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        subwayTabTitle = "Subway";

//        subwayViewFragment.changeAdapterToItineraryView();
        fragmentPagerAdapter.notifyDataSetChanged();
        mViewPager.notifyHeaderChanged();
        PREVENT_CLOSE = false;
    }

    @Override
    public void finish() {
        if(PREVENT_CLOSE){
           return;
        }else{
            super.finish(); //This will finish the activity and take you back
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) ||
                super.onOptionsItemSelected(item);
    }

    /*Implementation for BSL and MFL Button clicks in Subway Tab */
    @Override
    public void onItinerarySelection(String line) {
        subwayTabTitle = line;
//        subwayViewFragment.changeAdapterToScheduleView(line);
        fragmentPagerAdapter.notifyDataSetChanged();
        mViewPager.notifyHeaderChanged();
    }




}


