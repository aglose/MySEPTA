package team5.capstone.com.mysepta.DropDownView;

/**
 * Model to hold child for expandable list
 * Created by Kevin on 9/23/15.
 */
public class RailChooserChild {

    private String stationName;

    public RailChooserChild(String stationName){
        this.stationName = stationName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}
