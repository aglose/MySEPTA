package team5.capstone.com.mysepta.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import team5.capstone.com.mysepta.MainActivity;
import team5.capstone.com.mysepta.Models.SubwayLocationData;
import team5.capstone.com.mysepta.R;
import team5.capstone.com.mysepta.SubwayActivity;

/**
 * Adapter for subway location view fragment.
 * Created by Andrew on 9/20/2015.
 */
public class SubwayLocationViewAdapter extends RecyclerView.Adapter {
    private static final String TAG = "SubwayLocationViewAdapter";
    private static final String STOP_ID_KEY = "StopID";
    private static final String LOCATION_KEY = "Location";
    private static final String DIRECTION_KEY = "Direction";
    private static final String LINE_KEY = "Line";

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_CELL = 1;

    private MainActivity context;
    private String[] listLocations;
    private SubwayLocationData subwayLocationData;
    private String line;

    /**
     * Constructor
     * @param line name of subway line
     * @param context activity
     * @param subwayLocationData stop data
     */
    public SubwayLocationViewAdapter(String line, MainActivity context, SubwayLocationData subwayLocationData){
        if(line.equalsIgnoreCase("MFL")){
            listLocations = context.getResources().getStringArray(R.array.market_frankford_line_sorted);
        }else if(line.equalsIgnoreCase("BSL")){
            listLocations = context.getResources().getStringArray(R.array.broad_street_line_sorted);
        }else{
            Log.d(TAG, "Messed up mere");
        }
        this.line = line;
        this.subwayLocationData = subwayLocationData;
        this.context = context;
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

    /**
     * Inflate view holder.
     * @param parent parent view group
     * @param viewType view type
     * @return subway location holder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        if(viewType == TYPE_HEADER){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.subway_list_item_header, parent, false);
            return new SubwayLocationHeaderHolder(view){};
        }else{
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.subway_list_item_card, parent, false);

            return new SubwayLocationItemHolder(view);
        }
    }

    /**
     * Initialize and bind view holder.  Starts subway activity and passes data.
     * @param holder current item holder
     * @param position current item position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(getItemViewType(position) == TYPE_HEADER){
            ((SubwayLocationHeaderHolder)holder).header.setCardBackgroundColor(ContextCompat.getColor(context, R.color.headerBlue));
            ((SubwayLocationHeaderHolder)holder).headerText.setGravity(Gravity.CENTER);
        }else{
            ((SubwayLocationItemHolder)holder).locationText.setText(listLocations[position-1]);
            ((SubwayLocationItemHolder)holder).cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int stopID;
                    String direction = "";
                    if (line.equalsIgnoreCase("BSL")) {
                        stopID = subwayLocationData.getStopId(listLocations[position], "North");
                        direction = "NORTH";
                    } else {
                        stopID = subwayLocationData.getStopId(listLocations[position], "East");
                        direction = "EAST";
                    }
                    Intent startSubwayActivity = new Intent(context, SubwayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(STOP_ID_KEY, stopID);
                    bundle.putString(DIRECTION_KEY, direction);
                    bundle.putString(LINE_KEY, line);
                    bundle.putString(LOCATION_KEY, listLocations[position]);
                    startSubwayActivity.putExtras(bundle);

                    context.startActivityForResult(startSubwayActivity, 0);
                }
            });
        }

    }

    /**
     * Get number of items.
     * @return number of listLocations and header
     */
    @Override
    public int getItemCount() {
        return listLocations.length+1;
    }

    /**
     * Holder for subway locations
     */
    public class SubwayLocationItemHolder extends RecyclerView.ViewHolder{
        TextView locationText;
        CardView cardView;

        /**
         * Constructor
         * @param itemView item view
         */
        public SubwayLocationItemHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.subway_location_item_card);
            locationText = (TextView) itemView.findViewById(R.id.locationText);
        }
    }

    public class SubwayLocationHeaderHolder extends RecyclerView.ViewHolder{
        CardView header;
        TextView headerText;
        /**
         * Constructor
         * @param itemView item view
         */
        public SubwayLocationHeaderHolder(View itemView) {
            super(itemView);
            header = (CardView) itemView.findViewById(R.id.subwayLocationHeader);
            headerText = (TextView) itemView.findViewById(R.id.header_title);
        }
    }
}
