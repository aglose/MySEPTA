package team5.capstone.com.mysepta.Adapters;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.headerrecyclerview.HeaderRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import team5.capstone.com.mysepta.Activities.BusStopsActivity;
import team5.capstone.com.mysepta.Fragment.BusLinesFragment;
import team5.capstone.com.mysepta.Models.BusLineModel;
import team5.capstone.com.mysepta.R;

/**
 * Created by Andrew on 1/26/2016.
 */
public class BusLinesAdapter extends HeaderRecyclerViewAdapter {
    private static final String TAG = "BusLinesAdapter";

    private Context context;
    private RecyclerView rv;
    private FilterAdapterListener adapterUpdateListener;
    private ArrayList<BusLineModel> busLineList;

    public BusLinesAdapter(Context context, RecyclerView rv, FilterAdapterListener adapterUpdateListener, ArrayList<BusLineModel> busLineList){
        this.context = context;
        this.rv = rv;
        this.adapterUpdateListener = adapterUpdateListener;
        this.busLineList = busLineList;
    }

    public interface FilterAdapterListener{
        void updateFilter(String currentText);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = getLayoutInflater(parent);
        View headerView = inflater.inflate(R.layout.bus_calc_header, parent, false);
        return new HeaderViewHolder(headerView);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = getLayoutInflater(parent);
        View itemView = inflater.inflate(R.layout.bus_line_item, parent, false);
        return new BusLineViewHolder(itemView);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        final HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;

        headerViewHolder.button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String previousText = headerViewHolder.busNumberEditText.getText().toString();
                String newDisplay = previousText+"0";
                headerViewHolder.busNumberEditText.setText(newDisplay);
                adapterUpdateListener.updateFilter(newDisplay);
            }
        });

        headerViewHolder.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String previousText = headerViewHolder.busNumberEditText.getText().toString();
                String newDisplay = previousText+"1";
                headerViewHolder.busNumberEditText.setText(newDisplay);
                adapterUpdateListener.updateFilter(newDisplay);
            }
        });

        headerViewHolder.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String previousText = headerViewHolder.busNumberEditText.getText().toString();
                String newDisplay = previousText+"2";
                headerViewHolder.busNumberEditText.setText(newDisplay);
                adapterUpdateListener.updateFilter(newDisplay);
            }
        });

        headerViewHolder.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String previousText = headerViewHolder.busNumberEditText.getText().toString();
                String newDisplay = previousText+"3";
                headerViewHolder.busNumberEditText.setText(newDisplay);
                adapterUpdateListener.updateFilter(newDisplay);
            }
        });

        headerViewHolder.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String previousText = headerViewHolder.busNumberEditText.getText().toString();
                String newDisplay = previousText+"4";
                headerViewHolder.busNumberEditText.setText(newDisplay);
                adapterUpdateListener.updateFilter(newDisplay);
            }
        });

        headerViewHolder.button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String previousText = headerViewHolder.busNumberEditText.getText().toString();
                String newDisplay = previousText+"5";
                headerViewHolder.busNumberEditText.setText(newDisplay);
                adapterUpdateListener.updateFilter(newDisplay);
            }
        });

        headerViewHolder.button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String previousText = headerViewHolder.busNumberEditText.getText().toString();
                String newDisplay = previousText+"6";
                headerViewHolder.busNumberEditText.setText(newDisplay);
                adapterUpdateListener.updateFilter(newDisplay);
            }
        });

        headerViewHolder.button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String previousText = headerViewHolder.busNumberEditText.getText().toString();
                String newDisplay = previousText+"7";
                headerViewHolder.busNumberEditText.setText(newDisplay);
                adapterUpdateListener.updateFilter(newDisplay);
            }
        });

        headerViewHolder.button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String previousText = headerViewHolder.busNumberEditText.getText().toString();
                String newDisplay = previousText+"8";
                headerViewHolder.busNumberEditText.setText(newDisplay);
                adapterUpdateListener.updateFilter(newDisplay);
            }
        });

        headerViewHolder.button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String previousText = headerViewHolder.busNumberEditText.getText().toString();
                String newDisplay = previousText+"9";
                headerViewHolder.busNumberEditText.setText(newDisplay);
                adapterUpdateListener.updateFilter(newDisplay);
            }
        });

        headerViewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String previousText = headerViewHolder.busNumberEditText.getText().toString();
                if(previousText != null && previousText.length() > 0){
                    String newDisplay = previousText.substring(0, previousText.length() - 1);
                    headerViewHolder.busNumberEditText.setText(newDisplay);
                    adapterUpdateListener.updateFilter(newDisplay);
                }
            }
        });
    }

    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        final BusLineViewHolder busLineViewHolder = (BusLineViewHolder) holder;
        if(position > 0){
            final BusLineModel lineModel = busLineList.get(position-1);
            busLineViewHolder.busNumberText.setText(lineModel.getRouteShortName());
            busLineViewHolder.busLineName.setText(lineModel.getRouteLongName());
            busLineViewHolder.busLineView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "clicked");
                    Intent intent = new Intent(context, BusStopsActivity.class);
                    intent.putExtra("busLine", lineModel.getRouteShortName());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position) {

    }


    private LayoutInflater getLayoutInflater(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext());
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder{
        private Button button0;
        private Button button1;
        private Button button2;
        private Button button3;
        private Button button4;
        private Button button5;
        private Button button6;
        private Button button7;
        private Button button8;
        private Button button9;
        private Button buttonDelete;
        private EditText busNumberEditText;

        public HeaderViewHolder(View itemView){
            super(itemView);
            button0 = (Button) itemView.findViewById(R.id.button0);
            button1 = (Button) itemView.findViewById(R.id.button1);
            button2 = (Button) itemView.findViewById(R.id.button2);
            button3 = (Button) itemView.findViewById(R.id.button3);
            button4 = (Button) itemView.findViewById(R.id.button4);
            button5 = (Button) itemView.findViewById(R.id.button5);
            button6 = (Button) itemView.findViewById(R.id.button6);
            button7 = (Button) itemView.findViewById(R.id.button7);
            button8 = (Button) itemView.findViewById(R.id.button8);
            button9 = (Button) itemView.findViewById(R.id.button9);
            buttonDelete = (Button) itemView.findViewById(R.id.buttonDelete);
            busNumberEditText = (EditText) itemView.findViewById(R.id.busNumberEditText);
        }
    }

    public static class BusLineViewHolder extends RecyclerView.ViewHolder{
        private TextView busNumberText;
        private TextView busLineName;
        private View busLineView;

        public BusLineViewHolder(View itemView){
            super(itemView);
            busNumberText = (TextView) itemView.findViewById(R.id.busNumberText);
            busLineName = (TextView) itemView.findViewById(R.id.busLineName);
            busLineView = itemView.findViewById(R.id.busLineView);
        }
    }

    public void clearData(ArrayList<BusLineModel> busLineList) {
        int size = this.busLineList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.busLineList.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
        this.busLineList = busLineList;
        setItems(busLineList);
        this.notifyItemRangeInserted(0, this.busLineList.size());
    }
}
