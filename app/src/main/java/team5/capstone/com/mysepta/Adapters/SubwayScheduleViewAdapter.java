package team5.capstone.com.mysepta.Adapters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import team5.capstone.com.mysepta.Models.SubwayScheduleItemModel;
import team5.capstone.com.mysepta.R;
import team5.capstone.com.mysepta.SubwayActivity;

/**
 * Created by tiestodoe on 10/1/15.
 */
public class SubwayScheduleViewAdapter extends RecyclerView.Adapter<SubwayScheduleViewAdapter.SubwayScheduleHolder> {
    private ArrayList<SubwayScheduleItemModel> arrivals;

    public SubwayScheduleViewAdapter(ArrayList<SubwayScheduleItemModel> arrivals){
        this.arrivals = arrivals;
    }

    @Override
    public SubwayScheduleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subway_schedule_item, parent, false);
        return new SubwayScheduleHolder(view);
    }

    @Override
    public void onBindViewHolder(SubwayScheduleHolder holder, int position) {
        holder.scheduleText.setText(arrivals.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return arrivals.size();
    }

    public class SubwayScheduleHolder extends RecyclerView.ViewHolder{
        TextView scheduleText;
        CardView cardView;

        public SubwayScheduleHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.subway_schedule_item_card);
            scheduleText = (TextView) itemView.findViewById(R.id.scheduleText);
        }
    }
}
