package team5.capstone.com.mysepta.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import team5.capstone.com.mysepta.Models.SubwayLocationData;
import team5.capstone.com.mysepta.Models.SubwayNextModel;
import team5.capstone.com.mysepta.R;

/**
 * Created by Andrew on 9/20/2015.
 */
public class SubwayScheduleViewAdapter extends RecyclerView.Adapter<SubwayScheduleViewAdapter.SubwayScheduleHolder> {
    private static final String TAG = "SubwayScheduleViewAdapter";
    Context context;
    String[] listLocations;

    public SubwayScheduleViewAdapter(String line, Context context){
        if(line.equalsIgnoreCase("MFL")){
            listLocations = context.getResources().getStringArray(R.array.market_frankford_line);
        }else if(line.equalsIgnoreCase("BSL")){
            listLocations = context.getResources().getStringArray(R.array.broad_street_line);
        }else{
            Log.d(TAG, "Messed up mere");
        }
        this.context = context;
    }


    @Override
    public SubwayScheduleViewAdapter.SubwayScheduleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subway_list_item_card, parent, false);
        return new SubwayScheduleHolder(view);
    }

    @Override
    public void onBindViewHolder(SubwayScheduleHolder holder, int position) {
        holder.locationText.setText(listLocations[position]);
    }

    @Override
    public int getItemCount() {
        return listLocations.length;
    }

    public class SubwayScheduleHolder extends RecyclerView.ViewHolder{
        TextView locationText;

        public SubwayScheduleHolder(View itemView) {
            super(itemView);

            locationText = (TextView) itemView.findViewById(R.id.nextStopText);
        }
    }
}
