package team5.capstone.com.mysepta.Models;

import java.sql.Time;
import java.util.Date;

/**
 * Created by Andrew on 9/20/2015.
 */
public class SubwayScheduleItemModel {
    private String formattedTimeStr;

    public SubwayScheduleItemModel(){

    }

    public SubwayScheduleItemModel(String formattedTimeStr){
        this.formattedTimeStr = formattedTimeStr;
    }

    public String getFormattedTimeStr() {
        return formattedTimeStr;
    }

    public void setFormattedTimeStr(String formattedTimeStr) {
        this.formattedTimeStr = formattedTimeStr;
    }

}
