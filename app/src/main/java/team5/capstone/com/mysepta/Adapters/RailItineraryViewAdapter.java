package team5.capstone.com.mysepta.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import team5.capstone.com.mysepta.R;
import team5.capstone.com.mysepta.RailActivity;

/**
 * Creates adapter to handle RailItinerary view.
 * Created by Kevin on 9/28/15.
 */
public class RailItineraryViewAdapter extends RecyclerView.Adapter<RailItineraryViewAdapter.RailItineraryHolder> {
    private Context context;
    static final int TYPE_LIVE = 0;
    static final int TYPE_STATIC_HEADER = 1;
    static final int TYPE_RAIL = 2;

    public RailItineraryViewAdapter(Context context){
        this.context = context;
    }

    /**
     * Create recyclerView.viewholder and setup button listeners.
     * @param parent parent viewgroup
     * @param viewType type of view
     * @return holder for rail home screen
     */
    @Override
    public RailItineraryViewAdapter.RailItineraryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rail_itinerary_card, parent, false);


        /*
        context = view.getContext();

        Button lrsButton = (Button) view.findViewById(R.id.lrsButton);
        lrsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launch = new Intent(context,RailActivity.class);
                context.startActivity(launch);
            }
        });

        Button ssButton = (Button) view.findViewById(R.id.ssButton);
        ssButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launch = new Intent(context,RailActivity.class);
                context.startActivity(launch);
            }
        });*/
        return new RailItineraryViewAdapter.RailItineraryHolder(view) {
        };
    }

    /**
     * Empty method that must be overwritten.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RailItineraryViewAdapter.RailItineraryHolder holder, int position) {

        switch (getItemViewType(position)){
            case TYPE_LIVE:
                holder.textView.setText(R.string.live_rail_label);
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent launch = new Intent(context,RailActivity.class);
                        context.startActivity(launch);
                    }
                });
                break;
            case TYPE_STATIC_HEADER:
                holder.textView.setText(R.string.static_rail_header);
                break;
            case TYPE_RAIL:
                String[] rails = context.getResources().getStringArray(R.array.rail_names);
                holder.textView.setText(rails[position-2]);
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                break;
        }
    }

    @Override
    public int getItemViewType(int position){
        switch (position){
            case 0:
                return TYPE_LIVE;
            case 1:
                return TYPE_STATIC_HEADER;
            default:
                return TYPE_RAIL;
        }
    }

    /**
     * Count of cards in adapter.
     * @return 1
     */
    @Override
    public int getItemCount() {
        return 2+context.getResources().getStringArray(R.array.rail_names).length;
    }

    public class RailItineraryHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView textView;

        public RailItineraryHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.rail_itinerary_card);
            textView = (TextView) itemView.findViewById(R.id.rail_itinerary_label);
        }
    }
}
