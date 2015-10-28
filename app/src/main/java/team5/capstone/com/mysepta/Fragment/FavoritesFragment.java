package team5.capstone.com.mysepta.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;

import team5.capstone.com.mysepta.Adapters.HomeViewAdapter;
import team5.capstone.com.mysepta.Managers.FavoritesManager;
import team5.capstone.com.mysepta.Models.RailLocationData;
import team5.capstone.com.mysepta.Models.SubwayScheduleItemModel;
import team5.capstone.com.mysepta.R;

/**
 * Created by Andrew on 10/24/2015.
 */
public class FavoritesFragment extends Fragment {
    /*DEBUG TAG*/
    private static final String TAG = "FavoritesFragment";

    /*Subway Button click listener*/
    private FavoritesFragmentListener mFavoritesFragmentListener;

    public interface FavoritesFragmentListener {
        void removeItems();
    }

    private RecyclerView recyclerHomeView;
    private RecyclerView.Adapter mAdapter;
    private static final int ITEM_COUNT = 5;

    private FavoritesManager favoritesManager;

    /*Keep track of the rootView to pass to the adapters*/
    private View rootView;

    /*Statically create a new instance of this Fragment*/
    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites_view, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;

        recyclerHomeView = (RecyclerView) view.findViewById(R.id.homeRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerHomeView.setLayoutManager(layoutManager);

        favoritesManager = new FavoritesManager();

        HomeViewAdapter homeViewAdapter = new HomeViewAdapter(favoritesManager);


        mAdapter = new RecyclerViewMaterialAdapter(homeViewAdapter);
        recyclerHomeView.setAdapter(mAdapter);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), recyclerHomeView, null);
    }




}