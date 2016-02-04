package team5.capstone.com.mysepta.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import team5.capstone.com.mysepta.Activities.BusTimesActivity;
import team5.capstone.com.mysepta.Models.BusStopModel;
import team5.capstone.com.mysepta.R;

/**
 * Created by Andrew on 1/27/2016.
 */
public class BusStopsAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<BusStopModel> busStopsList;

    public BusStopsAdapter(Context context, ArrayList<BusStopModel> busStopsList){
        this.context = context;
        this.busStopsList = busStopsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.bus_stops_item, parent, false);
        BusStopsViewHolder viewHolder = new BusStopsViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        BusStopsViewHolder busHolder = (BusStopsViewHolder) holder;
        busHolder.stopLocationText.setText(busStopsList.get(position).getStopName());
        busHolder.stopLocationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent timeActivity = new Intent(context, BusTimesActivity.class);
                timeActivity.putExtra("busStop", busStopsList.get(position).getStopName());
                timeActivity.putExtra("stopId", busStopsList.get(position).getStopID());
                context.startActivity(timeActivity);
//                timeActivity.putExtra("direction", busStopsList.get(position).ge);
            }
        });
    }

    @Override
    public int getItemCount() {
        return busStopsList.size();
    }

    public static class BusStopsViewHolder extends RecyclerView.ViewHolder{
        private TextView stopLocationText;

        public BusStopsViewHolder(View itemView) {
            super(itemView);
            stopLocationText = (TextView) itemView.findViewById(R.id.stopLocationText);
        }
    }
}
