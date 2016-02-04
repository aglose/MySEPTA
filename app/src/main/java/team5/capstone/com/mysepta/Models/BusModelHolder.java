package team5.capstone.com.mysepta.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 2/3/2016.
 */
public class BusModelHolder {
    public List<BusTimeModel> busScheduleList;
    private String shortName;

    public BusModelHolder() {
        busScheduleList = new ArrayList<>();
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }


    public void addBusScheduleModel(BusTimeModel busScheduleModel) {

        this.busScheduleList.add(busScheduleModel);
    }

    public List<BusTimeModel> getBusScheduleList() {
        return busScheduleList;
    }
}
