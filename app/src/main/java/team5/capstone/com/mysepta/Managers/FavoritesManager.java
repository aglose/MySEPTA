package team5.capstone.com.mysepta.Managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import team5.capstone.com.mysepta.Adapters.FavoritesViewAdapter;
import team5.capstone.com.mysepta.Activities.MainActivity;
import team5.capstone.com.mysepta.Models.FavoriteRailModel;
import team5.capstone.com.mysepta.Models.SubwayScheduleItemModel;
import team5.capstone.com.mysepta.R;

/**
 * Singleton of manager for favorites.
 * Created by Andrew on 10/24/2015.
 */
public class FavoritesManager{
    private static final String TAG = "FavoritesManager";
    private static ArrayList<SubwayScheduleItemModel> subwayFavoriteList;
    private static ArrayList<FavoriteRailModel> railFavoriteList;
    private static FavoritesManager fragmentManager = null;
    private SharedPreferences prefs;
    private static MainActivity context;
    private static RecyclerView recyclerView;

    /**
     * Init new manager
     */
    private FavoritesManager() {
        subwayFavoriteList = new ArrayList<>();
        railFavoriteList = new ArrayList<>();
    }

    /**
     * Get favorites manager
     * @return instance of favorites manager
     */
    public static FavoritesManager getInstance() {
        if(fragmentManager == null) {
            fragmentManager = new FavoritesManager();
        }
        return fragmentManager;
    }

    /**
     * Set context and build favorites
     * @param context context of call
     */
    public void setContext(MainActivity context){
        this.context = context;
        buildFromPreferences();
    }

    /**
     * Set recycler view
     * @param recyclerView
     */
    public void setRecyclerView(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
    }

    /**
     * Retrieve saved rail and subway information.
     */
    private void buildFromPreferences() {
        subwayFavoriteList = new ArrayList<>();
        railFavoriteList = new ArrayList<>();

        prefs = context.getSharedPreferences(context.getString(R.string.favorites_key), Context.MODE_PRIVATE);

        Log.d(TAG,"Preferences Init");

        Gson gson = new Gson();
        String json = prefs.getString(context.getString(R.string.subway_favorites_key), "");

        if(json.length() > 2){
            subwayFavoriteList = gson.fromJson(json, new TypeToken<ArrayList<SubwayScheduleItemModel>>(){}.getType());
        }else{
            addSubwayWelcomeCard();
        }

        Set<String> rail = prefs.getStringSet(context.getString(R.string.rail_favorites_key),new HashSet<String>());

        String[] station;
        for(String railString : rail){
            station = railString.split("-",2);
            railFavoriteList.add(new FavoriteRailModel(station[0],station[1]));
        }

    }

    /**
     * Add card if subway is empty.
     */
    private static void addSubwayWelcomeCard() {
        SubwayScheduleItemModel newSubway = new SubwayScheduleItemModel();
        newSubway.setLine("Test");
        newSubway.setLocation("Add favorites to this screen by clicking the star icon!");
        subwayFavoriteList.add(newSubway);
        if(recyclerView != null){
            recyclerView.getAdapter().notifyItemInserted(1);
        }
    }

    /**
     * Retrieve favortites
     * @return Array list of favorites.
     */
    public ArrayList getFavoriteList(){
        ArrayList fullList = new ArrayList<>();
        fullList.addAll(subwayFavoriteList);
        fullList.addAll(railFavoriteList);
        return fullList;
    }

    /**
     * Return subway list
     * @return subway favorites
     */
    public ArrayList getSubwayList(){
        return subwayFavoriteList;
    }

    /**
     * Return rail favorites
     * @return
     */
    public ArrayList getRailList(){
        return railFavoriteList;
    }

    /**
     * Add subway line to favorites
     * @param item item to add
     * @return true if success
     */
    public static boolean addSubwayLineToFavorites(SubwayScheduleItemModel item){
        if(subwayFavoriteList.size() == 1){
            if(subwayFavoriteList.get(0).getLine().equalsIgnoreCase("Test")){
                subwayFavoriteList.remove(0);
                recyclerView.getAdapter().notifyItemRemoved(1);
            }
        }
        boolean fav = checkForFavoriteSubwayLine(item.getLine(), item.getLocation());
        if(!fav){
            SubwayScheduleItemModel newSubway = new SubwayScheduleItemModel();
            newSubway.setLine(item.getLine());
            newSubway.setLocation(item.getLocation());
            newSubway.setDirection(item.getDirection());
            newSubway.setStopID(item.getStopID());

            subwayFavoriteList.add(newSubway);
            recyclerView.getAdapter().notifyItemInserted(subwayFavoriteList.size() + (FavoritesViewAdapter.HEADER_AMOUNT - 1));
            context.fragmentPagerAdapter.notifyDataSetChanged();
            return true;
        }

        return false;
    }

    /**
     * Remove subway line from favorites
     * @param line line to remove
     * @param location location to remove
     */
    public static void removeSubwayLineFromFavorites(String line, String location) {
        boolean fav = checkForFavoriteSubwayLine(line, location);
        if(fav){
            for(SubwayScheduleItemModel item: subwayFavoriteList){
                if(item.getLine().equalsIgnoreCase(line) && item.getLocation().equalsIgnoreCase(location)){
                    int index = subwayFavoriteList.indexOf(item);
                    subwayFavoriteList.remove(item);
                    recyclerView.getAdapter().notifyItemRemoved(index + 1); // +1 for the first header in this fragment
                    recyclerView.getAdapter().notifyItemRangeChanged(index, subwayFavoriteList.size());
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.refreshDrawableState();
                    context.fragmentPagerAdapter.notifyDataSetChanged();
                    break;
                }
            }
        }
        if(subwayFavoriteList.size() == 0){
            addSubwayWelcomeCard();
        }
    }

    /**
     * Check if subway is favorited
     * @param line subway line
     * @param location location
     * @return true if favorited
     */
    public static boolean checkForFavoriteSubwayLine(String line, String location) {
        for(SubwayScheduleItemModel item: subwayFavoriteList){
            if(item.getLine().equalsIgnoreCase(line) && item.getLocation().equalsIgnoreCase(location)){
                return true;
            }
        }
        return false;
    }

    /**
     * Add rail to favorites
     * @param startStation start station
     * @param endStation end station
     * @return true if success
     */
    public static boolean addRailLineToFavorites(String startStation, String endStation){
        FavoriteRailModel favoriteRailModel = new FavoriteRailModel(startStation,endStation);

        if(checkForFavoriteRailModel(favoriteRailModel) == -1){
            railFavoriteList.add(favoriteRailModel);
            recyclerView.getAdapter().notifyItemInserted(railFavoriteList.size()
                    + FavoritesViewAdapter.HEADER_AMOUNT + subwayFavoriteList.size());
            return true;
        }

        return false;
    }

    /**
     * Remove rail from favorites
     * @param startStation start station
     * @param endStation end station
     * @return true if success
     */
    public static boolean removeRailLineFromFavorites(String startStation,String endStation){
        FavoriteRailModel favoriteRailModel = new FavoriteRailModel(startStation,endStation);

        int loc = checkForFavoriteRailModel(favoriteRailModel);

        if(loc != -1){
            railFavoriteList.remove(loc);
            recyclerView.getAdapter().notifyItemRemoved(loc + subwayFavoriteList.size() + FavoritesViewAdapter.HEADER_AMOUNT);
            recyclerView.getAdapter().notifyItemRangeChanged(loc, subwayFavoriteList.size());
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.refreshDrawableState();
            context.fragmentPagerAdapter.notifyDataSetChanged();
            return true;
        }

        return false;
    }

    /**
     * Check if rail in favorites
     * @param startStation start station
     * @param endStation end station
     * @return true if exists
     */
    public static boolean checkForFavoriteRailLine(String startStation,String endStation){
        FavoriteRailModel favoriteRailModel = new FavoriteRailModel(startStation,endStation);

        if(checkForFavoriteRailModel(favoriteRailModel) != -1){
            return true;
        }

        return false;
    }

    /**
     * Check if rail exists
     * @param favoriteRailModel model of rail
     * @return location or -1
     */
    private static int checkForFavoriteRailModel(FavoriteRailModel favoriteRailModel){
        for(int i=0;i<railFavoriteList.size();i++){
            FavoriteRailModel model = railFavoriteList.get(i);
            if(model.getStartingStation().equalsIgnoreCase(favoriteRailModel.getStartingStation())
                    && model.getEndingStation().equalsIgnoreCase(favoriteRailModel.getEndingStation()))
                return i;
        }

        return -1;
    }

    /**
     * Store favorites to preferences
     */
    public void storeSharedPreferences(){
        if(prefs == null)
            return;

        Set<String> rail = new HashSet<>();

        Gson gson = new Gson();
        String json = gson.toJson(subwayFavoriteList, new TypeToken<ArrayList<SubwayScheduleItemModel>>(){}.getType());

        for(FavoriteRailModel model : railFavoriteList){
            rail.add(model.getStartingStation() + "-" + model.getEndingStation());
        }


        prefs.edit().putString(context.getString(R.string.subway_favorites_key), json).apply();
        prefs.edit().putStringSet(context.getString(R.string.rail_favorites_key), rail).apply();
    }

}
