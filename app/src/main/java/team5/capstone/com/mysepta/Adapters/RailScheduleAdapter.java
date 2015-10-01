package team5.capstone.com.mysepta.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import team5.capstone.com.mysepta.Models.RailLocationData;
import team5.capstone.com.mysepta.R;
import team5.capstone.com.mysepta.RailActivity;

/**
 * Created by Kevin on 9/30/15.
 */
public class RailScheduleAdapter extends RecyclerView.Adapter<RailScheduleAdapter.RailScheduleHolder> {
    private Context context;
    private ArrayList<RailLocationData> rails;

    public RailScheduleAdapter(ArrayList<RailLocationData> rails, Context context){
        this.rails = rails;
        this.context = context;
    }

    @Override
    public RailScheduleAdapter.RailScheduleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rail_list_item_card, parent, false);


        return new RailScheduleHolder(view);
    }

    @Override
    public void onBindViewHolder(RailScheduleHolder holder, int position) {
        RailLocationData temp = rails.get(position);
        holder.railText.setText(temp.getRailName());
        holder.railAcr.setText(temp.getRailAcr());
        holder.timeText.setText(temp.getTime());
        holder.trainText.setText(temp.getRailNumber()+" to "+temp.getStation());
    }

    @Override
    public int getItemCount() {
        return rails.size();
    }

    public class RailScheduleHolder extends RecyclerView.ViewHolder{
        TextView railText;
        TextView railAcr;
        TextView trainText;
        TextView timeText;
        CardView cardView;

        public RailScheduleHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.rail_item_card);
            railText = (TextView) itemView.findViewById(R.id.trainLabel);
            railAcr = (TextView) itemView.findViewById(R.id.lineName);
            trainText = (TextView) itemView.findViewById(R.id.trainInfo);
            timeText = (TextView) itemView.findViewById(R.id.arrivalTime);
        }
    }
}
