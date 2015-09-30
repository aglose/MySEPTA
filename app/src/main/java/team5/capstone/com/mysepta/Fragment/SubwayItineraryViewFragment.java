package team5.capstone.com.mysepta.Fragment;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import team5.capstone.com.mysepta.Adapters.SubwayItineraryViewAdapter;
import team5.capstone.com.mysepta.Adapters.SubwayScheduleViewAdapter;
import team5.capstone.com.mysepta.Models.SubwayLocationData;
import team5.capstone.com.mysepta.R;

/**
 * Created by Andrew on 9/20/2015.
 */
public class SubwayItineraryViewFragment extends Fragment {
    /*DEBUG TAG*/
    private static final String TAG = "SubwayItineraryViewFragment";

    /*Subway Button click listener*/
    private SubwayChangeFragmentListener mSubwayFragmentListener;
    public interface SubwayChangeFragmentListener {
        void onItinerarySelection(String line);
    }

    /*Keep track of the rootView to pass to the adapters*/
    private View rootView;

    /*Recycler View*/
    private RecyclerView recyclerSubwayView;
    /*The initial adapter for the Subway tab*/
    private SubwayItineraryViewAdapter subwayItineraryViewAdapter;
    private SubwayScheduleViewAdapter subwayScheduleViewAdapter;
    /*This is the Material Adapter that is wrapping our SubwayItineraryViewAdapter*/
    private RecyclerView.Adapter materialWrapperAdapter;

    /*Location HashMap*/
    private SubwayLocationData subLocData;

    /*Statically create a new instance of this Fragment*/
    public static SubwayItineraryViewFragment newInstance() {
        return new SubwayItineraryViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subway_itinerary_view, container, false);
    }


    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
        subLocData = new SubwayLocationData(rootView.getContext());

        recyclerSubwayView = (RecyclerView) rootView.findViewById(R.id.subwayItineraryView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity()); //Notice LinearLayoutManager
        recyclerSubwayView.setLayoutManager(layoutManager);
        recyclerSubwayView.setHasFixedSize(false);

        subwayItineraryViewAdapter = new SubwayItineraryViewAdapter(getActivity(), mSubwayFragmentListener);
        materialWrapperAdapter = new RecyclerViewMaterialAdapter(subwayItineraryViewAdapter);

        recyclerSubwayView.setAdapter(materialWrapperAdapter);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), recyclerSubwayView, null);
    }

    /*Initial and default Subway Tab View*/
    public void changeAdapterToItineraryView(){
        if(subwayScheduleViewAdapter != null){
            recyclerSubwayView = (RecyclerView) rootView.findViewById(R.id.subwayItineraryView);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity()); //Notice LinearLayoutManager
            recyclerSubwayView.setLayoutManager(layoutManager);
            recyclerSubwayView.setHasFixedSize(false);

            materialWrapperAdapter = new RecyclerViewMaterialAdapter(subwayItineraryViewAdapter);
            recyclerSubwayView.setAdapter(materialWrapperAdapter);
        }
    }

    /*After user chooses Subway Line the locations will be displayed via SubwayScheduleViewAdapter*/
    public void changeAdapterToScheduleView(String line){
        subwayScheduleViewAdapter = new SubwayScheduleViewAdapter(line, getActivity(), subLocData);
        materialWrapperAdapter = new RecyclerViewMaterialAdapter(subwayScheduleViewAdapter);
        recyclerSubwayView.swapAdapter(materialWrapperAdapter, true);
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        try {
            mSubwayFragmentListener = (SubwayChangeFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement SubwayChangeFragmentListener");
        }
    }
}
