package team5.capstone.com.mysepta.Activities;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Environment;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;
import team5.capstone.com.mysepta.Adapters.DrawerAdapter;
import team5.capstone.com.mysepta.Fragment.BusLinesFragment;
import team5.capstone.com.mysepta.Fragment.RecyclerViewFragment;
import team5.capstone.com.mysepta.Helpers.SubwayScheduleCreatorDbHelper;
import team5.capstone.com.mysepta.Fragment.FavoritesFragment;
import team5.capstone.com.mysepta.Fragment.RailItineraryViewFragment;
import team5.capstone.com.mysepta.Fragment.SubwayItineraryViewFragment;
import team5.capstone.com.mysepta.Managers.FavoritesManager;
import team5.capstone.com.mysepta.R;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, DrawerAdapter.GoogleLoginInterface{
    /*When you are debugging use this TAG as the first String (i.e. Log.d(TAG, String.valueOf(position));*/
    private static final String TAG = "MainActivity";
    private static final int RC_SIGN_IN = 9001;

    /*Tab Id's*/
    private static final int HOME_TAB = 0;
    private static final int RAIL_TAB = 1;
    private static final int BUS_TAB = 2;
    private static final int SUBWAY_TAB = 3;


    /*Third party library for the Material looking view pager*/
    public MaterialViewPager mViewPager;
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
    private RecyclerView drawerListView;
    private DrawerAdapter mDrawerAdapter;
    private static ArrayList<String> navDrawerTitles = new ArrayList<>();

    /*This is the Adapter that controls the Fragment views in the tabs*/
    public FragmentStatePagerAdapter fragmentPagerAdapter;

    /*Subway Title that is changed according to the user itinerary choice*/
    private String subwayTabTitle = "Subway";

    public static boolean PREVENT_CLOSE = false;

    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions gso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        final Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        copyDatabase();

        /*Set to no title*/
        setTitle("");

        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

        toolbar = mViewPager.getToolbar();
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);       // Creating a layout Manager
        drawerListView = (RecyclerView) findViewById(R.id.left_drawer); // Assigning the RecyclerView Object to the xml View
        drawerListView.setHasFixedSize(false);                            // Letting the system know that the list objects are of fixed size

        String title[] = getResources().getStringArray(R.array.drawer_items);
        navDrawerTitles.add(title[0]);
        navDrawerTitles.add(title[1]);
        navDrawerTitles.add(title[2]);
        navDrawerTitles.add(title[3]);
        mDrawerAdapter = new DrawerAdapter(navDrawerTitles, this, this);

        drawerListView.setAdapter(mDrawerAdapter);                              // Setting the adapter to RecyclerView
        drawerListView.setLayoutManager(mLayoutManager);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        buildGoogleApiClient();

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
                        if(lastKnownLocation != null)
                            return RailItineraryViewFragment.newInstance(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                        else
                            return RailItineraryViewFragment.newInstance();
                    case BUS_TAB:
                        return BusLinesFragment.newInstance();
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
                        return getResources().getString(R.string.tab_home_title);
                    case RAIL_TAB:
                        return getResources().getString(R.string.tab_rail_title);
                    case BUS_TAB:
                        return getResources().getString(R.string.tab_bus_title);
                    case SUBWAY_TAB:
                        return getResources().getString(R.string.tab_subway_title);
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
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
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
        subwayTabTitle = getResources().getString(R.string.tab_subway_title);

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

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Plus.API)
                .build();
    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle connectionHint) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            TextView name = (TextView) findViewById(R.id.name);
            TextView email = (TextView) findViewById(R.id.email);
            ImageView profileImage = (ImageView) findViewById(R.id.circleView);

            name.setVisibility(View.VISIBLE);
            email.setVisibility(View.VISIBLE);

            name.setText(acct.getDisplayName());
            email.setText(acct.getEmail());
            Person currentPerson = Plus.PeopleApi
                    .getCurrentPerson(mGoogleApiClient);
            Log.d(TAG, String.valueOf(currentPerson.getImage().getUrl()));
            Picasso.with(this).load(currentPerson.getImage().getUrl()).into(profileImage);
            navDrawerTitles.add("Logout");
            mDrawerAdapter.setList(navDrawerTitles, false);
            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
//            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
//            mStatusTextView.setText(R.string.signed_out);
//
//            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
//            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }

    @Override
    public void login() {
        signIn();
    }

    @Override
    public void logout() {
        Log.d(TAG, "called");
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        navDrawerTitles.remove(4);
                        mDrawerAdapter.setList(navDrawerTitles, true);
                    }
                });

        TextView name = (TextView) findViewById(R.id.name);
        TextView email = (TextView) findViewById(R.id.email);
        ImageView profileImage = (ImageView) findViewById(R.id.circleView);
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);

        signInButton.setVisibility(View.VISIBLE);
        name.setVisibility(View.INVISIBLE);
        email.setVisibility(View.INVISIBLE);
        Picasso.with(this).load(R.drawable.empty_header).into(profileImage);


    }
}


