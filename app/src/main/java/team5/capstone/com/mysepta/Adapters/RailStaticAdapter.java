package team5.capstone.com.mysepta.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import team5.capstone.com.mysepta.Models.StaticRailModel;
import team5.capstone.com.mysepta.R;

/**
 * Created by Kevin on 11/12/15.
 */
public class RailStaticAdapter extends RecyclerView.Adapter<RailStaticAdapter.RailScheduleHolder> {

    private ArrayList<StaticRailModel> trains;

    public RailStaticAdapter(ArrayList<StaticRailModel> trains){
        this.trains = sortArray(trains);
    }

    /**
     * Create view holder
     * @param parent parent group
     * @param viewType view type
     * @return view holder
     */
    @Override
    public RailScheduleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rail_static_list_item_card, parent, false);

        return new RailScheduleHolder(view);
    }

    /**
     * Bind values to view holder
     * @param holder holder for current view
     * @param position current position
     */
    @Override
    public void onBindViewHolder(RailScheduleHolder holder, int position) {
        switch(position){
            case 0:
                holder.trainNumber.setText(R.string.train_number);
                holder.startTime.setText(R.string.departure_time);
                holder.endTime.setText(R.string.arrival_time);
                break;
            default:
                holder.trainNumber.setText(trains.get(position-1).getTrainNumber());
                holder.startTime.setText(trains.get(position-1).getStartTime());
                holder.endTime.setText(trains.get(position-1).getEndTime());
                break;
        }
    }

    /**
     * Get item count.
     * @return trains.size + 1
     */
    @Override
    public int getItemCount() {
        return trains.size()+1;
    }

    /**
     * Creates holder for views to make item views easily retrievable.
     */
    public class RailScheduleHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView trainNumber;
        TextView startTime;
        TextView endTime;

        /**
         * Constructor
         * @param itemView current item
         */
        public RailScheduleHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.rail_static_item_card);
            trainNumber = (TextView) itemView.findViewById(R.id.lineNumberStatic);
            startTime = (TextView) itemView.findViewById(R.id.startTime);
            endTime = (TextView) itemView.findViewById(R.id.endTime);
        }
    }

    /**
     * Sort array by time.
     * @param srmArray array to sort
     * @return sorted array
     */
    public ArrayList<StaticRailModel> sortArray(ArrayList<StaticRailModel> srmArray){
        Collections.sort(srmArray, new Comparator<StaticRailModel>() {
            @Override
            public int compare(StaticRailModel lhs, StaticRailModel rhs) {
                int hrLHS,hrRHS,mLHS,mRHS;

                String[] lhsSplit = lhs.getStartTime().split(":|\\s");
                String[] rhsSplit = rhs.getStartTime().split(":|\\s");

                hrLHS = Integer.parseInt(lhsSplit[0]);
                hrRHS = Integer.parseInt(rhsSplit[0]);

                //Convert hours into 24 hrs and check for end of day AM trains
                if(lhsSplit[2].equalsIgnoreCase("PM")){
                    if(hrLHS != 12)
                        hrLHS = hrLHS + 12;
                }
                else if(hrLHS == 12){
                    hrLHS = hrLHS + 12;
                }
                else if(hrLHS < 4){
                    hrLHS = hrLHS + 24;
                }
                if(rhsSplit[2].equalsIgnoreCase("PM")){
                    if(hrRHS != 12)
                        hrRHS = hrRHS + 12;
                }
                else if(hrRHS == 12){
                    hrRHS = hrRHS + 12;
                }
                else if(hrRHS < 4){
                    hrRHS = hrRHS + 24;
                }

                //if hours are same compare minutes
                if(hrLHS == hrRHS){
                    mLHS = Integer.parseInt(lhsSplit[1]);
                    mRHS = Integer.parseInt(rhsSplit[1]);

                    if(mLHS < mRHS){
                        return -1;
                    }
                    else if(mLHS > mRHS){
                        return 1;
                    }
                    else{
                        return 0;
                    }
                }
                else if(hrLHS < hrRHS){
                    return -1;
                }
                else{
                    return 1;
                }

            }
        });

        return srmArray;
    }
}
