package team5.capstone.com.mysepta.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import team5.capstone.com.mysepta.R;

/**
 * Created by tiestodoe on 10/1/15.
 */
public class SubwayScheduleViewAdapter extends RecyclerView.Adapter<SubwayScheduleViewAdapter.SubwayScheduleHolder> {

    @Override
    public SubwayScheduleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SubwayScheduleHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class SubwayScheduleHolder extends RecyclerView.ViewHolder{

        public SubwayScheduleHolder(View itemView) {
            super(itemView);
        }
    }
}
