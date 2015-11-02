package team5.capstone.com.mysepta;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

import io.fabric.sdk.android.Fabric;
import team5.capstone.com.mysepta.DatabaseHelpers.SubwayScheduleCreatorDbHelper;
import team5.capstone.com.mysepta.Fragment.FavoritesFragment;
import team5.capstone.com.mysepta.Fragment.RailItineraryViewFragment;
import team5.capstone.com.mysepta.Fragment.RecyclerViewFragment;
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

    /*Third party library for the Material looking view pager*/
    private MaterialViewPager mViewPager;
    private Toolbar toolbar;

    /*The FavoritesManager SINGLETON*/
    private FavoritesManager favoritesManager;

    /*We want this Fragment stored when it is statically created to alter it later*/
    private SubwayItineraryViewFragment subwayViewFragment;
    private RailItineraryViewFragment railViewFragment;
    private FavoritesFragment favoritesFragment;

    /*Drawer layout*/
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] drawerListViewItems;
    private ListView drawerListView;

    /*This is the Adapter that controls the Fragment views in the tabs*/
    private FragmentPagerAdapter fragmentPagerAdapter;

    /*Subway Title that is changed according to the user itinerary choice*/
    private String subwayTabTitle = "Subway";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fabric.with(this, new Crashlytics());

        copyDatabase();

        /*Set to no title*/
        setTitle("");

        /*Drawer initialization*/
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        mDrawer.setDrawerListener(mDrawerToggle);

        // get list items from strings.xml
        drawerListViewItems = getResources().getStringArray(R.array.drawer_items);

        // get ListView defined in activity_main.xml
        drawerListView = (ListView) findViewById(R.id.right_drawer);

        // Set the adapter for the list view
        drawerListView.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_listview_item, drawerListViewItems));

        /*Set up the Material toolbar and pager*/
        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setDisplayShowHomeEnabled(false);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(false);
            }
        }

        /*Create the Tab Fragments*/
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Log.d(TAG, String.valueOf(position));
                switch (position % 4) {
                    case HOME_TAB:
                        return favoritesFragment = FavoritesFragment.newInstance();
                    case RAIL_TAB:
                        return railViewFragment = RailItineraryViewFragment.newInstance();
                    case BUS_TAB:
                        return RecyclerViewFragment.newInstance();
                    case SUBWAY_TAB:
                        return subwayViewFragment = SubwayItineraryViewFragment.newInstance();
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

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case HOME_TAB:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.green,
                                "http://www.towerstream.com/wp-content/uploads/2014/03/Philadelphia.jpg");
                    case RAIL_TAB:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                "http://www.uwishunu.com/wp-content/uploads/2012/08/boathouse-row-philadelphia-sunset1-680uw.jpg");
                    case BUS_TAB:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                "http://cdn.gobankingrates.com/wp-content/uploads/banks-in-philadelphia.jpg");
                    case SUBWAY_TAB:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
                                "https://www.issueone.org/wp-content/uploads/2015/06/Philly-Skyline.jpg");
                }
                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        View logo = findViewById(R.id.logo_background);
        if (logo != null) {
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    subwayTabTitle = "Subway";
                    subwayViewFragment.changeAdapterToItineraryView();
                    fragmentPagerAdapter.notifyDataSetChanged();
                    mViewPager.notifyHeaderChanged();
                }
            });
        }

        favoritesManager = FavoritesManager.getInstance();
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

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, String.valueOf(item.getItemId()));
        if (item != null && item.getItemId() == R.id.drawer_open_close_item) {
            if (mDrawer.isDrawerOpen(Gravity.RIGHT)) {
                mDrawer.closeDrawer(Gravity.RIGHT);
            } else {
                mDrawer.openDrawer(Gravity.RIGHT);
            }
        }
        return  super.onOptionsItemSelected(item);
    }

    //TODO get this onResume, onStop, onPause, onCupid, onDonner, onBlitzen shizznit working
    @Override
    public void onResume(){
        super.onResume();
        if(favoritesFragment != null)
            favoritesFragment.refreshFavorites();
        Log.d(TAG, "ON RESUME CALLED");
    }

    @Override
     public void onStop(){
        super.onStop();
        Log.d(TAG, "ON STOP CALLED");

    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "ON PAUSE CALLED");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*Implementation for BSL and MFL Button clicks in Subway Tab */
    @Override
    public void onItinerarySelection(String line) {
        subwayTabTitle = line;
        subwayViewFragment.changeAdapterToScheduleView(line);
        fragmentPagerAdapter.notifyDataSetChanged();
        mViewPager.notifyHeaderChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (0) : {
                if (resultCode == Activity.RESULT_OK) {
                    //favoritesFragment.refreshFavorites();
                }
                break;
            }
        }
    }
}


