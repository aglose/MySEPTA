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

import team5.capstone.com.mysepta.Adapters.TestRecyclerViewAdapter;
import team5.capstone.com.mysepta.R;

/**
 * Temporary recycler view fragment.
 * Created by florentchampigny on 24/04/15.
 */
public class RecyclerViewFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private static final int ITEM_COUNT = 100;

    private List<Object> mContentItems = new ArrayList<>();

    /**
     * Create new instance of fragment
     * @return new fragment
     */
    public static RecyclerViewFragment newInstance() {
        return new RecyclerViewFragment();
    }

    /**
     * Create view
     * @param inflater layout inflater
     * @param container parent view group
     * @param savedInstanceState saved state on close
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    /**
     * Initialize view
     * @param view current view
     * @param savedInstanceState saved state on close
     */
    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new RecyclerViewMaterialAdapter(new TestRecyclerViewAdapter(mContentItems, view.getContext()));
        mRecyclerView.setAdapter(mAdapter);

        {
            for (int i = 0; i < ITEM_COUNT; ++i)
                mContentItems.add(new Object());
            mAdapter.notifyDataSetChanged();
        }


        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }
}