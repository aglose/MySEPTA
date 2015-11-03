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
 * Adapter to define functionality of rail schedule fragment.
 * Created by Kevin on 9/30/15.
 */
public class RailScheduleAdapter extends RecyclerView.Adapter<RailScheduleAdapter.RailScheduleHolder> {
    private Context context;
    private ArrayList<RailLocationData> rails;
    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;
    private String start;
    private String end;

    /**
     * Constructor
     * @param rails List of rail data
     * @param context activity
     * @param start starting station
     * @param end ending station
     */
    public RailScheduleAdapter(ArrayList<RailLocationData> rails, Context context, String start,String end){
        this.rails = rails;
        this.context = context;
        this.start = start;
        this.end = end;
    }


    /**
     * Check type of item view.
     * @param position location of view
     * @return TYPE_HEADER if in position 0, otherwise TYPE_CELL
     */
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            //case 0:
              //  return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    /**
     * Create viewholder
     * @param parent parent viewgroup
     * @param viewType type of item view
     * @return rail schedule holder for view
     */
    @Override
    public RailScheduleAdapter.RailScheduleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch(viewType) {
            case TYPE_HEADER:{
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.header_card, parent, false);
                return new RailScheduleHolder(view);
            }
            case TYPE_CELL:{
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.rail_list_item_card, parent, false);
                return new RailScheduleHolder(view);
            }


        }

        return null;

    }

    /**
     * Initialize view parameters and bind them.
     * @param holder holder for view
     * @param position position of view
     */
    @Override
    public void onBindViewHolder(RailScheduleHolder holder, int position) {
        switch(getItemViewType(position)) {
            case TYPE_HEADER:{
                holder.arrivalStation.setText(end);
                holder.departureStation.setText(start);
                holder.cardView2.setBackgroundColor(context.getResources().getColor(R.color.black));
                break;
            }
            case TYPE_CELL:{
                RailLocationData temp = rails.get(position);

                if(temp.isConnection()){
                    holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.blue));
                }
                else{
                    holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.white));
                }
                holder.railText.setText(temp.getRailName());
                holder.railAcr.setText(temp.getRailAcr());
                holder.timeText.setText(temp.getTime());
                holder.trainText.setText("#" + temp.getRailNumber() + " to " + temp.getStation());
                break;
            }

        }
    }

    /**
     * Return number of items.
     * @return number of rails + headers
     */
    @Override
    public int getItemCount() {
        return rails.size();
    }

    /**
     * Creates holder for views to make item views easily retrievable.
     */
    public class RailScheduleHolder extends RecyclerView.ViewHolder{
        TextView railText;
        TextView railAcr;
        TextView trainText;
        TextView timeText;
        CardView cardView;
        CardView cardView2;
        TextView arrivalStation;
        TextView departureStation;

        /**
         * Constructor
         * @param itemView current item
         */
        public RailScheduleHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.rail_item_card);
            railText = (TextView) itemView.findViewById(R.id.trainLabel);
            railAcr = (TextView) itemView.findViewById(R.id.lineName);
            trainText = (TextView) itemView.findViewById(R.id.trainInfo);
            timeText = (TextView) itemView.findViewById(R.id.arrivalTime);

            arrivalStation = (TextView) itemView.findViewById(R.id.arrivalStation);
            departureStation = (TextView) itemView.findViewById(R.id.departureStation);
            cardView2 = (CardView) itemView.findViewById(R.id.header_card);

        }
    }
}
