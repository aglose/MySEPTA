package team5.capstone.com.mysepta.Models;

/**
 * Created by Kevin on 11/2/15.
 */
public class FavoriteRailModel {

    private String startingStation;
    private String endingStation;

    public FavoriteRailModel(String startingStation,String endingStation){
        this.endingStation = endingStation;
        this.startingStation = startingStation;
    }

    public String getEndingStation() {
        return endingStation;
    }

    public String getStartingStation() {
        return startingStation;
    }

    public void setEndingStation(String endingStation) {
        this.endingStation = endingStation;
    }

    public void setStartingStation(String startingStation) {
        this.startingStation = startingStation;
    }
}
