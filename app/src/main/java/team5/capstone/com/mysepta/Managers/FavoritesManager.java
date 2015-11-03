package team5.capstone.com.mysepta.Managers;

import java.util.ArrayList;

import team5.capstone.com.mysepta.Models.FavoriteRailModel;
import team5.capstone.com.mysepta.Models.RailLocationData;
import team5.capstone.com.mysepta.Models.SubwayScheduleItemModel;

/**
 * Created by Andrew on 10/24/2015.
 */
public class FavoritesManager {
    private static ArrayList<SubwayScheduleItemModel> subwayFavoriteList = new ArrayList<>();
    private static ArrayList<FavoriteRailModel> railFavoriteList = new ArrayList<>();
    private static FavoritesManager fragmentManager = null;

    protected FavoritesManager() {
        // Exists only to defeat instantiation, and testing purposes until mocking is unnecessary.
        SubwayScheduleItemModel d = new SubwayScheduleItemModel();
        d.setFormattedTimeStr("Subway Arrival #1");
        subwayFavoriteList.add(d);
        d = new SubwayScheduleItemModel();
        d.setFormattedTimeStr("Subway Arrival #2");
        subwayFavoriteList.add(d);
        d = new SubwayScheduleItemModel();
        d.setFormattedTimeStr("Subway Arrival #3");
        subwayFavoriteList.add(d);

        FavoriteRailModel f = new FavoriteRailModel("Philmont","Ambler");
        railFavoriteList.add(f);
        f = new FavoriteRailModel("test","Rail Arrival #1");
        railFavoriteList.add(f);
        f = new FavoriteRailModel("test","Rail Arrival #1");
        railFavoriteList.add(f);
    }

    public static FavoritesManager getInstance() {
        if(fragmentManager == null) {
            fragmentManager = new FavoritesManager();
        }
        return fragmentManager;
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

    public static boolean addSubwayLineToFavorites(String location){
        subwayFavoriteList.get(0).setFormattedTimeStr("Successful add for line: "+location);
        return false;
    }
}
