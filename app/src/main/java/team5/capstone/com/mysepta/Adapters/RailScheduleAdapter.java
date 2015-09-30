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
 * Created by Kevin on 9/30/15.
 */
public class RailScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private int numItems;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rail_list_item_card, parent, false);

        context = view.getContext();
        numItems = 1;

        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return numItems;
    }
}
