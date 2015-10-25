package team5.capstone.com.mysepta.Fragment;

import android.app.Activity;
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
import android.widget.Button;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.List;

import team5.capstone.com.mysepta.Adapters.TestRecyclerViewAdapter;
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

    private List<Object> mContentItems = new ArrayList<>();

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

        HomeViewAdapter homeViewAdapter = new HomeViewAdapter(mContentItems, mFavoritesFragmentListener);

            for (int i = 0; i < ITEM_COUNT; i++){
                mContentItems.add(new Object());
                homeViewAdapter.notifyDataSetChanged();
            }
        Log.d(TAG, "Size of: "+mContentItems.size());

        mAdapter = new RecyclerViewMaterialAdapter(homeViewAdapter);
        recyclerHomeView.setAdapter(mAdapter);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), recyclerHomeView, null);
    }

    public void removeItems() {
        Log.d(TAG, "Size of: "+mContentItems.size());
        for (int i = 1; i < 2; i++){
            mContentItems.remove(i);
        }
        Log.d(TAG, "Size of: " + mContentItems.size());
        mAdapter.notifyItemRangeRemoved(1, ITEM_COUNT);
        recyclerHomeView.swapAdapter(mAdapter, true);
    }


    public class HomeViewAdapter extends RecyclerView.Adapter<HomeViewAdapter.HomeViewHolder> {

        List<Object> contents;

        static final int TYPE_HEADER = 0;
        static final int TYPE_CELL = 1;
        Context context;
        FavoritesFragment.FavoritesFragmentListener mFavoritesFragmentListener;

        public HomeViewAdapter(List<Object> contents, FavoritesFragment.FavoritesFragmentListener mFavoritesFragmentListener) {
            this.contents = contents;
            this.mFavoritesFragmentListener = mFavoritesFragmentListener;
        }

        @Override
        public int getItemViewType(int position) {
            switch (position) {
                case 0:
                    return TYPE_HEADER;
                default:
                    return TYPE_CELL;
            }
        }

        @Override
        public int getItemCount() {
            return contents.size();
        }

        @Override
        public HomeViewAdapter.HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;
            context = parent.getContext();
            switch (viewType) {
                case TYPE_HEADER: {
                    view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.list_item_card_big, parent, false);
                    Button button = (Button) view.findViewById(R.id.button);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            removeItems();
                        }
                    });
                    return new HomeViewAdapter.HomeViewHolder(view) {
                    };
                }
                case TYPE_CELL: {
                    view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.list_item_card_small, parent, false);
                    return new HomeViewAdapter.HomeViewHolder(view) {
                    };
                }
            }
            return null;
        }

        @Override
        public void onBindViewHolder(HomeViewHolder holder, int position) {
            switch (getItemViewType(position)) {
                case TYPE_HEADER:
                    break;
                case TYPE_CELL:
                    break;
            }
        }


        public class HomeViewHolder extends RecyclerView.ViewHolder{
            public HomeViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}