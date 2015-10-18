package team5.capstone.com.mysepta.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import team5.capstone.com.mysepta.Models.SubwayLocationData;
import team5.capstone.com.mysepta.R;
import team5.capstone.com.mysepta.SubwayActivity;

/**
 * Created by Andrew on 9/20/2015.
 */
public class SubwayLocationViewAdapter extends RecyclerView.Adapter<SubwayLocationViewAdapter.SubwayLocationHolder> {
    private static final String TAG = "SubwayLocationViewAdapter";
    private static final String STOP_ID_KEY = "StopID";
    private static final String LOCATION_KEY = "Location";
    private static final String DIRECTION_KEY = "Direction";
    private static final String LINE_KEY = "Line";

    private Context context;
    private String[] listLocations;
    private SubwayLocationData subwayLocationData;
    private String line;

    public SubwayLocationViewAdapter(String line, Context context, SubwayLocationData subwayLocationData){
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
    public SubwayLocationViewAdapter.SubwayLocationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subway_list_item_card, parent, false);
        return new SubwayLocationHolder(view);
    }

    @Override
    public void onBindViewHolder(SubwayLocationHolder holder, final int position) {
        holder.locationText.setText(listLocations[position]);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int stopID;
                if (line.equalsIgnoreCase("BSL")) {
                    stopID = subwayLocationData.getStopId(listLocations[position], "North");
                } else {
                    stopID = subwayLocationData.getStopId(listLocations[position], "East");
                }
                Intent startSubwayActivity = new Intent(context, SubwayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(STOP_ID_KEY, stopID);
                bundle.putString(DIRECTION_KEY, "default");
                bundle.putString(LINE_KEY, line);
                bundle.putString(LOCATION_KEY, listLocations[position]);
                startSubwayActivity.putExtras(bundle);
                context.startActivity(startSubwayActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listLocations.length;
    }

    public class SubwayLocationHolder extends RecyclerView.ViewHolder{
        TextView locationText;
        CardView cardView;

        public SubwayLocationHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.subway_location_item_card);
            locationText = (TextView) itemView.findViewById(R.id.locationText);
        }
    }
}
