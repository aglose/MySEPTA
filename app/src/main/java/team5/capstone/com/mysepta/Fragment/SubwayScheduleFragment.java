package team5.capstone.com.mysepta.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.List;

import team5.capstone.com.mysepta.Adapters.SubwayItineraryViewAdapter;
import team5.capstone.com.mysepta.Adapters.SubwayScheduleViewAdapter;
import team5.capstone.com.mysepta.R;

/**
 * Created by Andrew on 9/20/2015.
 */
public class SubwayScheduleFragment extends Fragment {

    private RecyclerView mSubwayView;
    private RecyclerView.Adapter mAdapter;
    private static final int ITEM_COUNT = 100;

    private List<Object> mContentItems = new ArrayList<>();

    public static SubwayScheduleFragment newInstance(String line) {
        return new SubwayScheduleFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subway_schedule_view, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSubwayView = (RecyclerView) view.findViewById(R.id.subwayScheduleView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mSubwayView.setLayoutManager(layoutManager);
        mSubwayView.setHasFixedSize(true);

        mAdapter = new RecyclerViewMaterialAdapter(new SubwayScheduleViewAdapter(null, view.getContext()));
        mSubwayView.setAdapter(mAdapter);

        {
            for (int i = 0; i < ITEM_COUNT; ++i)
                mContentItems.add(new Object());
            mAdapter.notifyDataSetChanged();
        }


        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mSubwayView, null);
    }
}

