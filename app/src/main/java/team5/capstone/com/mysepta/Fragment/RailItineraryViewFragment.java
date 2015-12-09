package team5.capstone.com.mysepta.Fragment;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.List;

import team5.capstone.com.mysepta.Adapters.RailItineraryViewAdapter;
import team5.capstone.com.mysepta.R;

/**
 * Fragment for rail view in home screen.
 * Created by kevin on 9/28/15.
 */
public class RailItineraryViewFragment extends Fragment {
    private static final String TAG = "RailItineraryViewFragment";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private double latitude;
    private double longitude;

    public static RailItineraryViewFragment newInstance(double latitude, double longitude){
        RailItineraryViewFragment f = new RailItineraryViewFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putDouble("lat", latitude);
        args.putDouble("long", longitude);
        f.setArguments(args);

        return f;
    }

    public static RailItineraryViewFragment newInstance(){
        RailItineraryViewFragment f = new RailItineraryViewFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        f.setArguments(args);

        return f;
    }

    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        latitude = getArguments() != null ? getArguments().getDouble("lat") : 1;
        longitude = getArguments() != null ? getArguments().getDouble("long") : 1;
    }

    /**
     * Create fragment view
     * @param inflater layout inflater
     * @param container parent container
     * @param savedInstanceState saved state on close
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rail_itinerary_view, container, false);
    }

    /**
     * initialize view.
     * @param view current fragment view
     * @param savedInstanceState saved state on close
     */
    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.railItineraryView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new RecyclerViewMaterialAdapter(new RailItineraryViewAdapter(latitude, longitude, this.getContext()));
        mRecyclerView.setAdapter(mAdapter);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }
}
