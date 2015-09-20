package team5.capstone.com.mysepta.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import team5.capstone.com.mysepta.Models.SubwayNextModel;
import team5.capstone.com.mysepta.R;

/**
 * Created by Andrew on 9/20/2015.
 */
public class SubwayScheduleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Object> contents;
    Context context;

    public SubwayScheduleViewAdapter(List<Object> contents, Context context) {
        this.context = context;
        this.contents = contents;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subway_list_item_card, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return contents.size();
    }
}
