package team5.capstone.com.mysepta;

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
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import java.util.Set;

import io.fabric.sdk.android.Fabric;
import team5.capstone.com.mysepta.Fragment.RailItineraryViewFragment;
import team5.capstone.com.mysepta.Fragment.RecyclerViewFragment;
import team5.capstone.com.mysepta.Fragment.SubwayItineraryViewFragment;

public class MainActivity extends AppCompatActivity implements SubwayItineraryViewFragment.SubwayChangeFragmentListener {
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

    /*We want this Fragment stored when it is statically created to alter it later*/
    private SubwayItineraryViewFragment subwayViewFragment;
    private RailItineraryViewFragment railViewFragment;

    /*Drawer layout*/
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;

    /*This is the Adapter that controls the Fragment views in the tabs*/
    private FragmentPagerAdapter fragmentPagerAdapter;

    /*Subway Title that is changed according to the user itinerary choice*/
    private String subwayTabTitle = "Subway";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fabric.with(this, new Crashlytics());

        /*Set to no title*/
        setTitle("");

        /*Drawer initialization*/
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        mDrawer.setDrawerListener(mDrawerToggle);

        /*Set up the Material toolbar and pager*/
        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        toolbar = mViewPager.getToolbar();
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

        /*Create the Tab Fragments*/
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Log.d(TAG, String.valueOf(position));
                switch (position % 4) {
                    case HOME_TAB:
                        return RecyclerViewFragment.newInstance();
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
                                "http://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2014/06/wallpaper_51.jpg");
                    case BUS_TAB:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                "http://www.droid-life.com/wp-content/uploads/2014/10/lollipop-wallpapers10.jpg");
                    case SUBWAY_TAB:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
                                "http://www.tothemobile.com/wp-content/uploads/2014/07/original.jpg");
                }
                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        View logo = findViewById(R.id.logo_background);
        if (logo != null)
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    subwayTabTitle = "Subway";
                    subwayViewFragment.changeAdapterToItineraryView();
                    fragmentPagerAdapter.notifyDataSetChanged();
                    mViewPager.notifyHeaderChanged();
                    Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                }
            });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item != null && item.getItemId() == android.R.id.home) {
            if (mDrawer.isDrawerOpen(Gravity.RIGHT)) {
                mDrawer.closeDrawer(Gravity.RIGHT);
            } else {
                mDrawer.openDrawer(Gravity.RIGHT);
            }
        }
        return  super.onOptionsItemSelected(item);
    }

    /*Implementation for BSL and MFL Button clicks in Subway Tab*/
    @Override
    public void onItinerarySelection(String line) {
        subwayTabTitle = line;
        subwayViewFragment.changeAdapterToScheduleView(line);
        fragmentPagerAdapter.notifyDataSetChanged();
        mViewPager.notifyHeaderChanged();
    }
}


