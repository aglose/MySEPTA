package team5.capstone.com.mysepta.Fragment;

import android.content.Context;
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

import team5.capstone.com.mysepta.Adapters.DrawerAdapter;
import team5.capstone.com.mysepta.R;

/**
 *
 */
public class DrawerFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    //private static final int ITEM_COUNT = 100;

    private String mNavTitles[] = {"Alerts","Settings","Help & Feedback"};
    private int mIcons[] = {R.drawable.ic_alerts,R.drawable.ic_settings,R.drawable.ic_help};

    private String name = "Username";
    private String email = "username@gmail.com";

    private int profile = R.drawable.profile;
    private Context context;


    /**
     * Create new instance of fragment
     * @return new fragment
     */
    public static DrawerFragment newInstance() {
        return new DrawerFragment();
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

        mAdapter = new RecyclerViewMaterialAdapter(new DrawerAdapter(mNavTitles,mIcons,name, email, profile, context));
        mRecyclerView.setAdapter(mAdapter);


            mAdapter.notifyDataSetChanged();



        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }
}