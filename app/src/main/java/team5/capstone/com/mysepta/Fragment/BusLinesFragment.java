package team5.capstone.com.mysepta.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.google.android.gms.plus.model.people.Person;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import team5.capstone.com.mysepta.Adapters.BusLinesAdapter;
import team5.capstone.com.mysepta.Models.BusLineModel;
import team5.capstone.com.mysepta.R;

/**
 * Created by Andrew on 1/26/2016.
 */
public class BusLinesFragment extends Fragment implements BusLinesAdapter.FilterAdapterListener{
    private static final String TAG = "BusLinesFragment";

    private View view;
    private RecyclerView busLineRecyclerView;
    private ArrayList<BusLineModel> busLineList;

    private InputStream in;
    private BufferedReader reader;
    private String line;
    private RecyclerViewMaterialAdapter materialAdapter;
    private BusLinesAdapter basicAdapter;

    public static BusLinesFragment newInstance() {
        Bundle args = new Bundle();
        BusLinesFragment fragment = new BusLinesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bus_lines, container, false);

        busLineList = new ArrayList();

        busLineRecyclerView = (RecyclerView) view.findViewById(R.id.busLineRecyclerView);
        busLineRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        basicAdapter = new BusLinesAdapter(getActivity(), busLineRecyclerView, this, busLineList);
        materialAdapter = new RecyclerViewMaterialAdapter(basicAdapter);

        setUpLineList();

        busLineRecyclerView.setAdapter(materialAdapter);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), busLineRecyclerView, null);
        return view;
    }

    private void setUpLineList(){
        busLineList.clear();
        try {
            in = getActivity().getAssets().open("bus_lines.txt");

            reader = new BufferedReader(new InputStreamReader(in));
            while((line = reader.readLine()) != null){
                BusLineModel busLineModel = new BusLineModel();
                int firstComma = line.indexOf(",");  //15057,1,Parx Casino to 54th-City,3
                String routeID = line.substring(0, firstComma);
                int secondComma = line.indexOf(",", firstComma + 1);
                String shortName = line.substring(firstComma + 1, secondComma);

                int thirdComma = line.indexOf(",", secondComma + 1);
                String longName = line.substring(secondComma + 1, thirdComma);
                busLineModel.setRouteId(routeID);
                busLineModel.setRouteShortName(shortName);
                busLineModel.setRouteLongName(longName);
                Log.d(TAG, routeID + " " + shortName + " " + longName);

                busLineList.add(busLineModel);
            }

//            Collections.sort(busLineList, new Comparator<BusLineModel>() {
//                @Override
//                public int compare(BusLineModel lhs, BusLineModel rhs) {
//                    Integer left = 0;
//                    Integer right = 0;
//                    try {
//                        left = Integer.valueOf(lhs.getRouteShortName());
//                        right = Integer.valueOf(rhs.getRouteShortName());
//                    } catch (NumberFormatException e) {
//                        String charLeftString = "";
//                        for (char c : lhs.getRouteShortName().toCharArray()) {
//                            int character = Character.digit(c, 9);
//
//                            if (character > -1) {
//                                charLeftString += character;
//                            }
//
//                        }
//                        if (charLeftString.length() == 0) {
//                            left = lhs.getRouteShortName().charAt(0) + 999;
//                        } else {
//                            left = Integer.valueOf(charLeftString);
//                        }
//                        Log.d(TAG, ""+left);
//
//
//                        String charRightString = "";
//                        for (char d : rhs.getRouteShortName().toCharArray()) {
//                            int character = Character.digit(d, 9);
//                            if (character > -1) {
//                                charRightString += character;
//                            }
//                        }
//
//                        if (charRightString.length() == 0) {
//                            right = rhs.getRouteShortName().charAt(0) + 999;
//                        } else {
//                            right = Integer.valueOf(charRightString);
//                        }
//                        Log.d(TAG, ""+right);
//                    }
//
//                    return left.compareTo(right);
//                }
//            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        basicAdapter.setItems(busLineList);
        basicAdapter.setHeader("Header");
        materialAdapter.notifyDataSetChanged();
    }
    @Override
    public void updateFilter(String currentText) {
        setUpLineList();
        Collection<BusLineModel> filtered = null;

        final String currentTextFinal = currentText.trim();

        filtered = Collections2.filter(busLineList,
                new Predicate<BusLineModel>() {
                    @Override
                    public boolean apply(BusLineModel input) {
                        if(input.getRouteShortName().trim().contains(currentTextFinal)){
                            Log.d(TAG, "removing :" + input.getRouteShortName());
                            return true;
                        }
                        return false;
                    }
                });
//        materialAdapter.notifyItemRemoved(i);
//        materialAdapter.notifyItemRangeChanged(i, busLineList.size());

        ArrayList<BusLineModel> elements = Lists.newArrayList(filtered);

        for(BusLineModel model : filtered){
            Log.d(TAG, "line: "+model.getRouteShortName());
        }

        basicAdapter.clearData(elements);
        basicAdapter.notifyDataSetChanged();
        materialAdapter.notifyDataSetChanged();
    }
}
