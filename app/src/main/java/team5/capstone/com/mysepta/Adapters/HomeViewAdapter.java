package team5.capstone.com.mysepta.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import team5.capstone.com.mysepta.Fragment.FavoritesFragment;
import team5.capstone.com.mysepta.Managers.FavoritesManager;
import team5.capstone.com.mysepta.Models.RailLocationData;
import team5.capstone.com.mysepta.Models.SubwayScheduleItemModel;
import team5.capstone.com.mysepta.R;

/**
 * Created by Andrew on 10/28/2015.
 */
public class HomeViewAdapter extends RecyclerView.Adapter {
    private static final String TAG = "HomeViewAdapter";
    FavoritesManager favoritesManager;

    static final int HEADER_AMOUNT = 2;
    static final int TYPE_HEADER = 99;
    static final int TYPE_SUBWAY = 100;
    static final int TYPE_RAIL = 101;
    int headerCount = 1;
    Context context;

    public HomeViewAdapter(FavoritesManager favoritesManager) {
        this.favoritesManager = favoritesManager;
    }

    @Override
    public int getItemCount() {
        return favoritesManager.getFavoriteList().size()+HEADER_AMOUNT;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            case 1:
            case 2:
            case 3:
                return TYPE_SUBWAY;
            case 4:
                return TYPE_HEADER;
            case 5:
            case 6:
            case 7:
                return TYPE_RAIL;
            default:
                return -1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        Log.d(TAG, "viewType: " + viewType + "\nHeadercount: " + headerCount);
        context = parent.getContext();
        if(viewType == TYPE_HEADER){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.favorites_list_item_header, parent, false);
            return new HomeViewHeaderHolder(view){};
        }else if(viewType == TYPE_SUBWAY){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.favorites_list_item_subway, parent, false);
            return new HomeViewSubwayItemHolder(view){};
        }else if(viewType == TYPE_RAIL){
            Log.d(TAG, "railTYPEEEEEEEEEEEEEEEEEE");
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.favorites_list_item_rail, parent, false);
            return new HomeViewRailItemHolder(view){};
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "bindViewHolder position: "+position);

        if(getItemViewType(position) == TYPE_HEADER){
            if(position == 0){
                ((HomeViewHeaderHolder)holder).headerText.setText("Subway");
            }else if(position == 4){
                headerCount++;
                ((HomeViewHeaderHolder)holder).headerText.setText("Regional Rail");
            }
        }else if(getItemViewType(position) == TYPE_SUBWAY){
            int listPosition = position - headerCount;
            Log.d(TAG, "List position: "+listPosition);
            Log.d(TAG, "content type:  "+favoritesManager.getSubwayList().get(listPosition));
            SubwayScheduleItemModel schedule = (SubwayScheduleItemModel) favoritesManager.getSubwayList().get(listPosition);
            ((HomeViewSubwayItemHolder)holder).listItem.setText(schedule.getFormattedTimeStr());
        }else if(getItemViewType(position) == TYPE_RAIL){
            /*KEVIN THIS IS WHERE YOU WILL IMPLEMENT YOUR CODE */
            int listPosition = position - headerCount - 3; //3 represents the number of list items from the subway list
            Log.d(TAG, "List position: "+listPosition);
            RailLocationData schedule = (RailLocationData) favoritesManager.getRailList().get(listPosition);
            ((HomeViewRailItemHolder)holder).testItem.setText(schedule.getTime());
        }

    }

    public class HomeViewHeaderHolder extends RecyclerView.ViewHolder{
        TextView headerText;
        public HomeViewHeaderHolder(View itemView) {
            super(itemView);
            headerText = (TextView) itemView.findViewById(R.id.header_title);
        }
    }

    public class HomeViewSubwayItemHolder extends RecyclerView.ViewHolder{
        TextView listItem;
        public HomeViewSubwayItemHolder(View itemView) {
            super(itemView);
            listItem = (TextView) itemView.findViewById(R.id.nextToArrive);
        }
    }

    public class HomeViewRailItemHolder extends RecyclerView.ViewHolder{
        TextView testItem;
        public HomeViewRailItemHolder(View itemView) {
            super(itemView);
            testItem = (TextView) itemView.findViewById(R.id.testRail);
        }
    }
}