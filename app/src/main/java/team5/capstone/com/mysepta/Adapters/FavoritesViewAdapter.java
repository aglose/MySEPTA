package team5.capstone.com.mysepta.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import team5.capstone.com.mysepta.Managers.FavoritesManager;
import team5.capstone.com.mysepta.Models.FavoriteRailModel;
import team5.capstone.com.mysepta.Models.SubwayScheduleItemModel;
import team5.capstone.com.mysepta.R;
import team5.capstone.com.mysepta.RailActivity;
import team5.capstone.com.mysepta.SubwayActivity;

/**
 * Created by Andrew on 10/28/2015.
 */
public class FavoritesViewAdapter extends RecyclerView.Adapter {
    private static final String TAG = "HomeViewAdapter";
    FavoritesManager favoritesManager;

    public static final int HEADER_AMOUNT = 2;
    static final int TYPE_HEADER = 99;
    static final int TYPE_SUBWAY = 100;
    static final int TYPE_RAIL = 101;
    int headerCount = 1;
    Context context;

    public FavoritesViewAdapter() {
        this.favoritesManager = FavoritesManager.getInstance();
    }

    @Override
    public int getItemCount() {
        return favoritesManager.getFavoriteList().size()+HEADER_AMOUNT;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0 || position == favoritesManager.getSubwayList().size() + 1)
            return TYPE_HEADER;
        else if(position < favoritesManager.getSubwayList().size() + 1)
            return TYPE_SUBWAY;
        else
            return TYPE_RAIL;


        /*
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
        */
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.favoritesManager = FavoritesManager.getInstance();
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
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.home_rail_item, parent, false);
            return new HomeViewRailItemHolder(view){};
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_HEADER){
            ((HomeViewHeaderHolder)holder).headerCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.headerBlue));
            if(position == 0){
                ((HomeViewHeaderHolder)holder).headerText.setText("Subway");
            }else if(position == favoritesManager.getSubwayList().size() + 1){
                ((HomeViewHeaderHolder)holder).headerText.setText("Regional Rail");
            }
        }else if(getItemViewType(position) == TYPE_SUBWAY){
            int listPosition = position - 1;
            Log.d(TAG, "List position: "+listPosition);
            Log.d(TAG, "content type:  "+favoritesManager.getSubwayList().get(listPosition));
            final SubwayScheduleItemModel schedule = (SubwayScheduleItemModel) favoritesManager.getSubwayList().get(listPosition);
            ((HomeViewSubwayItemHolder)holder).listItem.setText(schedule.getLocation());
            ((HomeViewSubwayItemHolder)holder).cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent startSubwayActivity = new Intent(context, SubwayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(context.getString(R.string.STOP_ID_KEY), 0);
                    bundle.putString(context.getString(R.string.DIRECTION_KEY), schedule.getDirection());
                    bundle.putString(context.getString(R.string.LINE_KEY), schedule.getLine());
                    bundle.putString(context.getString(R.string.LOCATION_KEY), schedule.getLocation());
                    startSubwayActivity.putExtras(bundle);
                    context.startActivity(startSubwayActivity);
                }
            });
        }else if(getItemViewType(position) == TYPE_RAIL){
            int listPosition = position - HEADER_AMOUNT - favoritesManager.getSubwayList().size();
            Log.d(TAG, "List position: "+listPosition);
            final FavoriteRailModel schedule = (FavoriteRailModel) favoritesManager.getRailList().get(listPosition);
            ((HomeViewRailItemHolder)holder).startingStation.setText(schedule.getStartingStation());
            ((HomeViewRailItemHolder)holder).endingStation.setText(schedule.getEndingStation());
            ((HomeViewRailItemHolder)holder).cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, RailActivity.class);
                    intent.putExtra(context.getString(R.string.name_tag),schedule.getStartingStation());
                    intent.putExtra(context.getString(R.string.loc_tag),schedule.getEndingStation());
                    context.startActivity(intent);
                }
            });
        }

    }

    public class HomeViewHeaderHolder extends RecyclerView.ViewHolder{
        TextView headerText;
        CardView headerCard;
        public HomeViewHeaderHolder(View itemView) {
            super(itemView);
            headerText = (TextView) itemView.findViewById(R.id.home_header_title);
            headerCard = (CardView) itemView.findViewById(R.id.favoriteFragmentHeaderCard);
        }
    }

    public class HomeViewSubwayItemHolder extends RecyclerView.ViewHolder{
        TextView listItem;
        CardView cardView;
        public HomeViewSubwayItemHolder(View itemView) {
            super(itemView);
            listItem = (TextView) itemView.findViewById(R.id.nextToArrive);
            cardView = (CardView) itemView.findViewById(R.id.home_subway_item);
        }
    }

    public class HomeViewRailItemHolder extends RecyclerView.ViewHolder{
        TextView startingStation;
        TextView endingStation;
        CardView cardView;

        public HomeViewRailItemHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.home_rail_item);
            startingStation = (TextView) itemView.findViewById(R.id.departureStation);
            endingStation = (TextView) itemView.findViewById(R.id.arrivalStation);
        }
    }
}