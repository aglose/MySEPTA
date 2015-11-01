package team5.capstone.com.mysepta.Managers;

import java.util.ArrayList;

import team5.capstone.com.mysepta.Models.RailLocationData;
import team5.capstone.com.mysepta.Models.SubwayScheduleItemModel;

/**
 * Created by Andrew on 10/24/2015.
 */
public class FavoritesManager {
    private ArrayList<SubwayScheduleItemModel> subwayFavoriteList = new ArrayList<>();
    private ArrayList<RailLocationData> railFavoriteList = new ArrayList<>();

    //Just for testing, will be removed later
    public FavoritesManager(){
        SubwayScheduleItemModel d = new SubwayScheduleItemModel();
        d.setFormattedTimeStr("Subway Arrival #1");
        subwayFavoriteList.add(d);
        d = new SubwayScheduleItemModel();
        d.setFormattedTimeStr("Subway Arrival #2");
        subwayFavoriteList.add(d);
        d = new SubwayScheduleItemModel();
        d.setFormattedTimeStr("Subway Arrival #3");
        subwayFavoriteList.add(d);

        RailLocationData f = new RailLocationData("null","test","test", "fff ","Rail Arrival #1", true);
        railFavoriteList.add(f);
        f = new RailLocationData("null","test","test", "fff ","Rail Arrival #2", true);
        railFavoriteList.add(f);
        f = new RailLocationData("null","test","test", "fff ","Rail Arrival #3", true);
        railFavoriteList.add(f);
    }

    public ArrayList getFavoriteList(){
        ArrayList fullList = new ArrayList<>();
        fullList.addAll(subwayFavoriteList);
        fullList.addAll(railFavoriteList);
        return fullList;
    }
    public ArrayList getSubwayList(){
        return subwayFavoriteList;
    }

    public ArrayList getRailList(){
        return railFavoriteList;
    }
}
