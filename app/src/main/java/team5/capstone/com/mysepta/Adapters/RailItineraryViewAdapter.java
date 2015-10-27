package team5.capstone.com.mysepta.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import team5.capstone.com.mysepta.R;
import team5.capstone.com.mysepta.RailActivity;

/**
 * Creates adapter to handle RailItinerary view.
 * Created by Kevin on 9/28/15.
 */
public class RailItineraryViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;

    /**
     * Create recyclerView.viewholder and setup button listeners.
     * @param parent parent viewgroup
     * @param viewType type of view
     * @return holder for rail home screen
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rail_itinerary_card, parent, false);

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
        });
        return new RecyclerView.ViewHolder(view) {
        };
    }

    /**
     * Empty method that must be overwritten.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    /**
     * Count of cards in adapter.
     * @return 1
     */
    @Override
    public int getItemCount() {
        return 1;
    }
}
