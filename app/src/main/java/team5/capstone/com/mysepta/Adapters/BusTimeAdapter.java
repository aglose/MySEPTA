package team5.capstone.com.mysepta.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import team5.capstone.com.mysepta.Models.BusModelHolder;
import team5.capstone.com.mysepta.Models.BusStopModel;
import team5.capstone.com.mysepta.Models.BusTimeModel;
import team5.capstone.com.mysepta.R;

/**
 * Created by Andrew on 2/3/2016.
 */
public class BusTimeAdapter extends RecyclerView.Adapter{
    private Context context;
    private List<BusTimeModel> busTimeList;

    public BusTimeAdapter(Context context, List<BusTimeModel> busTimeList){
        this.context = context;
        this.busTimeList = busTimeList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.bus_schedule_item, parent, false);

        return new BusTimeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BusTimeViewHolder busHolder = (BusTimeViewHolder) holder;
        busHolder.busArrivalText.setText(busTimeList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return busTimeList.size();
    }

    public static class BusTimeViewHolder extends RecyclerView.ViewHolder{
        private TextView busArrivalText;

        public BusTimeViewHolder(View itemView) {
            super(itemView);
            busArrivalText = (TextView) itemView.findViewById(R.id.busArrivalText);
        }
    }
}
