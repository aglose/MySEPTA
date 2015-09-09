package team5.capstone.com.mysepta;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import team5.capstone.com.mysepta.AsyncTasks.RetrieveTrainMapTask;
import team5.capstone.com.mysepta.CallbackProxies.RailLocationProxy;
import team5.capstone.com.mysepta.Models.RailModel;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = MapsActivity.class.getName();
    private final int[] trainColors = {Color.BLACK, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.CYAN, Color.RED, Color.YELLOW, Color.rgb(255, 0, 0), Color.rgb(255, 0, 255), Color.rgb(255, 255, 222), Color.rgb(255, 0, 13), Color.rgb(33, 0, 255)};
    private GoogleMap googleMap; // Might be null if Google Play services APK is not available.
    private MapFragment mapFragment;
    private ArrayList<Marker> markers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mapFragment == null) {
            // Try to obtain the map from the SupportMapFragment.
            mapFragment = ((MapFragment) getFragmentManager().findFragmentById(R.id.map));
            mapFragment.getMapAsync(this);

            FloatingActionButton button = (FloatingActionButton) findViewById(R.id.refreshButton);
            button.setSize(FloatingActionButton.SIZE_NORMAL);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MapsActivity.this, "Refresh", Toast.LENGTH_SHORT).show();
                    getAllTrainLocations();
                }
            });
        }
    }

    private void getAllTrainLocations() {
        Callback callback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                ArrayList<RailModel> trainLocationList = (ArrayList<RailModel>) o;
                addTrainLocationsToMap(trainLocationList);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Debug", "fail");
            }
        };

        RailLocationProxy railViews = new RailLocationProxy();
        railViews.getTrainView(callback);
    }

    private void addTrainLocationsToMap(ArrayList<RailModel> trainLocationList) {
        for (Marker mark : markers) {
            mark.remove();
        }
        for (RailModel loc : trainLocationList) {

            Bitmap drawable;

            if (loc.getLate() > 0) {
                drawable = BitmapFactory.decodeResource(getResources(), R.drawable.train_transportation_late);
            } else {
                drawable = BitmapFactory.decodeResource(getResources(), R.drawable.train_transportation);
            }
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(drawable, 80, 80, true);

            MarkerOptions markerOption = new MarkerOptions().position(loc.getLatLng()).title("Destination: " + loc.getDestination()).icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap));

            Marker marker = googleMap.addMarker(markerOption);
            markers.add(marker);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        markers = new ArrayList<>();
        /////----------------------------------Zooming camera to position user-----------------

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MapsActivity.this, "GPS Permission Needed", Toast.LENGTH_SHORT).show();
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 10));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(10)
                    .bearing(0)                // Sets the orientation of the camera to east
                    .tilt(0)
                    .build();                   // Creates a CameraPosition from the builder
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }

        drawTrainLines();
        googleMap.setMyLocationEnabled(true);
    }

    private void drawTrainLines() {
        InputStream inputStream = null;
        try {
            inputStream = getResources().getAssets().open("regionalrail_json.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        RetrieveTrainMapTask task = new RetrieveTrainMapTask(googleMap);
        task.execute(inputStream);
    }
}
