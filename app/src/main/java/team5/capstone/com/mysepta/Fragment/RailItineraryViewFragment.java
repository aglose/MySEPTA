package team5.capstone.com.mysepta.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.List;

import team5.capstone.com.mysepta.Adapters.RailItineraryViewAdapter;
import team5.capstone.com.mysepta.Adapters.TestRecyclerViewAdapter;
import team5.capstone.com.mysepta.MapsActivity;
import team5.capstone.com.mysepta.R;

/**
 * Created by kevin on 9/28/15.
 */
public class RailItineraryViewFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private static final int ITEM_COUNT = 100;

    private List<Object> mContentItems = new ArrayList<>();

    public static RailItineraryViewFragment newInstance() {
        return new RailItineraryViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rail_itinerary_view, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.railItineraryView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new RecyclerViewMaterialAdapter(new RailItineraryViewAdapter());
        mRecyclerView.setAdapter(mAdapter);


        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }
}
