package team5.capstone.com.mysepta.Managers;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
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

    public static void createJOUSPObject(){
        JSOUPScheduleParser j = new JSOUPScheduleParser();
    }
}

class JSOUPScheduleParser{
    private final static String TAG = "JSOUPScheduleParser";

    private final static String BSL_WEEKDAY_NORTHBOUND_URL = "http://www.septa.org/schedules/transit/w/BSL_1.htm";
    private final static String BSL_WEEKDAY_SOUTHBOUND_URL = "http://www.septa.org/schedules/transit/w/BSL_0.htm";
    private final static String BSL_SAT_NORTHBOUND_URL = "http://www.septa.org/schedules/transit/s/BSL_1.htm";
    private final static String BSL_SAT_SOUTHBOUND_URL = "http://www.septa.org/schedules/transit/s/BSL_0.htm";
    private final static String BSL_SUN_NORTHBOUND_URL = "http://www.septa.org/schedules/transit/h/BSL_1.htm";
    private final static String BSL_SUN_SOUTHBOUND_URL = "http://www.septa.org/schedules/transit/h/BSL_0.htm";

    private final static String MFL_WEEKDAY_WESTBOUND_URL = "http://www.septa.org/schedules/transit/w/MFL_1.htm";
    private final static String MFL_WEEKDAY_EASTBOUND_URL = "http://www.septa.org/schedules/transit/w/MFL_0.htm";
    private final static String MFL_SAT_WESTBOUND_URL = "http://www.septa.org/schedules/transit/s/MFL_1.htm";
    private final static String MFL_SAT_EASTBOUND_URL = "http://www.septa.org/schedules/transit/s/MFL_0.htm";
    private final static String MFL_SUN_WESTBOUND_URL = "http://www.septa.org/schedules/transit/h/MFL_1.htm";
    private final static String MFL_SUN_EASTBOUND_URL = "http://www.septa.org/schedules/transit/h/MFL_0.htm";

    public JSOUPScheduleParser(){
        Thread urlThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getURLAndParse();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        urlThread.start();
    }

    private void getURLAndParse() throws IOException {
        HashMap<String, ArrayList<String>> stopIDSchedules = new HashMap<>();

        Document docData = Jsoup.connect(BSL_WEEKDAY_NORTHBOUND_URL).get();

        Elements blueElems = docData.select("thead .headers .bluedata");
        Elements yellowElems = docData.select("thead .headers .yellowdata");
        Elements arrivalBlueElems = docData.select("td.bluedata");
        Elements arrivalYellowElems = docData.select("td.yellowdata");

        String[] locationArray = new String[28];

        ArrayList<String> stopIDs = new ArrayList<>();
        ArrayList<String> fullStringsSTOPBlue = new ArrayList<>();
        ArrayList<String> fullStringsSTOPYellow = new ArrayList<>();

        //BLUE DATA
        int count = 0;
        for(Element element: blueElems){
            String fullString = element.text();
            int colon = fullString.indexOf(":");

            locationArray[count] = fullString.substring(0, colon-7);
            count += 2;

            String stopIDString = fullString.substring(++colon, fullString.length());
            fullStringsSTOPBlue.add(stopIDString);
        }


        //YELLOW DATA
        count = 1;
        for(Element element: yellowElems){
            String fullString = element.text();
            int colon = fullString.indexOf(":");

            locationArray[count] = fullString.substring(0, colon-7);

            count += 2;

            String stopIDString = fullString.substring(++colon, fullString.length());
            fullStringsSTOPYellow.add(stopIDString);
        }


        Log.d(TAG, "Blue size : " + String.valueOf(fullStringsSTOPBlue.size()));
        Log.d(TAG, "Yellow size : " + String.valueOf(fullStringsSTOPYellow.size()));

        //STOP ID SECTION
        int size =  fullStringsSTOPYellow.size() + fullStringsSTOPBlue.size();
        int j = 0;
        int k = 0;
        for(int i=0; i<size; i++){
            if(i%2 == 0){
                stopIDs.add(fullStringsSTOPBlue.get(j++));
            }else{
                stopIDs.add(fullStringsSTOPYellow.get(k++));
            }
        }

        ArrayList<String> tempList = new ArrayList<>();

        count = 0;
        //ARRRIVAL BLUE DATA
        for(Element element: arrivalBlueElems){
            Log.d(TAG, "title : " + (element.attr("title"))+"\nlocationArray : "+locationArray[count]+"\nbool: "+String.valueOf(element.attr("title").trim().equalsIgnoreCase(locationArray[count].trim())));

            if(element.attr("title").trim().equalsIgnoreCase(locationArray[count].trim())){
                Elements tdElements = element.select("td");
                for(Element elemental: tdElements){
                    tempList.add(elemental.text().trim());
                }

                stopIDSchedules.put(stopIDs.get(count), tempList);
                Log.d(TAG, "Location: "+locationArray[count]+"\nStopId: " + stopIDs.get(count)+"\nTempList First: "+
                        tempList.get(0)+"\nLast: "+
                        tempList.get(tempList.size()-1)+"\nSize : "+tempList.size());
                count += 2;
                tempList = new ArrayList<>();
            }else{
                Log.d(TAG, "HEEELLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
            }
        }

        count = 1;
        //ARRRIVAL YELLOW DATA
        for(Element element: arrivalYellowElems){
            Log.d(TAG, "title : " + (element.attr("title"))+"\nlocationArray : "+locationArray[count]+"\nbool: "+String.valueOf(element.attr("title").trim().equalsIgnoreCase(locationArray[count].trim())));

            if(element.attr("title").trim().equalsIgnoreCase(locationArray[count].trim())){
                Elements tdElements = element.select("td");
                for(Element elemental: tdElements){
                    tempList.add(elemental.text().trim());
                }

                stopIDSchedules.put(stopIDs.get(count), tempList);
                Log.d(TAG, "Location: "+locationArray[count]+"\nStopId: " + stopIDs.get(count)+"\nTempList First: "+
                        tempList.get(0)+"\nLast: "+
                        tempList.get(tempList.size()-1)+"\nSize : "+tempList.size());
                count += 2;
                tempList = new ArrayList<>();
            }else{
                Log.d(TAG, "HEEELLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
            }
        }
    }
}
