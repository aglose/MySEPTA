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
 * Created by Kevin on 9/28/15.
 */
public class RailItineraryViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;

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

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
