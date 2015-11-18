package team5.capstone.com.mysepta.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import team5.capstone.com.mysepta.Models.AlertsModel;
import team5.capstone.com.mysepta.Models.RailLocationData;
import team5.capstone.com.mysepta.R;

/**
 *
 */
public class AlertsAdapter extends RecyclerView.Adapter<AlertsAdapter.AlertsHolder>{

    private Context context;
    private ArrayList<AlertsModel> alerts;
    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    /**
     *
     * @param alerts
     * @param context
     */
    public AlertsAdapter(ArrayList<AlertsModel> alerts, Context context){

        this.alerts = alerts;
        this.context = context;

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
            //    return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    /**
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public AlertsAdapter.AlertsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch(viewType) {
            case TYPE_HEADER:{
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.alerts_header_item_card, parent, false);
                return new AlertsHolder(view);
            }
            case TYPE_CELL:{
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.alerts_item_card, parent, false);
                return new AlertsHolder(view);
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
    public void onBindViewHolder(AlertsHolder holder, int position) {
        switch(getItemViewType(position)) {
            case TYPE_HEADER:{

                break;
            }
            case TYPE_CELL:{
                AlertsModel temp = alerts.get(position);

                //holder.modeText.setText(temp.getMode());
                holder.routeNameText.setText(temp.getRouteName());
                holder.updateText.setText((CharSequence) temp.getLastUpdate());
                holder.descText.setText(temp.getDescription());
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
        return alerts.size();
    }

    /**
     *
     */
    public class AlertsHolder extends RecyclerView.ViewHolder{
        //TextView modeText;
        TextView routeNameText;
        TextView updateText;
        TextView descText;
        CardView cardView;


        /**
         * Constructor
         * @param itemView current item
         */
        public AlertsHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.alerts_item_card);
            //modeText = (TextView) itemView.findViewById(R.id.mode);
            routeNameText = (TextView) itemView.findViewById(R.id.route_name);
            updateText = (TextView) itemView.findViewById(R.id.lastUpdate);
            descText = (TextView) itemView.findViewById(R.id.desc);
        }
    }
}
