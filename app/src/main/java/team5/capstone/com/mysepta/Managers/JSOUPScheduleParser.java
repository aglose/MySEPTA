package team5.capstone.com.mysepta.Managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import team5.capstone.com.mysepta.DatabaseHelpers.SubwayScheduleReaderDbHelper;

public class JSOUPScheduleParser{
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

    private final static String[] WEBSITE_LIST = {BSL_WEEKDAY_NORTHBOUND_URL, BSL_WEEKDAY_SOUTHBOUND_URL, BSL_SAT_NORTHBOUND_URL,
            BSL_SAT_SOUTHBOUND_URL, BSL_SUN_NORTHBOUND_URL, BSL_SUN_SOUTHBOUND_URL, MFL_WEEKDAY_WESTBOUND_URL,
            MFL_WEEKDAY_EASTBOUND_URL, MFL_SAT_WESTBOUND_URL, MFL_SAT_EASTBOUND_URL, MFL_SUN_WESTBOUND_URL, MFL_SUN_EASTBOUND_URL};

    private SubwayScheduleReaderDbHelper dbHelper;
    private SQLiteDatabase database;
    private ArrayList<ArrayList<String>> columnSQL;
    private ArrayList<String> SUBWAY_DATABASE_COLUMN_NAMES;
    private HashMap<String, ArrayList<String>> stopIDSchedules;
    private ArrayList<HashMap<String, ArrayList<String>>> stopIDSchedulesLIST;

    private Context context;

    public JSOUPScheduleParser(Context context){
        this.context = context;
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

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        dbHelper.onCreate(database);
    }

    public void close() {
        dbHelper.close();
    }

    private void addSchedules() {
        open();

        for (int websiteNum = 0; websiteNum < WEBSITE_LIST.length; websiteNum++) {
            HashMap<String, ArrayList<String>> localStopIDSchedules = stopIDSchedulesLIST.get(websiteNum);
            Log.d(TAG, WEBSITE_LIST[websiteNum]+"addSchedules");
            SUBWAY_DATABASE_COLUMN_NAMES = columnSQL.get(websiteNum);
            int rowLimit;
            if(WEBSITE_LIST[websiteNum].contains("MFL")){
                rowLimit = 298;
            }else{
                rowLimit = 298;
            }
            for (int row = 0; row < rowLimit; row++) {
                ArrayList<String> initialValues = new ArrayList<>();
                StringBuilder sql = new StringBuilder();
                for (int column = 0; column < SUBWAY_DATABASE_COLUMN_NAMES.size(); column++) {
                    ArrayList<String> scheduleList = localStopIDSchedules.get(SUBWAY_DATABASE_COLUMN_NAMES.get(column));
                    if(row >= scheduleList.size()){
                        break;
                    }
                    initialValues.add(scheduleList.get(row));
                }
                sql.append("INSERT");
                sql.append(" INTO ");
                sql.append(SubwayScheduleReaderDbHelper.TABLE_NAME_LIST[websiteNum]);
                sql.append('(');

                Object[] bindArgs = null;
                int size = (initialValues != null && initialValues.size() > 0)
                        ? initialValues.size() : 0;
                if (size > 0) {
                    bindArgs = new Object[size];

                    for (int i = 0; i < SUBWAY_DATABASE_COLUMN_NAMES.size(); i++) {
                        sql.append((i > 0) ? "," : "");
                        sql.append("\"");
                        sql.append(SUBWAY_DATABASE_COLUMN_NAMES.get(i));
                        sql.append("\"");
                    }
                    sql.append(')');
                    sql.append(" VALUES (");
                    for (int i = 0; i < size; i++) {
                        sql.append((i > 0) ? "," : "");
                        sql.append("\"");
                        sql.append(initialValues.get(i));
                        sql.append("\"");
                    }

                    sql.append(')');
                    sql.append(';');
//                    Log.d(TAG, sql.toString());
                    database.execSQL(sql.toString());
                } else {
                    Log.d(TAG, "ERORRRRRR");
                }

            }
        }
        close();
    }

    private void getURLAndParse() throws IOException {
        columnSQL = new ArrayList<>();
        stopIDSchedulesLIST = new ArrayList<>();
        for (int websiteNum = 0; websiteNum < WEBSITE_LIST.length; websiteNum++) {
            stopIDSchedules = new HashMap<>();
            Log.d(TAG, WEBSITE_LIST[websiteNum]);

            Document docData;
            try{
                docData = Jsoup.connect(WEBSITE_LIST[websiteNum]).ignoreHttpErrors(true).get();
            }catch(ProtocolException e){
                docData = Jsoup.connect(WEBSITE_LIST[websiteNum]).ignoreHttpErrors(true).get();
            }

            Elements blueElems = docData.select("thead .headers .bluedata");
            Elements yellowElems = docData.select("thead .headers .yellowdata");
            Elements arrivalBlueElems = docData.select("td.bluedata");
            Elements arrivalYellowElems = docData.select("td.yellowdata");

            String[] locationArray;

            if(WEBSITE_LIST[websiteNum].contains("MFL")){
                locationArray = new String[28];
            }else{
                locationArray = new String[24];
            }


            ArrayList<String> stopIDs = new ArrayList<>();
            ArrayList<String> fullStringsSTOPBlue = new ArrayList<>();
            ArrayList<String> fullStringsSTOPYellow = new ArrayList<>();

            //BLUE DATA
            int count = 0;
            for (Element element : blueElems) {
                String fullString = element.text();
                int colon = fullString.indexOf(":");

                locationArray[count] = fullString.substring(0, colon - 7);

                count += 2;

                String stopIDString = fullString.substring(++colon, fullString.length());

                StringBuilder stopIDStringBuild = new StringBuilder();
                stopIDStringBuild.append(stopIDString);
                fullStringsSTOPBlue.add(stopIDStringBuild.toString());
            }


            //YELLOW DATA
            count = 1;
            for (Element element : yellowElems) {
                String fullString = element.text();
                int colon = fullString.indexOf(":");

                locationArray[count] = fullString.substring(0, colon - 7);

                count += 2;

                String stopIDString = fullString.substring(++colon, fullString.length());

                StringBuilder stopIDStringBuild = new StringBuilder();
                stopIDStringBuild.append(stopIDString);
                fullStringsSTOPYellow.add(stopIDStringBuild.toString());
            }


//            Log.d(TAG, "Blue size : " + String.valueOf(fullStringsSTOPBlue.size()));
//            Log.d(TAG, "Yellow size : " + String.valueOf(fullStringsSTOPYellow.size()));

            //STOP ID SECTION
            int size = fullStringsSTOPYellow.size() + fullStringsSTOPBlue.size();
            int j = 0;
            int k = 0;
            for (int i = 0; i < size; i++) {
                if (i % 2 == 0) {
                    stopIDs.add(fullStringsSTOPBlue.get(j++));
                } else {
                    stopIDs.add(fullStringsSTOPYellow.get(k++));
                }
            }

            SUBWAY_DATABASE_COLUMN_NAMES = new ArrayList<>(Arrays.asList(locationArray));

            columnSQL.add(SUBWAY_DATABASE_COLUMN_NAMES);

            ArrayList<String> tempList = new ArrayList<>();

            count = 0;
            //ARRRIVAL BLUE DATA
            for (Element element : arrivalBlueElems) {
//                Log.d(TAG, "title : " + (element.attr("title")) + "\nlocationArray : " + locationArray[count] + "\nbool: " + String.valueOf(element.attr("title").trim().equalsIgnoreCase(locationArray[count])));

                if (element.attr("title").trim().equalsIgnoreCase(locationArray[count].replaceAll("\"", ""))) {
                    Elements tdElements = element.select("td");
                    for (Element elemental : tdElements) {
                        if (elemental.text().trim().equalsIgnoreCase("—")) {
                            tempList.add("X");
                        } else {
                            tempList.add(elemental.text().trim());
                        }
                    }
                    tempList.remove(0); //remove junk first td
                    stopIDSchedules.put(SUBWAY_DATABASE_COLUMN_NAMES.get(count), tempList);
//                    Log.d(TAG, "Location: " + SUBWAY_DATABASE_COLUMN_NAMES.get(count) + "\nStopId: " + stopIDs.get(count) + "\nTempList First: " +
//                            tempList.get(0) + "\nLast: " +
//                            tempList.get(tempList.size() - 1) + "\nSize : " + tempList.size());
                    count += 2;
                    tempList = new ArrayList<>();
                } else {
                    Log.d(TAG, "HEEELLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
                }
            }

            count = 1;
            //ARRRIVAL YELLOW DATA
            for (Element element : arrivalYellowElems) {
//                Log.d(TAG, "title : " + (element.attr("title")) + "\nlocationArray : " + locationArray[count] + "\nbool: " + String.valueOf(element.attr("title").trim().equalsIgnoreCase(locationArray[count])));

                if (element.attr("title").trim().equalsIgnoreCase(locationArray[count].replaceAll("\"", ""))) {
                    Elements tdElements = element.select("td");
                    for (Element elemental : tdElements) {
                        if (elemental.text().trim().equalsIgnoreCase("—")) {
                            tempList.add("X");
                        } else {
                            tempList.add(elemental.text().trim());
                        }
                    }

                    tempList.remove(0);

                    stopIDSchedules.put(SUBWAY_DATABASE_COLUMN_NAMES.get(count), tempList);
//                    Log.d(TAG, "Location: " + SUBWAY_DATABASE_COLUMN_NAMES.get(count) + "\nStopId: " + stopIDs.get(count) + "\nTempList First: " +
//                            tempList.get(0) + "\nLast: " +
//                            tempList.get(tempList.size() - 1) + "\nSize : " + tempList.size());
                    count += 2;
                    tempList = new ArrayList<>();
                } else {
                    Log.d(TAG, "HEEELLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
                }
            }
            stopIDSchedulesLIST.add(stopIDSchedules);

        }
        dbHelper = new SubwayScheduleReaderDbHelper(context, columnSQL);
        addSchedules();
    }
}
