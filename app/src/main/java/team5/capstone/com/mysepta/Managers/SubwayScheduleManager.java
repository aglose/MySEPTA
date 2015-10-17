package team5.capstone.com.mysepta.Managers;

import android.content.Context;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import team5.capstone.com.mysepta.Models.SubwayScheduleItemModel;

/**
 * Created by Andrew on 10/1/2015.
 */
public class SubwayScheduleManager {

    public static ArrayList<SubwayScheduleItemModel> parseResponseString(String responseString){
        ArrayList<SubwayScheduleItemModel> returnedList = new ArrayList<>();

        for(int i=0; i < responseString.length(); i++){
            char letter = responseString.charAt(i);
            if(letter == '@'){
                String arrival = responseString.substring(i+1, i+7);
                SubwayScheduleItemModel newArrival = new SubwayScheduleItemModel();
                newArrival.setTime(arrival);
                returnedList.add(newArrival);
                i += 6;
            }
        }
        return returnedList;
    }

    public static void createJOUSPObject(Context context){
        JSOUPScheduleParser j = new JSOUPScheduleParser(context);
    }
}

